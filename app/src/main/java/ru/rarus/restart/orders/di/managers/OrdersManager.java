package ru.rarus.restart.orders.di.managers;


import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.cache.OrdersCache;
import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;
import ru.rarus.restart.orders.data.entities.Cancel;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.data.entities.OrderAddress;
import ru.rarus.restart.orders.data.entities.OrderDistance;
import ru.rarus.restart.orders.data.events.EventDrawer;
import ru.rarus.restart.orders.data.events.EventProgressListOrder;
import ru.rarus.restart.orders.data.retrofit.RestAPI;
import ru.rarus.restart.orders.data.retrofit.dto.BaseResponse;
import ru.rarus.restart.orders.data.retrofit.dto.CancelRestResponse;
import ru.rarus.restart.orders.data.retrofit.dto.ActionRequest;
import ru.rarus.restart.orders.data.retrofit.dto.OrderRestResponse;
import ru.rarus.restart.orders.data.events.EventUpdateOrderList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static ru.rarus.restart.orders.data.entities.Operation.Action.ACTION_ORDER_ATTACH;
import static ru.rarus.restart.orders.data.entities.Operation.Action.ACTION_ORDER_CANCELED;
import static ru.rarus.restart.orders.data.entities.Operation.Action.ACTION_ORDER_DETACH;
import static ru.rarus.restart.orders.data.entities.Operation.Action.ACTION_ORDER_SUCCESS;


public class OrdersManager extends BaseManager {

    private RestAPI restAPI;
    private ErrorsManager errorsManager;
    private GEOManager geoManager;
    private UserManager mUserManager;
    private OrdersCache ordersCache;
    private HistoryManager historyManager;


    private final static int INDEX = 0;
    private final static int REGION = 1;
    private final static int AREA = 2;
    private final static int CITY = 3;
    private final static int LOCALITY = 4;
    private final static int STREET = 5;
    private final static int HOUSE = 6;
    private final static int HOUSING = 7;
    private final static int APARTMENT = 8;
    private final static int PORCH = 9;
    private final static int FLOOR = 10;
    private final static int DOOR_CODE = 11;
    private final static int SUBWAY = 12;
    private final static int ZONE = 13;


    public OrdersManager(Context context, RestAPI restAPI, DataBaseHelper dataBaseHelper, ErrorsManager errorsManager, RxBus mRxBus, GEOManager geoManager, UserManager userManager, OrdersCache ordersCache, HistoryManager historyManager) {
        super(context, dataBaseHelper, mRxBus);
        this.restAPI = restAPI;
        this.errorsManager = errorsManager;
        this.geoManager = geoManager;
        this.mUserManager = userManager;
        this.ordersCache = ordersCache;
        this.historyManager = historyManager;

    }


    public Observable<List<Order>> getCloseOrdersFromDb() {
        return Observable.defer(() -> Observable.just(ordersCache.getSuccessOrders()));
    }

    public Observable<List<Order>> getCloseOrdersFromServer() {

        return restAPI.getCloseOrders(mUserManager.getUser().getId())
                .flatMap(new Func1<OrderRestResponse, Observable<OrderRestResponse>>() {
                    @Override
                    public Observable<OrderRestResponse> call(OrderRestResponse response) {
                        if (response.getCode() == 0) return Observable.just(response);
                        else return Observable.error(new Throwable(response.getMessage()));
                    }
                })
                .map(OrderRestResponse::getOrders)
                .map(this::parseOrderAddress)
                .map(list -> ordersCache.saveListSuccessOrders(list))
                .onErrorResumeNext(throwable -> {
                    ErrorsManager.Error error = errorsManager.getError(throwable);
                    if (error.code == 3)
                        return Observable.just(ordersCache.saveListSuccessOrders(new ArrayList<>()));
                    if (error.code == 800)
                        return Observable.just(ordersCache.getSuccessOrders());
                    if (error.code == 101) return getCloseOrdersFromServer();
                    else return Observable.error(new Throwable(error.description));

                });


    }

    public Observable<List<Order>> getOrders(boolean isFree, boolean update) {

        return Observable.just(true)
                .flatMap(orders -> {
                    if (update) return loadFromServer(isFree);
                    List<Order> list = isFree ? ordersCache.getFreeOrders() : ordersCache.getMyOrders();
                    if (list.size() == 0) return loadFromServer(isFree);
                    return Observable.just(list);
                })
//                .onErrorResumeNext(throwable -> {
//                    ErrorsManager.Error error = errorsManager.getError(throwable);
//                    if (error.code == 3) {
//                        return Observable.just(ordersCache.saveListOrder(new ArrayList<>(), isFree));
//                    }
//                    if (error.code == 101) return getOrders(isFree, update);
//                    else return Observable.error(new Throwable(error.description));
//
//                })
                .doOnNext(list -> {
                    if (!isFree) mRxBus.send(new EventDrawer(list.size()));
                });
    }

    private Observable<List<Order>> loadFromServer(boolean isFree) {

        mRxBus.send(new EventProgressListOrder());

        return restAPI.getOrders()
                .flatMap(new Func1<OrderRestResponse, Observable<OrderRestResponse>>() {
                    @Override
                    public Observable<OrderRestResponse> call(OrderRestResponse response) {
                        if (response.getCode() == 0) return Observable.just(response);
                        else return Observable.error(new Throwable(response.getMessage()));
                    }
                })
                .flatMap(response -> Observable.from(response.getOrders()))
                .filter(order -> !order.isPaidDelivery())
                .toList()
                .map(this::parseOrderAddress)
                .map(geoManager::initGeoData)
                .map(list -> ordersCache.saveListOrder(list, isFree))
                .doOnNext(list -> {
                    if (!isFree) mRxBus.send(new EventDrawer(list.size()));
                })
                .doOnCompleted(() -> {
                    if (isFree) geoManager.updateDistanceListFreeOrders().subscribe(r -> {
                    }, e -> {
                    });
                })
                .doOnCompleted(() -> {
                    if (!isFree) geoManager.updateDistanceMy().subscribe(r -> {
                    }, e -> {
                    });
                });
    }

    public Observable<Order> getOrder(String idOrder) {
        return Observable.just(true)
                .map(r -> ordersCache.getOrderFromDB(idOrder))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> completeOrder(Order order) {
        return cancelOrCompleteOrder(order, "", "succes")
                .doOnNext(res -> historyManager.createOrderOperation(order, ACTION_ORDER_SUCCESS))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> cancelOrder(Order order, String idCancel) {
        return cancelOrCompleteOrder(order, idCancel, "cancel")
                .doOnNext(res -> historyManager.createOrderOperation(order, ACTION_ORDER_CANCELED))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Boolean> cancelOrCompleteOrder(Order order, String idCancel, String action) {
        return restAPI.cancelOrder(new ActionRequest(order.getId(), "", action, idCancel))
                .flatMap(new Func1<BaseResponse, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 0) {
                            if (action.equals("succes")) ordersCache.saveCloseOrderBD(order);
                            if (action.equals("cancel")) ordersCache.removeOrderBD(order.getId());
                            mRxBus.send(new EventUpdateOrderList());
                            return Observable.just(true);
                        } else return Observable.error(new Throwable(baseResponse.getMessage()));
                    }
                })
                .onErrorResumeNext(throwable -> {
                    ErrorsManager.Error error = errorsManager.getError(throwable);
                    if (error.code == 101) return cancelOrder(order, idCancel);
                    else return Observable.error(new Throwable(error.description));
                });
    }


    public Observable<Boolean> attachOrder(Order order) {
        return actionsOrder(order, "set")
                .doOnNext(res -> historyManager.createOrderOperation(order, ACTION_ORDER_ATTACH))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> detachOrder(Order order) {
        return actionsOrder(order, "clear")
                .doOnNext(res -> historyManager.createOrderOperation(order, ACTION_ORDER_DETACH))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Boolean> actionsOrder(Order order, String action) {
        return restAPI.postCourier(new ActionRequest(order.getId(), action, "", ""))
                .flatMap(new Func1<BaseResponse, Observable<Order>>() {
                    @Override
                    public Observable<Order> call(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 0) {
                            Order ord = ordersCache.getOrderFromDB(order.getId());
                            if (action.equals("set")) ord.setMy(false);
                            if (action.equals("clear")) ord.setMy(true);

                            ordersCache.saveOrderBD(ord);
                            ord.getLocation().clearDistance();
                            ordersCache.saveLocation(ord.getLocation());

                            return Observable.just(ord);
                        } else return Observable.error(new Throwable(baseResponse.getMessage()));
                    }
                })
                .doOnNext(o -> {
                    if (o.isFree()) geoManager.updateDistanceListFreeOrders().subscribe();
                    else geoManager.updateDistanceMy().subscribe();
                })
                .doOnNext(r -> mRxBus.send(new EventUpdateOrderList()))
                .map(r -> true)
                .onErrorResumeNext(throwable -> {
                    ErrorsManager.Error error = errorsManager.getError(throwable);
                    if (error.code == 101) return actionsOrder(order, action);
                    else return Observable.error(new Throwable(error.description));

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<List<Cancel>> getCancelItems() {
        return restAPI.getCancels()
                .flatMap(new Func1<CancelRestResponse, Observable<List<Cancel>>>() {
                    @Override
                    public Observable<List<Cancel>> call(CancelRestResponse response) {
                        if (response.getCode() == 0) {

                            return Observable.just(response.getCancel());
                        } else return Observable.error(new Throwable(response.getMessage()));
                    }
                })
                .onErrorResumeNext(throwable -> {
                    ErrorsManager.Error error = errorsManager.getError(throwable);
                    if (error.code == 101) return getCancelItems();
                    else return Observable.error(new Throwable(error.description));

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private List<Order> parseOrderAddress(List<Order> orders) {
        for (Order order : orders) {
            OrderAddress orderAddress = new OrderAddress();
            orderAddress.setOrderId(order.getId());

            String string = order.getDeliveryAddress();
            if (!string.isEmpty()) {
                string += " ";

                try {
                    Pattern pattern = Pattern.compile(",");
                    String[] address = pattern.split(string);

//                    orderAddress.setIndex(address[INDEX]);
//                    orderAddress.setRegion(address[REGION]);
//                    orderAddress.setArea(address[AREA]);
//                    orderAddress.setCity(address[CITY]);
//                    orderAddress.setLocality(address[LOCALITY]);
//                    orderAddress.setStreet(address[STREET]);
//                    orderAddress.setHouse(address[HOUSE]);
//                    orderAddress.setHousing(address[HOUSING]);
//                    orderAddress.setApartment(address[APARTMENT]);
//                    orderAddress.setPorch(address[PORCH]);
//                    orderAddress.setFloor(address[FLOOR]);
//                    orderAddress.setDoorCode(address[DOOR_CODE]);
//                    orderAddress.setSubway(address[SUBWAY]);
//                    orderAddress.setZone(address[ZONE]);
                    order.setOrderAddress(orderAddress);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return orders;
    }


    public Observable<List<Order>> getMyOrdersFromDB() {
        return Observable.defer(() -> Observable.just(ordersCache.getMyOrders()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<List<Order>> getFreeOrdersFromDB() {
        return Observable.defer(() -> Observable.just(ordersCache.getFreeOrders()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public void setNewSortMyOrders(List<String> list) {
        int i = 0;
        for (String c : list) {
            OrderDistance item = ordersCache.getLocationByIdOrder(c);
            if (item != null) {
                item.setIndex(i);
                ordersCache.saveLocation(item);
            }
            i++;
        }
    }

    public Observable<Integer> createNewOrder(String name, Date date, String address, String number) {
        Order order = new Order();
        return Observable.just(order)
                .map(order1 -> {
                    order.setId(String.valueOf(UUID.randomUUID()));
                    order.setMy(true);
                    order.setDeliveryFirstName(name);
                    order.setDateDelivery(date);
                    order.setDeliveryAddress(address);
                    order.setDeliveryPhone(number);
                    order.setStatus(Order.STATUS.IN_WORK);
                    order.setStatusPay(Order.STATUS_PAY.NOT_PAY);
                    return  ordersCache.saveOrderBD(order1);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

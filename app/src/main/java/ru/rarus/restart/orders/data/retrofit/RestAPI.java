package ru.rarus.restart.orders.data.retrofit;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.rarus.restart.orders.data.entities.DiscCard;
import ru.rarus.restart.orders.data.entities.Guest;
import ru.rarus.restart.orders.data.entities.Hall;
import ru.rarus.restart.orders.data.entities.OrderRow;
import ru.rarus.restart.orders.data.entities.Table;
import ru.rarus.restart.orders.data.retrofit.dto.BaseResponse;
import ru.rarus.restart.orders.data.retrofit.dto.CancelRestResponse;
import ru.rarus.restart.orders.data.retrofit.dto.ActionRequest;
import ru.rarus.restart.orders.data.retrofit.dto.MenuItemRestResponse;
import ru.rarus.restart.orders.data.retrofit.dto.MenuRestResponse;
import ru.rarus.restart.orders.data.retrofit.dto.OrderRestResponse;
import ru.rarus.restart.orders.data.retrofit.dto.SignInRequest;
import ru.rarus.restart.orders.data.retrofit.dto.SignInRestResponse;
import ru.rarus.restart.orders.data.retrofit.dto.UserRestResponse;
import rx.Observable;

public interface RestAPI {


    /**
     *Авторизация
     */
    @POST("/demo/hs/exchangemobile/sign_in")
    Observable<SignInRestResponse> signIn(@Header("password") String pass,
                                          @Header("device_name") String device_name);


    /**
     *Выход
     */
    @POST("/demo/hs/exchangemobile/sign_out")
    Observable<BaseResponse> signOut(@Header("X-Auth-Token") String tok);


    /**
     * Получить виды меню
     */
    @GET("/menu")
    Observable<MenuRestResponse> getKindsOfMenu();

    /**
     * Получить состав меню
     */
    @GET("/menu/{id}/items")
    Observable<MenuItemRestResponse> getMenuItems(@Path("id") String id);

    /**
     * Получить гостей
     */
    @GET("/guest")
    Observable<List<Guest>> getGuests();

    /**
     * Добавить нового гостя
     */
    @POST("/guest/add")
    Observable addGuest();

    /**
     * Добавить/удалить гостя в черный список
     */
    @POST("/guest/{id}/blacklist")
    Observable addOrDeleteGuestToBlackList(@Path("id") String id);

    /**
     * Получить дисконтные карты
     */
    @GET("/discountcard")
    Observable<List<DiscCard>> getDiscCard();

    /**
     * Добавить дисконтную карту
     */
    @POST("/discountcard/add")
    Observable addDiscCard();

    /**
     * Получить заказы
     */
//    @GET("/order?page=1&per_page=10")
//    Observable<OrderRestResponse> getOrders();
    @GET("/base/hs/exchangemobile/request")
    Observable<OrderRestResponse> getOrders();

    /**
     * Получить заказы
     */
    @GET("/order/orders?items=true")
    Observable<OrderRestResponse> getOrders(@Query("freeorder") boolean free);

    /**
     * Получить закрытые заказы
     */
    @GET("/order?delivery_status=successfully&items=true")
    Observable<OrderRestResponse> getCloseOrders(@Query("courier_id") String idCourier);

    /**
     * Получить строки заказа
     */
    @GET("/order/{id}/items")
    Observable<List<OrderRow>> getOrderRow(@Path("id") String id);

    /**
     * Добавить заказ
     */
    @POST("/order/add")
    Observable addOrder();

    /**
     * Получить залы
     */
    @GET("/area")
    Observable<List<Hall>> getHalls();

    /**
     * Получить столы
     */
    @GET("/object")
    Observable<List<Table>> getTables();

    /**
     * Получить причины отмен и списаний.
     */
    @GET("/cancel")
    Observable<CancelRestResponse> getCancels();

    /**
     * Получить права и информацию о пользователе.
     */
    @GET("/user")
    Observable<UserRestResponse> getUser();


    /**
     * Получить заказ
     */
    @POST("order/orders/courier")
    Observable<BaseResponse> postCourier(@Body ActionRequest request);

    /**
     * Завершить заказ
     */
    @POST("order/orders/delivstatus")
    Observable<BaseResponse> completeOrder(@Body ActionRequest request);

    /**
     * Отменить заказ
     */
    @POST("order/orders/delivstatus")
    Observable<BaseResponse> cancelOrder(@Body ActionRequest request);




}

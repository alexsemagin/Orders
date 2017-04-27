package ru.rarus.restart.orders.logic.location.geocode;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.data.entities.OrderAddress;
import ru.rarus.restart.orders.data.entities.OrderDistance;



public class GeocodeService {

    private final static String LOG = "GeocodeService";

    public final static int INDEX = 0;
    public final static int REGION = 1;
    public final static int AREA = 2;
    public final static int CITY = 3;
    public final static int LOCALITY = 4;
    public final static int STREET = 5;
    public final static int HOUSE = 6;
    public final static int HOUSING = 7;
    public final static int APARTMENT = 8;
    public final static int PORCH = 9;
    public final static int FLOOR = 10;
    public final static int DOOR_CODE = 11;
    public final static int SUBWAY = 12;
    public final static int ZONE = 13;

    private Context context;

    public GeocodeService(Context context) {
        this.context = context;
    }

    public OrderDistance createFromAddress(OrderAddress orderAddress) {
        if (orderAddress == null) {
            return null;
        }
        String address = orderAddress.getCity() + " " + orderAddress.getStreet() + " " + orderAddress.getHouse();
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> list = geocoder.getFromLocationName(address, 1);
            if (list.isEmpty()) {
                return null;
            }
            OrderDistance coordinates = new OrderDistance();
            coordinates.setDestinationLat(list.get(0).getLatitude());
            coordinates.setDestinationLng(list.get(0).getLongitude());

            Log.d(LOG, "Geocoding result: address " + address + " lat " +
                    coordinates.getDestinationLat() + " lng " + coordinates.getDestinationLng());
            return coordinates;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void handleList(List<Order> orders) {
        for (Order order : orders) {
            OrderDistance fromAddress = createFromAddress(order.getOrderAddress());
            if (fromAddress != null) {
                fromAddress.setOrderId(order.getId());
                order.setLocation(fromAddress);
            }
        }
    }

    public void parseOrderAddress(List<Order> orders) {
        for (Order order : orders) {
            OrderAddress orderAddress = new OrderAddress();
            orderAddress.setOrderId(order.getId());
            Log.d(LOG, order.getDeliveryAddress());

            String string = order.getDeliveryAddress().replaceAll(",", " ,");
            string += " ";

            try {
                Pattern pattern = Pattern.compile(",");
                String[] address = pattern.split(string);

                orderAddress.setIndex(address[INDEX]);
                orderAddress.setRegion(address[REGION]);
                orderAddress.setArea(address[AREA]);
                orderAddress.setCity(address[CITY]);
                orderAddress.setLocality(address[LOCALITY]);
                orderAddress.setStreet(address[STREET]);
                orderAddress.setHouse(address[HOUSE]);
                orderAddress.setHousing(address[HOUSING]);
                orderAddress.setApartment(address[APARTMENT]);
                orderAddress.setPorch(address[PORCH]);
                orderAddress.setFloor(address[FLOOR]);
                orderAddress.setDoorCode(address[DOOR_CODE]);
                orderAddress.setSubway(address[SUBWAY]);
                orderAddress.setZone(address[ZONE]);
                order.setOrderAddress(orderAddress);

                Log.d(LOG, order.getOrderAddress().getHouse() + " " +
                        order.getOrderAddress().getStreet() + " " +
                        order.getOrderAddress().getCity());
            } catch (Exception e) {
                Log.d(LOG, e.getMessage());
            }
        }
    }

    public OrderDistance geocode(Order order) {
        if (order.getOrderAddress()!= null) {
            String address = order.getOrderAddress().getCity() + " "
                    + order.getOrderAddress().getStreet() + " "
                    + order.getOrderAddress().getHouse();
            Geocoder geocoder = new Geocoder(context);
            try {
                List<Address> list = geocoder.getFromLocationName(address, 1);
                if (list.isEmpty()) {
                    return null;
                }
                OrderDistance orderDistance = new OrderDistance();
                orderDistance.setOrderId(order.getId());
                orderDistance.setDestinationLat(list.get(0).getLatitude());
                orderDistance.setDestinationLng(list.get(0).getLongitude());
                order.setLocation(orderDistance);

                Log.d(LOG, "Geocoding result: address " + address + " lat " +
                        orderDistance.getDestinationLat() + " lng " + orderDistance.getDestinationLng());
                return orderDistance;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

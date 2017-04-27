package ru.rarus.restart.orders.logic.location.distance;

import ru.rarus.restart.orders.data.entities.OrderDistance;



public interface OrderDistanceStrategy {

    int FIRST_ELEMENT = 0;

    OrderDistance handle(double currentLat, double currentLng, OrderDistance orderDistance);
}

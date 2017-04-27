package ru.rarus.restart.orders.data.retrofit.dto;


public class ActionRequest {
    private String order_id ;
    private String action;
    private String delivstatus;
    private String cancel_id;

    public ActionRequest(String order_id, String action, String delivStatus, String idCancel) {
        this.order_id = order_id;
        this.action = action;
        this.delivstatus = delivStatus;
        this.cancel_id = idCancel;

    }



}

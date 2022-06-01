package com.example.marketkurly_clone.src.user.model;

import lombok.Data;

import java.util.List;

@Data
public class PostUserPaymentReq {
    private int user_idx;
    private String product_price;
    private String delivery_fee;
    private String product_discount;
    private String coupon_discount;
    private String use_point;
    private String payment_fee;
    private String earn_point;
    private String pay_method;
    private String orderer;
    private String sender;
    private String recevied_name;
    private String recipient_phone;
    private String deliver_method;
    private String address;
    private String pickup_location;
    private String entrance_method;
    private String packaging_method;
    private String notify_time;
    private String non_release;


    private List<paydetailproduct> orderList;
}

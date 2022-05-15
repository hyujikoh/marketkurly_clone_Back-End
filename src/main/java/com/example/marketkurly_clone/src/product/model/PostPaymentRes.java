package com.example.marketkurly_clone.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostPaymentRes {
    private int idx;
    //private String status;
    private int productIdx;
    private int buyerIdx;
    private int safetyTax;
    private int point;
    private int totalPaymentAmount;
    private int paymentMethod;
    private int transactionMethod;
    private String address;
    //private int sellerIdx;
}

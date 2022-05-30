package com.example.marketkurly_clone.src.user.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Value;

@Data
public class PatchEditCartProductCountReq {
    private int user_idx;
    private int product_idx;
    private int product_detail_idx;
    private int is_type;


}

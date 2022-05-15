package com.example.marketkurly_clone.src.product;
import com.example.marketkurly_clone.src.product.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.*;


@Mapper
public interface ProductMapper {
     public List<GetProductSearchRes> getProductSearchRes(String keyword);

}

package com.design.pension_system.sys.service;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;

public interface OrderService {

    int insertOrder(HashMap param);

    int updateOrder(HashMap param);

    int deleteOrder(String wid);

    PageInfo<HashMap> OrderListByUser(HashMap params);

    HashMap OrderDetails(String wid);
}

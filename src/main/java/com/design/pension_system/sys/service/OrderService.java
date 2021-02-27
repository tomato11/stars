package com.design.pension_system.sys.service;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;

public interface OrderService {

    int insertOrder(HashMap param);

    int updateOrder(HashMap param);

    int deleteOrder(String wid);

    PageInfo<HashMap> OrderListByUser(HashMap params);

    HashMap OrderDetails(String wid);

    List<HashMap> orderProgress();
}

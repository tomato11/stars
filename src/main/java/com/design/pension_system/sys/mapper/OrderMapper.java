package com.design.pension_system.sys.mapper;

import java.util.HashMap;
import java.util.List;

public interface OrderMapper {
    int insertOrder(HashMap param);

    int updateOrder(HashMap param);

    int deleteOrder(String wid);

  

    HashMap OrderDetails(String wid);


    List<HashMap> OrderListByUser(HashMap params);


    List<HashMap> orderProgress();

    String queryUserTypeByWid(String userWid);

    List<HashMap> queryOrderThree(HashMap params);

    List<HashMap> OrderListByRoom(HashMap params);

    List<HashMap> OrderListByActivity(HashMap params);

    List<HashMap> queryOrderThreeByService(HashMap params);
}

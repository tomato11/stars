package com.design.pension_system.sys.mapper;

import java.util.HashMap;
import java.util.List;

public interface ElderlyMapper {
    int insertElderly(HashMap param);

    int updateElderly(HashMap param);

    int deleteElderly(String wid);

  

    HashMap ElderlyDetails(String wid);


    List<HashMap> ElderlyListByUser(HashMap params);


}

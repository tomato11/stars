package com.design.pension_system.sys.service;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;

public interface ElderlyService {

    int insertElderly(HashMap param);

    int updateElderly(HashMap param);

    int deleteElderly(String wid);

    PageInfo<HashMap> ElderlyListByUser(HashMap params);

    HashMap ElderlyDetails(String wid);
}

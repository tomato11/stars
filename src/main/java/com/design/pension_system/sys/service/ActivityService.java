package com.design.pension_system.sys.service;

import com.github.pagehelper.PageInfo;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public interface ActivityService {

    int insertActivity(HashMap param);

    int updateActivity(HashMap param);

    int deleteActivity(String wid);

    PageInfo<HashMap> ActivityListByAdmin(HashMap params);

    PageInfo<HashMap> ActivityListByUser(HashMap params);

    HashMap ActivityDetails(String wid);

}

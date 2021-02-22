package com.design.pension_system.sys.service.impl;

import com.design.pension_system.sys.mapper.ActivityMapper;
import com.design.pension_system.sys.mapper.ActivityMapper;
import com.design.pension_system.sys.mapper.SignMapper;
import com.design.pension_system.sys.service.ActivityService;
import com.design.pension_system.sys.service.ObjectService;
import com.design.pension_system.sys.service.SignService;
import com.design.pension_system.sys.util.HmServiceUtil;
import com.design.pension_system.sys.util.MsgUtils;
import com.dtflys.forest.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper ActivityMapper;


    @Autowired
    private ObjectService objectService;

    private String activityPhoneId = "04";

    @Override
    public int insertActivity(HashMap param) {
        int i = ActivityMapper.insertActivity(param);
        String wid = (String) param.get("wid");
        objectService.savePhoto((List<HashMap>) param.get("activityPhoto"), wid,activityPhoneId);
        return i;
    }

    @Override
    public int updateActivity(HashMap param) {
        int i = ActivityMapper.updateActivity(param);
        objectService.savePhoto((List<HashMap>) param.get("activityPhoto"), (String) param.get("wid"),activityPhoneId);
        return i;
    }

    @Override
    public int deleteActivity(String wid) {
        int i = ActivityMapper.deleteActivity(wid);
        objectService.deletePhoto(wid,activityPhoneId);
        return i;
    }

    @Override
    public PageInfo<HashMap> ActivityListByAdmin(HashMap params) {
        HmServiceUtil.checkPageParams(params);
        PageHelper.startPage(params);
        List<HashMap> hashMaps = ActivityMapper.ActivityListByAdmin(params);
        return new PageInfo<HashMap>(hashMaps);

    }

    @Override
    public HashMap ActivityDetails(String wid) {

        HashMap map = ActivityMapper.ActivityDetails(wid);
        List<HashMap> hashMaps = objectService.queryPhotoByMainId(wid, activityPhoneId);
        map.put("activityPhoto",hashMaps);
        return map;
    }

    @Override
    public PageInfo<HashMap> ActivityListByUser(HashMap params) {
        HmServiceUtil.checkPageParams(params);
        PageHelper.startPage(params);
        List<HashMap> hashMaps = ActivityMapper.ActivityListByUser(params);
        if(null!=hashMaps&&hashMaps.size()>0){
            for (int i = 0; i < hashMaps.size(); i++) {
                HashMap hashMap = hashMaps.get(i);
                List<HashMap> photoList = objectService.queryPhotoByMainId((String) hashMap.get("wid"), activityPhoneId);
                hashMap.put("activityPhoto",photoList);
            }
        }
        return new PageInfo<HashMap>(hashMaps);
    }
}
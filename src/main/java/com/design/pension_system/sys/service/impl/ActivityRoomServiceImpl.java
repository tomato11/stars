package com.design.pension_system.sys.service.impl;

import com.design.pension_system.sys.mapper.ActivityRoomMapper;
import com.design.pension_system.sys.mapper.ObjectMapper;
import com.design.pension_system.sys.mapper.SignMapper;
import com.design.pension_system.sys.service.ActivityRoomService;
import com.design.pension_system.sys.service.ObjectService;
import com.design.pension_system.sys.service.SignService;
import com.design.pension_system.sys.util.DateUtil;
import com.design.pension_system.sys.util.HmServiceUtil;
import com.design.pension_system.sys.util.MsgUtils;
import com.dtflys.forest.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class ActivityRoomServiceImpl implements ActivityRoomService {
    @Autowired
    private ActivityRoomMapper activityRoomMapper;


    @Autowired
    private ObjectService objectService;

    private String activityRoomPhotoId = "03";

    @Override
    public int insertActivityRoom(HashMap param) {
        int i = activityRoomMapper.insertActivityRoom(param);
        String wid = (String) param.get("wid");
        objectService.savePhoto((List<HashMap>) param.get("activityRoomPhoto"), wid,activityRoomPhotoId);
        return i;
    }

    @Override
    public int updateActivityRoom(HashMap param) {
        int i = activityRoomMapper.updateActivityRoom(param);
        objectService.savePhoto((List<HashMap>) param.get("activityRoomPhoto"), (String) param.get("wid"),activityRoomPhotoId);
        return i;
    }

    @Override
    public int deleteActivityRoom(String wid) {
        int i = activityRoomMapper.deleteActivityRoom(wid);
        objectService.deletePhoto(wid,activityRoomPhotoId);
        return i;
    }

    @Override
    public PageInfo<HashMap> activityRoomList(HashMap params) {
        HmServiceUtil.checkPageParams(params);
        PageHelper.startPage(params);
        List<HashMap> hashMaps = activityRoomMapper.activityRoomList(params);
        if(null!=hashMaps&&hashMaps.size()>0){
            for (int i = 0; i < hashMaps.size(); i++) {
                HashMap hashMap = hashMaps.get(i);
                List<HashMap> photoList = objectService.queryPhotoByMainId((String) hashMap.get("wid"), activityRoomPhotoId);
                hashMap.put("activityRoomPhoto",photoList);
            }
        }
        return new PageInfo<HashMap>(hashMaps);

    }

    @Override
    public HashMap activityRoomDetails(String wid) {
        HashMap map = activityRoomMapper.activityRoomDetails(wid);
        List<HashMap> hashMaps = objectService.queryPhotoByMainId(wid,activityRoomPhotoId);
        map.put("activityRoomPhoto",hashMaps);
        return map;
    }


}
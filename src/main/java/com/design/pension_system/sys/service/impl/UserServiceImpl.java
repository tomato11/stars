package com.design.pension_system.sys.service.impl;

import com.design.pension_system.sys.mapper.SignMapper;
import com.design.pension_system.sys.mapper.UserMapper;
import com.design.pension_system.sys.service.ObjectService;
import com.design.pension_system.sys.service.SignService;
import com.design.pension_system.sys.service.UserService;
import com.design.pension_system.sys.util.HmServiceUtil;
import com.design.pension_system.sys.util.MsgUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    ObjectService objectService;
    private String userPhoneId = "01";//用户照片
    private String userQualificationId = "02";//证件照

    @Override
    public List<HashMap> getBusinessByType(HashMap params) {
        return userMapper.getBusinessByType(params);
    }

    @Override
    public PageInfo<HashMap> userList(HashMap params) {
        HmServiceUtil.checkPageParams(params);
        PageHelper.startPage(params);
        List<HashMap> hashMaps = userMapper.userList(params);
        return new PageInfo<HashMap>(hashMaps);
    }


    @Override
    public int updateUser(HashMap param) {
        int i = userMapper.updateUser(param);
        objectService.savePhoto((List<HashMap>) param.get("userPhoto"), (String) param.get("wid"), userPhoneId);
        objectService.savePhoto((List<HashMap>) param.get("qualificationPhoto"), (String) param.get("wid"), userQualificationId);
        return i;
    }



    @Override
    public HashMap userDetils(String wid) {
        HashMap result = new HashMap();
        result = userMapper.queryUserDetils(wid);
        String loginId = String.valueOf(result.get("loginId"));
        HashMap typeAndWid = userMapper.queryTypeAndWidByLoginId(loginId);

        if ("1".equals(typeAndWid.get("type"))) {//用户
            List<HashMap> photoList = objectService.queryPhotoByMainId((String) typeAndWid.get("wid"), userPhoneId);
            result.put("userPhoto", photoList);
        } else {//专业人员
            List<HashMap> photoList = objectService.queryPhotoByMainId((String) typeAndWid.get("wid"), userPhoneId);
            List<HashMap> userQualificationList = objectService.queryPhotoByMainId((String) typeAndWid.get("wid"), userQualificationId);
            result.put("userPhoto", photoList);
            result.put("qualificationPhone", userQualificationList);
        }
        return result;
    }

    @Override
    public List<HashMap> evaluateList(HashMap params) {
        List<HashMap> hashMaps = new ArrayList<>();
        if (null != params.get("type") && !"".equals(params.get("type"))) {
            hashMaps = userMapper.evaluateList(params);
        } else {//全部
            params.put("type", "2");
            List<HashMap> hashMaps1 = userMapper.evaluateList(params);
            params.put("type", "3");
            List<HashMap> hashMaps2 = userMapper.evaluateList(params);
            params.put("type", "4");
            List<HashMap> hashMaps3 = userMapper.evaluateList(params);
            hashMaps.addAll(hashMaps1);
            hashMaps.addAll(hashMaps2);
            hashMaps.addAll(hashMaps3);
        }
        if (null != hashMaps && hashMaps.size() > 0) {
            for (int i = 0; i < hashMaps.size(); i++) {
                HashMap hashMap = hashMaps.get(i);
                List<HashMap> photoList = objectService.queryPhotoByMainId((String) hashMap.get("wid"), userPhoneId);
                hashMap.put("userPhoto", photoList);
            }
        }
        return hashMaps;
    }

    @Override
    public PageInfo<HashMap> businessList(HashMap params) {
        HmServiceUtil.checkPageParams(params);
        PageHelper.startPage(params);
        List<HashMap> hashMaps = userMapper.businessList(params);

        if(null!=hashMaps&&hashMaps.size()>0){
            for (int i = 0; i < hashMaps.size(); i++) {
                HashMap hashMap = hashMaps.get(i);
                List<HashMap> photoList = objectService.queryPhotoByMainId((String) hashMap.get("wid"), userPhoneId);
                hashMap.put("userPhoto",photoList);
            }
        }
        return new PageInfo<HashMap>(hashMaps);
    }

    @Override
    public List<HashMap> getMenuByLoginId(HashMap params) {
        return userMapper.getMenuByLoginId(params);
    }
}
package com.design.pension_system.sys.service.impl;

import com.design.pension_system.sys.service.ForumService;
import com.design.pension_system.sys.service.ObjectService;
import com.design.pension_system.sys.util.HmServiceUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ForumServiceImpl implements ForumService {
    @Autowired
    private com.design.pension_system.sys.mapper.ForumMapper forumMapper;

    @Autowired
    private ObjectService objectService;

    private String forumId = "06";
    private String userPhoneId = "01";//用户照片
    @Override
    public int insertForum(HashMap param) {
        int i = forumMapper.insertForum(param);
        String wid = (String) param.get("wid");
        objectService.savePhoto((List<HashMap>) param.get("forumPhoto"), wid,forumId);
        return i;
    }

    @Override
    public int updateForum(HashMap param) {
        int i = forumMapper.updateForum(param);
        objectService.savePhoto((List<HashMap>) param.get("forumPhoto"), (String) param.get("wid"),forumId);
        return i;
    }

    @Override
    public int deleteForum(String wid) {
        int i = forumMapper.deleteForum(wid);
        objectService.deletePhoto(wid,forumId);
        return i;
    }


    @Override
    public PageInfo<HashMap> ForumListByUser(HashMap params) {
        HmServiceUtil.checkPageParams(params);
        PageHelper.startPage(params);
        List<HashMap> hashMaps = forumMapper.ForumListByUser(params);
        if(null!=hashMaps&&hashMaps.size()>0){
            for (int i = 0; i < hashMaps.size(); i++) {
                HashMap hashMap = hashMaps.get(i);
                List<HashMap> photoList = objectService.queryPhotoByMainId((String) hashMap.get("wid"), forumId);
                hashMap.put("forumPhoto",photoList);
            }
        }

        if(null!=hashMaps&&hashMaps.size()>0){
            for (int i = 0; i < hashMaps.size(); i++) {
                HashMap hashMap = hashMaps.get(i);
                List<HashMap> photoList = objectService.queryPhotoByMainId((String) hashMap.get("userId"), userPhoneId);
                hashMap.put("userPhoto",photoList);
            }
        }
        return new PageInfo<HashMap>(hashMaps);
    }
}
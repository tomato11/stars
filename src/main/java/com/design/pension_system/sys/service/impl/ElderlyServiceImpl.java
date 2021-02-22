package com.design.pension_system.sys.service.impl;

import com.design.pension_system.sys.mapper.ElderlyMapper;
import com.design.pension_system.sys.service.ElderlyService;
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
public class ElderlyServiceImpl implements ElderlyService {
    @Autowired
    private  ElderlyMapper elderlyMapper;


    @Autowired
    private ObjectService objectService;

    private String ElderlyPhoneId = "05";

    @Override
    public int insertElderly(HashMap param) {
        int i = elderlyMapper.insertElderly(param);
        String wid = (String) param.get("wid");
        objectService.savePhoto((List<HashMap>) param.get("ElderlyPhoto"), wid,ElderlyPhoneId);
        return i;
    }

    @Override
    public int updateElderly(HashMap param) {
        int i = elderlyMapper.updateElderly(param);
        objectService.savePhoto((List<HashMap>) param.get("ElderlyPhoto"), (String) param.get("wid"),ElderlyPhoneId);
        return i;
    }

    @Override
    public int deleteElderly(String wid) {
        int i = elderlyMapper.deleteElderly(wid);
        objectService.deletePhoto(wid,ElderlyPhoneId);
        return i;
    }


    @Override
    public HashMap ElderlyDetails(String wid) {

        HashMap map = elderlyMapper.ElderlyDetails(wid);
        List<HashMap> hashMaps = objectService.queryPhotoByMainId(wid, ElderlyPhoneId);
        map.put("ElderlyPhoto",hashMaps);
        return map;
    }

    @Override
    public PageInfo<HashMap> ElderlyListByUser(HashMap params) {
        HmServiceUtil.checkPageParams(params);
        PageHelper.startPage(params);
        List<HashMap> hashMaps = elderlyMapper.ElderlyListByUser(params);
        if(null!=hashMaps&&hashMaps.size()>0){
            for (int i = 0; i < hashMaps.size(); i++) {
                HashMap hashMap = hashMaps.get(i);
                List<HashMap> photoList = objectService.queryPhotoByMainId((String) hashMap.get("wid"), ElderlyPhoneId);
                hashMap.put("ElderlyPhoto",photoList);
            }
        }
        return new PageInfo<HashMap>(hashMaps);
    }
}
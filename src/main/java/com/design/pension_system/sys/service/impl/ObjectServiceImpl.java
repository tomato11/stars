package com.design.pension_system.sys.service.impl;

import com.design.pension_system.sys.domain.DicCommon;
import com.design.pension_system.sys.mapper.ObjectMapper;
import com.design.pension_system.sys.mapper.SignMapper;

import com.design.pension_system.sys.service.ObjectService;
import com.design.pension_system.sys.service.SignService;
import com.design.pension_system.sys.util.MsgUtils;
import com.dtflys.forest.utils.StringUtils;
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
public class ObjectServiceImpl implements ObjectService {
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public int savePhoto(List<HashMap> photo, String wid, String tableId) {
        if (null != photo && photo.size() > 0) {
            String tableName = objectMapper.queryTableNameById(tableId);
            objectMapper.deletePhoto(wid, tableName);
            objectMapper.saveMiddlePhoto(photo, wid, tableName);
        }
        return 1;
    }

    @Override
    public int deletePhoto(String mainId, String tableId) {
        String tableName = objectMapper.queryTableNameById(tableId);
        return objectMapper.deletePhoto(mainId, tableName);
    }

    @Override
    public List<HashMap> queryPhotoByMainId(String mainId, String tableId) {
        String tableName = objectMapper.queryTableNameById(tableId);

        List<HashMap> fileInfos = objectMapper.queryPhotoByMainId(mainId, tableName);
        return fileInfos;
    }


    @Override
    public Object queryXzqhTree() {

       return  objectMapper.queryXzqhTree();
    }

    @Override
    public HashMap<String, Object> selectAll(HashMap<String, Object> params) {
        HashMap<Object, Object> hm = new HashMap<>();
        List<DicCommon> tbCateManageList = this.objectMapper.selectAll(params);
        int num = this.objectMapper.selectAllCount(params);
        hm.put("total", num);
        hm.put("content", tbCateManageList);
        return null;
    }
}
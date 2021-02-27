package com.design.pension_system.sys.service.impl;

import com.design.pension_system.sys.mapper.OrderMapper;
import com.design.pension_system.sys.service.OrderService;
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
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    ObjectService objectService;
    private String orderPhotoId = "07";


    @Override
    public int insertOrder(HashMap param) {
        int i = orderMapper.insertOrder(param);
        return i;
    }

    @Override
    public int updateOrder(HashMap param) {
        int i = orderMapper.updateOrder(param);
        objectService.savePhoto((List<HashMap>) param.get("orderPhoto"), (String) param.get("wid"),orderPhotoId);
        return i;
    }

    @Override
    public int deleteOrder(String wid) {
        int i = orderMapper.deleteOrder(wid);
        return i;
    }


    @Override
    public HashMap OrderDetails(String wid) {
        HashMap map = orderMapper.OrderDetails(wid);
        List<HashMap> hashMaps = objectService.queryPhotoByMainId(wid, orderPhotoId);
        map.put("orderPhoto",hashMaps);
        return map;
    }

    @Override
    public PageInfo<HashMap> OrderListByUser(HashMap params) {
        HmServiceUtil.checkPageParams(params);
        PageHelper.startPage(params);
        List<HashMap> hashMaps = orderMapper.OrderListByUser(params);
        return new PageInfo<HashMap>(hashMaps);
    }

    @Override
    public List<HashMap> orderProgress() {
        return orderMapper.orderProgress();
    }
}
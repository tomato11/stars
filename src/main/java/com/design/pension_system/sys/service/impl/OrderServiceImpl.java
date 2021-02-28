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

private String elderlyPhoto="05";
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
        String userWid = String.valueOf(params.get("userWid"));
       String uesrType= orderMapper.queryUserTypeByWid(userWid);
        if("1".equals(uesrType)){//用户
            String orderType = (String)params.get("type");
            if("123".contains(orderType)){//医疗护理         阳光陪护      家政服务
                HmServiceUtil.checkPageParams(params);
                PageHelper.startPage(params);
                List<HashMap> hashMaps =  orderMapper.queryOrderThree(params);

                for (int i = 0; i < hashMaps.size(); i++) {
                    HashMap hashMap = hashMaps.get(i);
                    String elderlyWid = (String)hashMap.get("elderlyWid");
                    List<HashMap> elderlyPhotoList = objectService.queryPhotoByMainId(elderlyWid, elderlyPhoto);
                    hashMap.put("elderlyPhoto",elderlyPhotoList);
                }

                return new PageInfo<HashMap>(hashMaps);
            }else if(orderType.equals("4")){//活动室
                HmServiceUtil.checkPageParams(params);
                PageHelper.startPage(params);
                List<HashMap> hashMaps = orderMapper.OrderListByRoom(params);
                return new PageInfo<HashMap>(hashMaps);
            }else{//活动
                HmServiceUtil.checkPageParams(params);
                PageHelper.startPage(params);
                List<HashMap> hashMaps = orderMapper.OrderListByActivity(params);
                return new PageInfo<HashMap>(hashMaps);
            }
        }else{//专业人员
            HmServiceUtil.checkPageParams(params);
            PageHelper.startPage(params);
            List<HashMap> hashMaps =  orderMapper.queryOrderThree(params);
            return new PageInfo<HashMap>(hashMaps);
        }


    }

    @Override
    public List<HashMap> orderProgress() {
        return orderMapper.orderProgress();
    }
}
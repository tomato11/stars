package com.design.pension_system.sys.service.impl;

import com.design.pension_system.sys.mapper.SignMapper;
import com.design.pension_system.sys.mapper.UserMapper;
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
import java.util.UUID;


@Service
@Transactional(rollbackFor = Exception.class)
public class SignServiceImpl implements SignService {
    @Autowired
    private MsgUtils msgUtils;
    @Autowired
    private SignMapper signMapper;
    @Autowired
    private ObjectService objectService;
    private String tableId1 = "01";//用户照片附件表
    private String tableId2 = "02";//资格证书照片附件表


    @Override
    public Object sendLoginCode(String phone) throws UnsupportedEncodingException {
        return msgUtils.sendMsg(phone);
    }

    @Autowired
    UserMapper userMapper;

    @Override
    public int userRegister(HashMap param) {
        String password = String.valueOf(param.get("password"));
        String newPassWord = DigestUtils.md5DigestAsHex(password.getBytes());
        param.put("passWord", newPassWord);
        int i = signMapper.userRegister(param);

        String wid = String.valueOf(param.get("wid"));
        java.util.List<HashMap> userPhoto = (java.util.List<HashMap>) param.get("userPhone");
        objectService.savePhoto(userPhoto, (String) param.get("wid"), tableId1);
        if (!((String) param.get("type")).equals("1")) {//专业人员
            java.util.List<HashMap> qualificationPhone = (java.util.List<HashMap>) param.get("qualificationPhone");
            objectService.savePhoto(qualificationPhone, (String) param.get("wid"), tableId2);
        }


        if ("1".equals(param.get("type"))) {
            String role = "1";
            userMapper.insertUserRoleMiddle(role, (String) param.get("loginId"));
        } else {
            String role = "2";
            userMapper.insertUserRoleMiddle(role, (String) param.get("loginId"));
        }


        return i;

    }

    @Override
    public String findUserByUP(HashMap params) {
        String loginId;
        if (StringUtils.isNotEmpty(String.valueOf(params.get("code")))) {
            String map = signMapper.queryPhoneExist(params.get("phone"));
            if (null == map) {
                return map;
            }
            //查询验证码
            loginId = signMapper.checkLoginCode(params);

        } else {
            String password = String.valueOf(params.get("password"));
            String newPassWord = DigestUtils.md5DigestAsHex(password.getBytes());
            params.put("passWord", newPassWord);
            //查询密码
            loginId = signMapper.checkPassWord(params);
        }
        //判断userDB是否为null
        if (loginId == null) {
            //用户名和密码不正确
            return null;
        }

        String ticket = UUID.randomUUID().toString();


        String token = DigestUtils.md5DigestAsHex(ticket.getBytes());

        signMapper.insertToken(token,loginId);

        //用户名和ticket绑定即可!!!!!!
        return token;
    }

}
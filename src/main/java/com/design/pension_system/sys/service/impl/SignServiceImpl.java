package com.design.pension_system.sys.service.impl;

import com.design.pension_system.sys.mapper.SignMapper;
import com.design.pension_system.sys.mapper.UserMapper;
import com.design.pension_system.sys.service.ObjectService;
import com.design.pension_system.sys.service.SignService;
import com.design.pension_system.sys.util.HmResponseUtil;
import com.design.pension_system.sys.util.MsgUtils;
import com.dtflys.forest.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private String userPhoneId = "01";//用户照片附件表
    private String userQualificationId = "02";//资格证书照片附件表
 

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
        param.put("password", newPassWord);
        int i = signMapper.userRegister(param);

        String wid = String.valueOf(param.get("wid"));
        java.util.List<HashMap> userPhoto = (java.util.List<HashMap>) param.get("userPhone");
        objectService.savePhoto(userPhoto, (String) param.get("wid"), userPhoneId);
        if (!((String) param.get("type")).equals("1")) {//专业人员
            java.util.List<HashMap> qualificationPhone = (java.util.List<HashMap>) param.get("qualificationPhone");
            objectService.savePhoto(qualificationPhone, (String) param.get("wid"), userQualificationId);
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
    public Boolean checkLoginId(String loginId) {
        return null==signMapper.checkLoginId(loginId)?true:false;//没有返回true
    }

    @Override
    public Boolean checkPhone(String phone) {
        return null==signMapper.checkPhone(phone)?true:false;//没有返回true
    }

    @Override
    public ResponseEntity<Map> findUserByUP(HashMap params, HttpServletResponse response, HttpServletRequest request) {
        String loginId;
        HashMap  tips = new HashMap<>();
        if (null!=params.get("code")  ) {
            String map = signMapper.queryPhoneExist(params.get("phone"));
            if (null == map) {
                tips.put("tips","当前手机号未绑定账户");
                String s = com.alibaba.fastjson.JSONObject.toJSONString(tips);
                return HmResponseUtil.error(  "当前手机号未绑定账户");
            }
            //查询验证码
            loginId = signMapper.checkLoginCode(params);
            if(null==loginId||"".equals(loginId)){
                tips.put("tips","验证码错误");
                String s = com.alibaba.fastjson.JSONObject.toJSONString(tips);
                return HmResponseUtil.error(  "验证码错误");
            }
        } else {

            String password = String.valueOf(params.get("password"));
            String newPassWord = DigestUtils.md5DigestAsHex(password.getBytes());
            params.put("password", newPassWord);
            //查询密码
            loginId = signMapper.checkPassWord(params);
            if(null==loginId||"".equals(loginId)){
                tips.put("tips","密码错误");
                String s = com.alibaba.fastjson.JSONObject.toJSONString(tips);
                return HmResponseUtil.error(  "密码错误");
            }
        }


        String ticket = UUID.randomUUID().toString();

//        String token = DigestUtils.md5DigestAsHex(ticket.getBytes());

        signMapper.insertToken(ticket,loginId);

        //生成的是ticket信息
        Cookie cookie = new Cookie("TICKET", ticket);
        cookie.setMaxAge(7 * 24 * 3600); //7天有效
        cookie.setPath("/");         //cookie数据读取的范围
        response.addCookie(cookie);

        String url = request.getHeader("Origin");
        if (!StringUtils.isEmpty(url)) {
            String val = response.getHeader("Access-Control-Allow-Origin");
            if (StringUtils.isEmpty(val)) {
                response.addHeader("Access-Control-Allow-Origin", url);
                response.addHeader("Access-Control-Allow-Credentials", "true");
            }
        }

        HashMap hashMap = userMapper.queryUserDetils(loginId);
        HashMap typeAndWid = userMapper.queryTypeAndWidByLoginId(loginId);
        if ("1".equals(typeAndWid.get("type"))) {//用户
            List<HashMap> photoList = objectService.queryPhotoByMainId((String) typeAndWid.get("wid"), userPhoneId);
            hashMap.put("userPhoto", photoList);
        } else {//专业人员
            List<HashMap> photoList = objectService.queryPhotoByMainId((String) typeAndWid.get("wid"), userPhoneId);
            List<HashMap> userQualificationList = objectService.queryPhotoByMainId((String) typeAndWid.get("wid"), userQualificationId);
            hashMap.put("userPhoto", photoList);
            hashMap.put("qualificationPhone", userQualificationList);
        }
        return HmResponseUtil.success(hashMap);
    }

}
package com.design.pension_system.sys.util;

import com.alibaba.fastjson.JSONObject;
import com.design.pension_system.sys.client.ClientUtil;
import com.design.pension_system.sys.mapper.UtilMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

@Component
public class MsgUtils {
    @Autowired
    private ClientUtil clientUtil;
    @Autowired
    private UtilMapper utilMapper;

    public Object sendMsg(String phone) throws UnsupportedEncodingException {
        String random = getRandom(4);
        String url = "http://180.101.185.166:7862/sms?action=overage";
        HashMap hashMap = new HashMap<>();
        hashMap.put("account", URLDecoder.decode("922883", "UTF-8"));
        hashMap.put("password", URLDecoder.decode("XrwaGf", "UTF-8"));
        hashMap.put("extno", URLDecoder.decode("106901883", "UTF-8"));
        hashMap.put("mobile", URLDecoder.decode(phone, "UTF-8"));
        hashMap.put("content", URLDecoder.decode("【繁星养老】您的短信验证码是:" +random+ ",此验证码用于登录繁星养老服务系统，" +
                "如非本人操作,请忽略该短信！", "UTF-8"));
        hashMap.put("rt", URLDecoder.decode("json", "UTF-8"));
        hashMap.put("action", URLDecoder.decode("send", "UTF-8"));
        HashMap param = new HashMap<>();
        param.put("phone",phone);
        param.put("code",random);
        utilMapper.saveLoginCode(param);
        String post = clientUtil.post(url, hashMap);
        JSONObject jsonObject = JSONObject.parseObject(post);

        return   jsonObject.get("status");
    }


    public static String getRandom(int len) {
               Random r = new Random();
               StringBuilder rs = new StringBuilder();
              for (int i = 0; i < len; i++) {
                       rs.append(r.nextInt(10));
                   }
                 return rs.toString();
    }
}

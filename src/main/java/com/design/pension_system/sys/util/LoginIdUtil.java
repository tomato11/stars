package com.design.pension_system.sys.util;

import com.design.pension_system.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class LoginIdUtil {
    @Autowired
    private UserMapper userMapper;

    public   String getLoginIdByToken(String token) {
        String ticket = DigestUtils.md5DigestAsHex(token.getBytes());

        String loginIdByToken = userMapper.getLoginIdByToken(ticket);
        return loginIdByToken;
    }
}

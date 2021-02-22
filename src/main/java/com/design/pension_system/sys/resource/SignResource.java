package com.design.pension_system.sys.resource;

import com.design.pension_system.sys.service.SignService;
import com.design.pension_system.sys.util.HmResponseUtil;
import com.dtflys.forest.utils.StringUtils;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SignResource {

    @Autowired
    private SignService signService;

    @ApiOperation(value = "登陆校验")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "phone", value = "手机号", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "loginId", value = "账号", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "密码", required = false, paramType = "query", dataType = "string"),
    })
    @GetMapping("/loginCheck")
    public ResponseEntity<Map> loginCheck(@RequestParam HashMap params) throws Exception {
        HashMap result = signService.loginCheck(params);
        if (null != result) {
            return HmResponseUtil.success("校验通过");
        } else {
            if (StringUtils.isNotEmpty(String.valueOf(params.get("code")))) {
                return HmResponseUtil.error(400, "验证码错误");
            } else {
                return HmResponseUtil.error(400, "账号或密码不正确");
            }

        }
    }

    @ApiOperation(value = "发送验证码")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "string")
    })
    @GetMapping("/sendLoginCode")
    public ResponseEntity<Map> sendLoginCode( @RequestParam("phone")  String phone) throws Exception {
        Object result = signService.sendLoginCode(phone);
        if ("0".equals(result)) {
            return HmResponseUtil.success("发送成功");
        } else {
            return HmResponseUtil.error(-1, "有错误");
        }
    }

    @ApiOperation(value = "注册用户")
    @PostMapping("/register")
    public ResponseEntity<Map> userRegister(@RequestBody HashMap param) throws Exception {
        int result = signService.userRegister(param);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }


}

package com.design.pension_system.sys.resource;

import com.design.pension_system.sys.mapper.SignMapper;
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
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SignResource {

    @Autowired
    private SignService signService;
    @Autowired
    private SignMapper signMapper;

    @ApiOperation(value = "发送验证码")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "string")
    })
    @GetMapping("/sendLoginCode")
    public ResponseEntity<Map> sendLoginCode(@RequestParam("phone") String phone) throws Exception {
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


    @ApiOperation(value = "校验用户账号是否存在")
    @GetMapping("/checkLoginId")
    public ResponseEntity<Map> checkLoginId(@RequestParam("loginId") String loginId) throws Exception {
        Boolean result = signService.checkLoginId(loginId);
        return HmResponseUtil.success(result);

    }

    @ApiOperation(value = "校验用户手机号是否存在")
    @GetMapping("/checkPhone")
    public ResponseEntity<Map> checkPhone(@RequestParam("phone") String phone) throws Exception {
        Boolean result = signService.checkPhone(phone);
        return HmResponseUtil.success(result);

    }

    @ApiOperation(value = "登陆校验")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "phone", value = "手机号", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "loginId", value = "账号", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "密码", required = false, paramType = "query", dataType = "string"),
    })
    @GetMapping("/loginCheck")
    public ResponseEntity<Map> doLogin(@RequestParam HashMap user, HttpServletResponse response, HttpServletRequest request) {
        //获取userIP

        String ticket = signService.findUserByUP(user);

        //判断数据是否为null
        if (org.springframework.util.StringUtils.isEmpty(ticket)) {
            if (StringUtils.isNotEmpty(String.valueOf(user.get("code")))) {
                return HmResponseUtil.error(400, "验证码错误");
            } else {
                return HmResponseUtil.error(401, "账号或密码不正确");
            }
        }

        //生成的是ticket信息
        Cookie cookie = new Cookie("TICKET", ticket);
        cookie.setMaxAge(24 * 24 * 3600); //24天有效
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

        return HmResponseUtil.success("校验通过");
    }

}

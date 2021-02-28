package com.design.pension_system.sys.resource;

import com.design.pension_system.sys.service.ElderlyService;
import com.design.pension_system.sys.service.SignService;
import com.design.pension_system.sys.service.UserService;
import com.design.pension_system.sys.util.CookieUtil;
import com.design.pension_system.sys.util.HmResponseUtil;
import com.design.pension_system.sys.util.LoginIdUtil;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据类型获取涉及业务字典表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type", value = "注册类型", required = false, paramType = "query", dataType = "string"),
    })
    @GetMapping("/user/getBusinessByType")
    public ResponseEntity<Map> getBusinessByType(@RequestParam HashMap params) throws Exception {
        List<HashMap> result = userService.getBusinessByType(params);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }

    @ApiOperation(value = "用户列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type", value = "注册类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "姓名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNum", value = "分页参数：第几页", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页数量", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping("/user/list")
    public ResponseEntity<Map> userList(@RequestParam HashMap params) throws Exception {
        PageInfo<HashMap> result = userService.userList(params);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }

    @Autowired
    LoginIdUtil loginIdUtil;
    @Autowired
    ElderlyService elderlyService;

    @ApiOperation(value = "用户详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "wid", value = "wid", required = true, paramType = "query", dataType = "string"),
    })
    @GetMapping("/user/detils")
    public ResponseEntity<Map> userDetils(@RequestParam HashMap params, HttpServletRequest request) throws Exception {

        String wid;

        String token = request.getHeader("User_Token");
        if (null == params.get("wid")) {
            wid = elderlyService.queryWidByToken(token);
        } else {
            wid = String.valueOf(params.get("wid"));
        }
        HashMap result = userService.userDetils(wid);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }


    @ApiOperation(value = "修改用户信息")
    @PutMapping("/user")
    public ResponseEntity<Map> updateUser(@RequestBody HashMap param) throws Exception {
        int result = userService.updateUser(param);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }

    @ApiOperation(value = "好评榜")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type", value = "注册类型", required = false, paramType = "query", dataType = "string"),

    })
    @GetMapping("/user/evaluate/list")
    public ResponseEntity<Map> evaluateList(@RequestParam HashMap params) throws Exception {
        List<HashMap> result = userService.evaluateList(params);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }

    @ApiOperation(value = "医疗护理/家政服务/阳光陪护")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type", value = "注册类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sex", value = "性别", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "age", value = "年龄", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "business", value = "涉及业务", required = false, paramType = "query", dataType = "string"),
    })
    @GetMapping("/user/business/list")
    public ResponseEntity<Map> businessList(@RequestParam HashMap params) throws Exception {
        PageInfo<HashMap> result = userService.businessList(params);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }


    @ApiOperation(value = "获取菜单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginId", value = "登陆用户", required = false, paramType = "query", dataType = "string"),
    })
    @GetMapping("/menu")
    public ResponseEntity<Map> getMenuByLoginId(HttpServletRequest request) throws Exception {
        String token = request.getHeader("User_Token");
        String loginId = loginIdUtil.getLoginIdByToken(token);
        HashMap<Object, Object> params = new HashMap<>();
        params.put("loginId", loginId);
        List<HashMap> result = userService.getMenuByLoginId(params);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }

    @ApiOperation(value = "好评榜排名")

    @GetMapping("/evaluate/dic")
    public ResponseEntity<Map> getEvaluateDic() throws Exception {

        List<HashMap> result = userService.getEvaluateDic();
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }

}

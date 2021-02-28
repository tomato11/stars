package com.design.pension_system.sys.resource;

import com.design.pension_system.sys.service.ElderlyService;
import com.design.pension_system.sys.service.OrderService;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderResource {
    @Autowired
    private OrderService orderService;
    @ApiOperation(value = "新增订单")
    @PostMapping("/order")
    public ResponseEntity<Map> insertOrder(@RequestBody HashMap param,HttpServletRequest request) throws Exception {
        String token = request.getHeader("User_Token");
        String ticket = loginIdUtil.getLoginIdByToken(token);
        String wid=elderlyService.queryWidByToken(ticket);
        param.put("userWid",wid);

        int result = orderService.insertOrder(param);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }



    @ApiOperation(value = "修改订单")
    @PutMapping("/order")
    public ResponseEntity<Map> updateOrder(@RequestBody HashMap param) throws Exception {
        int result = orderService.updateOrder(param);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }

    @ApiOperation(value = "删除订单")
    @DeleteMapping("/order/{wid}")
    public ResponseEntity<Map> deleteOrder(@PathVariable String wid) throws Exception {
        int result = orderService.deleteOrder(wid);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }




    @ApiOperation(value = "订单详情")
    @GetMapping("/order/{wid}")
    public ResponseEntity<Map> OrderDetails(@PathVariable String wid) throws Exception {
        HashMap result = orderService.OrderDetails(wid);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }


    @Autowired
    ElderlyService elderlyService;
    @Autowired
    LoginIdUtil loginIdUtil;
    @ApiOperation(value = "用户订单列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginId", value = "当前账号", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "订单名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNum", value = "分页参数：第几页", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页数量", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "status", value = "status", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "score", value = "score", required = true, paramType = "query", dataType = "string"),
    })
    @GetMapping("/order/list/user")
    public ResponseEntity<Map> OrderListByUser(@RequestParam HashMap params, HttpServletRequest request) throws Exception {
//        Cookie loginIdCookie = CookieUtil.get(request, "LOGINID");
//        String loginId = loginIdCookie.getValue();

        String token = request.getHeader("User_Token");
        String ticket = loginIdUtil.getLoginIdByToken(token);
        String wid=elderlyService.queryWidByToken(ticket);
        params.put("userWid",wid);

        PageInfo<HashMap> result = orderService.OrderListByUser(params);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }


    @ApiOperation(value = "订单进度字典表")
    @GetMapping("/order/progress")
    public ResponseEntity<Map> orderProgress() throws Exception {
        List<HashMap> result = orderService.orderProgress( );
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }
}

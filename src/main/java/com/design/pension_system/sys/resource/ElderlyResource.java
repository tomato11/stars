package com.design.pension_system.sys.resource;

import com.design.pension_system.sys.service.ElderlyService;
import com.design.pension_system.sys.util.HmResponseUtil;
import com.design.pension_system.sys.util.LoginIdUtil;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ElderlyResource {

    @Autowired
    private ElderlyService elderlyService;
    @Autowired
    LoginIdUtil loginIdUtil;
    @ApiOperation(value = "新增老人")
    @PostMapping("/Elderly")
    public ResponseEntity<Map> insertElderly(@RequestBody HashMap param,HttpServletRequest request) throws Exception {
        String token = request.getHeader("User_Token");
        String ticket = loginIdUtil.getLoginIdByToken(token);
        String wid=elderlyService.queryWidByToken(ticket);
        param.put("mainId",wid);
        int result = elderlyService.insertElderly(param);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }



    @ApiOperation(value = "修改老人")
    @PutMapping("/Elderly")
    public ResponseEntity<Map> updateElderly(@RequestBody HashMap param) throws Exception {
        int result = elderlyService.updateElderly(param);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }

    @ApiOperation(value = "删除老人")
    @DeleteMapping("/Elderly/{wid}")
    public ResponseEntity<Map> deleteElderly(@PathVariable String wid) throws Exception {
        int result = elderlyService.deleteElderly(wid);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }




    @ApiOperation(value = "老人详情")
    @GetMapping("/Elderly/{wid}")
    public ResponseEntity<Map> ElderlyDetails(@PathVariable String wid) throws Exception {
        HashMap result = elderlyService.ElderlyDetails(wid);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }

    @ApiOperation(value = "用户老人列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginId", value = "当前账号", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "老人名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNum", value = "分页参数：第几页", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页数量", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping("/Elderly/list/user")
    public ResponseEntity<Map> ElderlyListByUser(@RequestParam HashMap params, HttpServletRequest request) throws Exception {
        String token = request.getHeader("User_Token");
        String ticket = loginIdUtil.getLoginIdByToken(token);
        String wid=elderlyService.queryWidByToken(ticket);
        params.put("mainId",wid);
        PageInfo<HashMap> result = elderlyService.ElderlyListByUser(params);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }


}

package com.design.pension_system.sys.resource;

import com.design.pension_system.sys.service.ActivityService;
import com.design.pension_system.sys.service.SignService;
import com.design.pension_system.sys.util.HmResponseUtil;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ActivityResource {
    @Autowired
    private ActivityService activityService;
    @ApiOperation(value = "新增活动")
    @PostMapping("/activity")
    public ResponseEntity<Map> insertActivity(@RequestBody HashMap param) throws Exception {
        int result = activityService.insertActivity(param);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }



    @ApiOperation(value = "修改活动")
    @PutMapping("/activity")
    public ResponseEntity<Map> updateActivity(@RequestBody HashMap param) throws Exception {
        int result = activityService.updateActivity(param);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }

    @ApiOperation(value = "删除活动")
    @DeleteMapping("/activity/{wid}")
    public ResponseEntity<Map> deleteActivity(@PathVariable String wid) throws Exception {
        int result = activityService.deleteActivity(wid);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }

    @ApiOperation(value = "管理员活动列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type", value = "活动类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "活动名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNum", value = "分页参数：第几页", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页数量", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping("/activity/list/admin")
    public ResponseEntity<Map> ActivityList(@RequestParam HashMap params) throws Exception {
        PageInfo<HashMap> result = activityService.ActivityListByAdmin(params);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }


    @ApiOperation(value = "活动详情")
    @GetMapping("/activity/{wid}")
    public ResponseEntity<Map> ActivityDetails(@PathVariable String wid) throws Exception {
        HashMap result = activityService.ActivityDetails(wid);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }

    @ApiOperation(value = "用户活动列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type", value = "活动类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "活动名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNum", value = "分页参数：第几页", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页数量", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping("/activity/list/user")
    public ResponseEntity<Map> ActivityListByUser(@RequestParam HashMap params) throws Exception {
        PageInfo<HashMap> result = activityService.ActivityListByUser(params);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }
}

package com.design.pension_system.sys.resource;

import com.design.pension_system.sys.service.ActivityRoomService;
import com.design.pension_system.sys.service.UserService;
import com.design.pension_system.sys.util.HmResponseUtil;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ActivityRoomResource {

    @Autowired
    private ActivityRoomService activityRoomService;


    @ApiOperation(value = "新增活动室")
    @PostMapping("/activityRoom")
    public ResponseEntity<Map> insertActivityRoom(@RequestBody HashMap param) throws Exception {
        int result = activityRoomService.insertActivityRoom(param);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }



    @ApiOperation(value = "修改活动室")
    @PutMapping("/activityRoom")
    public ResponseEntity<Map> updateActivityRoom(@RequestBody HashMap param) throws Exception {
        int result = activityRoomService.updateActivityRoom(param);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }

    @ApiOperation(value = "删除活动室")
    @DeleteMapping("/activityRoom/{wid}")
    public ResponseEntity<Map> deleteActivityRoom(@PathVariable String wid) throws Exception {
        int result = activityRoomService.deleteActivityRoom(wid);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }

    @ApiOperation(value = "活动室列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type", value = "活动室类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "活动室名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNum", value = "分页参数：第几页", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页数量", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping("/activityRoom/list")
    public ResponseEntity<Map> activityRoomList(@RequestParam HashMap params) throws Exception {
        PageInfo<HashMap> result = activityRoomService.activityRoomList(params);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }


    @ApiOperation(value = "活动室详情")
    @GetMapping("/activityRoom/{wid}")
    public ResponseEntity<Map> activityRoomDetails(@PathVariable String wid) throws Exception {
        HashMap result = activityRoomService.activityRoomDetails(wid);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }
}

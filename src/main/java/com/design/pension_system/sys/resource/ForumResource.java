package com.design.pension_system.sys.resource;

import com.design.pension_system.sys.service.ForumService;
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
public class ForumResource {
    @Autowired
    private ForumService ForumService;
    @ApiOperation(value = "新增讨论")
    @PostMapping("/Forum")
    public ResponseEntity<Map> insertForum(@RequestBody  HashMap param) throws Exception {
        int result = ForumService.insertForum(param);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }



    @ApiOperation(value = "修改讨论")
    @PutMapping("/Forum")
    public ResponseEntity<Map> updateForum(@RequestBody HashMap param) throws Exception {
        int result = ForumService.updateForum(param);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }

    @ApiOperation(value = "删除讨论")
    @DeleteMapping("/Forum/{wid}")
    public ResponseEntity<Map> deleteForum(@PathVariable String wid) throws Exception {
        int result = ForumService.deleteForum(wid);
        if (result > 0) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error(400, "有错误");
        }
    }






    @ApiOperation(value = "用户讨论列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type", value = "讨论类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "讨论名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNum", value = "分页参数：第几页", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页数量", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping("/Forum/list/user")
    public ResponseEntity<Map> ForumListByUser(@RequestParam  HashMap params) throws Exception {
        PageInfo<HashMap> result = ForumService.ForumListByUser(params);
        if (null != result) {
            return HmResponseUtil.success(result);
        } else {
            return HmResponseUtil.error("有错误");
        }
    }
}

package com.design.pension_system.sys.resource;

import com.design.pension_system.sys.domain.DicCommon;
import com.design.pension_system.sys.domain.DicSearchVO;
import com.design.pension_system.sys.mapper.ObjectMapper;
import com.design.pension_system.sys.service.ObjectService;
import com.design.pension_system.sys.util.HmResponseUtil;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ObjectResource {

    @Autowired
    ObjectService objectService;

    @Autowired
    ObjectMapper objectMapper;


    @ApiOperation(value = "上传图片")
    @CrossOrigin(origins = "*")//跨域
    @RequestMapping("/upload")
    public Map<String, String> upload(@RequestParam("upload_file") MultipartFile file) {
        String path = "F:\\pension_system\\upload"; // 文件保存路径

        Map<String, String> result = new HashMap<>();
        /**
         * 可能会出现重复文件，所以我们要对文件进行一个重命名的操作
         * 截取文件的原始名称，然后将扩展名和文件名分开，使用：时间戳-文件名.扩展名的格式保存
         */
        // 获取文件名称
        String fileName = file.getOriginalFilename();
        // 获取扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 获取文件名
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        // 生成最终保存的文件名,格式为: 时间戳-文件名.扩展名
        String id = String.valueOf(new Date().getTime());
        String saveFileName = id + "-" + name + "." + fileExtensionName;
        /**
         * 上传操作：可能upload目录不存在，所以先判断一下如果不存在，那么新建这个目录
         */
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        /**
         * 上传
         */
        File targetFile = new File(path, saveFileName);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 返回值，这三个对象是ng-zorro那边需要的
         */
        result.put("url", String.format("http://localhost:8080/upload/%s", saveFileName));
        result.put("uid", id);
        result.put("name", fileName);

        return result;
    }


    @GetMapping("/xzqh/tree")

    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(name = "loginId", value = "登录账号（不传获取当前账号的loginId）", required = false, paramType = "query", dataType = "string"),
            }

    )
    @ApiOperation(value = "获取县以上行政区划代码树形结构", notes = "获取县以上行政区划代码树形结构", response = HmResponseUtil.HmResponse.class)
    public ResponseEntity<Map> queryXzqhTree() {
        try {
            Object result = objectService.queryXzqhTree();
            if (result != null) {
                return HmResponseUtil.success(result);
            } else {
                return HmResponseUtil.error("有错误");
            }
        } catch (Exception e) {
            return HmResponseUtil.error(e.getMessage());
        }
    }


    @ApiOperation(value = "字典表查询", notes = "根据表名查询字典表")
    @ApiImplicitParams(
            {@ApiImplicitParam(
                    name = "tableName", value = "字典表 表名", required = true, dataType = "string", paramType = "query"),
                    @ApiImplicitParam(
                            name = "orderBy", value = "排序字段 字段名", required = false, dataType = "string", paramType = "query")})

    @RequestMapping(
            value = {"selectCommonDic"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<?> selectDic(@RequestParam HashMap<String, Object> params) {
        try {
            String contentKey = "content";
            HashMap<String, Object> hm = this.objectService.selectAll(params);
            return hm.get(contentKey) != null ? HmResponseUtil.success(hm) : HmResponseUtil.error("缺少参数");
        } catch (Exception var4) {
            return HmResponseUtil.error(var4.getMessage());
        }
    }


    @ApiOperation(
            value = "字典表批量查询",
            notes = "根据表名查询字典表"
    )
    @RequestMapping(
            value = {"/selectCommonDic/multi"},
            method = {RequestMethod.POST}
    )

    public ResponseEntity<?> selectDics(@RequestBody List<DicSearchVO> dicSearchVOs) {
        try {
            HashMap<String, Object> result = new HashMap(16);
            Iterator var3 = dicSearchVOs.iterator();

            while (var3.hasNext()) {
                DicSearchVO dicSearchVO = (DicSearchVO) var3.next();
                HashMap<String, Object> params = new HashMap(16);
                params.put("tableName", dicSearchVO.getTableName());
                params.put("orderBy", dicSearchVO.getOrderBy());
                HashMap<String, Object> dicMap = this.objectService.selectAll(params);
                result.put(dicSearchVO.getObjName(), dicMap);
            }

            return result.size() > 0 ? HmResponseUtil.success(result) : HmResponseUtil.error("缺少参数");
        } catch (Exception var7) {
            return HmResponseUtil.error(var7.getMessage());
        }
    }

}

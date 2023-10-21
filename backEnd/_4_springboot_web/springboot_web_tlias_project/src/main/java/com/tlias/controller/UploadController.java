package com.tlias.controller;

/**
 * @author MrkWinter
 * @version 1.0
 */

import com.tlias.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传控制类
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {
    @PostMapping
    public Result upload(String username, Integer age, MultipartFile image) throws Exception {
        log.info("上传文件");
        //本地存储
        String ofName = image.getOriginalFilename();
        //获取原始文件名
        String director = "C:\\Mysoftware\\JavaWebTool\\webResource\\";
        //获取UUID 长度唯一标识符 -- 通用唯一识别码
        String uuid = UUID.randomUUID().toString();
        //获取后缀名
        int index = ofName.lastIndexOf(".");
        String extname = ofName.substring(index);

        image.transferTo(new File(director+uuid+ofName));
        return Result.success();
    }
}

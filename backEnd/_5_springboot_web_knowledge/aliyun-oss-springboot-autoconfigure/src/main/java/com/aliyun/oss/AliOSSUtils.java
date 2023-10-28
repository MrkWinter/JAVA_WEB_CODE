package com.aliyun.oss;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云 OSS 工具类
 * 简化通过MultipartFile 上传文件到 阿里云对象存储服务oss上
 * //Component注解用于非Controller Service  Repository三层架构中 但需要交给ioc容器 实现控制反转的类
 */

public class AliOSSUtils {

    private AliOSSProperties aliOSSProperties;
    /**
     *   //指定连接阿里云的参数
     *   //可以使用@Value进行属性注入
     *     @Value("${aliyun.oss.endpoint}")
     *   private String endpoint;
     *     @Value("${aliyun.oss.accessKeyId}")
     *   private String accessKeyId;
     *     @Value("${aliyun.oss.accessKeySecret}")
     *   private String accessKeySecret;
     *     @Value("${aliyun.oss.bucketName}")
     *   private String bucketName;
     */

    public AliOSSProperties getAliOSSProperties() {
        return aliOSSProperties;
    }

    public void setAliOSSProperties(AliOSSProperties aliOSSProperties) {
        this.aliOSSProperties = aliOSSProperties;
    }

    /**
     * 实现上传文件到OSS
     */
    public String upload(MultipartFile file) throws IOException {
        //使用依赖注入获取属性 也可以不使用
        String endpoint = aliOSSProperties.getEndpoint();

        String accessKeyId = aliOSSProperties.getAccessKeyId();

        String accessKeySecret = aliOSSProperties.getAccessKeySecret();

        String bucketName = aliOSSProperties.getBucketName();

        // 获取上传的文件的输入流 - 由临时文件得到 (MultipartFile类接收到前端的文件后会生成临时文件)
        InputStream inputStream = file.getInputStream();

        // 避免文件覆盖 -- 通过uuid拼接文件格式的形式命名文件
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //上传文件到 OSS -- 通过对象上传
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);

        //文件访问路径 文件路径为https://bucket名.阿里云oss域名/文件名
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        // 关闭ossClient
        ossClient.shutdown();
        return url;// 把上传到oss的路径返回
    }
}

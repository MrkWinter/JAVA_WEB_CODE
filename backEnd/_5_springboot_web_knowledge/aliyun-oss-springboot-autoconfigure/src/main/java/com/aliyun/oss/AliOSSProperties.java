package com.aliyun.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author MrkWinter
 * @version 1.0
 */
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOSSProperties {
    //使用自动属性注入 需要四个条件
    // 1.该对象交给ioc容器
    // 2. 有get和set方法
    // 3. 配置文件中key值和成员属性名一致
    // 4. @ConfigurationProperties指定前缀
    //这里使用依赖注入获取该类中的属性
    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    public AliOSSProperties(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}

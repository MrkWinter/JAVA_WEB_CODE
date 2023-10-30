package com.itheima;

import com.itheima.config.CommonConfig;
import com.itheima.config.MyImportSelector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
//@Import(Integer.class) //普通类
//@Import(CommonConfig.class)  // 配置类
@Import(MyImportSelector.class) //对应接口实现类

@SpringBootApplication
public class SpringbootWebConfig2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebConfig2Application.class, args);
    }

}

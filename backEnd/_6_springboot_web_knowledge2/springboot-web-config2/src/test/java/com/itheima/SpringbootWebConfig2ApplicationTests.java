package com.itheima;

import com.itheima.controller.DeptController;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringbootWebConfig2ApplicationTests {
    @Autowired//IOC容器对象
    private ApplicationContext applicationContext;

    //获取bean对象
    @Test
    public void testGetBean() {
        //根据bean的名称获取
        DeptController bean1 = (DeptController) applicationContext.getBean("deptController");//bean名称默认为类名首字母小写

        //根据bean的类型获取
        DeptController bean2 = applicationContext.getBean(DeptController.class);

        //根据bean的名称 及 类型获取
        DeptController bean3 = applicationContext.getBean("deptController", DeptController.class);
    }


    //bean的作用域
    @Test
    public void testScope() {


    }


    //第三方bean的管理
    @Test
    public void testThirdBean() throws Exception {
        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(this.getClass().getClassLoader().getResource("1.xml"));
        Element rootElement = document.getRootElement();
        String name = rootElement.element("name").getText();
        String age = rootElement.element("age").getText();

        System.out.println(name + " : " + age);
    }

}

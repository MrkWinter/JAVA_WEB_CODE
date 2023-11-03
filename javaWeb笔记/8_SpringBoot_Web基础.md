## 以下通过案例完成SpringWeb的基础学习

### 准备工作

* 需求 

  1. 部门管理
     1. 查询部门
     2. 编辑部门
     3. 新增部门
     4. 删除部门
  2. 员工管理
     1. 条件分页查询
     2. 新增员工
     3. 删除员工
     4. 修改员工

* 环境搭建 

  ![image-20231018141017107](img/image-20231018141017107.png)

* 开发规范

  1. 规范

     ![image-20231018142147448](img/image-20231018142147448.png)

  2. 传统风格接口

     传统风格访问url进行相关操作可以实现 但较难维护 不整洁

     ![image-20231018141752573](img/image-20231018141752573.png)

  3. Restful风格接口 (采用)

     Restful较为简洁 规范  优雅 

     > Resultful风格简单来说就是通过请求方式来区别增删查改 能更好的统一请求路径并简化请求路径  一般请求路径格式为
     >
     > http://映射名/功能模块名/路径参数1/路径参数2... 并通过请求方式来区别增删查改

     ![image-20231018142022349](img/image-20231018142022349.png)

  4. 统一响应结果Result对象

     ![image-20231018142518674](C:/Users/MrkWinter/AppData/Roaming/Typora/typora-user-images/image-20231018142518674.png)

* 开发流程

  ![image-20231018142715882](img/image-20231018142715882.png)

### 部门管理

* 查询部门

  1. 思路

     ![image-20231018174826096](img/image-20231018174826096.png)

  2. 详情见工程

     > D:\VSProject\Java\javaWeb\backEnd\_4_springboot_web\springboot_web_tlias_project

* 前后端联调

  前端页面部署在nginx服务器上 通过访问前端页面 前端页面调用后端部署在服务器上的程序 服务器上的程序访问数据库 得到数据 返回给前端 前端再呈现出来

* 删除部门

  1. 思路

     ![image-20231018201020857](img/image-20231018201020857.png)

* 新增部门

  1. 思路

     注意接收和返回参数自动封装到对象中 (json和实体类互转靠的是@ResonBody 和 @RequestBody注解) Responbody 将响应的实体类转为json发送给前端RequestBody将request的json转为实体类给后端
  
     ![image-20231018203010256](img/image-20231018203010256.png)
  
  2. tip使用@ResqustMapping  访问路径可以抽取到类上 方法中只提供对应的参数
  
     这样较为简便  如
  
     ![image-20231018205252463](img/image-20231018205252463.png)
  
     ```java
     @Slf4j
     @RestController
     @RequestMapping("/depts")
     public class DeptController {
         @Autowired
         private DeptService deptService;
         @GetMapping
         public Result list() {
             log.info("查询全部部门数据");
             List<Dept> result = deptService.list();
             return Result.success(result);
         }
         @DeleteMapping("/{id}")
         public Result delete(@PathVariable Integer id) {
             log.info("删除部门{}", id);
             deptService.delete(id);
             return Result.success();
         }
         @PostMapping
         public Result add(@RequestBody Dept dept) {
             log.info("新增部门{}", dept);
             deptService.add(dept);
             return Result.success();
         }
     }
     ```
  
* 修改部门

  1. 根据id查询部门 用于页面回显
  1. 根据id修改部门

### 员工管理

#### 员工查询

* 分页查询

  1. sql分页查询 select * from 表名 limit 起始索引 每页展示的记录数

  2. sql分页公式 select * from 表名 limit (页面-1) * 每页查询的记录数 每页展示的记录数

  3. 分析 

     需要从新定义一个实体类包装页数

     ![image-20231019104118531](img/image-20231019104118531.png)

  4. 思路

     > 前端请求信息中包含page(第几页数) 和 pagesize (每页数)  后端接收到后需要传给前端总记录数和该页的数据信息 所以使用一个PageBean封装了total 和 list (和前端统一好的) 后端先从数据库中查数据条数 封装到total中 在根据分页查询查到对应的页的数据集合 封装给list 然后再将PageBean封装到Result中 统一返回类型 最后返回给前端

     ![image-20231019104523580](img/image-20231019104523580.png)

  5. 分页查询插件

     该插件原理介绍：

     1. 因为分页查询都要进行两个操作 一个是统统计符号条件的数据条数 一个是返回符合条件的数据的分页数据 他们都有共同之处 就是条件相同(统计数据数量和分页得到数据列表的条件相同)  如

        > ```sql
        > select count(*) from emp where gender = 1 and id in (1,2,3); -- 得到符合条件的数据条数
        > select * from emp where gender = 1 and id = (1,2,3) limit 1,2
        > -- 得到符合条件的数据集合
        > ```
        >
        > 

     2. 由此可以将他们进行封装起来 外部只显示声明查询条件 pageHelper插件就会自动根据该条件查询数据库符合条件的数据条数封装到Page<> 类集合对象中

        > ```sql
        > -- 外部声明查询条件
        > @Select("select * from emp where gender = 1 and id = (1,2,3)")
        > 
        > 自动执行
        > 
        > PageHelper.total = select count(*) from emp where gender = 1 and id in (1,2,3);
        > -- 以上为思路 并非具体实现
        > ```

     3. 然后根据设置的分页信息得到对应的数据集合 封装得到Page<> 类集合对象中如

        >```sql
        >pageHelper.startPage(页码数,每页数据条数)
        >
        >PageHelper.result = select * from emp where gender = 1 and id = (1,2,3) limit 1,2
        >-- 以上为思路 并非具体实现
        >```

     4. 最后就可以根据total 和 result获取符号条件的总记录数和分页查询返回的对象数据集合

     5. 图解

        ![image-20231019130023980](img/image-20231019130023980.png)

* 分页查询(带条件)

  1. 思路 (动态sql实现)

     ![image-20231019135146553](img/image-20231019135146553.png)



#### 员工删除

* 删除员工

  1. 思路

     ![image-20231019172240976](img/image-20231019172240976.png)

#### 员工添加

* 新增员工

  1. 思路

     ![image-20231019183316857](img/image-20231019183316857.png)

* 文件上传

  * 简介

    前端页面上传方法

    ```java
    from 表单设置编码格式 enctype="multipart/form-data" 才能通过post方式 传输文件
    并且传输的请求体中表单项的数据以一个自动生成的分隔符分割 分为多个部分进行提交
        
    post请求的请求体数据有表单数据 json数据 和文件数据
        具体区别如下
    1. 表单数据（Form Data）：表单数据是以键值对的形式组织的数据，通常用于传递用户提交的表单信息。在POST请求中，表单数据通常被编码为application/x-www-form-urlencoded或multipart/form-data格式的数据。表单数据可以通过表单元素的名称（键）来访问对应的值。
    
    2. 文件数据（File Data）：文件数据是二进制数据，用于传递文件，如上传图片、视频等。在POST请求中，文件数据通常使用multipart/form-data格式进行编码。文件数据需要以特定的格式传递，包括文件的名称、大小、文件类型以及文件的二进制数据。
    
    3. JSON数据：JSON数据是一种轻量级的数据交换格式，用于存储和传输结构化的数据。JSON数据以键值对的形式组织，可以包含对象、数组、字符串、数字等数据类型。在POST请求中，JSON数据通常使用application/json格式进行编码。JSON数据需要将数据转换为JSON字符串，并将其作为请求体的一部分发送。
        
    他们都可以被后端框架所接收
       	表单数据和json数据接收的格式一样 文件数据接收需要用 MultipartFile类接收
    ```

    ![image-20231019201320353](img/image-20231019201320353.png)

    后端服务器程序接收方法

    ```java
    使用参数接收 文件使用实现的api MultipartFile类接收 
    **注意** 文件表单项的名字和MultipartFile类接收的参数名应一致 否则需要用@RqusetParm("表单项名")对参数进行绑定
    后端服务器接收表单数据和文件数据后会在部署服务器的硬盘上存储对应的临时文件 用于操作访问
    ```

  * 本地存储

    1. 本机当服务器 存储到本地 -- 使用MultipartFile的api方法转存到本地
  
       ```java
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
       ```
  
    2. MultipartFile类方法常用方法
  
       ![image-20231020204006716](img/image-20231020204006716.png)
  
    3. 存储限制
  
       ps：可以一次请求发送多个文件 接收可以用数组或集合(MultipartFile数组或集合)
  
       ```properties
       #配置单个文件上传大小限制
       spring.servlet.multipart.max-file-size=10MB
       #配置单个请求最大大小的限制(一次请求可以上传多个文件)
       spring.servlet.multipart.max-request-size=100MB
       ```
  
    4. 本地存储一般项目中不再使用 原因如下
  
       > 1. 本地磁盘无法被前端直接访问
       > 2. 磁盘存储有限制
       > 3. 磁盘损坏 数据易丢失
       >
       > 可以使用云存储解决以上存储问题
       >
       > tip: nginx服务器代理的前端有上传文件限制 所有当用服务器代理时 会出现大于1M(默认)的文件无法通过前端上传到后端 (用postman发送请求参数就可以) 要想前端可以上传大文件 可以配置nginx代理服务器配置
  
  * 阿里云OSS
  
    1. 介绍
  
       阿里云
  
       ![image-20231020204328970](img/image-20231020204328970.png)
  
       阿里云OSS
  
       ![image-20231020204850776](img/image-20231020204850776.png)
  
    2. 通用第三方服务 使用思路
  
       ![image-20231020205215992](img/image-20231020205215992.png)
  
    3. 准备
  
       ![image-20231020214140992](img/image-20231020214140992.png)
    
    4. 入门程序
    
       通过配置阿里云oss的域名 backet accessKeyId accessKeySecret 以及存储在backet的文件路径名
    
       以及需要上传的文件路径名 就可以实现将文件上传到阿里云服务器oss的  bucket容器中 每个文件都会给分配一个url 访问该url就可以获取该文件 
    
       > url介绍
       >
       > URL（Uniform Resource Locator）是统一资源定位符的缩写，它是用于标识和定位互联网上资源的字符串。
       >
       > URL由多个部分组成，包括协议、主机名（或域名）、端口号、路径、查询参数和片段标识符。
       >
       > 下面是一个典型的URL示例：https://www.example.com:8080/path/to/resource?param1=value1&param2=value2#fragment
       >
       > - 协议：在示例中，协议是 "https://"，指定了客户端和服务器之间通信所使用的协议。常见的协议包括 HTTP、HTTPS、FTP、SMTP 等。
       > - 主机名：示例中的主机名是 "www.example.com"，用于标识服务器的域名或 IP 地址。
       > - 端口号：示例中的端口号是 ":8080"，指定了服务器上用于与客户端通信的端口。如果没有明确指定端口号，则默认使用协议默认的端口（比如 HTTP 默认使用端口 80）。
       > - 路径：示例中的路径是 "/path/to/resource"，指定了服务器上资源的路径。
       > - 查询参数：示例中的查询参数是 "?param1=value1&param2=value2"，用于向服务器传递额外的参数。多个参数使用 "&" 分隔。
       > - 片段标识符：示例中的片段标识符是 "#fragment"，用于在文档内部定位到特定的片段或锚点。
       >
       > URL使得我们可以通过简洁的字符串形式来访问和定位互联网上的资源，例如网页、图像、视频等。
       >
    
       ```java
       public static void main(String[] args) throws Exception {
               // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
               String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
       
              String accessKeyId = "LTAI5tFmaAbcHbpomecuoRUB";
              String accessKeySecret = "A1Gm4dlcnhJDkjCLFbLbaomb0kkOv3";
       
               // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
               EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
               // 填写Bucket名称，例如examplebucket。
               String bucketName = "weba-tlias";
               // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
               String objectName = "1.jpg";
               // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
               // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
               String filePath= "D:\\Pictures\\wallpaper\\1.jpg";
       
               // 创建OSSClient实例。 通过环境变量
       //        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);
               // 创建OSSClient实例。 通过id和密匙
               OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);
       
       
               try {
                   InputStream inputStream = new FileInputStream(filePath);
                   // 创建PutObjectRequest对象。
                   PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
                   // 创建PutObject请求。
                   PutObjectResult result = ossClient.putObject(putObjectRequest);
               } catch (OSSException oe) {
                   System.out.println("Caught an OSSException, which means your request made it to OSS, "
                           + "but was rejected with an error response for some reason.");
                   System.out.println("Error Message:" + oe.getErrorMessage());
                   System.out.println("Error Code:" + oe.getErrorCode());
                   System.out.println("Request ID:" + oe.getRequestId());
                   System.out.println("Host ID:" + oe.getHostId());
               } catch (ClientException ce) {
                   System.out.println("Caught an ClientException, which means the client encountered "
                           + "a serious internal problem while trying to communicate with OSS, "
                           + "such as not being able to access the network.");
                   System.out.println("Error Message:" + ce.getMessage());
               } finally {
                   if (ossClient != null) {
                       ossClient.shutdown();
                   }
               }
           }
       ```
    
    5. 阿里云oss集成
    
       1. 思路
    
          前端点击文件上传 会给后端服务器发送一次文件上传的请求/upload 后端服务器接收后将文件数据存储到oss中 然后后端服务器返回该文件的url  前端获取到该文件的url后就可以从oss中获取上传的文件 然后展示出来 并存储将该文件的url以便后序直接访问
    
          总的来说就是前端发送文件给后端 后端发送文件给oss服务器 返回可以直接得到文件资源url 前端接收 通过该url就可以一直获取该文件数据
    
          目前后端分为三部分 后端java程序服务器 (mysql)数据服务器 (阿里云oss)文件资源服务器
    
          ![image-20231021104648483](img/image-20231021104648483.png)
    
       2. 操作
    
          使用AliossUtils工具类简化连接阿里云oss服务的步骤
    
          ```java
          /**
           * 阿里云 OSS 工具类
           * 简化通过MultipartFile 上传文件到 阿里云对象存储服务oss上
           */
          @Component
          //Component注解用于非Controller Service  Repository三层架构中 但需要交给ioc容器 实现控制反转的类
          public class AliOSSUtils {
              //指定连接阿里云的参数
              private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
              private String accessKeyId = "LTAI5tFmaAbcHbpomecuoRUB";
              private String accessKeySecret = "A1Gm4dlcnhJDkjCLFbLbaomb0kkOv3";
              private String bucketName = "weba-tlias";
          
              /**
               * 实现上传文件到OSS
               */
              public String upload(MultipartFile file) throws IOException {
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
          ```
    
          

#### 员工修改

* 修改员工

  1. 查询回显

     ![image-20231021114614793](img/image-20231021114614793.png)

  2. 修改员工

     ![image-20231021115515219](img/image-20231021115515219.png)

### 配置文件

* 参数配置化

  1. 一个SpringBoot项目可能会多个需要配置的属性参数 直接固定的定义到类中太分散并且更改麻烦不便于同一管理 这时可以使用 配置文件信息 + 属性注入 -- (属性注入的原理就是读取properties配置文件中对应的key值赋值给成员属性)

  2. 使用@Value 进行属性注入 使配置文件中对应的value值赋值给属性

     ![image-20231021140626720](img/image-20231021140626720.png)

  3. 例 -- 配置阿里云oss的连接参数

     ```properties
     #配置阿里云连接参数
     aliyun.oss.endpoint=https://oss-cn-hangzhou.aliyuncs.com
     aliyun.oss.accessKeyId=LTAI5tFmaAbcHbpomecuoRUB
     aliyun.oss.accessKeySecret=A1Gm4dlcnhJDkjCLFbLbaomb0kkOv3
     aliyun.oss.bucketName=weba-tlias
     ```

     属性注入

     ```java
         //使用@Value进行属性注入
         @Value("${aliyun.oss.endpoint}")
         private String endpoint;
         @Value("${aliyun.oss.accessKeyId}")
         private String accessKeyId;
         @Value("${aliyun.oss.accessKeySecret}")
         private String accessKeySecret;
         @Value("${aliyun.oss.bucketName}")
         private String bucketName;
     ```

  4. 该思路就是参数配置化

     ![image-20231021141850448](img/image-20231021141850448.png)

* yml配置文件

  1. 配置格式yml yaml 他们按层级进行配置

     ![image-20231021142113422](img/image-20231021142113422.png)

  2. 配置文件格式对比

     ![image-20231021142635569](img/image-20231021142635569.png)

  3. yml配置文件基本语法

     ![image-20231021142830760](img/image-20231021142830760.png)

  4. yml数据格式 (特殊)

     ```yaml
     # 对象/map集合:
     user: 
       name: zhang
       age: 10
       password: 123456
     # 数组/list/set集合:
     hobby:
       - java
       - c
       - sport
      # 该数组中的元素用 - 元素显示
     ```

  5. properties配置文件转yml配置文件

     properties配置文件

     ```properties
     # 设置数据库驱动类
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     # 设置数据库连接 URL
     spring.datasource.url=jdbc:mysql://localhost:3306/tlias
     # 设置数据库用户名
     spring.datasource.username=root
     # 设置数据库密码
     spring.datasource.password=xing123456
     # 设置 MyBatis 的日志实现类
     mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
     # 设置 MyBatis 是否将下划线转换为驼峰命名规则
     mybatis.configuration.map-underscore-to-camel-case=true
     # 设置文件上传的最大文件大小
     spring.servlet.multipart.max-file-size=10MB
     # 设置请求中包含文件的最大大小
     spring.servlet.multipart.max-request-size=100MB
     # 配置阿里云连接参数
     aliyun.oss.endpoint=https://oss-cn-hangzhou.aliyuncs.com
     aliyun.oss.accessKeyId=LTAI5tFmaAbcHbpomecuoRUB
     aliyun.oss.accessKeySecret=A1Gm4dlcnhJDkjCLFbLbaomb0kkOv3
     aliyun.oss.bucketName=weba-tlias
     ```

     yml配置文件

     ```yaml
     spring:
       # springboot框架下jdbc配置信息
       datasource:
         driver-class-name: com.mysql.cj.jdbc.Driver
         url: jdbc:mysql://localhost:3306/tlias
         username: root
         password: xing123456
       # spring框架服务器配置
       servlet:
         multipart:
           max-file-size: 10MB
           max-request-size: 100MB
     
     # 配置mybatis信息
     mybatis:
       configuration:
         log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
         map-underscore-to-camel-case: true
     # 阿里云连接信息
     aliyun:
       oss:
         endpoint: https://oss-cn-hangzhou.aliyuncs.com
         accessKeyId: LTAI5tFmaAbcHbpomecuoRUB
         accessKeySecret: A1Gm4dlcnhJDkjCLFbLbaomb0kkOv3
         bucketName: weba-tlias
     ```

     tip: 在Spring Boot项目中，当同时存在`.properties`和`.yml`配置文件时，优先使用`.properties`配置文件

* @ConfigurationProperties

  1. 可以使用注解自动进行属性注入  条件如下

     > 1. 需要注入的属性的类需要交给ioc容器
     >
     > 2. 需要注入的属性的类需要get和set方法
     >
     > 3. 需要注入的属性的类的属性名应与配置文件中的属性名一致
     >
     > 4. 需要注入的属性的类上要使用@ConfigurationProperties(prefix= "前缀名指定前缀")
     >
     >    ![image-20231021170931351](img/image-20231021170931351.png)
     >
     > 5. tip: 此过程可以包装到另一个配置类下 用依赖注入获取数据 也可以直接在对应的类下使用 较为简便

  2. @ConfigurationProperties 和 @ Value的区别

     ![image-20231021191535468](img/image-20231021191535468.png)

### 登录页面

#### 登录功能

* 思路

  ![image-20231022123135944](img/image-20231022123135944.png)

#### 登录校验

* 介绍

  如果不做登录校验 因为Http协议是无状态的 本次的请求不会得知上次请求的信息 进行访问功能接口时就会可以根据访问路径随意访问资源 不安全 这时就需要进行请求拦截 如果拦截到请求后发现已经登录 则可以正常访问功能接口 否则返回错误信息 前端就会自动跳转到登录页面进行登录

  ![image-20231022130710086](img/image-20231022130710086.png)

* 会话技术

  1. 介绍

     ![image-20231022132157022](img/image-20231022132157022.png)

  2. cookie技术

     通过请求头cookie和响应头set-cookie传递cookie信息

     服务器自动设置cookie 返回给客户端(响应数据)

     信息存储在客户端

     ![image-20231022142340862](img/image-20231022142340862.png)

     客户端请求自动在请求头中加上cookie(请求数据)

     ![image-20231022142429456](img/image-20231022142429456.png)

     从而实现在多次请求中共享数据

     优缺点

     ![image-20231022133738168](img/image-20231022133738168.png)

  3. session技术

     通过cookie技术传输会话id 找到相同的会话对象 相比于cookie技术 话对象中储存cookie中应存储的共享数据

     信息存储在服务端

     ![image-20231022143903264](img/image-20231022143903264.png)

     优缺点

     ![image-20231022144559513](img/image-20231022144559513.png)

  4. 令牌技术

     数据存储在令牌中 令牌不能伪造(窃取) 安全  服务器不用存储 压力小 集群服务器每个服务器都检验令牌 获取共享数据 

     相当于cookie的好处 和 session的好处集中在一起

     信息存储在客户端

     ![image-20231022145228232](img/image-20231022145228232.png)

* JWT令牌

  1. 介绍

     基于json 数据加密 + 令牌 

     ![image-20231022173925975](img/image-20231022173925975.png)

  2. 场景

     ![image-20231022174057511](img/image-20231022174057511.png)

  3. 使用

     使用前需要引入相关依赖

     ```java
     //加密获取jwt令牌
     public class JwtDemo {
         @Test
         public void getJwt() {
             Map<String, Object> claims = new HashMap<>();
             claims.put("id",1);
             claims.put("name","top");
             //claims 数据
             String jwt = Jwts.builder() //调用该工具类上的builder方法构造令牌
                     .signWith(SignatureAlgorithm.HS256, "itheima") //指定签名算法和秘钥 秘钥被加到最后一段数字签名中
                     .setClaims(claims) //指定中间一段内容 通过key:value的Map对象形式传递
                     .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) //设置该令牌的失效时间
                     .compact(); //获取字符串
             System.out.println(jwt);
         }
             @Test
         public void parseJwt() {
             String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG9wIiwiaWQiOjEsImV4cCI6MTY5Nzk3MjQwNX0.0u0-AU9gBshGQp3cNyZ7UKd4S3bE7jwE00-nYsxrklw";
             Map<String, Object> parseJwt = Jwts.parser()//调用工具类 解析Jwt令牌
                     .setSigningKey("ithema")//给出相同的秘钥
                     .parseClaimsJws(jwt)//指定jwt令牌
                     .getBody();//获取第二段的数据内容
             System.out.println(parseJwt);
         }
     }
     jwt令牌一共有三部分 
         第一部分为签名算法 该部被base64加密
         第二部分为数据 该部分被base64加密
         第三部分为数字签名 基于第一部分指定的签名算法和数据内容和指定的秘钥生成(header + payload + secret 生成 因此是安全的)
     ```

     ```xml
             <!--        jwt令牌依赖
             这里上面关于阿里云的依赖也要引入，只引入一个是无法生成令牌的-->
             <dependency>
                 <groupId>io.jsonwebtoken</groupId>
                 <artifactId>jjwt</artifactId>
                 <version>0.9.1</version>
             </dependency>
     ```

  4. 注意事项

     ![image-20231022181709556](img/image-20231022181709556.png)

  5. 使用

     需要与前端配合 这里使用工具类

     jwt工具类

     ```java
     public class JwtUtils {
     
         private static String signKey = "itheima";
         private static Long expire = 43200000L;
     
         /**
          * 生成JWT令牌
          * @param claims JWT第二部分负载 payload 中存储的内容
          * @return
          */
         public static String generateJwt(Map<String, Object> claims){
             String jwt = Jwts.builder()
                     .addClaims(claims)
                     .signWith(SignatureAlgorithm.HS256, signKey)
                     .setExpiration(new Date(System.currentTimeMillis() + expire))
                     .compact();
             return jwt;
         }
     
         /**
          * 解析JWT令牌
          * @param jwt JWT令牌
          * @return JWT第二部分负载 payload 中存储的内容
          */
         public static Claims parseJWT(String jwt){
             Claims claims = Jwts.parser()
                     .setSigningKey(signKey)
                     .parseClaimsJws(jwt)
                     .getBody();
             return claims;
         }
     }
     
     ```

     login方法 下发jwt令牌 令牌中添加用户信息 username 和 password

     ```java
     @Slf4j
     @RestController
     @RequestMapping("/login")
     public class LoginController {
         @Autowired
         private EmpService empService;
         @PostMapping
         public Result login(@RequestBody Emp emp) {
             log.info("用户登录{}", emp);
             Emp e = empService.login(emp);
             //如果查询到用户 给前端下发jwt令牌
             String jwt = null;
             if (e!=null) {
                 //封装 username 和 password 到map中 作为数据 构造Jwt令牌
                 Map<String, Object> map = new HashMap<>();
                 map.put("username",emp.getUsername());
                 map.put("password",emp.getPassword());
                 jwt = JwtUtils.generateJwt(map);
             }
             return e == null ? Result.error("用户名或密码错误") : Result.success(jwt);
         }
     }
     ```

  6. 原理

     在前端登录页面 发送登录请求 如果数据库中有账户信息 则在data信息中保存jwt令牌并返回成功信息(Result) 前端得到jwt令牌后 会存储在浏览器本地 在每次进行访问功能接口时都会在请求中包含该令牌  该令牌正确则可以成功访问 错误则会被拦截请求 返回到登录页面 保证了前端页面及数据的安全性 进而实现了登录了用户才能访问对应的数据

* 过滤器Filter

  1. 介绍

     拦截所有请求 进行统一的业务逻辑避免在每一个功能接口中定义 例如: 登录校验 统一编码处理 敏感字符处理等

     ![image-20231023211104457](img/image-20231023211104457.png)

  2. 快速入门

     ![image-20231023211643129](img/image-20231023211643129.png)

  3. 详解

     执行流程

     > doFilter() 方法拦截到请求后 先执行放行前的逻辑 之后执行放行方法 方向方法执行意味着执行功能接口 执行完功能接口后接着放行方法后进行执行放行后的逻辑
     >
     > ![image-20231023213843615](img/image-20231023213843615.png)

     拦截路径

     > 有以下几种拦截路径
     >
     > 1. @WebFilter(urlPatterns = "/*") 拦截所有路径
     >
     > 2. @WebFilter(urlPatterns = "/emp/*") 拦截某一目录下的请求
     >
     > 3. @WebFilter(urlPatterns = "/login")  拦截特定的某一路径
     >
     > 4. 总和
     >
     >    ![image-20231023214301495](img/image-20231023214301495.png)

     过滤器链

     > 多个过滤器重叠 进行多次过滤 形成过滤器链 FilterChain类就是过滤器链类 调用放行方法后 如果不是最后一个过滤器 就会放行到下一个过滤器 如果是最后一个过滤器 就会放行到web资源中去访问web资源  注解配置的过滤器 按照类名排序 字母考前的先执行 (好像可以用注解设置优先级)
     >
     > ![image-20231023215151250](img/image-20231023215151250.png)

  4. 登录校验- Filter

     1. 思路 

        > 过滤器中得到请求头中的jwt令牌 如果有令牌 建议 看是否放行 如果没令牌 或者令牌错误 返回特定错误请求 前端设置回到登录页面
        >
        > 思路1 -  大致![image-20231023215646696](img/image-20231023215646696.png)
        >
        > 思路2 - 流程
        >
        > ![image-20231023215852815](img/image-20231023215852815.png)

     2. 实现

        配置文件

        ```xml
        <!--        阿里巴巴fastJson 快速进行json格式转换-->
                <dependency>
                    <groupId>com.alibaba</groupId>
                    <artifactId>fastjson</artifactId>
                    <version>1.2.76</version>
                </dependency>
        ```

        java代码

        ````java
        @Slf4j
        @WebFilter(urlPatterns = "/*")
        public class LoginCheckFilter implements Filter {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                HttpServletRequest req = (HttpServletRequest) servletRequest; //可以强转 向下转型
                HttpServletResponse resp = (HttpServletResponse) servletResponse;
        
                //1. 获取请求url
                String url = req.getRequestURI();
        
                //2. 判断url中是否含login
                log.info("url{}", url);
                if (url.contains("login")) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
                //3. 获取请求头中的令牌
                String jwt = req.getHeader("token");
        
                //4. 判断该令牌是否存在
        
                if(!StringUtils.hasLength(jwt)) { //该方法判断该jwt令牌是否有长度(null 或空 串)
                    log.info("请求头token为空 返回未登录信息");
                    //不放行 直接过滤器中返回数据
                    Result error = Result.error("NOT_LOGIN");
                    //手动转换 json对象返回 --> 阿里巴巴fastJson工具包 快速进行json格式转换
                    String noLogin = JSONObject.toJSONString(error);
                    //通过ServletResponse 对象响应返回给浏览器
                    resp.getWriter().write(noLogin);
                    return;
                }
                //5. 解析token 如果解析失败 返回错误结构
        
                try {
                    Map<String, Object> claims = JwtUtils.parseJWT(jwt);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("解析令牌失败 返回未登录的信息");
                    Result error = Result.error("NOT_LOGIN");
                    String noLogin = JSONObject.toJSONString(error);
                    resp.getWriter().write(noLogin);
                    return;
                }
                //6. 令牌合法 放行
                log.info("令牌合法 放行");
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }
        ````

     3. tip : 令牌失效与 清除

        > 浏览器中的 JWT 令牌在以下情况下可能会被清除：
        >
        > 1. 令牌过期：JWT 令牌通常具有一个预设的过期时间。一旦令牌过期，浏览器将不再使用该令牌，并且需要重新进行身份验证。过期时间通常在令牌的负载中定义，并由服务器进行验证。
        > 2. 用户退出登录：当用户主动退出登录时，浏览器通常会清除与该用户相关的令牌。这可以通过清除浏览器中的存储（如 LocalStorage 或 SessionStorage）中的令牌来实现。
        > 3. 清除浏览器缓存：如果用户清除了浏览器缓存或浏览器的 cookie，那么存储在客户端的 JWT 令牌也将被清除。
        > 4. 会话结束：如果用户关闭了浏览器，浏览器通常会清除所有与该会话相关的信息，包括 JWT 令牌。

* 拦截器Interceptor

  1. 简介 & 快速入门

     基本介绍

     ![image-20231024144737298](img/image-20231024144737298.png)

     快速入门

     两步操作 

     1. 定义拦截器类 实现HandInterator接口 重写其中的三个逻辑处理方法 并在该类上添加@Component注解将该类交给ioc容器
     2. 定义注册拦截器的配置类 该类实现WebMvcConfigure接口 重写实现其中的addInterceotor 方法 来添加注册拦截器 在该方法中可以指定拦截器拦截路径

     ![image-20231024150124637](img/image-20231024150124637.png)

  2. 详解

     拦截路径配置
  
     ![image-20231024171917446](img/image-20231024171917446.png)
  
     执行流程
  
     > 浏览器发送请求 过滤器过滤 过滤后发送到 前端控制器DispatcherServilet 然后传入到Interceptor拦截器 拦截后放行后可以访问web资源 然后再依次返回
  
     ![image-20231024172924668](img/image-20231024172924668.png)
  
  3. 登录校验 Interceptor
  
     ```java
     //定义拦截器 重写方法
     @Slf4j
     @Component
     public class LoginCheckInterceptor implements HandlerInterceptor {
         @Override //目标资源方法运行前运行 返回true 放行 false 不放行
         public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
             //1. 获取请求url
             String url = request.getRequestURI();
     
             //2. 判断url中是否含login
             log.info("url{}", url);
             if (url.contains("login")) {
                 return true;
             }
             //3. 获取请求头中的令牌
             String jwt = request.getHeader("token");
     
             //4. 判断该令牌是否存在
     
             if (!StringUtils.hasLength(jwt)) { //该方法判断该jwt令牌是否有长度(null 或空 串)
                 log.info("请求头token为空 返回未登录信息");
                 //不放行 直接过滤器中返回数据
                 Result error = Result.error("NOT_LOGIN");
                 //手动转换 json对象返回 --> 阿里巴巴fastJson工具包 快速进行json格式转换
                 String noLogin = JSONObject.toJSONString(error);
                 //通过ServletResponse 对象响应返回给浏览器
                 response.getWriter().write(noLogin);
                 return false;
             }
             //5. 解析token 如果解析失败 返回错误结构
     
             try {
                 Map<String, Object> claims = JwtUtils.parseJWT(jwt);
             } catch (Exception e) {
                 e.printStackTrace();
                 log.info("解析令牌失败 返回未登录的信息");
                 Result error = Result.error("NOT_LOGIN");
                 String noLogin = JSONObject.toJSONString(error);
                 response.getWriter().write(noLogin);
                 return false;
             }
             //6. 令牌合法 放行
             log.info("令牌合法 放行");
             return true;
         }
     
         @Override //目标资源方法运行后运行
         public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
             System.out.println("访问web资源后执行");
         }
     
         @Override //视图渲染完毕后执行 最后执行
         public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
             System.out.println("视图渲染完毕后执行 最后执行");
         }
     }
     ```

#### 异常处理

* 异常处理方式

  ![image-20231024174600842](img/image-20231024174600842.png)

* 全局异常处理器

  ![image-20231024174754404](img/image-20231024174754404.png)

  代码

  ```java
  # 存在异常会不断向上抛 抛给全局异常处理器 
  # 没有异常就会直接返回对应数据 
  # 业务处理时单考虑正常业务 异常业务交给全局异常处理器
  
  @RestControllerAdvice
  // 该注解表示声明该类为全局异常处理器类 返回类型自动转换成json
  public class GlobalExceptionHandler {
      //表示捕获特定的异常  这里演示的是所有异常
      @ExceptionHandler(Exception.class)
      public Result ex(Exception ex) {
          ex.printStackTrace();
          //捕获异常后返回前端统一Result对象 前端再进行处理
          return Result.error("操作失败 请联系管理员");
      }
  }
  ```

  


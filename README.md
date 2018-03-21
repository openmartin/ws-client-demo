# ws-client-demo

客户端调用远程服务

http://localhost:8280/services/HelloWorld?wsdl


## spring + cfx 配置

### maven

添加依赖包

```xml
<dependency>
  <groupId>org.apache.cxf</groupId>
  <artifactId>cxf-rt-frontend-jaxws</artifactId>
  <version>3.2.3</version>
</dependency>
<dependency>
  <groupId>org.apache.cxf</groupId>
  <artifactId>cxf-rt-transports-http</artifactId>
  <version>3.2.3</version>
</dependency>
```

### wsdl2java

把 HelloWorld.wsdl 下载到 resources 当中

```xml
  <plugin>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-codegen-plugin</artifactId>
    <version>LATEST</version>
    <executions>
      <execution>
        <id>generate-sources</id>
        <phase>generate-sources</phase>
        <configuration>
          <sourceRoot>${project.build.directory}/generated/cxf</sourceRoot>
          <wsdlOptions>
            <wsdlOption>
              <wsdl>${basedir}/src/main/resources/HelloWorld.wsdl</wsdl>
            </wsdlOption>
          </wsdlOptions>
        </configuration>
        <goals>
          <goal>wsdl2java</goal>
        </goals>
      </execution>
    </executions>
  </plugin>
```

maven compile 任务完成之后在`target\generated\cxf\com\example\demo\ws` 下会有很多文件

```
    CurrentDate.java
    CurrentDateResponse.java
    HelloWorld.java
    HelloWorldImplService.java
    ObjectFactory.java
    package-info.java
    SayHi.java
    SayHiResponse.java
```

### 调用 WebService

#### 使用 spring

把 HelloWorld.java 复制到 src\main\java\com\example\demo\ws 目录下

同时 HelloWorld.java 里面 @XmlSeeAlso({ObjectFactory.class})这行代码删掉

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:jaxws="http://cxf.apache.org/jaxws"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://cxf.apache.org/jaxws http://cxf.apache.org/schemas/jaxws.xsd">

    <jaxws:client id="helloClient"
                  serviceClass="com.example.demo.ws.service.HelloWorld"
                  address="http://localhost:8280/services/HelloWorld" />
</beans>
```

```java
package com.example.demo;

import com.example.demo.ws.service.HelloWorld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.datatype.XMLGregorianCalendar;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloWorld client = (HelloWorld) context.getBean("helloClient");

        String resp1 = client.sayHi("小明");
        System.out.println(resp1);
        XMLGregorianCalendar resp2 = client.currentDate();
        System.out.println(resp2.toString());
    }
}
```

#### 不使用 spring



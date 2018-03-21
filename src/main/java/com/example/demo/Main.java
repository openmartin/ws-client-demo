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

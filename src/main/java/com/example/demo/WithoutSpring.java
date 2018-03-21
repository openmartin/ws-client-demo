package com.example.demo;

import com.example.demo.ws.service.HelloWorld;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class WithoutSpring {
    public static void main(String[] args) throws MalformedURLException {
        URL wsdlURL = new URL("http://localhost:8280/services/HelloWorld?wsdl");
        QName SERVICE_NAME = new QName("http://service.ws.demo.example.com/", "HelloWorldImplService");
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        HelloWorld client = service.getPort(HelloWorld.class);
        String result = client.sayHi("小红");
        System.out.println(result);
    }
}

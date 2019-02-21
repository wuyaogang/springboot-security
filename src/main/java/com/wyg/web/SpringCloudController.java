package com.wyg.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by A14857 on 2019/2/21.
 */
@RestController
public class SpringCloudController {
    @Autowired
    private RestTemplate restTemplate;
    //http://localhost:8080/redis/setget.html?data=test&key=test
     @GetMapping("/api/setget.json")
    public String env(String key, String data){
        String str =  restTemplate.getForObject("http://localhost:8082/redis/setget.html?key="+key+"&data="+data,String.class);
        return str;
    }
}

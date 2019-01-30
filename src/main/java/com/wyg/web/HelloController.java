package com.wyg.web;

import com.wyg.po.User;
import com.wyg.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by A14857 on 2019/1/23.
 */
@RestController //相当于@Controller+@ResponseBody。
public class HelloController {
    @Autowired
    private IUserService userService;
    @RequestMapping(value= "/hello", method= RequestMethod.GET)
    public String hello(){
        return "hello world!";
    }
    @RequestMapping(value= "/query/{id}", method= RequestMethod.GET)
    public User toIndex(HttpServletRequest request, @PathVariable Integer id){

        //int userId = Integer.parseInt(request.getParameter("id"));
        User user = this.userService.getUserById(1);
        return user;
    }

    @RequestMapping(value= "/add", method= RequestMethod.GET)
    public int toAdd(){
        User user = new User();
        user.setName("zhangsan");
        user.setAge(17);
       int num = this.userService.addUser(user);
        return num;
    }
}

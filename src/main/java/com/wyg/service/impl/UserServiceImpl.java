package com.wyg.service.impl;

import com.wyg.dao.UserMapper;
import com.wyg.po.User;
import com.wyg.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by A14857 on 2019/1/23.
 */
@Service
public class UserServiceImpl implements IUserService{
    @Resource
    private UserMapper userMapper;
    @Override
    public User getUserById(int userId) {
        return userMapper.selectByPrimaryKey(1L);
    }

    @Override
    public int addUser(User record) {

        return userMapper.insert(record);
    }
}

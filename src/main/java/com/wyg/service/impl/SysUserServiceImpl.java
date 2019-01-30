package com.wyg.service.impl;

import com.wyg.dao.SysUserMapper;
import com.wyg.po.SysUser;
import com.wyg.service.ISysUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by A14857 on 2019/1/30.
 */
@Service
public class SysUserServiceImpl implements ISysUserService{
    @Resource
    private SysUserMapper sysUserMapper;
    @Override
    public int addUser(SysUser record) {
        //进行加密
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        record.setPassword(encoder.encode(record.getPassword().trim()));
        return sysUserMapper.insert(record);
    }
}

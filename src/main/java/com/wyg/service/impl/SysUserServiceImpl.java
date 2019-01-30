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

    /**
     * 虽然每次 BCryptPasswordEncoder 的 encoder 结果都不一样，但是存贮其中一次加密结果 也能够验证成功
     * 这里是加密方式
     * @param record
     * @return
     */
    @Override
    public int addUser(SysUser record) {
        //进行加密
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        record.setPassword(encoder.encode(record.getPassword().trim()));
        return sysUserMapper.insert(record);
    }
}

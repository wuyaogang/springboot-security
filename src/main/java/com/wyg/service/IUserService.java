package com.wyg.service;

import com.wyg.po.User;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by A14857 on 2019/1/23.
 */
public interface IUserService {
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User getUserById(int userId);
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    int addUser(User record);
}

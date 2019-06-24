package com.reven.service.impl;

import com.reven.dao.UserMapper;
import com.reven.model.entity.User;
import com.reven.service.IUserService;
import com.reven.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/06/24.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements IUserService {
    @Resource
    private UserMapper userMapper;

}

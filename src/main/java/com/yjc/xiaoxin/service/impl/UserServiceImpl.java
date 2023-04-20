package com.yjc.xiaoxin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjc.xiaoxin.domain.User;
import com.yjc.xiaoxin.service.UserService;
import com.yjc.xiaoxin.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2023-04-17 16:21:36
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}





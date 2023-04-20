package com.yjc.xiaoxin.mapper;

import com.yjc.xiaoxin.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DELL
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2023-04-17 16:21:36
* @Entity com.yjc.xiaoxin.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}





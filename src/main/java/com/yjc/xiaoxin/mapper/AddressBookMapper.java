package com.yjc.xiaoxin.mapper;

import com.yjc.xiaoxin.domain.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DELL
* @description 针对表【address_book(地址管理)】的数据库操作Mapper
* @createDate 2023-04-17 20:35:32
* @Entity com.yjc.xiaoxin.domain.AddressBook
*/
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}





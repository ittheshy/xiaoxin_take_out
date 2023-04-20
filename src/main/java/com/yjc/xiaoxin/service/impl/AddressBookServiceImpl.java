package com.yjc.xiaoxin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjc.xiaoxin.domain.AddressBook;
import com.yjc.xiaoxin.service.AddressBookService;
import com.yjc.xiaoxin.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2023-04-17 20:35:32
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}





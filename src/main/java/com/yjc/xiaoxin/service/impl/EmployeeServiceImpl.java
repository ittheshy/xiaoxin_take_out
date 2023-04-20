package com.yjc.xiaoxin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjc.xiaoxin.domain.Employee;
import com.yjc.xiaoxin.service.EmployeeService;
import com.yjc.xiaoxin.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2023-04-11 15:01:01
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{
}





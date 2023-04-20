package com.yjc.xiaoxin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjc.xiaoxin.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DELL
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2023-04-11 15:01:00
* @Entity generator.domain.Employee
*/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}





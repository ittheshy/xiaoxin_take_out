package com.yjc.xiaoxin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjc.xiaoxin.common.R;
import com.yjc.xiaoxin.domain.Employee;
import com.yjc.xiaoxin.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;


    /**
     * 员工登录
     * @param httpServletRequest
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee){
         //判断账户是否存在
        String username = employee.getUsername();
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(lqw);
        if(emp == null){
            return R.error("账户不存在");
        }

        //将密码用md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //判断密码是否正确
        if(!emp.getPassword().equals(password)){
            return R.error("密码不正确");
        }

        //账号是否被封禁
        if(emp.getStatus() == 0){
            return R.error("账号已经被封禁");
        }

        //将账户id存到session中
        httpServletRequest.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 退出登录
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 添加员工
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        //md5加密默认密码（默认密码为123456)
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        //给员工设置相关信息
        employee.setPassword(password);
        Long id = (Long)request.getSession().getAttribute("employee");
        employeeService.save(employee);
        return R.success("添加用户成功");
    }

    /**
     * 分页请求
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);
        //构造分页构造器
        Page pageInfo = new Page<>(page,pageSize);
        //构建查询构造器
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotBlank(name),Employee::getName,name);
        //添加排序条件
        lqw.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,lqw);
        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     * @param request
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        Long id = (Long)request.getSession().getAttribute("employee");
        employeeService.updateById(employee);
        return R.success("员工修改成功");
    }

    /**
     * 通过id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> findById(@PathVariable Long id){
        log.info("通过id查询用户信息");
         Employee employee = employeeService.getById(id);
         return R.success(employee);
    }
}

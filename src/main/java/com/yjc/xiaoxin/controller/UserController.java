package com.yjc.xiaoxin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yjc.xiaoxin.common.R;
import com.yjc.xiaoxin.domain.User;
import com.yjc.xiaoxin.service.UserService;
import com.yjc.xiaoxin.utils.SMSUtils;
import com.yjc.xiaoxin.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 发送短信验证码
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
          //获取手机号
          String phone = user.getPhone();
          if(StringUtils.isNotBlank(phone)){
              //生成随机的4位验证码
              String code = ValidateCodeUtils.generateValidateCode(4).toString();
              log.info(code);
              //调用阿里云的api发送短信
//              SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,code);
              //将生成的验证码保存到Session
              session.setAttribute(phone,code);
              return R.success("手机验证码发送成功");
          }
          return R.error("发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,HttpServletRequest request){
         log.info(map.toString());
         String phone = map.get("phone").toString();
         String code = map.get("code").toString();
         //获取存到Session中的验证码
         Object codeInSession = request.getSession().getAttribute(phone);
         if(codeInSession != null && codeInSession.equals(code)){
             LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
             lqw.eq(User::getPhone,phone);
             User user = userService.getOne(lqw);
             if(user == null){
                  user = new User();
                 user.setPhone(phone);
                 user.setStatus(1);
                 userService.save(user);
             }
             request.getSession().setAttribute("user",user.getId());
             return R.success(user);
         }
         return R.error("登录失败");
    }
}

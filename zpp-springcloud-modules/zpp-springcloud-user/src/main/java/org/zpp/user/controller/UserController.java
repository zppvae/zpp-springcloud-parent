package org.zpp.user.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zpp.api.dto.UserDTO;
import org.zpp.common.core.constant.MQConstant;
import org.zpp.common.core.template.MobileCodeTemplate;
import org.zpp.common.core.util.R;
import org.zpp.common.security.vo.SecurityUser;
import org.zpp.security.core.util.SecurityUtils;
import org.zpp.user.service.SysUserService;

/**
 * @author zpp
 * @date 2018/12/10 15:42
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SysUserService userService;

    @GetMapping("/username/{username}")
    public UserDTO getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/me")
    public SecurityUser getMe(){
        return (SecurityUser)SecurityUtils.getSecurityUser();
    }

    /**
     * 新增用户
     * @return
     */
    @PostMapping
    public R addUser(@RequestBody UserDTO dto){
        userService.saveUser(dto);
        return new R();
    }


    /**
     * 发送短信验证码
     * @return
     */
    @PostMapping("/sms/mobile/{phone}")
    public String send(){
        rabbitTemplate.convertAndSend(MQConstant.QUEUE_MOBILE_CODE,
                new MobileCodeTemplate("",MQConstant.MOBILE_CODE_CHANNEL_ALI,"" ));
        return "";
    }
}

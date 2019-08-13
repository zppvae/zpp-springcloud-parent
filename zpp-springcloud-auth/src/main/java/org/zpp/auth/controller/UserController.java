package org.zpp.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zpp
 * @date 2019/8/9 10:49
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/info")
    public UserDetails get(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }
}

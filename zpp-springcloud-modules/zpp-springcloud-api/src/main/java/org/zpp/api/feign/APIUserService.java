package org.zpp.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zpp.api.dto.UserDTO;
import org.zpp.common.constant.ServiceConstant;

/**
 * @author zpp
 * @date 2019/8/9 16:00
 */
@FeignClient(value = ServiceConstant.USER_SERVICE)
public interface APIUserService {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/user/{username}")
    UserDTO getUserByUsername(@PathVariable("username") String username);
}

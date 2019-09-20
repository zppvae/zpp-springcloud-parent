package org.zpp.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zpp.api.dto.UserDTO;
import org.zpp.api.feign.fallback.factory.APIUserServiceFallbackFactory;
import org.zpp.common.core.constant.ServiceConstant;

/**
 * https://cloud.spring.io/spring-cloud-static/Greenwich.SR1/single/spring-cloud.html#_feign_querymap_support
 * @SpringQueryMap：映射查询参数
 *
 * @author zpp
 * @date 2019/8/9 16:00
 */
@FeignClient(value = ServiceConstant.USER_SERVICE,fallbackFactory = APIUserServiceFallbackFactory.class)
public interface APIUserService {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/user/username/{username}")
    UserDTO getUserByUsername(@PathVariable("username") String username);
}

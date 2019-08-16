package org.zpp.api.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.zpp.api.dto.UserDTO;
import org.zpp.api.feign.APIUserService;

/**
 * @author zpp
 * @date 2019/8/16 16:29
 */
@Component
@Slf4j
public class APIUserServiceFallback implements APIUserService {

    @Setter
    private Throwable cause;

    @Override
    public UserDTO getUserByUsername(String username) {
        log.error("[getUserByUsername] - fallback; reason was: {}",cause.getMessage());
        return null;
    }
}

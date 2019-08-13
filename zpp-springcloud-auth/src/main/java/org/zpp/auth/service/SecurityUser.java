package org.zpp.auth.service;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;

/**
 * @author zpp
 * @date 2019/8/9 15:34
 */
public class SecurityUser extends User implements SocialUserDetails {

    @Getter
    private Integer id;

    public SecurityUser(Integer userId,String username, String password,
                        boolean enabled, boolean accountNonExpired,
                        boolean credentialsNonExpired, boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
        this.id = userId;
    }

    @Override
    public String getUserId() {
        return getUsername();
    }
}

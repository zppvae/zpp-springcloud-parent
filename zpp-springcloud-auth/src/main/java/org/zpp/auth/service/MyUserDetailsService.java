package org.zpp.auth.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;
import org.zpp.api.dto.UserDTO;
import org.zpp.api.feign.APIUserService;
import org.zpp.api.model.SysUser;
import org.zpp.common.security.vo.SecurityUser;

import java.util.Collection;

/**
 *
 * 此类由业务系统提供
 * @author zpp
 *
 */
@Component
@Slf4j
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

	private final PasswordEncoder passwordEncoder;

	private final APIUserService apiUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("[用户登录] - [{}]",username);
		return buildUser(username);
	}

	/**
	 *
	 * @param userId  社交登录表的userId
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		log.info("[社交登录用户Id] - [{}]",userId);
		return buildUser(userId);
	}

	private SocialUserDetails buildUser(String username) {
		// 根据用户名查找用户信息
		UserDTO dto = apiUserService.getUserByUsername(username);
		if (dto == null) {
			throw new UsernameNotFoundException("用户"+username+"不存在");
		}
		//根据查找到的用户信息判断用户是否被冻结
		String password = passwordEncoder.encode("123456");

		Collection<? extends GrantedAuthority> authorities
				= AuthorityUtils.createAuthorityList(dto.getPermissions().toArray(new String[0]));

		SysUser user = dto.getUser();
		return new SecurityUser(user.getUserId(),username,password,true,true,
				true,true, authorities);
	}

}

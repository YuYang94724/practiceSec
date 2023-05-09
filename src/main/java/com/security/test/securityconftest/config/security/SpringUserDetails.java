package com.security.test.securityconftest.config.security;

import com.security.test.securityconftest.member.data.repo.MemberRepositroy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class SpringUserDetails implements UserDetailsService {
    @Resource
    private MemberRepositroy memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .map(v-> new User(
                        v.getUsername(),
                        v.getPassword(),
                        Set.of(new SimpleGrantedAuthority(v.getRole().name()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException(""));
    }
}

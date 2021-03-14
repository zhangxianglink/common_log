package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.model.PermissionEntity;
import com.example.demo.model.UserEntity;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户是否存在
        UserEntity byUsername = userMapper.findByUsername(username);
        if (byUsername == null){
            return null;
        }
        // 查询对应用户的权限
         List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<PermissionEntity> permissionEntityList = userMapper.findPermissionByUsername(username);
        permissionEntityList.forEach(user -> {
            authorities.add(new SimpleGrantedAuthority(user.getPermTag()));
        });
        // 将权限添加到security
        byUsername.setAuthorities(authorities);
        return byUsername;
    }
}

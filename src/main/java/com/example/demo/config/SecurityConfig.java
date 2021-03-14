package com.example.demo.config;

import com.example.demo.dao.PermissionMapper;
import com.example.demo.model.PermissionEntity;
import com.example.demo.service.MyUserDetailsService;
import com.example.demo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("admin").password("123456")
//                .authorities("admin","user");
//        auth.inMemoryAuthentication().withUser("user").password("123456")
//                .authorities("user");

        auth.userDetailsService(myUserDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return MD5Util.encode((String) charSequence);
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                String encode = MD5Util.encode((String) charSequence);
                boolean bool = encode.equals(s);
                return bool;
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/**").fullyAuthenticated()
//                .and().httpBasic();

//        http.authorizeRequests().antMatchers("/admin").hasAnyAuthority("showMember")
//                .antMatchers("/user").hasAnyAuthority("user")
//                .antMatchers("/**").fullyAuthenticated()
//                .and().formLogin();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = http.authorizeRequests();
        List<PermissionEntity> allPermission = permissionMapper.findAllPermission();
        allPermission.forEach(permissionEntity -> {
            expressionInterceptUrlRegistry.antMatchers(permissionEntity.getUrl())
                    .hasAnyAuthority(permissionEntity.getPermTag());
        });
        expressionInterceptUrlRegistry.antMatchers("/**").fullyAuthenticated()
                .and().formLogin().and().csrf().disable();

    }
}

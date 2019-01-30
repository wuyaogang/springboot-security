package com.wyg.config;

import com.wyg.service.auto.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Created by A14857 on 2019/1/25.
 * 写一个类，继承WebSecurityConfigurerAdapter类，这是Spring Security的重头戏，是一个配置类，需要放在Config包中。
 不要忘记写@Configuration注解
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//方法安全权限开启
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    @Autowired
    UserDetailsService customUserService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService); //user Details Service验证

    }
    //在这里配置哪些页面不需要认证
    /*@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css*//**", "/templates*//**");
    }*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 所有用户均可访问的资源
                .antMatchers("/","/css/**","/login","/templates/**").permitAll()
                //.anyRequest().authenticated() //任何请求,登录后可以访问
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll() //登录页面用户任意访问
                .and()
                .logout().permitAll() //注销行为任意访问
                //退出登录后的默认网址是”/home”
                .logoutSuccessUrl("/login")
                .permitAll()
                .invalidateHttpSession(true)
                .and()
                //登录后记住用户，下次自动登录
                //数据库中必须存在名为persistent_logins的表
                //建表语句见code15
                .rememberMe()
                .tokenValiditySeconds(1209600);
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);

    }
}

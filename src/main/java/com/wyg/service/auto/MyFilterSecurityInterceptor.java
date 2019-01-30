package com.wyg.service.auto;

import com.wyg.component.MyAccessDecisionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by yangyibo on 17/1/19.
 * 准备:

 　　1、Spring Security需要自定义一个继承至AbstractSecurityInterceptor的Filter，该抽象类包含了AccessDecisionManager(决策管理器)、AuthenticationManager(身份认证管理器)的setter， 可以通过Spring自动注入，另外，资源角色授权器需要单独自定义注入

 　　2、AccessDecisionManager(决策管理器)的实现需要实现AccessDecisionManager接口，在实现的decide(Authentication arg0, Object arg1,Collection<ConfigAttribute> arg2)方法中,需要将用户具有的角色权限Collection<GrantedAuthority> grantedAuthorities=arg0.getAuthorities();与访问该资源所需要的角色权限Collection<ConfigAttribute> arg2进行比较，若有一个角色匹配，则放行允许该用户访问此资源。

 　　3、AuthenticationManager(身份认证管理器)可以通过applicationContext-security.xml中<authentication-manager />标签实现。该标签需要引用一个实现了UserDetailService接口的类。该类的loadUserByUsername(String username)方法，通过传进来的用户名返回一个User对象，构造该User对象时需要传入GrantedAuthority的Collection，此时可以通过不同的用户名赋予不同的GrantedAuthority。

 　　4、资源角色授权器需要实现FilterInvocationSecurityMetadataSource接口。请求的资源所需要的角色权限在服务器启动时候就已经确定的，所以在该实现类的构造方法中需要确定每一种资源需要那些角色权限，通过一个Map<String, List<ConfigAttribute>>即可将所有资源所需要的List<ConfigAttribute>存储起来。该实现类中getAttributes(Object arg0)方法，可以通过请求的url返回对应的Collection<ConfigAttribute>，通过传进来的FilterInvocation可以得到RequestUrl,然后遍历Map<String, List<ConfigAttribute>>。

 一、定义继承至AbstractSecurityInterceptor的CustomSecurityFilter。
 这一套核心流程具体每条的实现由AbstractSecurityInterceptor 实现，
 也就是说AbstractSecurityInterceptor 只是定义了一些行为，然后这些行为的安排，也就是执行流程则是由具体的子类所实现，
 AbstractSecurityInterceptor 虽然也叫Interceptor ,但是并没有继承和实现任何和过滤器相关的类，
 具体和过滤器有关的部分是由子类所定义。每一种受保护对象都拥有继承自AbstrachSecurityInterceptor的拦截器类。
 spring security 提供了两个具体实现类，MethodSecurityInterceptor 将用于受保护的方法，FilterSecurityInterceptor 用于受保护的web 请求。

 1.查找当前请求里分配的"配置属性"。
 2.把安全对象，当前的Authentication和配置属性,提交给AccessDecisionManager来进行以此认证决定。
 3.有可能在调用的过程中,对Authentication进行修改。
 4.允许安全对象进行处理（假设访问被允许了）。
 5.在调用返回的时候执行配置的AfterInvocationManager。如果调用引发异常,AfterInvocationManager将不会被调用。

 1.先将正在请求调用的受保护对象传递给beforeInvocation()方法进行权限鉴定。
 2.权限鉴定失败就直接抛出异常了。
 3.鉴定成功将尝试调用受保护对象，调用完成后，不管是成功调用，还是抛出异常，都将执行finallyInvocation()。
 4如果在调用受保护对象后没有抛出异常，则调用afterInvocation()。
 */
@Service
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {


    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }


    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        //fi里面有一个被拦截的url
        //里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
        //再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }


    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;

    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
}

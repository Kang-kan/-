package com.markerhub.shiro;
import cn.hutool.json.JSONString;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.markerhub.common.lang.Result;
import com.markerhub.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**AuthenticatingFilter:实现基本的自动登录逻辑*/
@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if(StringUtils.isEmpty(jwt)) {
            return null;
        }

        return new JwtToken(jwt);
    }

    /**判断token是否过期,抛出异常*/
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        /*登录完成,Token保存在header里面,现在获取headerToken信息*/
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isEmpty(jwt)){
            return true;
        }else{
            /*1.校验token*/
            Claims claims = jwtUtils.getClaimByToken(jwt);
            /*判断是否为空和判断是否过期*/
            if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
                throw new ExpiredCredentialsException("Token已失效,请重新登录");
            }
            //执行登录
            return executeLogin(servletRequest,servletResponse);
        }

    }

    /**执行登录异常,重写异常信息,使用自定义结果,用Json格式返回前端*/
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        /*1.获取错误的原因*/
        Throwable throwable = e.getCause() == null ? e : e.getCause();

        Result error = Result.error(throwable.getMessage());

        String Json = JSONUtil.toJsonStr(error);

        HttpServletResponse request1 = (HttpServletResponse) response;
        try {
            request1.getWriter().print(Json);
        } catch (IOException ioException) {

        }

        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }

}

package com.markerhub.shiro;

import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import com.markerhub.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    /**1.需要告知token格式*/
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**获取用户信息,拿到其角色权限并返回给shiro*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**校验token,对用户身份进行校验*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;

        /*1.获取用户信息*/
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        /*1.1利用userID查询用户信息*/
        User user = userService.getById(userId);
        /*2.判断信息是否存在,并返回相对于的异常信息*/
        if (user==null){
            throw  new UnknownAccountException("账户不存在");
        }
        if (user.getStatus() == -1){
            throw  new UnknownAccountException("账户已被锁定");
        }
        /*3.把一些不私密的用户信息返回去*/
        AccountProfile accountProfile = new AccountProfile();
        /*4.利用bean工具把用户实体信息复制过去*/
        BeanUtils.copyProperties(user,accountProfile);

        /*返回用户信息,token,用户名字*/
        return new SimpleAuthenticationInfo(accountProfile, jwtToken.getCredentials(), getName());
    }
}

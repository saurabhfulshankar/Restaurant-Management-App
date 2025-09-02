package com.alchemist.yoru.Tenant;

import com.alchemist.yoru.service.impl.AuthUserDetailImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class Interceptor implements HandlerInterceptor {

    private final AuthUserDetailImpl authUserDetail;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String tenantId = authUserDetail.getUserDetails().getUsername();
        Context.setTenantId(tenantId);
        // Set tenant ID as a request attribute
        return true;
    }
}


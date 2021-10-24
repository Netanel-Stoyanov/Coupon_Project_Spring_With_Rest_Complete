package com.nikitacouponproject.service.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Data
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserDetails {
    private Integer id;
    private String name;
}

package com.nikitacouponproject.filter;

import com.nikitacouponproject.exception.LoginException;
import com.nikitacouponproject.service.security.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoginFilter implements Filter {

    private ApplicationContext applicationContext;

    public LoginFilter(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String authorization = httpServletRequest.getHeader("Authorization");

        if (!authorization.startsWith("Bearer")){
            throw new LoginException("you are not allowed to login ");
        }
        String token = authorization.substring(7);

        try {

            Jws<Claims> jwsParsed = Jwts.parserBuilder()
                    .setSigningKey("sakjdfhaskkljsdnfkjnrnskjnfkldnsfkljsdkljndskljnsdfkjsdnkjsdfnkjsdnsdkjlnsdklnsdjhf8wrkadjh9832oaweh".getBytes())
                    .build()
                    .parseClaimsJws(token);
            Claims tokenBody = jwsParsed.getBody();

            UserDetails userDetails = applicationContext.getBean(UserDetails.class);
            userDetails.setName(tokenBody.get("name").toString());
            userDetails.setId(Integer.parseInt(tokenBody.get("user-id").toString()));

        }catch (Exception e){
            e.printStackTrace();
            throw new LoginException("something go wrong with your token");
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}

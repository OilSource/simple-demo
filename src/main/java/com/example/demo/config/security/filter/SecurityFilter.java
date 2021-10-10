package com.example.demo.config.security.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.*;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.util.SurenessContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Order(1)
@Component
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean optionsFlag = false;
        long start = System.currentTimeMillis();
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "OPTIONS, POST, PUT, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,Authorization");
            if(StrUtil.isNotEmpty(request.getMethod()) && request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())){
                optionsFlag =true;
                response.setStatus(HttpStatus.ACCEPTED.value());
            } else {
                SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(servletRequest);
                if (subject != null) {
                    SurenessContextHolder.bindSubject(subject);
                }
            }
        } catch (IncorrectCredentialsException | UnknownAccountException | ExpiredCredentialsException e1) {
            log.warn("this request account info is illegal");
            responseWrite(ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED).body("bad request, can not Auth"), servletResponse);
            return;
        } catch (DisabledAccountException | ExcessiveAttemptsException e2) {
            log.warn("the account is disabled");
            responseWrite(ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED).body(e2.getMessage()), servletResponse);
            return;
        } catch (UnauthorizedException e5) {
            log.warn("this account can not access this resource");
            responseWrite(ResponseEntity
                    .status(HttpStatus.FORBIDDEN).body(e5.getMessage()), servletResponse);
            return;
        } catch (RuntimeException e) {
            log.error("other exception happen: ", e);
            responseWrite(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(),
                    servletResponse);
            return;
        }
        try {
            // if ok, doFilter and add subject in request
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if(!optionsFlag){
                logResponseBody(request,System.currentTimeMillis()- start);
            }
            SurenessContextHolder.clear();
        }

    }

    private void responseWrite(ResponseEntity<?> content, ServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        ((HttpServletResponse) response).setStatus(content.getStatusCodeValue());
        content.getHeaders().forEach((key, value) ->
                ((HttpServletResponse) response).addHeader(key, value.get(0)));
        try (PrintWriter printWriter = response.getWriter()) {
            if (content.getBody() != null) {
                if (content.getBody() instanceof String) {
                    printWriter.write(content.getBody().toString());
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    printWriter.write(objectMapper.writeValueAsString(content.getBody()));
                }
            } else {
                printWriter.flush();
            }
        } catch (IOException e) {
            log.error("responseWrite response error: ", e);
        }
    }

    public void logResponseBody(HttpServletRequest request,long useTime){
        log.info("{} {} 耗时:{} ms",request.getRequestURI(),request.getMethod(),useTime);
    }
}

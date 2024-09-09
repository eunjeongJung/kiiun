package com.cx_project.kiiun.global.auth.filter;

import com.cx_project.kiiun.global.exception.CustomException;
import com.cx_project.kiiun.global.exception.CustomExceptionEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;

    public ExceptionHandlerFilter(ObjectMapper mapper){
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(e.getErrorCode().getStatus().value());
            CustomExceptionEntity error = new CustomExceptionEntity(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
            response.getWriter().write(mapper.writeValueAsString(error));
        }
    }

}
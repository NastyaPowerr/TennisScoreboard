//package org.roadmap.tennisscoreboard.filter;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletResponse;
//import org.roadmap.tennisscoreboard.exception.PlayerAlreadyExistsException;
//import tools.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@WebFilter("/*")
//public class ExceptionFilter implements Filter {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse resp = (HttpServletResponse) servletResponse;
//        try {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } catch (PlayerAlreadyExistsException ex) {
//            resp.setContentType("application/json");
//            resp.setStatus(400);
//            Map<String, String> error = new HashMap<>();
//            error.put("message", ex.getMessage());
//
//            String jsonError = objectMapper.writeValueAsString(error);
//            resp.getWriter().write(jsonError);
//        }
//    }
//}

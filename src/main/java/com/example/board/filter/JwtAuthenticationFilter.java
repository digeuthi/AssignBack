package com.example.board.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.board.provider.JwtProvider;
import com.example.board.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    
    private final JwtProvider jwtProvider;
    private final UserService userService;

    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                try{
                    String jwt = parseToken(request);
                    
                    // 토큰이 null이거나 비어 있는지 확인합니다.
                    if (jwt == null || jwt.isEmpty()) {
                        filterChain.doFilter(request, response);
                        return;
            }

                    // 토큰이 "Bearer "로 시작하는 지 확인합니다.
                    if (!jwt.startsWith("Bearer ")) {
                    return;
            }

                    // 토큰에서 "Bearer " 접두사를 제거합니다.
                    jwt = jwt.substring(7);

                    // boolean hasJwt = jwt != null;
                    // if(!hasJwt) {
                    //     filterChain.doFilter(request, response);
                    //     return;
                    // }
                    String email = jwtProvider.validate(jwt);

                    boolean comparedResult = userService.validateStoredToken(email, jwt);
                    
                    if (!comparedResult) {
                        filterChain.doFilter(request, response);
                        return ;
                    }

                    AbstractAuthenticationToken authenticationToken = 
                    new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(authenticationToken);
                    SecurityContextHolder.setContext(securityContext);




                } catch (Exception exception){
                    exception.printStackTrace();

                }
                filterChain.doFilter(request, response);

    }
    private String parseToken(HttpServletRequest request){

        String token = request.getHeader("Authorization");
        
        // 토큰이 null이거나 비어 있는지 확인합니다.
        if (token == null || token.isEmpty()) {
            return null;
        }

        // 토큰이 "Bearer "로 시작하는 지 확인합니다.
        if (!token.startsWith("Bearer ")) {
            return null;
        }
        
        // boolean hasToken = 
        //     token != null && 
        //     !token.equalsIgnoreCase("null");
        // if(!hasToken) return null;

        // boolean isbearer = token.startsWith("Bearer ");
        // if(!isbearer) return null;

        String jwt = token.substring(7);
        return jwt;

    
}

}

package InstagramProject.InstagramProject.filters;

import com.auth0.jwt.JWT;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
public class TokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader("authorization").replace("Bearer ", "");
            String id = JWT.decode(token).getClaim("id").asString();
            String first_name = JWT.decode(token).getClaim("first_name").asString();
            String last_name = JWT.decode(token).getClaim("last_name").asString();
            String email = JWT.decode(token).getClaim("email").asString();
            String password = JWT.decode(token).getClaim("password").asString();

            filterChain.doFilter(request, response);
        }catch (Exception e){
            response.setStatus(401);
            response.getWriter().println("Invalid credentials!");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/login") || path.startsWith("/register");
    }


}

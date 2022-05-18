package tiberiu.assignment2.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tiberiu.assignment2.bussiness.AccountService;
import tiberiu.assignment2.model.Admin;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final byte[] JWT_TOKEN_SECRET = "Tiberiu".getBytes();

    private final AccountService accountService;

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AccountService accountService, AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.info("Trying to log in: " + username + " pass: " + password);
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication ath;
        try {
            ath = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return ath;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //org.springframework.security.core.userdetails.User

        log.info("Successful authentication");
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_SECRET); //not something you would do in a production "Tiberiu"
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 20))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

//        response.setHeader("access_token", access_token);
//        response.setHeader("refresh_token", refresh_token);


        // Alternative to the commented thing above:
//        Map<String, String> tokens = new HashMap<>();
//        tokens.put("access_token", access_token);
//        tokens.put("refresh_token", refresh_token);
//        response.setContentType(APPLICATION_JSON_VALUE);
//        new ObjectMapper().writeValue(response.getOutputStream(), tokens);

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000"); //hardcoded and necessary
        response.setHeader("Access-Control-Allow-Headers", "content-type"); //hardcoded and necessary
        response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,POST"); //hardcoded and necessary
        response.setHeader("Access-Control-Allow-Credentials", "true"); //hardcoded and necessary
        response.setHeader("Access-Control-Max-Age", "1800"); //hardcoded and necessary

        response.addCookie(new Cookie("access_token", access_token));
        response.addCookie(new Cookie("refresh_token", refresh_token));

        Long id = Long.getLong("-1"); // used for later
        //id cookie
        Optional<?> optional = accountService.login(user.getUsername());
        if (optional.isPresent()) {
            if (optional.get() instanceof tiberiu.assignment2.model.User u) {
                id = u.getId();
            }
            if (optional.get() instanceof Admin u) {
                id = u.getId();
            }
        }

        //writing the body
        PrintWriter write = response.getWriter();
        write.print("{ \"role\": \"" + user.getAuthorities().toArray()[0].toString() +
                "\", \"username\": \"" + user.getUsername() + "\", \"id\":" + id + " }");

    }
}

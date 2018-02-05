package pl.playerony.cantor.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pl.playerony.cantor.database.models.Role;
import pl.playerony.cantor.database.models.User;
import pl.playerony.cantor.database.services.RoleService;
import pl.playerony.cantor.database.services.UserService;
import pl.playerony.cantor.exceptions.CantorRestApiException;

public class TokenHelper {
	private final String SECRET = "CantorRestApi";
	private final String TOKEN_PREFIX = "Bearer";
	private final String HEADER_STRING = "Authorization";
	private final int EXPIRES_IN = 10000;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	public HttpServletResponse addAuthentication(HttpServletResponse response, String username) {
		Claims claims = Jwts.claims().setSubject(username);
		String roleName = "";
		Long userId = null;
		
		try {
			User user = userService.fetchUserByUsername(username);
			Role role = roleService.fetchRoleByRoleId(user.getRoleId());
			userId = user.getUserId();
			roleName = role.getRoleName();
		} catch(CantorRestApiException e) {
			e.printStackTrace();
		}
		
		claims.put("id", userId);
		claims.put("role", roleName);
		
		String JWT = Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setExpiration(new Date(System.currentTimeMillis() + (EXPIRES_IN * 1000)))
                .compact();
		
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");

        JSONObject jsonResponse = new JSONObject();

        try {
            jsonResponse.put("token", TOKEN_PREFIX + " " + JWT);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");

        try {
            response.getWriter().append(jsonResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
	}
	
	public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            String username = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            List<Role> roles = new ArrayList<>();

            try {
                User user = userService.fetchUserByUsername(username);
                Role role = roleService.fetchRoleByRoleId(user.getRoleId());
                roles.add(role);
            } catch (CantorRestApiException e) {
                e.printStackTrace();
            }

            return username != null ? new UsernamePasswordAuthenticationToken(username, null, roles) : null;
        }

        return null;
    }

	public UserService getUserService() {
		return userService;
	}

	public RoleService getRoleService() {
		return roleService;
	}
	
}

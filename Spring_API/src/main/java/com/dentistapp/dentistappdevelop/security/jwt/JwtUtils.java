package com.dentistapp.dentistappdevelop.security.jwt;

import com.dentistapp.dentistappdevelop.security.redis.config.RedisUtil;
import com.dentistapp.dentistappdevelop.service.impl.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.*;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.Language;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${dentist.app.jwtSecret}")
    private String jwtSecret;
    @Value("${dentist.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    private static final String REDIS_SET_ACTIVE_SUBJECTS = "active-subjects";
    @Autowired
    RedisUtil redisUtil;


    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();


        String token = Jwts.builder()
                .setId(userPrincipal.getId().toString())
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> getUserIdFromJwtToken(String token) {
        String data = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getId();
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> idList = gson.fromJson(data, type );
        return idList;
    }

    public List<String> getRoles(Authentication authentication) {
        String data = authentication.getAuthorities().toString();
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> rolesList = gson.fromJson(data, type );
        return rolesList;
    }

    public boolean validateJwtToken(String authToken) {

        if (redisUtil.sismember(REDIS_SET_ACTIVE_SUBJECTS, authToken)) {
            return false;
        }
        try {
            if (redisUtil.sismember(REDIS_SET_ACTIVE_SUBJECTS, authToken)) {
                throw new MalformedJwtException("Invalid JWT token: {}");
            }
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public void invalidateRelatedTokens(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        redisUtil.sadd(REDIS_SET_ACTIVE_SUBJECTS, token.substring(7, token.length()));
    }
}

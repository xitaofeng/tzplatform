package com.tzplatform.utils.jwtauth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtils {

    private static final String SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQKsdfkjsdrow32234545fdfcombanc.com.cn>?N<:{LWPW";

    private static final String PARAM_NAME = "account";

    private static final String CREATE_TIME = "createtime";

    private static final String PARAM_USERID = "userid";


    public static String createToken(String username) {
        String token = "";
        try {
            Map<String, Object> configmap = new HashMap<String, Object>();
            configmap.put("alg", "HS256");
            configmap.put("typ", "JWT");
            token = JWT.create().withHeader(configmap).withClaim(PARAM_NAME, username).withClaim(CREATE_TIME, System.currentTimeMillis()).sign(Algorithm.HMAC256(SECRET));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();

        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return token;
    }

    public static String veriftToken(String token) {
        String account = "";
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            account = claims.get(PARAM_NAME).asString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return account;
    }

    public static String veriftUseridToken(String token) {
        String userid = "";
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            userid = claims.get(PARAM_USERID).asString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return userid;
    }

    public static String createUserToken(String username, String userid) {
        String token = "";
        try {
            Map<String, Object> configmap = new HashMap<String, Object>();
            configmap.put("alg", "HS256");
            configmap.put("typ", "JWT");
            token = JWT.create().withHeader(configmap).withClaim(PARAM_NAME, username).withClaim(PARAM_USERID, userid).withClaim(CREATE_TIME, System.currentTimeMillis()).sign(Algorithm.HMAC256(SECRET));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return token;
    }
}

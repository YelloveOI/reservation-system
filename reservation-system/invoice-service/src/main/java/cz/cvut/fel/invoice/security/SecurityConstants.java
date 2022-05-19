package cz.cvut.fel.invoice.security;

import com.auth0.jwt.algorithms.Algorithm;

public class SecurityConstants {
    public static String JWT_SECRET = "TopSecretPassword";
    public static String TOKEN_PREFIX = "Bearer ";
    public static Long JWT_ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 24 * 1000 * 365L;
    public static Algorithm JWT_ALGORITHM = Algorithm.HMAC256(JWT_SECRET);
}

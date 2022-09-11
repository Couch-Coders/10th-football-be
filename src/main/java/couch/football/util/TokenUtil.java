package couch.football.util;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {

    public static String getAuthorizationToken(String header){
        //토큰 : Bearer <access_token>

        //토큰이 없거나 Bearer 토큰이 아닌 경우
        if(header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header");
        }
        //토큰이 있는 경우
        String[] parts = header.split(" ");
        //parts => Bearer[0] <access_token>[1]

        //토큰 형태가 다른 경우
        if(parts.length != 2) {
            throw new IllegalArgumentException("Invalid authorization header");
        }

        //정삭적인 경우
        return parts[1];
    }

    //Filter
    public static String getAuthorizationToken (HttpServletRequest request) {
        return getAuthorizationToken(request.getHeader("Authorization"));
    }
}

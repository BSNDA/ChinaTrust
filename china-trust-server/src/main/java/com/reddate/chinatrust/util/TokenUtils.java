package com.reddate.chinatrust.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class TokenUtils {
	private static String SECRET = "bsndidtesttoken@bn.com";
	
	
//	public static String createJWTToken() throws Exception {
//		Date iatDate = new Date();
//		Calendar nowTime = Calendar.getInstance();
//		nowTime.add(Calendar.YEAR,1);
//		Date expiresDate = nowTime.getTime();
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("typ", "JWT");
//		map.put("alg", "HS256");
//
//		String token = JWT.create()
//				.withHeader(map)//头部 header
//                .withClaim("iss", "bsn-did-hub")
//                .withClaim("aud", "bsn-did-01")
//				.withExpiresAt(expiresDate)
//				.withIssuedAt(iatDate)
//				.sign(Algorithm.HMAC256(SECRET));
//		return token;
//	}
//
//
//	public static Map<String, Claim> verifyToken(String token)throws Exception{
//		JWTVerifier verify = JWT.require(Algorithm.HMAC256(SECRET)).build();
//		DecodedJWT jwt = null;
//		try {
//			jwt = verify.verify(token);
//		} catch (Exception e) {
//			System.out.println("登录凭证已过去，请重新登录");
//		}
//		return jwt.getClaims();
//	}
	
	
	
//	public static void main(String[] args) {
//		System.out.println("测试获取token");
//		try {
//			String token = TokenUtils.createJWTToken();
//			System.out.println(token);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}









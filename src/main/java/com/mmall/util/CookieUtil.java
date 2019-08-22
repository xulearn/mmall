package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = ".happytest.com";   //.happymmall.com   //作用域
    private final static String COOKIE_NAME = "mmall_login_token";


    //此处的cookie读写都是站在服务器的角度
    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();    //request携带cookie到服务器，服务器从发过来的请求里拿cookie
        if(cks != null){
            for(Cookie ck : cks){
                log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();//返回sessionid
                }
            }
        }
        return null;
    }

    public static void writeLoginToken(HttpServletResponse response, String token){//TOKEN 就是sessionid
        Cookie ck = new Cookie(COOKIE_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);    //设置cookie作用域
        ck.setPath("/");//代表设置在根目录，表示根目录下的页面或者代码才能访问这个cookie

        ck.setMaxAge(60*60*24*365);//-1表示永久，单位是秒， 不设置setmaxage，cookie就不会写入硬盘，而是写在内存，只在当前页面有效
        log.info("write cookieName:{}, cookieValue:{}",ck.getName(),ck.getValue());
        response.addCookie(ck); //添加cookie返回给浏览器
    }

    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);    //设置有效期为0，代表删除此cookie
                    log.info("del cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    response.addCookie(ck); //response返回给浏览器后，浏览器删除cookie，因为有效期为0
                    return;
                }
            }
        }
    }

}

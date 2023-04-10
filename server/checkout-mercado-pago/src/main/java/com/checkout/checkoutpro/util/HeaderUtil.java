package com.checkout.checkoutpro.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HeaderUtil {

    public  static String getAuthorization(){
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs != null) {
            HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
            return request.getHeader("Authorization") ;
        }

        throw new IllegalArgumentException("Request must not be null!");
    }
}

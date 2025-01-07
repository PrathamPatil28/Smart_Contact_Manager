package com.example.SCM.helper;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

@Component
public class SessionHelper {

    public static void removeMessage(){
        try {
            System.out.println("removing message from session");
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes())
                    .getRequest()
                    .getSession();
            session.removeAttribute("message");
        }
        catch (Exception e){
            System.out.println("Error in SessionHelper"  + e);
            e.printStackTrace();
        }
    }
}

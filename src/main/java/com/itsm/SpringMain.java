package com.itsm;

import com.itsm.core.Server;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

public class SpringMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        context.registerShutdownHook();

        Runnable server = (Runnable) context.getBean("Server");
         server.run(); //TODO: Config docker file



    }
}

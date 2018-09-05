package com.itsm;

import com.itsm.core.Server;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        context.registerShutdownHook();

        Runnable server = (Runnable) context.getBean("Server");
        // server.run(); //TODO: Config docker file
        System.out.println( server.toString());



    }
}

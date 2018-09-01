package com.itsm;

import com.itsm.core.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.itsm")
@PropertySource("classpath:server.properties")
public class SpringConfig {

    @Autowired
    private Environment environment;

    @Bean
    public Server getServer(){
        return new Server(
                environment.getProperty("server.messagedelay", Integer.class, 100),
                environment.getProperty("server.port",Integer.class, 8088),
                environment.getProperty("server.threadcount", Integer.class, 4));
    }

}

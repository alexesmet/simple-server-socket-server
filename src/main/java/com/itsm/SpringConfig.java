package com.itsm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsm.core.Server;
import com.itsm.processors.RequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.inject.Provider;
import java.util.List;

@Configuration
@ComponentScan("com.itsm")
@PropertySource("classpath:server.properties")
public class SpringConfig {

    @Autowired
    private Environment environment;

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean/*(initMethod = "init")*/
    public Server getServer(Provider<List<RequestProcessor>> requestProcessorProvider, ObjectMapper objectMapper){
        return new Server(
                environment.getProperty("server.messagedelay", Integer.class, 100),
                environment.getProperty("server.port",Integer.class, 8088),
                environment.getProperty("server.threadcount", Integer.class, 4),
                objectMapper,
                requestProcessorProvider);
    }

}

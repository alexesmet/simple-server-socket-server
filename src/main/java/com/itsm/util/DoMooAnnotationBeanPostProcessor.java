package com.itsm.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

@Component
public class DoMooAnnotationBeanPostProcessor implements BeanPostProcessor{

    private Map<String, Object> beans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        boolean match = Arrays.stream(bean.getClass().getMethods())
                .anyMatch(m -> suits(m));
        if (match) {
            beans.put(beanName, bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beans.containsKey(beanName)) {
            Object original = beans.get(beanName);
            Object beanProxy = Proxy.newProxyInstance(
                    original.getClass().getClassLoader(),
                    original.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        Object result = method.invoke(original, args);
                        if ( suits(method) ){
                            result = "MOOOOOOOOOOOO!!!"; //TODO: find out how to see if it had annotations
                        }
                        return result;
                    }
            );
            return beanProxy;
        }
        return bean;
    }

    private boolean suits(Method method){
        return method.isAnnotationPresent(DoMoo.class)
                || method.getName().contains("DoMoo")
                || Arrays.stream(method.getDeclaringClass().getInterfaces())
                        .anyMatch(i -> i == Mooable.class);
    }
}

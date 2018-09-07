package com.itsm.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

@Component
public class DoMooAnnotationBeanPostProcessor implements BeanPostProcessor{

    private Map<String, BeanMarkedMethods> beans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Method[] beanMethods = bean.getClass().getMethods();
        for (Method m : beanMethods) {
            if (suits(m)){
                if (beans.containsKey(beanName)){
                    beans.get(beanName).add(m);
                } else {
                    beans.put(beanName,new BeanMarkedMethods(bean, m));
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beans.containsKey(beanName)) {
            BeanMarkedMethods beanMarkedMethods = beans.get(beanName);
            Object original = beanMarkedMethods.getOriginalBean();
            return Proxy.newProxyInstance(
                    original.getClass().getClassLoader(),
                    original.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        Object result = method.invoke(original, args);
                        if ( beanMarkedMethods.contains(method.getName())){
                            result = "MOOOOOOOOOOOO!!!";
                        }
                        return result;
                    }
            );

        }
        return bean;
    }

    private boolean suits(Method method){
        return method.isAnnotationPresent(DoMoo.class)
                || method.getName().contains("DoMoo")
                || Arrays.stream(method.getDeclaringClass().getInterfaces())
                        .anyMatch(i -> i == Mooable.class);
    }

    private class BeanMarkedMethods{
        private final Object originalBean;
        private List<String> methods;

        BeanMarkedMethods(Object originalBean, Method firstMethod) {
            this.originalBean = originalBean;
            this.methods = new ArrayList<>();
            this.methods.add(firstMethod.getName());
        }

        Object getOriginalBean() {
            return originalBean;
        }

        void add(Method m){
            methods.add(m.getName());
        }

        boolean contains(String name){
            return methods.contains(name);
        }
    }
}

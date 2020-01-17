package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: mzp
 * @License: (C) Copyright 2019-2099, TC Corporation Limited.
 * @Date: 2020/1/17 13:26
 * @Version: 1.0
 * @Description: SpringBeanUtil
 */
@Component
@Slf4j
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtil.applicationContext == null) {
            SpringBeanUtil.applicationContext = applicationContext;
        }
        log.info("\r\n----------加载applicationContext成功-----------------");
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            char[] cs = clazz.getSimpleName().toCharArray();
            // 首字母大写到小写
            cs[0] += 32;
            return (T) getApplicationContext().getBean(String.valueOf(cs));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 但是在使用的时候，发现有些时候在线程中获取 bean 失败，查找原因是在使用 Spring 注解时没有自定义名字，
     * eg. @Service("service") ，这样在使用接口名获取时就会获取不到，
     * 因为 Spring 默认使用的是 @Service("serviceImpl") 这样的命名规则，
     * 所以为了避免手误未自定义命名，改造了一下其中的一个方法。
     *
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSuperBean(Class<T> clazz) {
        try {
            char[] cs = clazz.getSimpleName().toCharArray();
            // 首字母大写到小写
            cs[0] += 32;
            return (T) getApplicationContext().getBean(String.valueOf(cs));
        } catch (Exception e) {
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> interfaceClazz : interfaces) {
                if (clazz.getSimpleName().contains(interfaceClazz.getSimpleName())) {
                    char[] cs = interfaceClazz.getSimpleName().toCharArray();
                    // 首字母大写到小写
                    cs[0] += 32;
                    return (T) getApplicationContext().getBean(String.valueOf(cs));
                }
            }
            return null;
        }
    }

}

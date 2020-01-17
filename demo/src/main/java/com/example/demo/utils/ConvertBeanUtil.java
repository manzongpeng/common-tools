package com.example.demo.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: mzp
 * @License: (C) Copyright 2019-2099, TC Corporation Limited.
 * @Date: 2019/12/10 13:37
 * @Version: 1.0
 * @Description: Bean转换工具类
 */
public class ConvertBeanUtil {
    /**
     * Bean转换集合
     *
     * @param fromValues  转换类
     * @param toValueType 目标集合类型
     * @param <T>
     * @return
     */
    public static <T> List<T> convertList(List<?> fromValues, Class<T> toValueType) {
        List<T> instances = new ArrayList<T>();
        if (fromValues == null || fromValues.isEmpty()) {
            return instances;
        }
        fromValues.stream().forEach(fromValue -> {
            T instance = null;
            try {
                if (fromValue != null) {
                    instance = toValueType.newInstance();
                    BeanUtil.copyProperties(fromValue, instance);
                    instances.add(instance);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return instances;
    }

    /**
     * 转换Bean
     *
     * @param fromValue   转换类
     * @param toValueType 目标类型
     * @param <T>
     * @return
     */
    public static <T> T convert(Object fromValue, Class<T> toValueType) {
        T instance = null;
        try {
            instance = toValueType.newInstance();
            if (fromValue != null) {
                BeanUtil.copyProperties(fromValue, instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 转换mybatisPlus Page数据
     *
     * @param fromValues
     * @param toValueType
     * @param <T>
     * @return
     */
    public static <T> IPage<T> convertIPage(IPage<?> fromValues, Class<T> toValueType) {
        IPage iPage = null;
        if (null != fromValues) {
            iPage = new Page();
            List<T> convertList = convertList(fromValues.getRecords(), toValueType);
            iPage.setRecords(convertList);
            iPage.setCurrent(fromValues.getCurrent());
            iPage.setPages(fromValues.getPages());
            iPage.setSize(fromValues.getSize());
            iPage.setTotal(fromValues.getTotal());
        }
        return iPage;
    }

    /**
     * 获取对象为空的属性
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


}

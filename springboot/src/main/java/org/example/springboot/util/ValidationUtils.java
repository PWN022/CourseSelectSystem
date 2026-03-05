package org.example.springboot.util;

import org.example.springboot.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * 参数验证工具类
 */
public class ValidationUtils {
    
    /**
     * 验证对象不为空
     */
    public static void validateNotNull(Object obj, String message) {
        if (obj == null) {
            throw new ServiceException(message);
        }
    }
    
    /**
     * 验证字符串不为空
     */
    public static void validateNotBlank(String str, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new ServiceException(message);
        }
    }
    
    /**
     * 验证列表不为空
     */
    public static void validateNotEmpty(List<?> list, String message) {
        if (list == null || list.isEmpty()) {
            throw new ServiceException(message);
        }
    }
    
    /**
     * 验证数字为正数
     */
    public static void validatePositive(Number number, String message) {
        if (number == null || number.longValue() <= 0) {
            throw new ServiceException(message);
        }
    }
    
    /**
     * 安全地从Map中获取Long值
     */
    public static Long getLongFromMap(Map<String, Object> map, String key, String errorMessage) {
        Object value = map.get(key);
        if (value == null) {
            throw new ServiceException(errorMessage);
        }
        
        try {
            if (value instanceof Number) {
                return ((Number) value).longValue();
            } else {
                return Long.valueOf(value.toString());
            }
        } catch (NumberFormatException e) {
            throw new ServiceException(errorMessage + "：格式错误");
        }
    }
    
    /**
     * 安全地从Map中获取String值
     */
    public static String getStringFromMap(Map<String, Object> map, String key, String errorMessage) {
        Object value = map.get(key);
        if (value == null) {
            throw new ServiceException(errorMessage);
        }
        return value.toString();
    }
    
    /**
     * 安全地从Map中获取List值
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getListFromMap(Map<String, Object> map, String key, String errorMessage) {
        Object value = map.get(key);
        if (value == null) {
            throw new ServiceException(errorMessage);
        }
        
        try {
            return (List<T>) value;
        } catch (ClassCastException e) {
            throw new ServiceException(errorMessage + "：类型错误");
        }
    }
    
    /**
     * 验证邮箱格式
     */
    public static void validateEmail(String email, String message) {
        if (email == null || !email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            throw new ServiceException(message);
        }
    }
    
    /**
     * 验证手机号格式
     */
    public static void validatePhone(String phone, String message) {
        if (phone != null && !phone.matches("^1[3-9]\\d{9}$")) {
            throw new ServiceException(message);
        }
    }
}

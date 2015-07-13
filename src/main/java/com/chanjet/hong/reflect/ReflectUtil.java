/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *    
 * <p>
 * 反射工具类
 *</p>
 * @author 洪光华 <br/>
 * @Email honggh@chanjet.com 
 * @date 2014年12月12日 上午10:20:22
 *
 * @version V1.0     
 *     
 */
@SuppressWarnings("all")
public class ReflectUtil {
	//获得指定变量的值
	public static Object getValue(Object instance, String fieldName)   throws   IllegalAccessException, NoSuchFieldException{
	    Field field = getField(instance.getClass(), fieldName);
	    // 参数值为true，禁用访问控制检查
	    field.setAccessible(true);
	    return field.get(instance);
	}
	//该方法实现根据变量名获得该变量的值
	public static Field getField(Class thisClass, String fieldName)   throws NoSuchFieldException {
	    if (thisClass == null) {
	        throw new NoSuchFieldException("Error field !");
	    }
	    return thisClass.getField(fieldName);
	}
	
	public static Method getMethod(Object instance, String methodName, Class[] classTypes) throws NoSuchMethodException {
		Method accessMethod = getMethod(instance.getClass(), methodName, classTypes);
		// 参数值为true，禁用访问控制检查
		accessMethod.setAccessible(true);
		return accessMethod;
	}

	private static Method getMethod(Class thisClass, String methodName, Class[] classTypes) throws NoSuchMethodException {
		if (thisClass == null) {
			throw new NoSuchMethodException("Error method !");
		}
		try {
			return thisClass.getDeclaredMethod(methodName, classTypes);
		} catch (NoSuchMethodException e) {
			return getMethod(thisClass.getSuperclass(), methodName, classTypes);
		}
	}
	
	// 调用含单个参数的方法
	public static Object invokeMethod(Object instance, String methodName, Object arg) 
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Object[] args = new Object[1];
		args[0] = arg;
		return invokeMethod(instance, methodName, args);
	}

	// 调用含多个参数的方法
	public static Object invokeMethod(Object instance, String methodName, Object[] args) 
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Class[] classTypes = null;
		if (args != null) {
			classTypes = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				if (args[i] != null) {
					classTypes[i] = args[i].getClass();
				}
			}
		}
		return getMethod(instance, methodName, classTypes).invoke(instance, args);
	}
}

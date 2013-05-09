package org.waddy.task.core;

import java.util.List;

import org.framework.pub.util.ReflectUtil;

public class Proxy {

	private Object bean;
	
	private String methodName;
	
	private Object[] args;
	
	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	public Object invoke(){
		System.out.println("beanName="+bean.getClass().getSimpleName()+", methodName="+methodName+", args="+args[0]);
		return ReflectUtil.invoke(bean, methodName, args);
	}

}

package org.waddy.task.core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.framework.pub.util.common.StringUtil;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskContext {
	
	private static ApplicationContext context;
	private static ConfigPaser parser;
	
	static {
		init();
	}

	public static void init(){
		context = new ClassPathXmlApplicationContext("classpath*:spring-svc*.xml");
		parser = new ConfigPaser();
	}
	
	public static Object getBean(String beanName){
		return context.getBean(beanName);
	}
	
	public static Class getBeanType(String beanName){
		return context.getType(beanName);
	}
	
	public static Vector<String> getNodes(){
		Vector<String> nodes = new Vector<String>();
		File platformConfigFile = new File("./conf/platform.xml");
		SAXBuilder builder = new SAXBuilder();
		try {
			Document config = builder.build(platformConfigFile);
			List<Element> elements = config.getRootElement().getChild("cluster").getChildren("node");
			for(Element element : elements){
				String uri = element.getAttributeValue("uri");
				System.out.println(uri);
				if(!StringUtil.isBlank(uri)){
					nodes.add("http://"+uri+"/axis2/services/");
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}

	public static void main(String[] args) {
		System.out.println("nodes="+TaskContext.getNodes());;
		System.out.println("type="+TaskContext.getBeanType("dataExtractSVC"));;
	}
}

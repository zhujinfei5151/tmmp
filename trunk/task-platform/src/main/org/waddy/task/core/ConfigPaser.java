package org.waddy.task.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.jdom.Element;

public class ConfigPaser {
	private static final Logger logger = Logger.getLogger(ConfigPaser.class);

	public Vector<String> getEndpoints(){
		Vector<String> endpoints = new Vector<String>();
		return endpoints;
	}
	
	public Map<String, Object> getBeanMap(Element config){
		Map<String, Object> beanMap = new HashMap<String, Object>();
		Element beanConfig =config.getChild("bean");
		String beanName = beanConfig.getAttributeValue("name");
		logger.debug("beanName="+beanName);
		
		Element methodConfig =beanConfig.getChild("method");
		String methodName = methodConfig.getAttributeValue("name");
		logger.debug("methodName="+methodName);

		Vector<Object> args = new Vector<Object>();
		List<Element> params = methodConfig.getChildren("param"); 
		for(Element param : params){
			String argStr = param.getAttributeValue("value");
			args.add(Integer.valueOf(argStr).intValue());
		}
		logger.debug("args="+args);

		beanMap.put("beanName", beanName);
		beanMap.put("methodName", methodName);
		beanMap.put("args", args);
		
		return beanMap;
	}
	
}

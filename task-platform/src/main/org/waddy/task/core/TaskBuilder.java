package org.waddy.task.core;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class TaskBuilder extends SAXBuilder {
	private static final Logger logger = Logger.getLogger(TaskBuilder.class);

	private ConfigPaser parser;
	
	public TaskBuilder() {
		parser = new ConfigPaser();
	}
	
	public Task buildTask(File configFile) throws Exception{
		Document config = this.build(configFile);
		Element taskConfig = config.getRootElement();
		Inputer inputer = this.buildInputer(taskConfig);
		Vector<Processor> processors = this.buildProcessors(taskConfig);
		Task task = new Task();
		task.setInputer(inputer);
		task.setProcessors(processors);
		return task;
	}
	
	public Inputer buildInputer(Element config){
		Element inputerConfig =config.getChild("inputer");
		Map<String, Object> beanMap = parser.getBeanMap(inputerConfig);
		logger.debug(beanMap);
		
		//构造processor组件，从本地spring容器中构造代理
		Object bean = TaskContext.getBean((String) beanMap.get("beanName"));
//		String className = beanConfig.getAttributeValue("class");
//		System.out.println("className="+className);
//		Class clazz = Class.forName(className);
//		Constructor constructor = clazz.getConstructor();
//		Object bean = constructor.newInstance();
		
		//构造inputer组件，在本地构造即可
		Inputer inputer = new Inputer();
		inputer.setBean(bean);
		inputer.setMethodName((String) beanMap.get("methodName"));
		inputer.setArgs(((Vector) beanMap.get("args")).toArray());
		return inputer;
	}
	
	public Vector<Processor> buildProcessors(Element config) throws Exception{
		Vector<Processor> processors = new Vector<Processor>();

		Element processorConfig =config.getChild("processor");
		Map<String, Object> beanMap = parser.getBeanMap(processorConfig);
		logger.debug(beanMap);
		
		/**
		 * 构造processor组件，构造远程WS代理
		 */
		String beanName = (String) beanMap.get("beanName");
		String methodName = (String) beanMap.get("methodName");
		//以下构造namespace（注意：默认应是服务类对应包名称的逆序）
		String packageName = TaskContext.getBeanType(beanName).getPackage().getName();
		String nameSpace = "";
		for(String tmp : packageName.split("\\.")){
			nameSpace = "."+tmp+nameSpace;
		}
		nameSpace ="http://"+nameSpace.substring(1);
		logger.debug("nameSpace="+nameSpace);
		//以下根据服务节点构造相应的执行代理（有几个节点，就对应构造几个执行代理）
		Vector<String> nodes = TaskContext.getNodes();
//		nodes.add("http://localhost:8080/AxisSvc/services/MySVC");
//		nodes.add("http://localhost:8080/AxisSvc/services/CalculateSVC");
		for(String node : nodes){
			String endpointUri = node+beanName;
			Call call = (Call) new Service().createCall();
			call.setTargetEndpointAddress(new URL(endpointUri));
			QName qName = new QName(nameSpace, methodName);
//			QName qName = new QName(endpointUri, "isAlive");
			call.setOperationName(qName);
			Processor processor = new Processor();
			processor.setCall(call);
			processors.add(processor);
		}

		return processors;
	}
	
}

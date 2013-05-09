package org.waddy.task.master;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class TaskBuilder extends SAXBuilder {
	private static final Logger logger = Logger.getLogger(TaskBuilder.class);

	public TaskBuilder() {
	}
	/**
	 * 扫描并加载task.xml，并对每个task进行初始化和启动
	 * @return 实际加载的任务数量
	 */
	public List<MasterTask> loadTasks(File configFile) throws Exception {
		List<MasterTask> tasks = new LinkedList<MasterTask>();
		Document doc = this.build(configFile);
		Element taskConfig = doc.getRootElement();
		List<Element> taskletConfigs = taskConfig.getChildren("tasklet");
		for(Element config : taskletConfigs){
			Tasklet tasklet = buildTasklet(config);
			tasks.add(new MasterTask(tasklet));
		}
		return tasks;
	}
	
	/**
	 * 扫描并加载task.xml，并对每个task进行初始化
	 * @return 实际加载的任务数量
	 */
	public List<Tasklet> loadTasklets(File configFile) throws Exception {
		List<Tasklet> tasklets = new ArrayList<Tasklet>();
		Document doc = this.build(configFile);
		Element taskConfig = doc.getRootElement();
		List<Element> taskletConfigs = taskConfig.getChildren("tasklet");
		for(Element config : taskletConfigs){
			Tasklet tasklet = buildTasklet(config);
			tasklets.add(tasklet);
		}
		return tasklets;
	}
	
	/**
	 * 根据配置构造tasklet
	 * @param config
	 * @return
	 */
	public Tasklet buildTasklet(Element taskletConfig){
		String taskId = taskletConfig.getAttributeValue("id");
		String taskName = taskletConfig.getAttributeValue("name");
		String beanName = taskletConfig.getAttributeValue("bean");
		logger.debug(beanName);
		
		//构造processor组件，从本地spring容器中构造代理
		Tasklet tasklet = (Tasklet)TaskContext.getBean(beanName);
		tasklet.setTaskId(taskId);
		tasklet.setTaskName(taskName);
//		String className = beanConfig.getAttributeValue("class");
//		System.out.println("className="+className);
//		Class clazz = Class.forName(className);
//		Constructor constructor = clazz.getConstructor();
//		Object bean = constructor.newInstance();
		
		return tasklet;
	}
	
}

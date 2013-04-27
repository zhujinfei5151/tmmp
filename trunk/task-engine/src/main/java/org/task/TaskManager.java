/**
 * 
 */
package org.task;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.task.AbstractTask;

/**
 * @Title TaskManager.java
 * @version 1.0
 * @author DapengZhang
 * @Created 2012-12-10
 * Copyright (c) 2012 PanGene InfoAnalytics.
 *
 * @Description: 任务管理器，主要负责任务的实例化和加载执行
 */
public class TaskManager {
	private static final Logger logger = Logger.getLogger(TaskManager.class);
	
	private ApplicationContext context;
	private List<AbstractTask> tasks = new LinkedList<AbstractTask>();
	
	public static void main(String[] args) {
		logger.debug("args="+Arrays.toString(args));
		TaskManager taskManager = new TaskManager();
		taskManager.init();
		taskManager.loadTasks(args);
		taskManager.launchTasks();
	}
	
	public void init(){
		System.out.println("任务管理器初始化...");
		context = new ClassPathXmlApplicationContext("classpath*:spring-task.xml");
	}
	
	/**
	 * 加载任务群（根据配置文件）
	 */
	public void loadTasks(String... taskNames){
		logger.info("加载任务执行组件...");
		if(taskNames==null || taskNames.length==0){
			taskNames = context.getBeanNamesForType(AbstractTask.class);
		}
		for( String taskName : taskNames){
			logger.info("加载"+taskName);
			AbstractTask task = (AbstractTask) context.getBean(taskName);
			logger.info(task);
			tasks.add(task);
		}
	}
	
	/**
	 * 启动任务群
	 */
	public void launchTasks(){
		if(tasks == null || tasks.size()==0 ){
			logger.warn("无待执行任务，系统停止运行。");
			return;
		}
		AbstractTask task1=null;
		for(AbstractTask task : tasks){
			logger.info("执行"+task.getClass().getSimpleName()+", interval="+task.getInterval());
			new Timer().schedule(task, 0, task.getInterval() * 1000);
			task1 = task;
		}
		try {
			Thread.sleep(30*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		task1.setRunFlag(true);
	}
	
}

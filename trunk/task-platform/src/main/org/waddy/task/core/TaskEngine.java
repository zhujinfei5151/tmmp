package org.waddy.task.core;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import org.apache.log4j.Logger;

public class TaskEngine {
	private static final Logger logger = Logger.getLogger(TaskEngine.class);
	
	private TaskBuilder builder = new TaskBuilder();
	
	/** 任务列表 */
	private List<Task> tasks = new LinkedList<Task>();

	public TaskEngine() {
		// TODO Auto-generated constructor stub
	}
	
	public void init(){
		loadTasks();
	}
	
	public void start(){
		for(Task task : tasks){
			new Timer().schedule(task, 0, 10*1000);
		}
	}
	
	/**
	 * 扫描并加载task.xml，并对每个task进行初始化和启动
	 * @return 实际加载的任务数量
	 */
	private int loadTasks(){
		//扫描指定目录下的*-task.xml
		logger.info("scanning *-task.xml ...");
		File configDir = new File("./deploy");
		File[] files = configDir.listFiles();
		//解析每个task.xml
		//为每个task构造各运行代理组件：inputer\processors
		//封装成task对象，加入tasks列表中
		for(File taskConfig : files){
			logger.info("加载 "+taskConfig);
			try {
				Task task = builder.buildTask(taskConfig);
				tasks.add(task);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public static void main(String[] args) {
		TaskEngine taskEngine = new TaskEngine();
		taskEngine.init();
		taskEngine.start();
//		System.out.println("101%2="+101%2);
//		System.out.println("102%2="+102%2);
//		System.out.println("103%2="+103%2);
	}
}

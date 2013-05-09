package org.waddy.task.master;

public class MasterBootloader {
	
	private TaskEngine engine = new TaskEngine();

	public void init() throws Exception{
		//加载并初始化各task
		engine.init();
		//初始化runtimeTable
	}
	
	public void start() throws Exception{
		init();
		//启动各task-timer，从数据库中提取数据，向相应dataQueue中注入任务数据
		engine.start();
		//启动runtimeMonitor
	}
	
}

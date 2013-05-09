package org.waddy.task.slave;

public class SlaveBootloader {
	
	private TaskEngine engine = new TaskEngine();

	public void init() throws Exception{
		//加载并初始化各task
		engine.init();
		//向master发起注册
	}
	
	public void start() throws Exception{
		init();
		//启动各task线程，while循环，请求-处理-请求-...
		engine.start();
	}
	
}

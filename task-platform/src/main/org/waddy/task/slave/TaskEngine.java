package org.waddy.task.slave;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.waddy.task.master.TaskBuilder;
import org.waddy.task.master.Tasklet;

public class TaskEngine {
	private static final Logger logger = Logger.getLogger(TaskEngine.class);
	
	private TaskBuilder builder = new TaskBuilder();
	
	/** 任务列表 */
	private List<Tasklet> tasklets;

	public TaskEngine() {
		// TODO Auto-generated constructor stub
	}
	
	public void init() throws Exception{
		logger.info("加载任务...");
		File taskConfig = new File("G:/WorkSpace/CodeSpace/PgiaCode/ias/TaskPlatform/conf/task.xml");
		tasklets = builder.loadTasklets(taskConfig);
		logger.info("成功加载任务数量："+tasklets.size());
	}
	
	public void start(){
		logger.info("启动任务...");
		for(Tasklet tasklet : tasklets){
			logger.info("启动任务："+tasklet.getTaskId()+"-"+tasklet.getTaskName());
			new SlaveTask(tasklet).start();
		}
	}
	
	public static void main(String[] args) throws Exception {
		TaskEngine taskEngine = new TaskEngine();
		taskEngine.init();
		taskEngine.start();
	}
}

package org.waddy.task.master;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

public class TaskEngine {
	private static final Logger logger = Logger.getLogger(TaskEngine.class);
	
	private TaskBuilder builder = new TaskBuilder();
	
	/** 任务列表 */
	private List<Tasklet> tasklets;

	public TaskEngine() {
		// TODO Auto-generated constructor stub
	}
	
	public void init() throws Exception{
		//获取应用的根目录，对应tomcat的bin目录，如E:\WEBserver\apache-tomcat-6.0.35\bin
		String path = System.getProperty("user.dir");
		System.out.println("path="+path);
		//临时兼容eclipse的解决办法，生产时须去掉
		if(!path.contains("tmmp")){
			path = "G:/WorkSpace/CodeSpace/PgiaCode/ias/tmmp";
		}
		System.out.println("path="+path);
		logger.info("加载任务...");
		File taskConfig = new File(path+"/conf/task.xml");
		tasklets = builder.loadTasklets(taskConfig);
		logger.info("成功加载任务数量："+tasklets.size());
	}
	
	public void start(){
		logger.info("启动任务...");
		for(Tasklet tasklet : tasklets){
			logger.info("启动任务："+tasklet.getTaskId()+"-"+tasklet.getTaskName());
			DataQueue.initTaskQueue(tasklet);
			new MasterTask(tasklet).start();
		}
	}
	
	public static void main(String[] args) throws Exception {
		//在eclipse下执行时，要进行此处设置。因为在eclipse环境中，user.dir环境变量对应的是 eclipse工具所在目录
//		System.setProperty("user.dir", "G:/WorkSpace/CodeSpace/PgiaCode/ias/TaskPlatform");
//		String path = Class.class.getClass().getResource("/").getPath().substring(1);
//		System.out.println("path="+path);
		TaskEngine taskEngine = new TaskEngine();
		taskEngine.init();
//		taskEngine.start();
		
	}
}

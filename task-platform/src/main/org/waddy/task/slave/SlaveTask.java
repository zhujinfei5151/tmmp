package org.waddy.task.slave;

import java.util.List;

import org.apache.log4j.Logger;
import org.waddy.task.master.Task;
import org.waddy.task.master.Tasklet;

public class SlaveTask extends Task {
	private static final Logger logger = Logger.getLogger(SlaveTask.class);
	
	private RequestProxy requestor; //后续考虑根据外部配置注入，可以基于ws、rmi等多种类型
	
	public SlaveTask(Tasklet tasklet) {
		super(tasklet);
		requestor = new RequestProxy();
		requestor.init();
	}

	@Override
	protected void doOnce() {
		// 发起任务数据请求，获取任务数据请求
		logger.debug("发起任务数据请求...");
		String[] taskDatas = requestor.request(this.getTaskId());
		// 如果无任务数据，则休眠10秒
		if(taskDatas==null || taskDatas.length==0){
			logger.debug("未请求到任务数据，休眠10秒...");
			try {
				Thread.sleep(1000*10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		// 处理任务数据
		logger.info("成功请求到任务数据"+taskDatas.length+"条。进行处理...");
		for(Object taskData : taskDatas){
			tasklet.process(taskData);
		}
	}
			
}

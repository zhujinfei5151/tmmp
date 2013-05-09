package org.waddy.task.master;

import java.util.List;

import org.apache.log4j.Logger;

public class MasterTask extends Task {
	private static final Logger logger = Logger.getLogger(MasterTask.class);
	
	public MasterTask(Tasklet tasklet) {
		super(tasklet);
	}


	public void doOnce(){
		String taskId = this.getTaskId();
		//调用inputer提取数据，并注入到dataQueue中
		while(true){
			if(DataQueue.size(taskId)<500){
				List<String> datas = tasklet.input(100);
				if(datas!=null && datas.size()>0){
					DataQueue.add(taskId,datas);
					logger.debug("成功向任务队列["+taskId+"]中注入数据"+datas.size()+"条.");
				}else{
					logger.debug("数据库中无数据，休眠10秒...");
					try {
						Thread.sleep(1000*10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else{
				logger.debug("内存队列数据已满，休眠10秒...");
				try {
					Thread.sleep(1000*5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}

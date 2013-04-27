/**
 * 文件名：AbstractTask.java
 *
 * 版本信息：v1.0
 * 日期：2011-10-20
 */
package org.waddy.task;

import java.util.TimerTask;

import org.apache.log4j.Logger;

/**
 * Name:AbstractTask<br>
 * Description:安全的任务线程<br>
 *
 * @author 张大朋 2011-10-20 下午04:44:17
 * @version 1.0
 */
public abstract class AbstractTask extends TimerTask {
	public static Logger logger = Logger.getLogger(AbstractTask.class);
	
	/**任务名称*/
	private String taskName;
	
	/**处理序号，用于并发分割任务数据*/
	private int psn;
	
	/**任务周期执行间隔时间，单位：秒钟*/
	private Long interval =60L;
	
	/**任务继续运行标志*/
	private boolean runFlag = true;
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getPsn() {
		return psn;
	}

	public void setPsn(int psn) {
		this.psn = psn;
	}

	public Long getInterval() {
		return interval;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

	public boolean isRunFlag() {
		return runFlag;
	}

	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
	}

	//服务线程首次初始化
	public abstract void init() throws Exception;
	
	//服务线程的执行体
	public abstract void exec() throws Exception;
	
	@Override
	public void run() {
		logger.info("runFlag="+runFlag);
		if(!runFlag){
			logger.info("工作空闲心跳，休眠<"+interval+"秒钟>...");
			return;
		}
		try {
			logger.info("工作周期开始...");
			exec();
			logger.info("工作周期结束.");
		} catch (Exception e) {
			logger.error("执行任务失败：", e);
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+
				":[" +
				"TaskName="+this.taskName+"," +
				"interval="+this.interval+"," +
				"RunFlag="+this.runFlag +
				"]";
	}
	
}

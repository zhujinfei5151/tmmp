/**
 * 文件名：AbstractTask.java
 *
 * 版本信息：v1.0
 * 日期：2011-10-20
 */
package org.task;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.framework.pub.util.ReflectUtil;

/**
 * Name:AbstractTask<br>
 * Description:所有任务组件的父类，增加线程池并发处理特性<br>
 *
 * @author 张大朋 2011-10-20 下午04:44:17
 * @version 1.0
 */
public abstract class AbstractTask<TaskData> extends TimerTask {
	public static Logger logger = Logger.getLogger(AbstractTask.class);
	
	/**任务名称*/
	private String taskName;
	
	/**处理序号，用于并发分割任务数据*/
	private int psn;
	
	/**任务周期执行间隔时间，单位：秒钟*/
	private Long interval =60L;
	
	/**任务继续运行标志*/
	private boolean runFlag = true;
	
	/**服务对象*/
	protected Object serviceObject;
	
	/**服务方法*/
	private String serviceMethod;
	
	/** threadPool : 线程池 */
	private ExecutorService threadPool;

	/** threadSize : 线程池容量 */
	private int threadSize = 10;
	
	public AbstractTask() {
		// 构造并初始化线程池
		threadPool = Executors.newFixedThreadPool(threadSize);
	}

	//服务线程首次初始化
	public abstract void init() throws Exception;

	/**
	 * 初始化线程池
	 */
	private void initThreadPool() {
		if (threadPool == null) {
			threadPool = Executors.newFixedThreadPool(threadSize);
		} else if (threadPool != null && threadPool.isShutdown()) {
			threadPool.shutdown();
			threadPool = Executors.newFixedThreadPool(threadSize);
		}
		logger.info("初始化线程池完毕! size : " + threadSize);
	}

	/**
	 * 每个工作周期初始化加载任务数据队列 
	 */
	protected abstract List<TaskData> initDataQueue() throws Exception;
	
	/**
	 * 服务线程的执行体
	 */
	public void exec() throws Exception{
		logger.info("提取任务数据...");
		List<TaskData> taskDatas = initDataQueue();
		if(taskDatas==null || taskDatas.isEmpty()){
			logger.warn("队列中无待处理目标！");
			return;
		}
		List<Future<String>> fList = new ArrayList<Future<String>>();
		//为每个任务数据封装一个Callable对象，并提交线程池运行
		for(final TaskData data : taskDatas){
			Callable<String> callable = new Callable<String>() {

				public String call() throws Exception {
					long start = System.currentTimeMillis();
					ReflectUtil.invoke(serviceObject, serviceMethod, data);
					long end = System.currentTimeMillis();
					String time = (end-start) / 1000.0 + "秒";
					return "处理数据完成："+data+", 花费时间："+time;
				}
				
			};
			fList.add(threadPool.submit(callable));
		}
		//等待所有工作线程执行完毕，并打印各个任务数据的处理结果
		for (Future<String> f : fList) {
			if (f != null) {
				logger.debug(f.get());
			}
		}
	};
	
	@Override
	public void run() {
		logger.info("runFlag="+runFlag);
		if(!runFlag){
			logger.info("工作空闲心跳，休眠<"+interval+"秒钟>...");
			return;
		}
		try {
			logger.info("工作周期开始...");
			logger.info("启动线程池...");
			initThreadPool();
			long start = System.currentTimeMillis();
			exec();
			long end = System.currentTimeMillis();
			String time = (end-start) / 1000.0 + "秒";
			logger.info("工作周期结束. 花费总时间："+time);
		} catch (Exception e) {
			logger.error("执行任务失败：", e);
		} finally {
			logger.info("此次任务完成，关闭线程池...");
			threadPool.shutdown();
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

	public Object getServiceObject() {
		return serviceObject;
	}

	public void setServiceObject(Object serviceObject) {
		this.serviceObject = serviceObject;
	}

	public String getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}
	
}

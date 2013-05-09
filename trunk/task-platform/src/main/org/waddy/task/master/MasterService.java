package org.waddy.task.master;

import java.util.List;

import org.apache.log4j.Logger;

public class MasterService {
	private static final Logger logger = Logger.getLogger(MasterService.class);
	
	public void init(){
		
	}

	/**
	 * 测试是否存活
	 */
	public String isAlive(){
		return "任务1队列剩余数据量："+DataQueue.size("1")+"条";
	}
	
	/**
	 * 注册slave节点
	 */
	public void register(){
		
	}
	
	/**
	 * 请求任务数据（由Master统一决定每次分给任务请求者多少条任务数据）
	 * @param taskId 任务编号（用于查找指定任务的数据）
	 * @param slaveId 节点编号（用于记录Slave节点的操作历史）
	 * @return 任务数据序列
	 */
	public String[] request(String taskId, String slaveId){
		logger.debug("接收到请求：taskId="+taskId+",slaveId="+slaveId);
		List<String> list = DataQueue.get(taskId);
		System.out.println("datas="+list);
		String[] array = (String[])list.toArray(new String[list.size()]);
		logger.debug(list);
		//从taskDataQueue中获取任务数据，同时更新runtimeTable
		return array;
	}
	
	/**
	 * 请求任务数据（由Slave各自决定每次请求任务数据量，Master尽量满足）
	 * @param taskId
	 * @param slaveId
	 * @param count
	 */
	private List<Object> request(String taskId, String slaveId, int count){
		logger.debug("接收到请求：taskId="+taskId+",slaveId="+slaveId);
		return null;
	}
	
}

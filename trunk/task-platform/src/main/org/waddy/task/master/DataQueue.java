package org.waddy.task.master;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.waddy.task.SampleTasklet;

public class DataQueue {
	//map<taskId,dataQueue>
	private static Map<String,ArrayBlockingQueue<String>> queueMap = new HashMap<String, ArrayBlockingQueue<String>>();
	
	public static Map<String,RuntimeItem> runtimeTable = new HashMap<String, RuntimeItem>();
	
	public static void initTaskQueue(Tasklet tasklet){
		String taskId = tasklet.getTaskId();
		queueMap.put(taskId, new ArrayBlockingQueue<String>(10000));
		RuntimeItem taskRuntime = new RuntimeItem();
		taskRuntime.setTaskId(taskId);
		taskRuntime.setTaskName(tasklet.getTaskName());
		taskRuntime.setStartTime(new Date());
		runtimeTable.put(taskId,taskRuntime);
	}

	/**
	 * 向任务队列中增加任务数据
	 * @param taskId
	 * @param datas
	 */
	public static void add(String taskId, List<String> datas){
		queueMap.get(taskId).addAll(datas);
		runtimeTable.get(taskId).setLatestInputNum(datas.size()+0L);
		runtimeTable.get(taskId).setLatestInputTime(System.currentTimeMillis());
		runtimeTable.get(taskId).setCurrNum(size(taskId)+0L);
	}
	
	/**
	 * 从任务队列中获取任务数据
	 * @param taskId
	 * @return
	 */
	public static List<String> get(String taskId){
		List<String> taskDatas = new ArrayList<String>(100);
		int oldsize = queueMap.get(taskId).size();
		queueMap.get(taskId).drainTo(taskDatas, 100);
		int newsize = queueMap.get(taskId).size();
		System.out.println("oldsize="+oldsize+", newsize="+newsize);
		runtimeTable.get(taskId).setLatestProcessNum(taskDatas.size()+0L);
		runtimeTable.get(taskId).setLatestProcessTime(System.currentTimeMillis());
		runtimeTable.get(taskId).setCurrNum(size(taskId)+0L);
		return taskDatas;
	}
	
	/**
	 * 获取任务队列的剩余数据量
	 * @param taskId
	 * @return
	 */
	public static int size(String taskId){
		return queueMap.get(taskId).size();
	}
	
	public static void main(String[] args) {
		String taskId = "1";
		Tasklet tasklet = new SampleTasklet();
		tasklet.setTaskId(taskId);
		tasklet.setTaskName("测试任务");
		DataQueue.initTaskQueue(tasklet);
		List<String> datas = new LinkedList<String>();
		datas.add("1");
		datas.add("2");
		datas.add("3");
		DataQueue.add(taskId, datas);
		System.out.println(DataQueue.size(taskId));
		System.out.println(DataQueue.get(taskId));
		System.out.println(DataQueue.size(taskId));
		
		System.out.println("-----------");
		System.out.println(DataQueue.runtimeTable);
	}
	
}

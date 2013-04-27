/**
 * 
 */
package org.task;

import java.util.List;

import org.svc.SampleSVC;
import org.task.AbstractTask;

/**
 * @Title SampleTask.java
 * @version 1.0
 * @author DapengZhang
 * @Created 2012-12-12
 * Copyright (c) 2012 PanGene InfoAnalytics.
 *
 * @Description: 一个简单的任务组件的例子
 */
public class SampleTask extends AbstractTask {

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public List initDataQueue() throws Exception {
		SampleSVC svc = (SampleSVC) serviceObject;
		return svc.getDatas();
	}

//  注意：可以覆盖此方法，执行自己的单线程任务方法！
//	@Override
//	public void exec() throws Exception {
//		System.out.println("working hard...");
//		//模拟需要10秒钟来完成此工作
//		Thread.sleep(10*1000);
//	}

}

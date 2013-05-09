package org.waddy.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.waddy.task.master.Tasklet;

public class SampleTasklet extends Tasklet {

	@Override
	public List<String> input(int count) {
		System.out.println("从数据库中提取任务数据...");
		try {
			Thread.sleep(1000*3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<String> taskDatas = new ArrayList<String>();
		Random random = new Random();
		int size = random.nextInt(10);
		for(int i=0; i<size; i++){
			taskDatas.add(""+i);
		}
		return taskDatas;
	}

	@Override
	public void process(Object taskData) {
		System.out.println("处理任务数据...");
		try {
			Thread.sleep(1000*1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

package org.waddy.task.master;

import java.util.List;

import org.apache.log4j.Logger;

public abstract class Task extends Thread {
	private static final Logger logger = Logger.getLogger(Task.class);

	protected Tasklet tasklet;
	
	public Task(Tasklet tasklet) {
		this.tasklet = tasklet;
	}
	
	public String getTaskId(){
		return tasklet.getTaskId();
	}
	
	public String getTaskName(){
		return tasklet.getTaskName();
	}
	
	public void run(){
		while(true){
			doOnce();
		}
	}

	protected abstract void doOnce();
	
}

package org.waddy.task.master;

import java.util.List;

public abstract class Tasklet {
	
	private String taskId;
	
	private String taskName;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public abstract List<String> input(int count);
	
	public abstract void process(Object taskData);
	
}

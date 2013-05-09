package org.waddy.task.master;

import java.util.Date;


public class RuntimeItem {

	private String taskId;
	
	private String taskName;
	
	private Long currNum;
	
	private Date startTime;
	
	private Long totalInputNum = 0L;
	
	private Long totalProcessNum = 0L;
	
	private Long latestInputTime;
	
	private Long latestInputNum;
	
	private Long latestProcessTime;
	
	private Long latestProcessNum;

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

	public Long getCurrNum() {
		return currNum;
	}

	public void setCurrNum(Long currNum) {
		this.currNum = currNum;
	}

	public Long getTotalInputNum() {
		return totalInputNum;
	}

	public void setTotalInputNum(Long totalInputNum) {
		this.totalInputNum = totalInputNum;
	}

	public Long getTotalProcessNum() {
		return totalProcessNum;
	}

	public void setTotalProcessNum(Long totalProcessNum) {
		this.totalProcessNum = totalProcessNum;
	}

	public Long getLatestInputNum() {
		return latestInputNum;
	}

	public void setLatestInputNum(Long latestInputNum) {
		this.latestInputNum = latestInputNum;
		this.totalInputNum += latestInputNum;
	}

	public Long getLatestProcessNum() {
		return latestProcessNum;
	}

	public void setLatestProcessNum(Long latestProcessNum) {
		this.latestProcessNum = latestProcessNum;
		this.totalProcessNum += latestProcessNum;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Long getLatestInputTime() {
		return latestInputTime;
	}

	public void setLatestInputTime(Long latestInputTime) {
		this.latestInputTime = latestInputTime;
	}

	public Long getLatestProcessTime() {
		return latestProcessTime;
	}

	public void setLatestProcessTime(Long latestProcessTime) {
		this.latestProcessTime = latestProcessTime;
	}

	@Override
	public String toString() {
		return this.taskId+","+this.taskName+","+this.currNum+","
					+this.startTime+","+this.totalInputNum+","+this.totalProcessNum+","
					+this.latestInputTime+","+this.latestInputNum+","
					+this.latestProcessTime+","+this.latestProcessNum;
	}
}

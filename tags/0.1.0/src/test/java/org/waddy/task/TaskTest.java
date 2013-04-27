package org.waddy.task;

import java.util.Arrays;

import org.apache.log4j.Logger;

public class TaskTest {
	private static final Logger logger = Logger.getLogger(TaskTest.class);

	/**
	 * @param args void
	 */
	public static void main(String[] args) {
		logger.debug("args="+Arrays.toString(args));
		TaskManager taskManager = new TaskManager();
		taskManager.init();
		taskManager.loadTasks(args);
		taskManager.launchTasks();
	}

}

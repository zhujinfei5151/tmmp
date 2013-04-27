package org;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.task.TaskManager;

public class LanunchTest {
	private static final Logger logger = Logger.getLogger(LanunchTest.class);

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

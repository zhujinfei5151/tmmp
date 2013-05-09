package org.waddy.task.core;

import java.util.List;
import java.util.TimerTask;
import java.util.Vector;

import org.apache.log4j.Logger;

public class Task extends TimerTask{
	private static final Logger logger = Logger.getLogger(Task.class);

	private Inputer inputer;
	
	private Vector<Processor> processors;

	public Vector<Processor> getProcessors() {
		return processors;
	}

	public void setProcessors(Vector<Processor> processors) {
		this.processors = processors;
	}

	public Inputer getInputer() {
		return inputer;
	}

	public void setInputer(Inputer inputer) {
		this.inputer = inputer;
	}
	
	@Override
	public void run() {
		List<Integer> targets = inputer.input();
		System.out.println(targets);
		for(Integer targetId : targets){
			int processorId = targetId%processors.size();
			logger.info("invoke processor-"+processorId);
			processors.get(processorId).process(targetId);
		}
	}
	
}

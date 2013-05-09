package org.waddy.task.core;

import java.util.List;

public class Inputer extends Proxy {

	public List<Integer> input(){
		return (List<Integer>)invoke();
	}
	
}

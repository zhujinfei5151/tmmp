package org.svc;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

public class SampleSVC {
	
	public List getDatas(){
		List datas = new LinkedList();
		datas.add("扫地");
		datas.add("做饭");
		return datas;
	}
	
	public void doSomething(Object data){
		int time = RandomUtils.nextInt(10);
		System.out.println("do a thing: "+data+" ...");
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

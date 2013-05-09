package org.waddy.task.core;

import java.rmi.RemoteException;

import org.apache.axis.client.Call;

public class Processor {
	
	private Call call;
	
	public Call getCall() {
		return call;
	}

	public void setCall(Call call) {
		this.call = call;
	}

	public void process(Integer target){
//		this.setArgs(new Object[]{target});
		try {
			call.invoke(new Object[]{target});
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}

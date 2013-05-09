package org.waddy.task.slave;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.log4j.Logger;

public class RequestProxy {
	private static final Logger logger = Logger.getLogger(RequestProxy.class);
	
	private final String SERVICE_URL = "http://localhost:8080/tmmp/services/MasterService";
    private final String TARGET_NAMESPACE = "http://master.task.waddy.org";
	private RPCServiceClient sender;
    
	private String slaveId = "1001";

	public RequestProxy() {
	}
	
	public void init(){
		try {
			sender = initRpcClient(SERVICE_URL);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
	}
	
    public RPCServiceClient initRpcClient(String serviceUrl) throws AxisFault{
          RPCServiceClient sender = new RPCServiceClient();
          Options options = sender.getOptions();         
          EndpointReference targetEPR = new EndpointReference(serviceUrl);
          options.setTo(targetEPR);
          return sender;
    }
	
	public String[] request(String taskId){
		String[] taskDatas = null;
		
		try {
	          QName qname = new QName(TARGET_NAMESPACE, "request");
	          Object[] args = new Object[] {taskId,slaveId};
	          Class[] returnType = new Class[] { String[].class };
	          Object[] response = sender.invokeBlocking(qname, args, returnType);
	          
	          taskDatas = (String[]) response[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
	        
		return taskDatas;
	}

    public static void main(String[] args) throws AxisFault {
		RequestProxy requestor = new RequestProxy();
		requestor.init();
		requestor.request("1");
	}

}

package org.waddy.task;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class Client {
	private final static String  SERVICE_URL = "http://localhost:8080/TaskPlatform-slave/services/MasterService";
    private final static String TARGET_NAMESPACE = "http://master.task.waddy.org";
   
    public static void main(String[] args) throws AxisFault {
        RPCServiceClient sender = initRpcClient(SERVICE_URL);
//          findById(sender);
          request(sender);
    }
   
   
    public static RPCServiceClient initRpcClient(String serviceUrl) throws AxisFault{
          RPCServiceClient sender = new RPCServiceClient();
          Options options = sender.getOptions();         
          EndpointReference targetEPR = new EndpointReference(serviceUrl);
          options.setTo(targetEPR);
          return sender;
    }
   
    public static void request(RPCServiceClient sender) throws AxisFault{
          QName qname = new QName(TARGET_NAMESPACE, "request");
          Object[] args = new Object[] {"1","1001"};  // 这是针对方法参数的
          Class[] returnType = new Class[] { String[].class }; 
                   //这是针对返值类型的,因为返值是List<String>,这里当做Object[].class处理
          Object[] response = sender.invokeBlocking(qname, args, returnType);
          System.out.println("response="+response);
          
          String[] datas = (String[]) response[0];
          System.out.println(datas);
          for(int i=0; i<datas.length; i++){
        	  System.out.print(datas[i]+"\t");
          }
    } 

   
    public static void findById(RPCServiceClient sender) throws AxisFault{
          // There are serveral import files. Choose "javax.xml.namespace.QName" to import
          QName qname1 = new QName(TARGET_NAMESPACE, "findByIdPlain");
          Object[] args1 = new Object[] {1 };  // 这是针对方法参数的
          Class[] types1 = new Class[] { String.class };  //这是针对返值类型的
          Object[] response1 = sender.invokeBlocking(qname1, args1, types1);

          String u1 = (String) response1[0];
          if (u1 == null) {
              System.out.println("u1 was null");
              return;
          }
        
          System.out.println(u1);
    }  
   
   
} 

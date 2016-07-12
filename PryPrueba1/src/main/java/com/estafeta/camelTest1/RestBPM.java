package com.estafeta.camelTest1;

import java.net.URL;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;

import org.kie.services.client.api.RemoteJmsRuntimeEngineFactory;
import org.kie.services.client.api.RemoteRuntimeEngineFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.services.client.api.command.RemoteRuntimeEngine;
import org.kie.services.client.api.command.exception.RemoteCommunicationException;

import com.estafeta.general.businessentities.BusinessData;
import com.estafeta.general.businessentities.ExceptionData;
import com.estafeta.general.businessentities.MensajeME;
import com.estafeta.general.businessentities.ProcessData;

import javax.naming.*;

public class RestBPM {

	private String host;
	private int port;
	private String user;
	private String password;
	private String deployId;
	private String processId;
	private String messageName; 
	private URL baseUrl;
	
	//private KieSession session;
	
	public void inicializar()
	{
		try {
			host="localhost";
			port=8080;
			user="jbossadmin";
			password="jboss123";
			deployId="com.estafeta.test:pryPrueba:LATEST";
			processId="bpManager";
			messageName="mensaje";
			baseUrl = new URL("http://" + host + ":" + port + "/business-central");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/*
	private InitialContext getRemoteJbossInitialContext(URL url, String user, String password) {
	    java.util.Properties jndiProps = new java.util.Properties();
	    jndiProps.setProperty(InitialContext.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
	    jndiProps.setProperty(InitialContext.PROVIDER_URL, "remote://"+ host + ":4447");
	    jndiProps.setProperty(InitialContext.SECURITY_PRINCIPAL, user);
	    jndiProps.setProperty(InitialContext.SECURITY_CREDENTIALS, password);
	  
	    for (Object keyObj : initialProps.keySet()) {
	        String key = (String) keyObj;
	        System.setProperty(key, (String) initialProps.get(key));
	    }
	    try {
	        return new InitialContext(jndiProps);
	    } catch (NamingException e) {
	    	e.printStackTrace();
	        //throw new RemoteCommunicationException("Unable to create " + InitialContext.class.getSimpleName(), e);
	    }
	}
	*/
	public void createRestSession()
	{
		try {
			inicializar();

			org.kie.api.runtime.manager.RuntimeEngine runtimeEngine = 
					 org.kie.services.client.api.RemoteRuntimeEngineFactory.newRestBuilder()
				.addUrl(baseUrl)
				.addUserName(user)
				//.useFormBasedAuth(true)
				.addPassword(password)
				.addDeploymentId(deployId)
				.addExtraJaxbClasses(new Class[]{
						MensajeME.class, 
						ProcessData.class, 
						ExceptionData.class,
						BusinessData.class,
						DummyClass.class}).build();
			
	        //org.kie.api.runtime.manager.RuntimeEngine runtimeEngine = factory.newRuntimeEngine();
	        KieSession ksession = runtimeEngine.getKieSession();
	       //System.out.println("ID Sesion: " + ksession.getId());
	        
	        java.util.Map<String, Object> params = new java.util.HashMap<String, Object>();
	        MensajeME objMensaje = new MensajeME();
	        objMensaje.setProcessData(new ProcessData());
		    objMensaje.getProcessData().setCorrelationKey("CK0005");
		    objMensaje.getProcessData().setEventDate(new java.util.Date());
		    objMensaje.getProcessData().setClosingMessage(false);
		    objMensaje.getProcessData().setStartingMessage(true);
		    objMensaje.getProcessData().setMessageName("registrarContenedor");
		    objMensaje.getProcessData().setRetryMessage(false);

		    
	        params.put("mensaje", objMensaje);
	        
	        ksession.signalEvent("Message-iniciarGenerico", params); 

		} catch (Exception e) {
			e.printStackTrace();
		}
		
        
        
	}
	
	public void createJMSSession() 
	{
		try
		{
			/*
			 * https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.0/html/Development_Guide/sect-Remote_Java_API.html
			 */
			inicializar();
			/*
			InitialContext remoteInitialContext = getRemoteJbossInitialContext(baseUrl, user, password);
			RemoteJmsRuntimeEngineFactory jmsRuntimeFactory = 
			          RemoteJmsRuntimeEngineFactory.newBuilder()
			          .addDeploymentId(deployId)
			       //   .addProcessInstanceId(processId)
			          .addUserName(user)
			          .addPassword(password)
			          .addRemoteInitialContext(remoteInitialContext)
			          .addTimeout(3)
			          .addExtraJaxbClasses(new Class[]{
								MensajeME.class, 
								ProcessData.class, 
								ExceptionData.class,
								BusinessData.class})
			          .useSsl(false)
			          .build();
			
			RemoteRuntimeEngine engine = jmsRuntimeFactory.newRuntimeEngine();	
			KieSession ksession = engine.getKieSession();
			
			java.util.Map<String, Object> params = new java.util.HashMap<String, Object>();
		    MensajeME objMensaje = new MensajeME();
		    objMensaje.setProcessData(new ProcessData());
		    objMensaje.getProcessData().setCorrelationKey("CK0005");
		    objMensaje.getProcessData().setEventDate(new java.util.Date());
		    objMensaje.getProcessData().setClosingMessage(false);
		    objMensaje.getProcessData().setStartingMessage(true);
		    objMensaje.getProcessData().setMessageName("registrar");
		    objMensaje.getProcessData().setRetryMessage(false);

		    params.put("mensaje", objMensaje);
		    ProcessInstance pi = ksession.startProcess(processId, params);
			
		    long procId = pi.getId();
		    System.out.println("Process Created ID:" + procId);
		   */
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

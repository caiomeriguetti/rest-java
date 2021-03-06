package com.globo.teste.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.globo.teste.model.Server;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class RemoteCommand {
	private String command;
	private boolean isSudo;
	public RemoteCommand(String command) {
		this.command = command;
	}
	
	public void setSudo(boolean isSudo) {
		this.isSudo = isSudo;
	}
	
	public String execute(Server server) {
		JSch jsch=new JSch();
		Session session = null;
		Channel channel = null;
		String commandReturn = null;
	    try {
	    	session = jsch.getSession(server.getUser(), server.getIp(), 22);
	    	
	    	java.util.Properties config = new java.util.Properties(); 
	    	config.put("StrictHostKeyChecking", "no");
	    	session.setConfig(config);
	    	
	    	session.setPassword(server.getPassword());
		    
		    session.connect(30000);

		    channel = session.openChannel("exec");
		    String commandStr = this.command;
		    
		    if (isSudo) {
		    	commandStr = "sudo -S -p '' "+ commandStr;
		    }
		    
		    ((ChannelExec)channel).setCommand(commandStr);

			InputStream in=channel.getInputStream();
			OutputStream out=channel.getOutputStream();
			((ChannelExec)channel).setErrStream(System.err);
			
			channel.connect();
			
			if (isSudo) {
				out.write((server.getPassword()+"\n").getBytes());
			}
			out.flush();
			
			  byte[] tmp=new byte[1024];
			  StringBuilder builder = new StringBuilder();
			  while(true){
			    while(in.available()>0){
			      int i=in.read(tmp, 0, 1024);
			      if(i<0)break;
			      builder.append(new String(tmp, 0, i));
			    }
			    if(channel.isClosed()){
			      break;
			    }
			    try{Thread.sleep(1000);}catch(Exception ee){}
			  }
			  
			  commandReturn = builder.toString();
		      
	    } catch (JSchException | IOException e) {
	    	//TODO: log the exception
	    	
	    	return "Problem executing the command";
	    } finally {
	    	if (channel != null) channel.disconnect();
	    	if (session != null) session.disconnect();
	    }
	    
	    return commandReturn;
	}

}

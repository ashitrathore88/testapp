package com.emodoki.rest.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import com.emodoki.rest.utils.ConnectionProvider;

public class UpdateTask extends TimerTask{  
	 
		ConnectionProvider connection =new ConnectionProvider();
		Connection conn;
	    @SuppressWarnings("unused")
		private ServletContext servletContext;  
	    @SuppressWarnings("unused")
		private static boolean isRunning = false;  
	      int i=0;
	      
		public UpdateTask() {
		}

		@Override
		public void run() {		
			 conn = connection.getConnection();
	           PreparedStatement pStatement = null;
	            try {            
	             String UPDATE_SQL ="update challenge set status=?,status_description=? where end_date < now() and status='1'";
	             pStatement = conn.prepareStatement(UPDATE_SQL);            
	             pStatement.setInt(1, 0);
	             pStatement.setString(2, "Challenge Expired");
	             i=pStatement.executeUpdate();
	             System.out.println("iiiiiiiiii......."+i);
	             conn.close();
	            } catch (Exception e) {
	             
	             e.printStackTrace();
	             
	            }
		}
}

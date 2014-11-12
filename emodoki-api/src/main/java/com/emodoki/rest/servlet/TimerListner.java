package com.emodoki.rest.servlet;

import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;


public class TimerListner implements ServletContextListener {
	  @SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;  
	  @SuppressWarnings("unused")
	private ServletContext context = null; 
	    private Timer timer;  
	    /** 
	     * @see HttpServlet#HttpServlet() 
	     */  
	    public TimerListner() {  
	        super();  
	    }  
	  
	  
	    @Override  
	    public void contextDestroyed(ServletContextEvent event) {  
	        if(timer  != null) {  
	            timer.cancel();  
	            event.getServletContext().log("timer destroy");  
	        }  
	    }  
	  
	    @Override  
	    public void contextInitialized(ServletContextEvent event) {  
	        timer = new Timer(true);  
	        event.getServletContext().log("timer start");  
	        timer.schedule(new UpdateTask(), 0,12*60*60*1000);  
	        event.getServletContext().log("task has been added");  
	    }  
	

}

package com.emodoki.rest.utils;

import java.util.HashMap;
import java.util.Map;

import com.emodoki.model.Account;

public class EmailThread implements Runnable {
	private Account account;
	private String[] to;
	private Sender velocityEmailSender;
	Map<Object, Object> hTemplateVariables;
	private String template;
	private String subject;
	private boolean sendConfirmation;
	public EmailThread(Account account, String[] to,Sender velocityEmailSender, String serverPath) {
		this.account = account;
		this.to = to;
		this.velocityEmailSender = velocityEmailSender;
		hTemplateVariables = new HashMap<Object, Object>();
		hTemplateVariables.put("firstName", account.getFirstName());
		hTemplateVariables.put("lastName", account.getLastName());
		hTemplateVariables.put("email", account.getEmail());
		hTemplateVariables.put("username", account.getUsername());
		hTemplateVariables.put("password", account.getPassword());
		hTemplateVariables.put("serverPath", serverPath);
		this.subject="Emodoki Invitation Confirmation";
		this.template="invitationConfirm.vm";
		this.sendConfirmation=true;
	}

	
	public EmailThread(Account account, String[] to,Sender velocityEmailSender, String serverPath, String subject, String template, boolean sendConfirmation) {
		this.account = account;
		this.to = to;
		this.velocityEmailSender = velocityEmailSender;
		hTemplateVariables = new HashMap<Object, Object>();
		hTemplateVariables.put("firstName", account.getFirstName());
		hTemplateVariables.put("lastName", account.getLastName());
		hTemplateVariables.put("email", account.getEmail());
		hTemplateVariables.put("username", account.getUsername());
		hTemplateVariables.put("password", account.getPassword());
		hTemplateVariables.put("serverPath", serverPath);
		this.subject= subject;
		this.template=template;
		this.sendConfirmation=sendConfirmation;
	}
	@Override
	public void run() {
		
		if(to!=null)
		for(String email:to) {
			try {
				//velocityEmailSender.sendInvitation(account,email, hTemplateVariables);
				velocityEmailSender.send(hTemplateVariables, subject, template, email);
				if(sendConfirmation)
				velocityEmailSender.send(hTemplateVariables,subject ,template, account.getEmail());
			}catch(Exception e) {
				
			}
		}
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public Sender getVelocityEmailSender() {
		return velocityEmailSender;
	}

	public void setVelocityEmailSender(Sender velocityEmailSender) {
		this.velocityEmailSender = velocityEmailSender;
	}
	
	
}

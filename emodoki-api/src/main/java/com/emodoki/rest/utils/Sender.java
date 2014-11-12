package com.emodoki.rest.utils;

import java.util.Map;

import com.emodoki.model.Account;

/**
 * Sends an e-mail message.
 * 
 * @author Sunny
 */
public interface Sender { 

    /**
     * Sends e-mail using Velocity template for the body and 
     * the properties passed in as Velocity variables.
     * 
     * @param   msg                 The e-mail message to be sent, except for the body.
     * @param   hTemplateVariables  Variables to use when processing the template. 
     */
    public void send(Map<Object, Object> hTemplateVariables, String subject, String template, String to);
    public void sendInvitation(Account account,String[] to, Map<Object, Object> hTemplateVariables);
    public void sendInvitation(Account account,String to, Map<Object, Object> hTemplateVariables);
    
}

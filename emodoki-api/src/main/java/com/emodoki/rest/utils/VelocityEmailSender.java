package com.emodoki.rest.utils;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.emodoki.model.Account;

/**
 * Sends an e-mail message.
 * 
 * @author Sunny
 */
@Component
public class VelocityEmailSender implements Sender {

	private static final Logger logger = LoggerFactory
			.getLogger(VelocityEmailSender.class);

	private final VelocityEngine velocityEngine;
	private final JavaMailSender mailSender;
	@Autowired
	private Environment environment;

	/**
	 * Constructor
	 */
	@Autowired
	public VelocityEmailSender(VelocityEngine velocityEngine,
			JavaMailSender mailSender) {
		this.velocityEngine = velocityEngine;
		this.mailSender = mailSender;
	}

	/**
	 * Sends e-mail using Velocity template for the body and the properties
	 * passed in as Velocity variables.
	 * 
	 * @param msg
	 *            The e-mail message to be sent, except for the body.
	 * @param hTemplateVariables
	 *            Variables to use when processing the template.
	 */

	public void send(final Map<Object, Object> hTemplateVariables, final String subject, final String template, final String to) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true);
				message.setTo(to);
				message.setFrom("emodoki.postmaster@gmail.com",
						"Emodoki Team");
				message.setSubject(subject);

				String body = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "/" + template, hTemplateVariables);

				logger.info("body={}", body);

				message.setText(body, true);
			}
		};
		mailSender.send(preparator);
		logger.info("Sent e-mail to '{}'.", to);
	}

	/**
	 * Sends e-mail using Velocity template for the body and the properties
	 * passed in as Velocity variables.
	 * 
	 * @param msg
	 *            The e-mail message to be sent, except for the body.
	 * @param hTemplateVariables
	 *            Variables to use when processing the template.
	 */

	public void sendInvitation(final Account account, final String[] to,
			final Map<Object, Object> hTemplateVariables) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true);
				message.setTo(to);

				message.setFrom(environment.getProperty("mail.smtp.from"),
						"Emodoki Team");
				message.setSubject("Emodoki Invitation From "
						+ account.getFirstName() + " " + account.getLastName()
						+ "!");

				String body = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "/invitation.vm", hTemplateVariables);

				logger.info("body={}", body);

				message.setText(body, true);
			}
		};
		try {
			mailSender.send(preparator);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		logger.info("Sent e-mail to '{}'.", account.getEmail());
	}

	public void sendInvitation(final Account account, final String to,
			final Map<Object, Object> hTemplateVariables) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true);
				message.setTo(to);

				message.setFrom(environment.getProperty("mail.smtp.from"),
						"Emodoki Team");
				message.setSubject("Emodoki Invitation From "
						+ account.getFirstName() + " " + account.getLastName()
						+ "!");

				String body = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "/invitation.vm", hTemplateVariables);

				logger.info("body={}", body);

				message.setText(body, true);
			}
		};
		try {
			mailSender.send(preparator);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		logger.info("Sent e-mail to '{}'.", account.getEmail());
	}
}

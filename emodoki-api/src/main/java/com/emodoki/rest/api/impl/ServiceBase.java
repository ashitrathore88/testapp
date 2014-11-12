package com.emodoki.rest.api.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.ws.WebServiceContext;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.emodoki.dao.DaoAccount;
import com.emodoki.dao.DaoKey;
import com.emodoki.dao.DaoMood;
import com.emodoki.model.Account;
import com.emodoki.model.Key;
import com.emodoki.rest.api.IConstants;
import com.emodoki.rest.beans.AcceptResponse;
import com.emodoki.rest.beans.ChallengeResponse;
import com.emodoki.rest.beans.CloseChallenge;
import com.emodoki.rest.beans.EmodokiResponse;
import com.emodoki.rest.beans.FriendListResponse;
import com.emodoki.rest.beans.LoginResponse;
import com.emodoki.rest.beans.UserDetailInfo;
import com.emodoki.rest.utils.Sender;
import com.emodoki.rest.utils.Toolbox;

@Component
public class ServiceBase {
	ResponseBuilder builder = Response.status(Status.OK);
	EmodokiResponse emodokiResponse = new EmodokiResponse();
	ChallengeResponse challengeResponse = new ChallengeResponse();
	LoginResponse loginResponse = new LoginResponse();
	AcceptResponse acceptResponse = new AcceptResponse();
	FriendListResponse friendListResponse = new FriendListResponse();
	String message = "success";
	String locale = IConstants.DEFAULT_LOCALE;

	@Autowired
	MessageSource messageSource;

	@Autowired
	HttpServletRequest request;

	@Context
	MessageContext messageContext;

	@Resource
	WebServiceContext wsContext;

	@Autowired
	DaoKey daoKey;

	@Autowired
	Sender velocityEmailSender;

	@Autowired
	DaoAccount daoAccount;

	@Autowired
	DaoMood daoMood;

	@Value("#{'${app.image.url}'}")
	String imageURL;

	/**
	 * Key Validation. Checking if key exists or not.
	 * 
	 * @param okey
	 * @return
	 */

	public boolean isKeyValid(String okey) {
		if (okey != null) {
			List<Key> aoKeys = daoKey.findAll();
			if (aoKeys != null)
				for (Key key : aoKeys) {
					if (key.isActive() && key.getKeyValue().equals(okey)) {
						return true;
					}
				}
		}
		return false;
	}

	/**
	 * Getting HttpServletRequest from messageContext
	 * 
	 * @param messageContext
	 * @return
	 */
	public HttpServletRequest getRequest() {
		if (messageContext.getServletContext() == null) {
			request = (HttpServletRequest) messageContext
					.getHttpServletRequest();
		} else {
			request = (HttpServletRequest) messageContext
					.get(AbstractHTTPDestination.HTTP_REQUEST);
		}
		return request;
	}

	/**
	 * Getting message from resource bundle
	 * 
	 * @param key
	 * @return
	 */
	public String getMessage(String key) {
		// Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(key, null,
				Toolbox.getLocaleFromString(IConstants.DEFAULT_LOCALE));
	}

	/**
	 * Getting message from resource bundle and passing dynamic arguments
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public String getMessage(String key, Object[] args) {

		return messageSource.getMessage(key, args,
				Toolbox.getLocaleFromString(IConstants.DEFAULT_LOCALE));
	}

	/**
	 * Getting message from resource bundle, passing dynamic arguments and
	 * locale
	 * 
	 * @param key
	 * @param args
	 * @param locale
	 * @return
	 */
	public String getMessage(String key, Object[] args, Locale locale) {
		if (locale != null)
			return messageSource.getMessage(key, args, locale);
		else
			return messageSource.getMessage(key, args,
					Toolbox.getLocaleFromString(IConstants.DEFAULT_LOCALE));
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * Getting Media type from request
	 * 
	 * @return
	 */
	public MediaType getMediaType() {
		if (request == null)
			getRequest();
		MediaType mt;
		try {
			mt = MediaType.valueOf(request.getHeader("Content-Type"));
		} catch (Exception e) {
			mt = MediaType.APPLICATION_XML_TYPE;
		}
		return mt;

	}

	public MediaType getJsonMediaType() {
		if (request == null)
			getRequest();
		MediaType mt;
		try {
			mt = MediaType.valueOf(request.getHeader("Content-Type"));
		} catch (Exception e) {
			mt = MediaType.APPLICATION_JSON_TYPE;
		}
		return mt;

	}

	public void Forbidden() {
		builder = Response.status(Status.FORBIDDEN);
		message = getMessage("msg.forbidden", null,
				Toolbox.getLocaleFromString(locale));
		emodokiResponse.setStatus(Status.FORBIDDEN.getStatusCode());
	}

	public void Failed() {
		builder = Response.status(Status.FORBIDDEN);
		message = getMessage("msg.failed", null,
				Toolbox.getLocaleFromString(locale));
		emodokiResponse.setStatus(Status.FORBIDDEN.getStatusCode());
	}

	public void NoContent() {
		builder = Response.status(Status.NOT_FOUND);
		message = getMessage("msg.notfound", null,
				Toolbox.getLocaleFromString(locale));
		emodokiResponse.setStatus(Status.NO_CONTENT.getStatusCode());
	}

	public void Success() {
		builder = Response.status(Status.OK);
		message = getMessage("msg.success", null,
				Toolbox.getLocaleFromString(locale));
		emodokiResponse.setStatus(Status.OK.getStatusCode());
	}

	public void challengeSuccess() {
		builder = Response.status(Status.OK);
		message = getMessage("msg.success", null,
				Toolbox.getLocaleFromString(locale));
		loginResponse.setStatus(Status.OK.getStatusCode());
	}
	
	public void acceptSuccess() {
		builder = Response.status(Status.OK);
		message = getMessage("msg.success", null,
				Toolbox.getLocaleFromString(locale));
		acceptResponse.setStatus(Status.OK.getStatusCode());
	}
	
	public void challengeFriendSuccess() {
		builder = Response.status(Status.OK);
		message = getMessage("msg.success", null,
				Toolbox.getLocaleFromString(locale));
		friendListResponse.setStatus(Status.OK.getStatusCode());
	}

	public void challengeInviteSuccess() {
		builder = Response.status(Status.OK);
		message = getMessage("msg.success", null,
				Toolbox.getLocaleFromString(locale));
		challengeResponse.setStatus(Status.OK.getStatusCode());
	}

	public void NotAcceptable() {
		builder = Response.status(Status.NOT_ACCEPTABLE);
		message = getMessage("msg.nullvalue", null,
				Toolbox.getLocaleFromString(locale));
		emodokiResponse.setStatus(Status.NOT_ACCEPTABLE.getStatusCode());
	}

	public void AlreadyExists() {
		builder = Response.status(Status.CONFLICT);
		message = getMessage("msg.already.exists", null,
				Toolbox.getLocaleFromString(locale));
		emodokiResponse.setStatus(Status.CONFLICT.getStatusCode());
	}

	public void AlreadyExisted() {
		builder = Response.status(Status.CONFLICT);
		message = getMessage("msg.email.already.exists", null,
				Toolbox.getLocaleFromString(locale));
		emodokiResponse.setStatus(Status.CONFLICT.getStatusCode());
	}

	public void UnAuthorize() {
		builder = Response.status(Status.UNAUTHORIZED);
		message = getMessage("msg.wrong.username", null,
				Toolbox.getLocaleFromString(locale));
		emodokiResponse.setStatus(Status.UNAUTHORIZED.getStatusCode());
	}

	public void UnAuthorized() {
		builder = Response.status(Status.UNAUTHORIZED);
		message = getMessage("msg.wrong.password", null,
				Toolbox.getLocaleFromString(locale));
		emodokiResponse.setStatus(Status.UNAUTHORIZED.getStatusCode());
	}

	
	 public void setEntityObjectWithMedia(Object object) {
	 emodokiResponse.setMessage(message);
	 builder.entity(object);
	 builder.type(getJsonMediaType()); 
	 }
	
	public void setEntity(Object object) {
		emodokiResponse.setMessage(message);
		builder.entity(object);
		builder.type(getMediaType());
	}
	
	public void setEntity(Object object, String mediaType) {
		emodokiResponse.setMessage(message);
		builder.entity(object);
		builder.type(mediaType);
	}

	public void setEntity() {
		emodokiResponse.setMessage(message);
		builder.entity(emodokiResponse);
		builder.type(getMediaType());
	}

	/**
	 * Use to send email
	 * 
	 * @param account
	 */
	public void sendEmail(Account account, String uuid, String subject,
			String to, String template) throws Exception {
		Map<Object, Object> hTemplateVariables = new HashMap<Object, Object>();
		hTemplateVariables.put("firstName", account.getFirstName());
		hTemplateVariables.put("lastName", account.getLastName());
		hTemplateVariables.put("email", account.getEmail());
		hTemplateVariables.put("username", account.getUsername());
		hTemplateVariables.put("password", account.getPassword());
		hTemplateVariables.put("serverPath", getMessage("app.url"));
		hTemplateVariables.put("UUID", uuid);
		velocityEmailSender.send(hTemplateVariables, subject, template, to);
	}

	public void sendWelcomeEmail(Account account, String password,
			String subject, String to, String template) throws Exception {
		Map<Object, Object> hTemplateVariables = new HashMap<Object, Object>();
		hTemplateVariables.put("firstName", account.getFirstName());
		hTemplateVariables.put("lastName", account.getLastName());
		hTemplateVariables.put("email", account.getEmail());
		hTemplateVariables.put("username", account.getUsername());
		hTemplateVariables.put("password", password);
		hTemplateVariables.put("serverPath", getMessage("app.url"));
		velocityEmailSender.send(hTemplateVariables, subject, template, to);
	}

	public Account getAccount(String username) {
		return daoAccount.findByUsername(username);
	}
 	public String convertImage1(String base64String, String code,
			String messageText,String p1,String p2) {
		String realPath1 = null;
		int index = request.getSession().getServletContext().getRealPath("").lastIndexOf("/");
		if (index > 0) {
			realPath1 = request.getSession().getServletContext().getRealPath("").substring(0, index);
		} else {
			index = request.getSession().getServletContext().getRealPath("").lastIndexOf("\\");
			realPath1 = request.getSession().getServletContext().getRealPath("").substring(0, index);
		}

		String path2 =p1;
		// String realPath = request.getServletContext().getRealPath("/");
		String imagePath = null;
		String base64 = base64String;
		@SuppressWarnings("restriction")
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		File file = null;
		try {
			@SuppressWarnings("restriction")
			byte[] decodedBytes = decoder.decodeBuffer(base64);
			BufferedImage imageBanner = ImageIO.read(new File(path2));
			String path = realPath1 +p2;
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(
					decodedBytes));
			if (image == null) {
				NoContent();
			}
			//BufferedImage textImage = writeImage(messageText, Color.BLACK,Color.WHITE);
			BufferedImage overlayedImage = overlayImages(imageBanner, image);
	//		BufferedImage finalImage = getMergedImage(overlayedImage, textImage);
			File directoryFile = new File(path);
			if (!directoryFile.exists()) {
				directoryFile.mkdir();
			}
			imagePath = path + "/image_" + code + ".png";
			file = new File(imagePath);
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}

			ImageIO.write(overlayedImage, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getName();
	}
 	
 	 	
 	
 	
		public String convertImage3(String base64String, String code,
			String messageText) {
		String realPath1 = null;
		int index = request.getSession().getServletContext().getRealPath("").lastIndexOf("/");
		if (index > 0) {
			realPath1 = request.getSession().getServletContext().getRealPath("").substring(0, index);
		} else {
			index = request.getSession().getServletContext().getRealPath("").lastIndexOf("\\");
			realPath1 = request.getSession().getServletContext().getRealPath("").substring(0, index);
		}

		String path2 = request.getServletContext().getRealPath(
				"/files/doneChallenge.jpg");
		// String realPath = request.getServletContext().getRealPath("/");
		String imagePath = null;
		String base64 = base64String;
		@SuppressWarnings("restriction")
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		File file = null;
		try {
			@SuppressWarnings("restriction")
			byte[] decodedBytes = decoder.decodeBuffer(base64);
			BufferedImage imageBanner = ImageIO.read(new File(path2));
			String path = realPath1 + "/donechallengeimagesdir";
			System.out.println("path..." + realPath1);
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(
					decodedBytes));
			if (image == null) {
				NoContent();
			}
			/*BufferedImage textImage = writeImage(messageText, Color.BLACK,
					Color.WHITE);*/
			BufferedImage overlayedImage = overlayImages(imageBanner, image);
		
			File directoryFile = new File(path);
			if (!directoryFile.exists()) {
				directoryFile.mkdir();
			}
			imagePath = path + "/image_" + code + ".png";
			file = new File(imagePath);
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}

			ImageIO.write(overlayedImage, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getName();
	}


	
	public String getRandomString(int length) {
		final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890";
		StringBuilder result = new StringBuilder();
		while (length > 0) {
			Random rand = new Random();
			result.append(characters.charAt(rand.nextInt(characters.length())));
			length--;
		}
		return result.toString();
	}

	public static BufferedImage overlayImages(BufferedImage bgImage,
			BufferedImage fgImage) {
		/*
		 * if (fgImage.getHeight() > bgImage.getHeight() || fgImage.getWidth() >
		 * fgImage.getWidth()) { JOptionPane.showMessageDialog(null,
		 * "Foreground Image Is Bigger In One or Both Dimensions" +
		 * "\nCannot proceed with overlay." +
		 * "\n\n Please use smaller Image for foreground"); return null; }
		 */
		Graphics2D g = bgImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(bgImage, 0, 0, null);
		//g.drawImage(fgImage, 40, 40, null);
		g.drawImage(fgImage, 16, 33, 346,370,null,null);
		g.dispose();
		return bgImage;
	}
		public static BufferedImage writeImage(String text, Color fgc, Color bgc) {

		// calculate image size requirements.
		// int width = (text.length() * 7) + 5;
		int width = 754;
		int height = 100;
		// standard height requirement of 16 px.

		BufferedImage buffRenderImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D flatGraphic = buffRenderImage.createGraphics();
		// Draw background
		flatGraphic.setColor(bgc);
		flatGraphic.fillRect(0, 0, width, height);
		// Draw text
		flatGraphic.setColor(fgc);
		String f1=Font.SANS_SERIF;
		Font font = new Font(f1,Font.BOLD, 25);
		Font newFont =font.deriveFont(30F);
		
		System.out.println("font   ,.............."+flatGraphic.getFont());
		flatGraphic.setFont(newFont);
		//flatGraphic.drawString("I CHALLENGE YOU!", 45, 20);
		
		
		
		drawString(flatGraphic, text, 45, 45, 700);
		
		// don't use drawn graphic anymore.
		flatGraphic.dispose();

		return buffRenderImage;
	}
	
	public static void drawString(Graphics2D g, String s, int x, int y,
			int width) {
		FontMetrics fm = g.getFontMetrics();

		int lineHeight = fm.getHeight();
        
		int curX = x;
		int curY = y;

		String[] words = s.split(" ");

		for (String word : words) {
			int wordWidth = fm.stringWidth(word + " ");
			if (curX + wordWidth >= x + width) {
				curY += lineHeight;
				curX = x;
			}
			g.drawString(word, curX, curY);
			
			curX += wordWidth;
		}
	}

	public static BufferedImage getMergedImage(BufferedImage img1,
			BufferedImage img2) throws IOException {
		@SuppressWarnings("unused")
		int type = img1.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img1
				.getType();
		@SuppressWarnings("unused")
		int type1 = img2.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img2
				.getType();
		int heightImg1 = img1.getHeight();
		@SuppressWarnings("unused")
		int heightImg2 = img2.getHeight();
		BufferedImage img = new BufferedImage(img1.getWidth(), heightImg1, // addition of widths and heights of
								// the images we already have
				BufferedImage.TYPE_INT_RGB);
		@SuppressWarnings("unused")
		boolean image1Drawn = img.createGraphics().drawImage(img1, 0, 0, null);
		//boolean image2Drawn = img.createGraphics().drawImage(img2, 0,
		//		heightImg1, null); // here width is mentioned as width of
		// horizontally
		
		@SuppressWarnings("unused")
		boolean image2Drawn = img.createGraphics().drawImage(img2,386, 45, 344, 82,null);
		return img;
	}
	
	public String convertProofImage(String base64String, String code) {
		String realPath1 = null;
		int index = request.getSession().getServletContext().getRealPath("").lastIndexOf("/");
		if (index > 0) {
			realPath1 = request.getSession().getServletContext().getRealPath("").substring(0, index);
		} else {
			index = request.getSession().getServletContext().getRealPath("").lastIndexOf("\\");
			realPath1 = request.getSession().getServletContext().getRealPath("").substring(0, index);
		}
		String imagePath = null;
		String base64 = base64String;
		@SuppressWarnings("restriction")
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		File file = null;
		try {
			@SuppressWarnings("restriction")
			byte[] decodedBytes = decoder.decodeBuffer(base64);
			String path = realPath1 + "/ProofImages";
			System.out.println("path..." + realPath1);
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
			if (image == null) {
				NoContent();
			}			
			File directoryFile = new File(path);
			if (!directoryFile.exists()) {
				directoryFile.mkdir();
			}
			imagePath = path + "/proofImage_" + code + ".png";
			file = new File(imagePath);
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
			ImageIO.write(image, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getName();
	}

	
}






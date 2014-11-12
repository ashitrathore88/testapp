package com.emodoki.rest.api.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.ocpsoft.pretty.time.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emodoki.dao.DaoComment;
import com.emodoki.dao.DaoFriend;
import com.emodoki.dao.DaoResetPassword;
import com.emodoki.dao.DaoUserConnection;
import com.emodoki.dao.DaoUserMood;
import com.emodoki.dao.impl.DaoInvitationImpl;
import com.emodoki.model.Account;
import com.emodoki.model.Comment;
import com.emodoki.model.CommonInUtil;
import com.emodoki.model.Friend;
import com.emodoki.model.FriendId;
import com.emodoki.model.Invitation;
import com.emodoki.model.InvitationId;
import com.emodoki.model.Mood;
import com.emodoki.model.ResetPassword;
import com.emodoki.model.UserConnection;
import com.emodoki.model.UserConnectionId;
import com.emodoki.model.UserMood;
import com.emodoki.rest.api.IConstants;
import com.emodoki.rest.api.ServiceAccount;
import com.emodoki.rest.beans.AccountInfo;
import com.emodoki.rest.beans.AddFriendInfo;
import com.emodoki.rest.beans.BasicInfo;
import com.emodoki.rest.beans.CommentInfo;
import com.emodoki.rest.beans.Credential;
import com.emodoki.rest.beans.DefaultMood;
import com.emodoki.rest.beans.FacebookLogin;
import com.emodoki.rest.beans.FindFriends;
import com.emodoki.rest.beans.ForgotPassword;
import com.emodoki.rest.beans.FriendInfo;
import com.emodoki.rest.beans.FriendProfileInfo;
import com.emodoki.rest.beans.FriendUserInfo;
import com.emodoki.rest.beans.FriendsInfo;
import com.emodoki.rest.beans.FriendsMoods;
import com.emodoki.rest.beans.GetRecentMoods;
import com.emodoki.rest.beans.GetUserMoods;
import com.emodoki.rest.beans.Graph;
import com.emodoki.rest.beans.ImageUpload;
import com.emodoki.rest.beans.Info;
import com.emodoki.rest.beans.Notification;
import com.emodoki.rest.beans.RecentMood;
import com.emodoki.rest.beans.UserGraph;
import com.emodoki.rest.utils.CountryCode;
import com.emodoki.rest.utils.EmailThread;
import com.emodoki.rest.utils.HttpRequestPoster;
import com.emodoki.rest.utils.Toolbox;

/**
 * 
 * @author Sunny
 * 
 */
@Service("serviceAccountImpl")
@Transactional
@CrossOriginResourceSharing(allowAllOrigins = true)
public class ServiceAccountImpl extends ServiceBase implements ServiceAccount {

	Log log = LogFactory.getLog(ServiceAccountImpl.class);
	public static String URL_PROFILE = "http://emodoki.herokuapp.com/profile/";
	public static final String URL_FACEBOOK_DEBUG = "http://developers.facebook.com/tools/debug/og/object";

	@Autowired
	DaoUserMood daoUserMood;
	@Autowired
	DaoInvitationImpl daoInvitation;
/*	
	@Autowired
	DaoChallenge daoChallenge;
	
	@Autowired
	DaoChallengeUser daoChallengeUser;*/
	@Autowired
	DaoComment daoComment;
	@Autowired
	DaoFriend daoFriend;
	@Autowired
	DaoResetPassword daoResetPassword;
	@Autowired
	DaoUserConnection daoUserConnection;

	@Override
	@Transactional
	public Response authenticate(Credential credential) {
		log.info("executing ---> authenticate() ");
		String key = credential.getKey();
		Account account = null;
		if (isKeyValid(key)) {
			account = daoAccount.findByEmail(credential.getEmail());
			locale = credential.getLocale();
			if (credential.getEmail() != null
					&& credential.getPassword() != null
					&& !"".equals(credential.getEmail())
					&& !"".equals(credential.getPassword())) {				
				if (account != null
						&& account.getPassword().equals(
								credential.getPassword())) {
					account.setOnline(true);
					account.setLastLogin(new Date());
					log.info("authenticate() --> Success");
					locale = credential.getLocale();
					Success();
					daoAccount.update(account);
					
				} else {
					log.info("authenticate() --> Failed");
					NoContent();
				}
			} else {
				log.info("authenticate() --> Failed");
				NotAcceptable();
			}
		} else {
			log.info("authenticate() --> Invalid Key. ");
			Forbidden();
		}
		setEntity();			
		log.info("executed ---> authenticate()");
		return builder.build();
	}

	@Override
	@Transactional
	public Response editPrivacy(AccountInfo accountInfo) {
		log.info("executing---> editPrivacy()");
		if (accountInfo != null) {
			String key = accountInfo.getKey();
			String status = accountInfo.getStatus();
			boolean statusPrivacy = true;

			if (status.equals("false")) {
				statusPrivacy = false;
			}

			if (isKeyValid(key)) {
				if (isUsernmaeExists(accountInfo.getUsername())) {
					Account account = daoAccount.findByUsername(accountInfo
							.getUsername());

					if (account != null) {
						account.setStatusPrivacy(statusPrivacy);
						try {
							daoAccount.update(account);
							Success();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						log.info("privacy setting() --> Failed");
						NoContent();
					}

				} else {
					log.info("privacy setting() --> Failed");
					NotAcceptable();
				}

			} else {
				log.info("privacy setting() --> Invalid Key. ");
				Forbidden();
			}

		} else {
			log.info("privacy setting()--> Failed");
			NotAcceptable();
		}
		setEntity();
		log.info("privacy setting() ---> authenticate()");
		return builder.build();
	}

	/* Used to edit user's profile */

	@Override
	@Transactional
	public Response editProfile(AccountInfo accountInfo) {
		log.info("executing---> editProfile()");
		if (accountInfo != null) {
			String key = accountInfo.getKey();
			if (isKeyValid(key)) {
				if (isUsernmaeExists(accountInfo.getUsername())) {

					/* Getting the User's Info through username and set new Info */

					Account account = daoAccount.findByUsername(accountInfo
							.getUsername());
					if (!isEmailExists(accountInfo.getEmail())
							|| accountInfo.getEmail()
									.equals(account.getEmail())) {
						if (accountInfo.getCurrentPassword() == null) {
							account.setFirstName(accountInfo.getFirstName());
							account.setLastName(accountInfo.getLastName());
							account.setEmail(accountInfo.getEmail());
							account.setDisplayMood(accountInfo.getDisplayMood());
							account.setEmodokiImageUrl(accountInfo.getImageUrl());
							account.setAbout(accountInfo.getAbout());
							account.setLocale(accountInfo.getLocale());
							account.setGender(accountInfo.getGender());
							account.setContact(accountInfo.getContact());
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							if(accountInfo.getDob()!=null){
							String dob = accountInfo.getDob();		
							try{
								Date date = formatter.parse(dob);		
							account.setDob(date);
							}catch(Exception e){
								e.printStackTrace();
							}
							}
							/*
							 * account.setCountry(CountryCode.getByCountryName(
							 * accountInfo.getCountry()).getNumeric() + "");
							 */
							if (accountInfo.getCountry() == null
									|| accountInfo.getCountry() == "") {
								account.setCountry("00");
							} else {

								account.setCountry(CountryCode
										.getByCountryName(
												accountInfo.getCountry().trim())
										.getNumeric()
										+ "");
							}

							// account.setCountry(accountInfo.getCountry());
							String mood = accountInfo.getMood();
							if(mood!=null){
							Mood userMood = daoMood.findByName(mood);
							account.setMood(userMood);
							}
							// account.setPassword(accountInfo.getPassword());
							try {
								daoAccount.update(account);
								Success();
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {

							if (account.getPassword().equals(
									accountInfo.getCurrentPassword())
									&& accountInfo.getPassword() != null) {
								account.setFirstName(accountInfo.getFirstName());
								account.setLastName(accountInfo.getLastName());
								account.setEmail(accountInfo.getEmail());

								if (accountInfo.getCountry() == null
										|| accountInfo.getCountry() == "") {
									account.setCountry("00");
								} else {

									account.setCountry(CountryCode
											.getByCountryName(
													accountInfo.getCountry()
															.trim())
											.getNumeric()
											+ "");
								}
								// account.setCountry(accountInfo.getCountry());
								account.setDisplayMood(accountInfo
										.getDisplayMood());
								String mood = accountInfo.getMood();
								Mood userMood = daoMood.findByName(mood);
								account.setMood(userMood);
								account.setPassword(accountInfo.getPassword());
								try {
									daoAccount.update(account);
									Success();
								} catch (Exception e) {
									e.printStackTrace();
								}

							} else {
								UnAuthorized();
							}

						}
					} else {
						AlreadyExisted();
					}

				} else {
					UnAuthorize();
				}
			} else {
				log.info("editProfile()--->Invalid key. ");
				Forbidden();
			}
		} else {
			NotAcceptable();
		}
		setEntity();
		return builder.build();
	}

	@Override
	public Response editUserPosition(AccountInfo accountInfo) {
		// TODO Auto-generated method stub
		log.info("executing---> editUserPosition()");
		if (accountInfo != null) {
			String key = accountInfo.getKey();
			if (isKeyValid(key)) {

				if (isUsernmaeExists(accountInfo.getUsername())) {

					/* Getting the User's Info through username and set new Info */

					Account account = daoAccount.findByUsername(accountInfo
							.getUsername());
					if (account != null) {
						account.setUserLatitude(accountInfo.getLatitude());
						account.setUserLongitude(accountInfo.getLongitude());
						account.setLastLogin(new Date());
						try {
							daoAccount.update(account);
							Success();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						log.info("editUserLocation() --> Failed");
						NoContent();
					}
				} else {
					log.info("editUserLocation() --> Failed");
					NotAcceptable();
				}
			} else {
				log.info("editUserLocation()--> Invalid Key. ");
				Forbidden();
			}
		} else {
			log.info("editUserLocation()--> Failed");
			NotAcceptable();
		}
		setEntity();
		log.info("editUserLocation()---> authenticate()");
		return builder.build();

	}

	@Override
	@Transactional
	public Response register(AccountInfo accountInfo) {
		log.info("executing ---> register() ");
		if (accountInfo != null) {
			String key = accountInfo.getKey();
			locale = accountInfo.getLocale();
			if (isKeyValid(key)) {
				if (isAccountInfoValid(accountInfo)) {
					if (!isEmailExists(accountInfo.getEmail())) {
						log.info("!isEmailExists");
						CommonInUtil inUtil = new CommonInUtil();
						String uniqUserName = null;
						String emailId = accountInfo.getEmail();
						String userName = inUtil.SubEmailId(emailId);
						String ranUserName = null;
						log.info("=======================>>>>"
								+ isUsernmaeExists(userName));
						if ((isUsernmaeExists(userName))) {
							while (true) {// use while loop for random check the
											// userName with DataBase
								inUtil = new CommonInUtil();
								uniqUserName = inUtil.UserLogin();
								emailId = accountInfo.getEmail();
								userName = inUtil.SubEmailId(emailId);
								ranUserName = userName + uniqUserName;
								Boolean value = isUsernmaeExists(ranUserName);
								if (!value)
									break;
							}
							Account account = new Account();
							account.setUsername(ranUserName);
							account.setFirstName(accountInfo.getFirstName());
							account.setLastName(accountInfo.getLastName());
							account.setEmail(accountInfo.getEmail());
							String password = RandomStringUtils
									.randomAlphanumeric(6);
							account.setPassword(Toolbox.stringToSHA1(password));
							account.setCountry(CountryCode.getByCountryName(
									accountInfo.getCountry()).getNumeric()
									+ "");
							account.setGender(accountInfo.getGender());
							account.setDob(Toolbox.stringToDate2(accountInfo
									.getDob()));
							account.setEmodokiImageUrl(getMessage("app.image.url")
									+ "default.gif");
							account.setDisplayMood(Integer
									.parseInt(getMessage("setting.default.no.of.post")));
							account.setWhichProfilePicture(getMessage("ttl.image.emodoki"));
							account.setOnline(false);
							Mood mood = daoMood.findByName("Neutral");
							account.setMood(mood);
							try {
								daoAccount.save(account);

								UserMood userMood = new UserMood();

								userMood.setAccount(account);

								userMood.setMood(mood);
								daoUserMood.save(userMood);
								sendWelcomeEmail(
										account,
										password,
										getMessage(
												"welcome.subject",
												new String[] {
														account.getFirstName(),
														account.getLastName() }),
										account.getEmail(), "welcome.vm");
								System.out.println("mail sent");
								Success();
							} catch (Exception e) {
								e.printStackTrace();
								AlreadyExists();
							}
						} else {
							log.info("else part");
							Account account = new Account();

							account.setUsername(userName);
							account.setFirstName(accountInfo.getFirstName());
							account.setLastName(accountInfo.getLastName());
							account.setEmail(accountInfo.getEmail());
							String password = RandomStringUtils
									.randomAlphanumeric(6);
							account.setPassword(Toolbox.stringToSHA1(password));
							account.setCountry(CountryCode.getByCountryName(
									accountInfo.getCountry()).getNumeric()
									+ "");
							account.setGender(accountInfo.getGender());
							account.setDob(Toolbox.stringToDate2(accountInfo
									.getDob()));
							account.setEmodokiImageUrl(getMessage("app.image.url")
									+ "default.gif");
							account.setDisplayMood(Integer
									.parseInt(getMessage("setting.default.no.of.post")));
							account.setWhichProfilePicture(getMessage("ttl.image.emodoki"));
							account.setOnline(false);
							Mood mood = daoMood.findByName("Neutral");
							account.setMood(mood);
							try {
								daoAccount.save(account);

								UserMood userMood = new UserMood();
								userMood.setWhy(accountInfo.getFirstName()
										+ "'S MOOD FOR TODAY IS "
										+ mood.getDescription());
								userMood.setAccount(account);

								userMood.setMood(mood);
								daoUserMood.save(userMood);
								sendWelcomeEmail(
										account,
										password,
										getMessage(
												"welcome.subject",
												new String[] {
														account.getFirstName(),
														account.getLastName() }),
										account.getEmail(), "welcome.vm");
								System.out.println("mail sent");
								Success();
							} catch (Exception e) {
								e.printStackTrace();
								AlreadyExists();
							}

						}

					} else {
						AlreadyExists();
					}
				} else {
					NotAcceptable();
				}

			} else {
				log.info("register() --> Invalid Key. ");
				Forbidden();
			}
		} else {
			NotAcceptable();

		}
		setEntity();
		log.info("executed ---> register()");
		return builder.build();
	}

	private boolean isAccountInfoValid(AccountInfo account) {
		if (!StringUtils.isEmpty(account.getEmail())
				&& !StringUtils.isEmpty(account.getGender())
				&& !StringUtils.isEmpty(account.getDob())
				&& !StringUtils.isEmpty(account.getFirstName())
				&& !StringUtils.isEmpty(account.getLastName())) {
			return true;

		} else {
			return false;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Response profile(Info info) {
		log.info("executing ---> profile() ");
		locale = info.getLocale();
		if (isKeyValid(info.getKey())) {
			if (info.getValue() != null && !"".endsWith(info.getValue())) {
				Account account = daoAccount.findByEmail(info.getValue());
				if (account != null) {
					if (account.getCountry().equalsIgnoreCase("00")) {
						account.setCountry("NA");
					} else {

						account.setCountry(CountryCode.getByCode(
								Integer.parseInt(account.getCountry().trim()))
								.getName());
					}
					PrettyTime prettyTime = new PrettyTime();
					account.setLastLoginAgo((prettyTime.format(account
							.getLastLogin())));
					SimpleDateFormat format = new SimpleDateFormat(
							"dd-MMM-yyyy");
					String dob = format.format(account.getDob());
					account.setBirthDate(dob);
					Success();
					setEntity(account);
					log.info("profile() --> Success");
				} else {
					NoContent();
					setEntity();
					log.info("profile() --> Not found. ");
				}
			} else {
				NotAcceptable();
				setEntity();
			}
		} else {
			log.info("profile() --> Invalid Key. ");
			Forbidden();
			setEntity();
		}
		log.info("executed ---> profile()");
		return builder.build();
	}

	@Override
	@Transactional
	public Response usernameExists(Info info) {
		log.info("executing ---> usernameExists() ");
		if (isKeyValid(info.getKey())) {
			if (info.getValue() != null && !"".equals(info.getValue())) {
				if (isUsernmaeExists(info.getValue())) {
					log.info("usernameExists() --> YES");
					Success();
				} else {
					log.info("usernameExists() --> Not found. ");
					NoContent();
				}
			} else {
				NotAcceptable();

			}
		} else {
			log.info("usernameExists() --> Invalid key. ");
			Forbidden();
		}
		setEntity();
		log.info("executed ---> usernameExists()");
		return builder.build();
	}

	@Override
	@Transactional
	public Response emailExists(Info info) {
		log.info("executing ---> emailExists() ");
		if (isKeyValid(info.getKey())) {
			if (info.getValue() != null && !"".equals(info.getValue())) {
				if (isEmailExists(info.getValue())) {
					log.info("emailExists() --> YES");
					Success();
				} else {
					log.info("emailExists() --> Not found. ");
					NoContent();
				}
			} else {
				NotAcceptable();

			}
		} else {
			log.info("emailExists() --> Invalid key. ");
			Forbidden();
		}
		setEntity();
		log.info("executed ---> emailExists()");
		return builder.build();
	}

	@Override
	@Transactional
	public Response mood(Info info) {
		log.info("executing ---> mood() ");
		if (isKeyValid(info.getKey())) {
			if (StringUtils.isNotBlank(info.getUsername())
					&& StringUtils.isNotBlank(info.getMood())) {
				if (isUsernmaeExists(info.getUsername())) {
					Mood mood = daoMood.findByName(info.getMood());
					Account account = getAccount(info.getUsername());
					if (!(mood == null && account == null)) {
						UserMood userMood = new UserMood();
						userMood.setMood(mood);
						userMood.setAccount(account);
						userMood.setWhy(info.getWhy());
						userMood.setLatitude(info.getLatitude());
						userMood.setLongitude(info.getLongitude());
						daoUserMood.save(userMood);
						Success();
						HttpRequestPoster.sendGetRequest(URL_FACEBOOK_DEBUG,
								"q=" + getMessage("app.url") + "profile/"
										+ account.getUsername());
						log.info("mood() --> YES");
					} else {
						NoContent();
					}
				} else {
					log.info("mood() --> Not found. ");
					NoContent();
				}
			} else {
				NotAcceptable();

			}
		} else {
			log.info("mood() --> Invalid key. ");
			Forbidden();
		}
		setEntity();
		log.info("executed ---> mood()");
		return builder.build();
	}

	@Override
	@Transactional
	public Response updateMood(Info info) {
		log.info("executing ---> updateMood() ");
		if (isKeyValid(info.getKey())) {
			if (StringUtils.isNotBlank(info.getUsername())
					&& StringUtils.isNotBlank(info.getWhy())) {
				if (isUsernmaeExists(info.getUsername())) {
					Account account = getAccount(info.getUsername());
					if (account != null) {
						UserMood userMood = daoUserMood.currentMood(info
								.getUsername());
						if (userMood != null) {
							userMood.setWhy(info.getWhy());
							daoUserMood.update(userMood);
							Success();
							HttpRequestPoster.sendGetRequest(
									URL_FACEBOOK_DEBUG,
									"q=" + getMessage("app.url") + "profile/"
											+ account.getUsername());
							log.info("updateMood() --> YES");
						} else {
							NoContent();
						}
					} else {
						NoContent();
					}
				} else {
					log.info("updateMood() --> Not found. ");
					NoContent();
				}
			} else {
				NotAcceptable();

			}
		} else {
			log.info("updateMood() --> Invalid key. ");
			Forbidden();
		}
		setEntity();
		log.info("executed ---> updateMood()");
		return builder.build();
	}

	@Override
	@Transactional
	public Response getMood(Info info) {
		log.info("executing ---> getMood() ");
		setEntity();
		if (isKeyValid(info.getKey())) {
			if (StringUtils.isNotBlank(info.getUsername())
					&& info.getUserMoodId() != null && info.getUserMoodId() > 0) {
				if (isUsernmaeExists(info.getUsername())) {
					Account account = getAccount(info.getUsername());
					UserMood userMood = daoUserMood.findOne(info
							.getUserMoodId());
					// UserMood userMood1 = new UserMood();

					List<Comment> comments = userMood.getComments();
					// Set<Comment> commentList = new HashSet<Comment>();
					Iterator<Comment> itr = comments.iterator();
					PrettyTime p = new PrettyTime();
					while (itr.hasNext()) {
						Comment temp = itr.next();
						temp.setAgo(p.format(temp.getCreated()));
						temp.setAccount(temp.getAccount());
						// commentList.add(temp);
						// userMood1.setComments(commentList);
					}
					userMood.setAccount(userMood.getAccount());
					userMood.setMood(userMood.getMood());
					if (account != null && userMood != null) {
						Success();
						setEntity(userMood);
						log.info("getMood() --> YES");
					} else {
						NoContent();
					}
				} else {
					log.info("getMood() --> Not found. ");
					NoContent();
				}
			} else {
				NotAcceptable();
			}
		} else {
			log.info("getMood() --> Invalid key. ");
			Forbidden();
		}
		log.info("executed ---> getMood()");
		return builder.build();
	}

	@Override
	@Transactional
	public Response getCurrentMood(Info info) {
		log.info("executing ---> getCurrentMood() ");
		setEntity();
		if (isKeyValid(info.getKey())) {
			if (StringUtils.isNotBlank(info.getUsername())) {
				if (isUsernmaeExists(info.getUsername())) {
					Account account = getAccount(info.getUsername());

					UserMood userMood = daoUserMood.currentMood(info
							.getUsername());

					if (account != null && userMood != null) {
						Success();
						setEntity(userMood);
						log.info("getCurrentMood() --> YES");
					} else {
						NoContent();
					}
				} else {
					log.info("getCurrentMood() --> Not found. ");
					NoContent();
				}
			} else {
				NotAcceptable();
			}
		} else {
			log.info("getCurrentMood() --> Invalid key. ");
			Forbidden();
		}
		log.info("executed ---> getCurrentMood()");
		return builder.build();
	}

	@Override
	@Transactional
	public Response addComment(Info info) {
		log.info("executing ---> addComment() ");
		setEntity();
		if (isKeyValid(info.getKey())) {
			if (StringUtils.isNotBlank(info.getUsername())
					&& info.getUserMoodId() > 0
					&& StringUtils.isNotBlank(info.getFrom())
					&& StringUtils.isNotBlank(info.getComment())) {
				if (isUsernmaeExists(info.getUsername())) {
					Account account = getAccount(info.getUsername());
					Account from = getAccount(info.getFrom());
					UserMood userMood = daoUserMood.findOne(info
							.getUserMoodId());
					if (account != null && from != null && userMood != null) {

						Comment comment = new Comment();
						comment.setUserMood(userMood);
						comment.setAccount(from);
						comment.setText(info.getComment());
						daoComment.save(comment);
						userMood = daoUserMood.findOne(info.getUserMoodId());

						try {
							Thread t = new Thread(
									new EmailThread(
											account,
											new String[] { account.getEmail() },
											velocityEmailSender,
											getMessage("app.url"),
											"Emodoki Alert : You have received 1 comment!",
											"notification.vm", false));
							t.start();
						} catch (Exception e) {

						}
						Success();
						// setEntity(userMood);
						log.info("addComment() --> YES");
					} else {
						NoContent();
					}
				} else {
					log.info("addComment() --> Not found. ");
					NoContent();
				}
			} else {
				NotAcceptable();
			}
		} else {
			log.info("addComment() --> Invalid key. ");
			Forbidden();
		}
		log.info("executed ---> addComment()");
		return builder.build();
	}

	private boolean isEmailExists(String email) {
		Account account = daoAccount.findByEmail(email);
		if (account != null)
			return true;
		return false;
	}

	private boolean isUsernmaeExists(String username) {
		Account account = daoAccount.findByUsername(username);
		if (account != null)
			return true;
		return false;
	}

	/** Get the friends info **/
	@Override
	@Transactional
	public Response getFriendsInfo(FriendsInfo accountInfo) {
		log.info("executing--->getFriendsInfo()");

		setEntity(); // Creates default response.
		if (accountInfo != null) {
			String key = accountInfo.getKey();
			if (isKeyValid(key)) {
				Account account = daoAccount.findByUsername(accountInfo
						.getUserName());
				List<FriendInfo> allFriends = extractFriendInfo(account); // extract
																			// friendsinfo
																			// needed
																			// data

				FriendsInfo friendsInfo = new FriendsInfo();
				friendsInfo.setFriendsCount(account.getFriendCount());
				friendsInfo.setFriendsInfo(allFriends);
				Success(); // Sets the success message string that will b used
							// by setEntity(param) method.
				setEntity(friendsInfo); // Sets the entity to send the response.
			} else {
				log.info("getFriendsInfo()--->Invalid Key.");
			}
		} else {
			NotAcceptable();
		}
		log.info("executed ---> getFriendsInfo()");
		return builder.build();
	}

	/** Get the friends info **/
	@Override
	@Transactional
	public Response getInviteNotification(BasicInfo basicInfo) {
		log.info("executing--->getFriendsInfo()");
		// setEntity(); // Creates default response.
		if (basicInfo != null) {
			String key = basicInfo.getKey();
			// setEntity(); // Creates default response.
			if (isKeyValid(key)) {
				AccountInfo accountInfo = new AccountInfo();
				Set<Notification> notification = new HashSet<Notification>();
				Account account = daoAccount.findByUsername(basicInfo
						.getUserName());
				List<Invitation> listInvitation = daoInvitation
						.findByEmail(account.getEmail());
				Iterator<Invitation> itr = listInvitation.iterator();

				while (itr.hasNext()) {
					Notification notify = new Notification();
					Account account1 = itr.next().getAccount();
					String firstName = account1.getFirstName();
					String lastName = account1.getLastName();
					String userName = account1.getUsername();
					String email = account1.getEmail();
					String facebookImageUrl = null;
					String imageUrl = account1.getEmodokiImageUrl();
					String whichProfileImageUrl = account1
							.getWhichProfilePicture();
					if (whichProfileImageUrl.equalsIgnoreCase("emodoki")) {
						notify.setImageUrl(imageUrl);
					} else {

						Set<UserConnection> userConnection = account1
								.getUserConnections();
						Iterator<UserConnection> userConnectionitr = userConnection
								.iterator();
						while (userConnectionitr.hasNext()) {
							facebookImageUrl = userConnectionitr.next()
									.getImageUrl();
						}
						notify.setImageUrl(facebookImageUrl);
					}

					notify.setFirstName(firstName);
					notify.setLastName(lastName);
					notify.setEmail(email);
					notify.setUserName(userName);
					notification.add(notify);
					accountInfo.setNotification(notification);
				}
				setEntity(accountInfo);
				// Success(); // Sets the success message string that will b
				// used by setEntity(param) method.

			} else {
				NotAcceptable();
			}
		} else {
			Forbidden();
		}
		log.info("executed ---> getInvitationNotification()");
		return builder.build();
	}

	/** Add a friend **/
	@Override
	@Transactional
	public Response addFriend(AddFriendInfo addFriendInfo) {
		log.info("executing--->getInviteNotification()");

		setEntity(); // Creates default response.
		if (addFriendInfo != null) {
			String key = addFriendInfo.getKey();
			String fromUsername = addFriendInfo.getSenderUserName();
			if (isKeyValid(key)) {

				if (!fromUsername.equals("")) {
					Account account = daoAccount.findByUsername(addFriendInfo
							.getUserName());
					InvitationId id = new InvitationId(fromUsername,
							account.getEmail());
					Invitation invitation = daoInvitation.findOne(id);
					invitation.setStatus(1);
					daoInvitation.update(invitation);

					Set<Friend> friendsOfUser = account.getFriendsForUsername();
					Iterator<Friend> itrFriends = friendsOfUser.iterator();
					while (itrFriends.hasNext()) {
						System.out.println(itrFriends.next().getId()
								.getFriend()
								+ "sdsadfdsf");
					}

					// System.out.println(fId.getFriend() + "friendFid");

					Success(); // Sets the success message string that will b
								// used by setEntity(param) method.
					setEntity(); // Sets the entity to send the response.
				} else {
					log.info("addFriend()--->From username is empty");
					NotAcceptable();
				}
			} else {
				log.info("addFriend()--->Invalid Key.");
			}
		} else {
			NotAcceptable();
		}

		log.info("executed ---> addFriend()");
		return builder.build();
	}

	/**
	 * Extract the friends info(name, imageUrl).
	 * 
	 * @account The user account of which the data is be extracted.
	 * @return The list of the extracted data.
	 */
	private List<FriendInfo> extractFriendInfo(Account account) {
		List<FriendInfo> allFriends = new ArrayList<FriendInfo>();
		Set<Friend> friends = account.getFriendsForUsername();
		Iterator<Friend> friendItr = friends.iterator();

		while (friendItr.hasNext()) { // Iterate the friends and get needed data
										// (name, imageurl).
			Friend friend = friendItr.next();
			
			Account acc = friend.getAccountByFriend();
			if (acc.getWhichProfilePicture().equalsIgnoreCase("emodoki")) {
				acc.setEmodokiImageUrl(acc.getEmodokiImageUrl());
			} else {
				Set<UserConnection> userConnection = acc.getUserConnections();
				Iterator<UserConnection> itr = userConnection.iterator();
				while (itr.hasNext()) {
					UserConnection userConn = itr.next();
					acc.setEmodokiImageUrl(userConn.getImageUrl());
				}
			}
			PrettyTime p = new PrettyTime();

			FriendInfo friendsInfo = new FriendInfo(friend.getAccountByFriend()
					.getUsername(), friend.getAccountByFriend()
					.getEmodokiImageUrl(), friend.getAccountByFriend()
					.getFirstName(), friend.getAccountByFriend().getLastName(),
					p.format(friend.getAccountByFriend().getLastLogin()),
					friend.getAccountByFriend().getEmail(), friend.getFriendUpdateStatus());
			allFriends.add(friendsInfo);
		}

		return allFriends;
	}

	@Override
	public Response setFriendUpdateStatus(FriendUserInfo friendUserInfo) {
		FriendId friendId = new FriendId();
		friendId.setUsername(friendUserInfo.getUserName());
		friendId.setFriend(friendUserInfo.getFriendUserName());
		log.info(">>>>>>>>>>>>>>>>>>> FriendUsername"
				+ friendUserInfo.getFriendUserName());
		log.info(">>>>>>>>>>>>>>>>>>> username" + friendUserInfo.getUserName());
		Friend frnd = daoFriend.findFriendByUserName(friendId);

		if (frnd != null) {
			log.info(">>>>>>>>>>>>>>>>>>>" + frnd.getId().getUsername());
			frnd.setFriendUpdateStatus(friendUserInfo.getStatus());
			log.info("&&&&&&&&&&&&&&&&&&&&&&&& bEFORE uPDATE : "+friendUserInfo.getStatus());
			daoFriend.update(frnd);
			log.info("&&&&&&&&&&&&&&&&&&&&&&&& After uPDATE");
			Success(); // Sets the success message string that will b
			// used by setEntity(param) method.
			setEntity(); // Sets the entity to send the response.
		} else {
			log.info("friend update --->From username is empty");
			NotAcceptable();
		}

		return builder.build();
	}

	public boolean getFriendStatus(FriendId friendId) {

		boolean status = false;

		Friend frnd = daoFriend.findFriendByUserName(friendId);
		if (frnd != null) {
			if (frnd.getFriendUpdateStatus()) {
				status = true;
			}

		}

		return status;

	}

	@Override
	@Transactional
	public Response getDefaultMood(DefaultMood defaultMood) {
		log.info("executing--->getDefaultMood()");
		String key = defaultMood.getKey();
		if (isKeyValid(key)) {
			Account account = daoAccount.findByUsername(defaultMood
					.getUsername());
			String username = account.getUsername();
			Mood defaultUserMood = account.getMood();
			String mood = defaultUserMood.getDescription();
			System.out.println(username);
			System.out.println(mood);
			DefaultMood userMood = new DefaultMood(username, mood);
			Success();
			setEntity(userMood);
		} else {
			NotAcceptable();
		}
		return builder.build();
	}

	public Response sendInvitation(InvitationId invitationId) {
		log.info("executing--->sendInvitation()");
		String key = invitationId.getKey();
		Invitation invitation = new Invitation();
		if (isKeyValid(key)) {
			if (isEmailExists(invitationId.getEmail())) {
				if (isUsernmaeExists(invitationId.getUsername())) {
					Account account = daoAccount.findByEmail(invitationId
							.getEmail());
					invitation.setAccount(account);
					invitation.setStatus(invitation.getStatus());
					invitation.setId(invitationId);
					Success();
					daoInvitation.save(invitation);
					try {
						sendEmail(account, "", "Invitation Email",
								account.getEmail(), "invitation.vm");

					} catch (Exception e) {
						e.printStackTrace();
					}
					setEntity();
				} else {
					Forbidden();
				}
			} else {
				NotAcceptable();
			}

		} else {
			NotAcceptable();
		}

		return builder.build();
	}

	public Response ignoreInvitation(InvitationId invitationId) {
		if (isKeyValid(invitationId.getKey())) {
			if (isUsernmaeExists(invitationId.getUsername())
					&& isEmailExists(invitationId.getEmail())) {
				Invitation invitation = daoInvitation.findByUsernameAndEmail(
						invitationId.getUsername(), invitationId.getEmail());
				Success();
				daoInvitation.delete(invitation);
				setEntity();
			} else {
				Forbidden();
			}

		} else {
			NotAcceptable();
		}
		return builder.build();
	}

	/** Get the profile of another user having its comments on the current mood **/
	@Override
	@Transactional
	public Response getFriendProfile(FriendProfileInfo friendProfileInfo) {
		log.info("executing--->getDefaultMood()");
		String key = friendProfileInfo.getKey();

		List<CommentInfo> comments = new ArrayList<CommentInfo>(); // User
																	// comments
		// on mood.

		SimpleDateFormat formatter = new SimpleDateFormat();

		if (isKeyValid(key)) {
			if (friendProfileInfo.getFriendName() != null
					&& (!friendProfileInfo.getFriendName().equals(""))) {
				Account account = daoAccount.findByUsername(friendProfileInfo
						.getFriendName()); // Get the user account by his/her
				// name.
				if (account != null) {
					UserMood userMood = daoUserMood
							.currentMood(friendProfileInfo.getFriendName()); // Get
					// the
					// user's
					// current
					// mood
					// by
					// his/her
					// name.

					// Prepare parent object to send in response.
					AccountInfo accountInfo = new AccountInfo(
							account.getUsername(), account.getFirstName(),
							account.getLastName());
					Integer countryCode = Integer.parseInt(account.getCountry()
							.trim());

					if (countryCode != null) {
						accountInfo.setCountry(CountryCode
								.getByCode(countryCode) == null ? ""
								: CountryCode.getByCode(countryCode).getName());
					} else {
						accountInfo.setCountry("");
					}
					// accountInfo.setDob(formatter.format(account.getDob()));
					accountInfo.setDob(account.getDob() != null ? formatter
							.format(account.getDob()) : null);
					accountInfo.setEmail(account.getEmail());
					accountInfo.setGender(account.getGender());
					accountInfo.setKey(account.getKey());
					accountInfo.setLocale(account.getLocale());
					accountInfo.setMood(userMood.getMood().getDescription());
					accountInfo.setFriendCount(account.getFriendCount());
					accountInfo.setWhyMood(userMood.getWhy());
					accountInfo.setMoodImageUrl(userMood.getMood().getImage());
					String whichImageUrl = account.getWhichProfilePicture();
					PrettyTime p = new PrettyTime();

					accountInfo.setLastLogin(account.getLastLogin() != null ? p
							.format(account.getLastLogin()) : p
							.format(new Date()));
					UserMood userMood1 = daoUserMood.currentMood(account
							.getUsername());
					accountInfo.setUserMoodId(userMood1.getId());
					if (whichImageUrl.equalsIgnoreCase("emodoki")) {
						accountInfo.setImageUrl(account.getEmodokiImageUrl());

					} else {

						Set<UserConnection> userConnections = account
								.getUserConnections();
						Iterator<UserConnection> itr = userConnections
								.iterator();
						while (itr.hasNext()) {
							UserConnection user = itr.next();
							String imageurl = user.getImageUrl();
							accountInfo.setImageUrl(imageurl);
						}
					}
					/*
					 * accountInfo.setWhichProfilePicture(account
					 * .getWhichProfilePicture());
					 */
					accountInfo.setUserCreated(account.getCreated());
					/**
					 * Read all comments on the current mood of the user and
					 * prepare them to send as a Response
					 **/
					Iterator<Comment> itrComments = userMood1.getComments()
							.iterator();
					while (itrComments.hasNext()) {
						Comment comment = itrComments.next();
						String text = comment.getText();
						// PrettyTime p = new PrettyTime();
						// String date = formatter.format(comment.getCreated());
						Account account1 = comment.getAccount();
						String firstName = account1.getFirstName();
						String lastName = account1.getLastName();
						String imageUrl = account1.getEmodokiImageUrl();
						String userName = account1.getUsername();

						comments.add(new CommentInfo(text, p.format(comment
								.getCreated()), firstName, lastName, imageUrl,
								userName));
					}

					accountInfo.setComments(comments);
					Success(); // Return success message along with its code.
					setEntity(accountInfo); // set the response object to send
					// as response.
				} else {
					Success();
					NoContent();
				}
			}
		} else {
			NotAcceptable();
		}
		return builder.build();
	}

	@Override
	@Transactional
	public Response acceptInvitation(InvitationId invitationId) {
		if (isKeyValid(invitationId.getKey())) {
			if (isUsernmaeExists(invitationId.getUsername())
					&& isEmailExists(invitationId.getEmail())) {
				InvitationId id = new InvitationId(invitationId.getUsername(),
						invitationId.getEmail());
				Account account = daoAccount.findByUsername(invitationId
						.getUsername());
				Account account1 = daoAccount.findByEmail(invitationId
						.getEmail());

				Invitation invitation = daoInvitation.findOne(id);
				invitation.setStatus(1);
				Success();
				daoInvitation.update(invitation);

				Friend friend = new Friend();
				FriendId fId = new FriendId();
				fId.setUsername(account.getUsername());
				fId.setFriend(account1.getUsername());
				friend.setId(fId);
				daoFriend.save(friend);

				Friend friend1 = new Friend();
				FriendId fId1 = new FriendId();
				fId1.setUsername(account1.getUsername());
				fId1.setFriend(account.getUsername());
				friend1.setId(fId1);
				daoFriend.save(friend1);

				account.setFriendCount(account.getFriendCount() == null ? 1
						: account.getFriendCount() + 1);
				account1.setFriendCount(account1.getFriendCount() == null ? 1
						: account1.getFriendCount() + 1);
				daoAccount.update(account);
			} else {
				Forbidden();
			}
		} else {
			NotAcceptable();
		}
		setEntity();
		return builder.build();
	}

	@Override
	@Transactional
	public Response forgotPassword(ForgotPassword forgotPassword) {
		log.info("executing--->forgotPassword()");
		String key = forgotPassword.getKey();
		String email = forgotPassword.getEmail();
		if (isKeyValid(key)) {
			if (email != null && !"".equals(email) && isEmailExists(email)) {
				String uuid = UUID.randomUUID().toString();
				Account account = daoAccount.findByEmail(forgotPassword
						.getEmail());
				ResetPassword resetPassword = new ResetPassword();
				resetPassword.setAccount(account);
				resetPassword.setCreated(new Date());
				resetPassword.setUuid(uuid);
				resetPassword
						.setStatus(IConstants.ResetPassword.STATUS_PENDING);
				Success();
				daoResetPassword.save(resetPassword);
				try {
					sendEmail(account, uuid,
							getMessage("reset.password.subject"),
							account.getEmail(), "reset-password.vm");

				} catch (Exception e) {

					e.printStackTrace();
				}

			} else {
				Forbidden();
			}
		} else {
			NotAcceptable();
		}
		setEntity();
		return builder.build();
	}

	@Override
	@Transactional
	public Response facebookLogin(FacebookLogin facebookLogin) {
		String key = facebookLogin.getKey();
		Account account = null;
		if (isKeyValid(key)) {
			if (!(isEmailExists(facebookLogin.getEmail()))) {
				log.info("!isEmailExists");
				CommonInUtil inUtil = new CommonInUtil();
				String uniqUserName = null;
				String emailId = facebookLogin.getEmail();
				String userName = inUtil.SubEmailId(emailId);
				String ranUserName = null;
				log.info("=======================>>>>"
						+ isUsernmaeExists(userName));
				if ((isUsernmaeExists(userName))) {
					while (true) {// use while loop for random check the
									// userName with DataBase
						inUtil = new CommonInUtil();
						uniqUserName = inUtil.UserLogin();
						emailId = facebookLogin.getEmail();
						userName = inUtil.SubEmailId(emailId);
						ranUserName = userName + uniqUserName;
						Boolean value = isUsernmaeExists(ranUserName);
						if (!value)
							break;
					}
					 account = new Account();
					UserConnection userConnection = new UserConnection();
					UserConnectionId userId = new UserConnectionId();
					account.setFirstName(facebookLogin.getFirstName());
					account.setLastName(facebookLogin.getLastName());
					if (facebookLogin.getCountry() == null
							|| facebookLogin.getCountry().equalsIgnoreCase("")) {
						account.setCountry("00");
					} else {

						account.setCountry(CountryCode.getByCountryName(
								facebookLogin.getCountry().trim()).getNumeric()
								+ "");
					}
					String password = RandomStringUtils.randomAlphanumeric(6);
					account.setPassword(Toolbox.stringToSHA1(password));
					account.setGender(facebookLogin.getGender());
					if (facebookLogin.getDob() != null)
						account.setDob(Toolbox.stringToDate2(facebookLogin
								.getDob()));
					account.setEmail(facebookLogin.getEmail());
					account.setSessionId(facebookLogin.getSessionId());
					account.setIp(facebookLogin.getIp());
					account.setCreated(new Date());
					Mood mood = daoMood.findByName("Neutral");

					account.setMood(mood);
					account.setDisplayMood(Integer
							.parseInt(getMessage("setting.default.no.of.post")));
					account.setEmodokiImageUrl(facebookLogin
							.getFacebookImageUrl());
					account.setUsername(ranUserName);
					account.setWhichProfilePicture("facebook");
					daoAccount.save(account);
					UserMood userMood = new UserMood();
					userMood.setAccount(account);
					userMood.setWhy(facebookLogin.getFirstName()
							+ "'S MOOD FOR TODAY IS " + mood.getDescription());
					userMood.setMood(mood);
					daoUserMood.save(userMood);
					userId.setProviderId(facebookLogin.getProviderId());
					userId.setProvideruserId(facebookLogin.getProviderUserId());
					userId.setUserId(ranUserName);
					userConnection.setAccessToken(facebookLogin
							.getAccessToken());
					userConnection.setDisplayName(facebookLogin
							.getDisplayName());
					userConnection.setExpireTime(facebookLogin.getExpireTime());
					userConnection.setImageUrl(facebookLogin
							.getFacebookImageUrl());
					userConnection.setProfileUrl(facebookLogin.getProfileUrl());
					userConnection.setRank(facebookLogin.getRank());
					userConnection.setId(userId);
					Success();
					daoUserConnection.save(userConnection);
					try {
						sendWelcomeEmail(
								account,
								password,
								getMessage("welcome.subject", new String[] {
										facebookLogin.getFirstName(),
										facebookLogin.getLastName() }),
								facebookLogin.getEmail(), "welcome.vm");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					account = new Account();
					UserConnection userConnection = new UserConnection();
					UserConnectionId userId = new UserConnectionId();
					account.setFirstName(facebookLogin.getFirstName());
					account.setLastName(facebookLogin.getLastName());
					if (facebookLogin.getCountry() == null
							|| facebookLogin.getCountry().equalsIgnoreCase("")) {
						System.out.println("in if country");
						account.setCountry("00");
					} else {
						System.out.println("in else country");
						account.setCountry(CountryCode.getByCountryName(
								facebookLogin.getCountry().trim()).getNumeric()
								+ "");
					}
					String password = RandomStringUtils.randomAlphanumeric(6);
					account.setPassword(Toolbox.stringToSHA1(password));
					account.setGender(facebookLogin.getGender());
					if (facebookLogin.getDob() != null)
						account.setDob(Toolbox.stringToDate2(facebookLogin
								.getDob()));
					account.setEmail(facebookLogin.getEmail());
					account.setSessionId(facebookLogin.getSessionId());
					account.setIp(facebookLogin.getIp());
					account.setCreated(new Date());
					Mood mood = daoMood.findByName("Neutral");
					account.setMood(mood);
					account.setDisplayMood(Integer
							.parseInt(getMessage("setting.default.no.of.post")));
					account.setEmodokiImageUrl(facebookLogin
							.getFacebookImageUrl());
					account.setUsername(userName);
					account.setWhichProfilePicture("facebook");
					daoAccount.save(account);
					UserMood userMood = new UserMood();
					userMood.setAccount(account);
					userMood.setWhy(facebookLogin.getFirstName()
							+ "'S MOOD FOR TODAY IS " + mood.getDescription());
					userMood.setMood(mood);
					daoUserMood.save(userMood);
					userId.setProviderId(facebookLogin.getProviderId());
					userId.setProvideruserId(facebookLogin.getProviderUserId());
					userId.setUserId(userName);
					userConnection.setAccessToken(facebookLogin
							.getAccessToken());
					userConnection.setDisplayName(facebookLogin
							.getDisplayName());
					userConnection.setExpireTime(facebookLogin.getExpireTime());
					userConnection.setImageUrl(facebookLogin
							.getFacebookImageUrl());
					userConnection.setProfileUrl(facebookLogin.getProfileUrl());
					userConnection.setRank(facebookLogin.getRank());
					userConnection.setId(userId);
					Success();
					daoUserConnection.save(userConnection);
					try {
						sendWelcomeEmail(
								account,
								password,
								getMessage("welcome.subject", new String[] {
										facebookLogin.getFirstName(),
										facebookLogin.getLastName() }),
								facebookLogin.getEmail(), "welcome.vm");
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			} else {
				account = daoAccount.findByEmail(facebookLogin
						.getEmail());
				if (facebookLogin.getDob() != null)
					account.setDob(Toolbox.stringToDate2(facebookLogin.getDob()));
				account.setSessionId(facebookLogin.getSessionId());
				account.setIp(facebookLogin.getIp());
				account.setEmodokiImageUrl(facebookLogin.getFacebookImageUrl());
				account.setWhichProfilePicture("facebook");
				daoAccount.update(account);
				Set<UserConnection> connection = account.getUserConnections();
				if (connection == null) {
					UserConnection userConnection = new UserConnection();
					UserConnectionId userId = new UserConnectionId();
					userId.setProviderId(facebookLogin.getProviderId());
					userId.setProvideruserId(facebookLogin.getProviderUserId());
					userId.setUserId(account.getUsername());
					userConnection.setAccessToken(facebookLogin
							.getAccessToken());
					userConnection.setDisplayName(facebookLogin
							.getDisplayName());
					userConnection.setExpireTime(facebookLogin.getExpireTime());
					userConnection.setImageUrl(facebookLogin
							.getFacebookImageUrl());
					userConnection.setProfileUrl(facebookLogin.getProfileUrl());
					userConnection.setRank(facebookLogin.getRank());
					userConnection.setId(userId);
					Success();
					daoUserConnection.save(userConnection);
				} else {
					Iterator<UserConnection> itr = connection.iterator();
					while (itr.hasNext()) {
						UserConnection user = itr.next();
						UserConnectionId userId = user.getId();
						userId.setProviderId(facebookLogin.getProviderId());
						userId.setProvideruserId(facebookLogin
								.getProviderUserId());
						userId.setUserId(account.getUsername());
						user.setAccessToken(facebookLogin.getAccessToken());
						user.setDisplayName(facebookLogin.getDisplayName());
						user.setExpireTime(facebookLogin.getExpireTime());
						user.setImageUrl(facebookLogin.getFacebookImageUrl());
						user.setProfileUrl(facebookLogin.getProfileUrl());
						user.setRank(facebookLogin.getRank());
						user.setId(userId);
						Success();
						daoUserConnection.update(user);
					}

				}

			}

		} else {
			NotAcceptable();
		}
		log.info("UserId=======================>>>>"+ account.getUsername());
		if(account != null)
		setEntity(account.getUsername());
		return builder.build();
	}

	@Override
	@Transactional
	public Response getUserMoods(GetUserMoods moods) {
		// String key = moods.getKey();
		String userName = moods.getUserName();
		/* if(isKeyValid(key)){ */
		if (isUsernmaeExists(userName)) {
			int numberOfMoods = daoAccount.findByUsername(userName)
					.getDisplayMood();
			List<UserMood> userMoods = daoUserMood.getUserMoods(numberOfMoods,
					userName);
			Iterator<UserMood> itr = userMoods.iterator();
			PrettyTime p = new PrettyTime();
			while (itr.hasNext()) {
				UserMood mood = itr.next();
				mood.setAgo(p.format(mood.getCreated()));
				List<Comment> commentSet = mood.getComments();
				Iterator<Comment> itr1 = commentSet.iterator();
				while (itr1.hasNext()) {
					Comment comment = itr1.next();
					comment.setAgo(p.format(comment.getCreated()));

				}

			}

			// System.out.println("size is "+userMoods.size());
			moods.setUserMoods(userMoods);
			Success();
			setEntity(moods);
		} else {
			UnAuthorized();
		}
		/*
		 * } else{ NotAcceptable(); }
		 */

		return builder.build();

	}

	@Override
	@Transactional
	public Response getRecentMoods(GetRecentMoods moods) {
		// String key = moods.getKey();
		String userName = null;

		Account account = daoAccount.findByEmail(moods.getEmail());

		if (account != null) {
			userName = account.getUsername();
			moods.setUserName(account.getUsername());
		}

		/* if(isKeyValid(key)){ */
		if (userName != null) {
			int numberOfMoods = daoAccount.findByUsername(userName)
					.getDisplayMood();
			List<UserMood> userMoods = daoUserMood.getUserMoods(numberOfMoods,
					userName);

			List<RecentMood> recentMoods = new ArrayList<RecentMood>();
			if (userMoods != null && userMoods.size() > 0) {
				for (UserMood uMoods : userMoods) {
					Mood mood = uMoods.getMood();
					RecentMood rMood = new RecentMood();
					rMood.setLatitude(uMoods.getLatitude());
					rMood.setLongitude(uMoods.getLongitude());
					rMood.setMoodDescription(uMoods.getWhy());
					rMood.setTimeAgo(uMoods.getAgo());
					rMood.setCreatedOn(uMoods.getCreated() + "");
					rMood.setImageURL(account.getEmodokiImageUrl());
					if (mood != null) {
						rMood.setMood(uMoods.getMood().getDescription());
						rMood.setMoodImage(uMoods.getMood().getImage());
					}
					if ((uMoods.getLatitude() != null)
							&& (uMoods.getLongitude() != null)) {
						recentMoods.add(rMood);
					}

				}
			}
			moods.setMood(recentMoods);
			List<FriendsMoods> friendsMoods = new ArrayList<FriendsMoods>();;
			List<Friend> friends = daoFriend.findByUserUpdateStatus(userName);
			for (Friend friend : friends) {
				Account accFriend = daoAccount.findByUsername(friend.getId()
						.getFriend());
				//friendsMoods = 
				FriendsMoods friendlist = new FriendsMoods();

				friendlist.setFriendUsername(accFriend.getUsername());
				friendlist.setLatitude(accFriend.getUserLatitude());
				friendlist.setLongitude(accFriend.getUserLongitude());
				//System.out.println("friend.getId().getUsername() : "+friend.getId().getUsername());
				
				int friendsMoodsCount = daoAccount.findByUsername(
						accFriend.getUsername()).getDisplayMood();
				List<UserMood> friendMoods = daoUserMood.getUserMoods(
						friendsMoodsCount, accFriend.getUsername());
				List<RecentMood> recentMoods2 = new ArrayList<RecentMood>();
				for (UserMood uMoods : friendMoods) {
					RecentMood rMood = new RecentMood();
					rMood.setLatitude(uMoods.getLatitude());
					rMood.setLongitude(uMoods.getLongitude());
					rMood.setMood(uMoods.getMood() != null && uMoods.getMood().getDescription() != null ? uMoods.getMood().getDescription() : "");
					//rMood.setMood(uMoods.getMood().getDescription());
					rMood.setMoodDescription(uMoods.getWhy());
					rMood.setTimeAgo(uMoods.getAgo());
					rMood.setCreatedOn(uMoods.getCreated() + "");
					rMood.setMoodImage(uMoods.getMood().getImage());
					rMood.setImageURL(accFriend.getEmodokiImageUrl());
					if ((uMoods.getLatitude() != null)
							&& (uMoods.getLongitude() != null)) {
						recentMoods2.add(rMood);
					}
				}
				friendlist.setMoods(recentMoods2);
				friendsMoods.add(friendlist);

			}
			moods.setFriendsMoods(friendsMoods);
			Success();
			setEntity(moods);
		} else {
			UnAuthorized();
		}

		return builder.build();

	}

	@Override
	@Transactional
	public Response getUserGraph(BasicInfo graph) {
		Graph g = new Graph();
		String key = graph.getKey();
		String userName = graph.getUserName();
		if (isKeyValid(key)) {
			Account account = daoAccount.findByUsername(userName);
			UserGraph userGraph = new UserGraph();
			if (account != null) {
				final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
				List<UserGraph> userGraphList = new ArrayList<UserGraph>();
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, -6);
				Date d = cal.getTime();
				System.out.println("date is " + d);
				Date date = account.getCreated();

				int diffInDays = (int) ((d.getTime() - date.getTime()) / DAY_IN_MILLIS);

				System.out.println("number of days are " + diffInDays);
				List<UserMood> moodList = new ArrayList<UserMood>();
				if (diffInDays > 0) {
					System.out.println("in if");
					Date from = null;
					Date to = null;
					for (int i = 0; i < 7; i++) {
						cal.set(Calendar.HOUR_OF_DAY, 00);
						cal.set(Calendar.MINUTE, 00);
						cal.set(Calendar.SECOND, 00);

						from = cal.getTime();

						cal.add(Calendar.DAY_OF_MONTH, 1);
						to = cal.getTime();
						System.out.println("from " + from + " to " + to);

						UserMood userMoods = daoUserMood.getUserMood(from, to,
								userName);

						if (userMoods != null)
							System.out.println(">>>>>>>> " + userMoods.getId());
						moodList.add(userMoods);

					}
					System.out.println("new usermoods size is "
							+ moodList.size());

					for (UserMood mood : moodList) {
						userGraph = new UserGraph();
						if (mood != null) {
							System.out.println("in if 2");
							if (mood.getMood().getDescription()
									.equals("Very Happy")) {
								userGraph.setX("Mood1");
								userGraph.setY("53");
							}
							if (mood.getMood().getDescription().equals("Happy")) {
								userGraph.setX("Mood2");
								userGraph.setY("40");
							}
							if (mood.getMood().getDescription()
									.equals("Neutral")) {
								userGraph.setX("Mood3");
								userGraph.setY("28");
							}
							if (mood.getMood().getDescription().equals("Sad")) {
								userGraph.setX("Mood4");
								userGraph.setY("15");
							}
							if (mood.getMood().getDescription()
									.equals("Very Sad")) {
								userGraph.setX("Mood5");
								userGraph.setY("2");
							}

							userGraphList.add(userGraph);

						}

						else {
							System.out.println("in else");
							Mood defaultMood = account.getMood();
							if (defaultMood.getDescription().equalsIgnoreCase(
									"Very Happy")) {
								userGraph.setX("Mood1");
								userGraph.setY("53");
							}
							if (defaultMood.getDescription().equalsIgnoreCase(
									"Happy")) {
								userGraph.setX("Mood2");
								userGraph.setY("40");
							}
							if (defaultMood.getDescription().equalsIgnoreCase(
									"Neutral")) {
								userGraph.setX("Mood3");
								userGraph.setY("28");
							}
							if (defaultMood.getDescription().equalsIgnoreCase(
									"Sad")) {
								userGraph.setX("Mood4");
								userGraph.setY("15");
							}
							if (defaultMood.getDescription().equalsIgnoreCase(
									"Very Sad")) {
								userGraph.setX("Mood5");
								userGraph.setY("2");
							}
							/*
							 * userGraph.setX("Mood3"); userGraph.setY("0");
							 */
							userGraphList.add(userGraph);
						}
					}
				} else {
					System.out.println("in else 2");
					Calendar cal1 = Calendar.getInstance();

					cal1.add(Calendar.DAY_OF_MONTH, -(5 + diffInDays));
					Date from = null;
					Date to = null;
					for (int j = 0; j < -(diffInDays - 1); j++) {
						System.out.println("in 0");
						userGraph.setX("Mood3");
						userGraph.setY("0");

						userGraphList.add(userGraph);
					}
					for (int i = 0; i < 6 + diffInDays; i++) {
						cal1.set(Calendar.HOUR_OF_DAY, 00);
						cal1.set(Calendar.MINUTE, 00);
						cal1.set(Calendar.SECOND, 00);

						from = cal1.getTime();
						cal1.add(Calendar.DAY_OF_MONTH, 1);
						to = cal1.getTime();
						System.out.println("from " + from + " to " + to);

						UserMood userMoods = daoUserMood.getUserMood(from, to,
								userName);
						System.out.println("userMod>>>>> " + userMoods);
						if (userMoods != null)
							System.out.println(">>>>>>>> " + userMoods.getId());
						moodList.add(userMoods);

					}
					System.out.println("new usermoods size is "
							+ moodList.size());

					for (UserMood mood : moodList) {
						userGraph = new UserGraph();
						if (mood != null) {
							System.out.println("in if");
							if (mood.getMood().getDescription()
									.equals("Very Happy")) {
								userGraph.setX("Mood1");
								userGraph.setY("53");
							}
							if (mood.getMood().getDescription().equals("Happy")) {
								userGraph.setX("Mood2");
								userGraph.setY("40");
							}
							if (mood.getMood().getDescription()
									.equals("Neutral")) {
								userGraph.setX("Mood3");
								userGraph.setY("28");
							}
							if (mood.getMood().getDescription().equals("Sad")) {
								userGraph.setX("Mood4");
								userGraph.setY("15");
							}
							if (mood.getMood().getDescription()
									.equals("Very Sad")) {
								userGraph.setX("Mood5");
								userGraph.setY("2");
							}

							userGraphList.add(userGraph);

						}

						else {
							System.out.println("in else 6");
							Mood defaultMood = account.getMood();
							if (defaultMood.getDescription().equalsIgnoreCase(
									"Very Happy")) {
								userGraph.setX("Mood1");
								userGraph.setY("53");
							}
							if (defaultMood.getDescription().equalsIgnoreCase(
									"Happy")) {
								userGraph.setX("Mood2");
								userGraph.setY("40");
							}
							if (defaultMood.getDescription().equalsIgnoreCase(
									"Neutral")) {
								userGraph.setX("Mood3");
								userGraph.setY("28");
							}
							if (defaultMood.getDescription().equalsIgnoreCase(
									"Sad")) {
								userGraph.setX("Mood4");
								userGraph.setY("15");
							}
							if (defaultMood.getDescription().equalsIgnoreCase(
									"Very Sad")) {
								userGraph.setX("Mood5");
								userGraph.setY("2");
							}

							/*
							 * userGraph.setX("Mood3"); userGraph.setY("0");
							 */

							userGraphList.add(userGraph);
						}
					}
				}
				g.setValues(userGraphList);

				Success();
				setEntity(g, MediaType.APPLICATION_JSON);

			} else {
				UnAuthorize();
			}
		} else {
			NotAcceptable();
		}

		return builder.build();
	}

	@Transactional
	@Override
	public Response searchFriends(FindFriends findFriends) {
		String key = findFriends.getKey();
		String searchFriend = findFriends.getSearchFriends();
		if (isKeyValid(key)) {
			List<Account> account = daoAccount.findByWord(searchFriend);
			if (account != null) {
				List<FriendInfo> friendInfoList = new ArrayList<FriendInfo>();
				Iterator<Account> itr = account.iterator();
				FriendsInfo friendsInfo = new FriendsInfo();
				while (itr.hasNext()) {
					FriendInfo userInfo = new FriendInfo();
					Account acc = itr.next();
					if (acc.getWhichProfilePicture()
							.equalsIgnoreCase("emodoki")) {
						acc.setEmodokiImageUrl(acc.getEmodokiImageUrl());
					} else {
						Set<UserConnection> userConn = acc.getUserConnections();
						Iterator<UserConnection> itr1 = userConn.iterator();
						while (itr1.hasNext()) {
							UserConnection userConnection = itr1.next();
							acc.setEmodokiImageUrl(userConnection.getImageUrl());
						}
					}
					userInfo.setFirstName(acc.getFirstName());
					userInfo.setLastName(acc.getLastName());
					userInfo.setUserName(acc.getUsername());
					userInfo.setImgPath(acc.getEmodokiImageUrl());
					userInfo.setEmail(acc.getEmail());
					friendInfoList.add(userInfo);
					System.out.println("size of friendInfoList  is "
							+ friendInfoList.size());
				}

				friendsInfo.setFriendsInfo(friendInfoList);
				Success();
				setEntity(friendsInfo);

			} else {
				NoContent();
			}
		}

		else {
			NotAcceptable();
		}
		return builder.build();
	}

	@Override
	@Transactional
	public Response imageUpload(ImageUpload imageUpload) {
		String key = imageUpload.getKey();
		String userName = imageUpload.getUserName();
		if (isKeyValid(key)) {
			if (isUsernmaeExists(userName)) {
				try {
					// File file = imageUpload.getFile();
					/*
					 * FileInputStream fis = new FileInputStream(file);
					 * FileOutputStream fos = new FileOutputStream(userName +
					 * ".JPG");
					 */

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				UnAuthorize();
			}
		} else {
			NotAcceptable();
		}
		return null;
	}

/*	@Override
	@Transactional
	public Response challengeInvite(ChallengeInvite challengeInvite) {		
		log.info("executing--->ChallengeInvite()");
		String key = challengeInvite.getKey();
		
		if (isKeyValid(key)) {			
			if (isEmailExists(challengeInvite.getUserId())) {
				Challenge challenge = new Challenge();
					Account account = daoAccount.findByEmail(challengeInvite.getUserId());
					challenge.setCreatedBy(account);
					challenge.setDescription(challengeInvite.getChallengeText());
					challenge.setImage(challengeInvite.getImage());
					challenge.setStatus(challengeInvite.getStatus());		
				
					daoChallenge.save(challenge);					
					Iterator<ToUser> iterator = challengeInvite.getToUser().iterator();
					if(iterator.hasNext()){						
						ChallengeUser challengeUser = new ChallengeUser();
						Account account1 = daoAccount.findByUsername(iterator.next().toString());						
						challengeUser.setInvitedUser(account1);
						challengeUser.setCId(challenge);
						daoChallengeUser.save(challengeUser);
					}
					Success();
					setEntity();
				} else {
					Forbidden();
				}
			} else {
				NotAcceptable();
			}

		

		return builder.build();*/
	//}

}

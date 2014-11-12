package com.emodoki.rest.api.impl;

import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emodoki.dao.DaoAccount;
import com.emodoki.dao.DaoChallenge;
import com.emodoki.dao.DaoChallengeComment;
import com.emodoki.dao.DaoChallengeProof;
import com.emodoki.dao.DaoChallengeUser;
import com.emodoki.dao.DaoUserConnection;
import com.emodoki.model.Account;
import com.emodoki.model.Challenge;
import com.emodoki.model.ChallengeComment;
import com.emodoki.model.ChallengeProof;
import com.emodoki.model.ChallengeUser;
import com.emodoki.model.CommonInUtil;
import com.emodoki.model.UserConnection;
import com.emodoki.model.UserConnectionId;
import com.emodoki.rest.api.ChallengeAccount;
import com.emodoki.rest.beans.AcceptInfo;
import com.emodoki.rest.beans.ChallengeDetail;
import com.emodoki.rest.beans.ChallengeInfo;
import com.emodoki.rest.beans.ChallengeUserInfo;
import com.emodoki.rest.beans.CloseChallenge;
import com.emodoki.rest.beans.CloseInfo;
import com.emodoki.rest.beans.CommentInput;
import com.emodoki.rest.beans.CommentResponse;
import com.emodoki.rest.beans.DoneInfo;
import com.emodoki.rest.beans.FriendListInfo;
import com.emodoki.rest.beans.InvitedUserInfoResponse;
import com.emodoki.rest.beans.Login;
import com.emodoki.rest.beans.UserDetailInfo;
import com.emodoki.rest.beans.UserDetailResponse;
import com.emodoki.rest.beans.UserInfo;
import com.emodoki.rest.beans.UserWallResponse;
import com.emodoki.rest.beans.WitnessInfo;
import com.emodoki.rest.beans.WitnessInfoResponse;
import com.emodoki.rest.beans.WitnessName;
import com.emodoki.rest.beans.WitnessResponse;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Service("challengeAccountImpl")
@Transactional
@CrossOriginResourceSharing(allowAllOrigins = true)
public class ChallengeAccountImpl extends ServiceBase implements
		ChallengeAccount {
	String message = "success";
	Log log = LogFactory.getLog(ChallengeAccountImpl.class);
	public static String URL_PROFILE = "http://emodoki.herokuapp.com/profile/";
	public static final String URL_FACEBOOK_DEBUG = "http://developers.facebook.com/tools/debug/og/object";

	@Autowired
	HttpServletRequest request;

	@Autowired
	DaoAccount daoAccount;

	@Autowired
	DaoChallengeComment daoChallengeComment;

	@Autowired
	DaoChallenge daoChallenge;

	@Autowired
	DaoUserConnection daoUserConnection;

	@Autowired
	DaoChallengeUser daoChallengeUser;

	@Autowired
	DaoChallengeProof daoChallengeProof;

	@Autowired
	Environment environment;

	private boolean isUsernameExists(String username) {
		Account account = daoAccount.findByUsername(username);
		if (account != null)
			return true;
		return false;
	}

	public String getUrl(String imagePath) {
		imagePath = "acceptChallengeImages?c=" + imagePath;
		String imageUrl = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort()
				+ request.getServletContext().getContextPath() + "/"
				+ imagePath;
		return imageUrl;

	}

	public String getRejectUrl(String imagePath) {
		imagePath = "rejectChallengeImages?c=" + imagePath;
		String imageUrl = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort()
				+ request.getServletContext().getContextPath() + "/"
				+ imagePath;
		return imageUrl;

	}

	public String getDoneUrl(String imagePath) {
		imagePath = "donechallengeimages?c=" + imagePath;
		String imageUrl = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort()
				+ request.getServletContext().getContextPath() + "/"
				+ imagePath;
		return imageUrl;

	}

	public String getCloseUrl(String imagePath) {
		imagePath = "closechallengeimages?c=" + imagePath;
		String imageUrl = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort()
				+ request.getServletContext().getContextPath() + "/"
				+ imagePath;
		return imageUrl;

	}

	public ArrayList<CommentResponse> getCommentsResponse(Challenge challenge) {
		List<ChallengeComment> comments = challenge.getComments();
		Iterator<ChallengeComment> commentItr = comments.iterator();
		ArrayList<CommentResponse> commentResponse = new ArrayList<CommentResponse>();
		while (commentItr.hasNext()) {
			ChallengeComment challengeComment = commentItr.next();
			CommentResponse comment = new CommentResponse();
			comment.setCommentCount(comments.size());
			comment.setCommentId(challengeComment.getId());
			comment.setMessage(challengeComment.getText());
			comment.setProofImageUrl(challengeComment.getProofImage());
			if (challengeComment.getCreated() != null) {
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				String date1 = formatter.format(challengeComment.getCreated());
				comment.setCreatedDate(date1);
			}
			if (challengeComment.getAccount().getUsername() != null) {
				comment.setCommentedUser(challengeComment.getAccount()
						.getUsername());
			}
			commentResponse.add(comment);
		}
		return commentResponse;
	}

	public ArrayList<WitnessInfoResponse> getWitnessResponse(Challenge challenge) {
		List<ChallengeProof> proofs = challenge.getWitnessProofs();
		Iterator<ChallengeProof> proofItr = proofs.iterator();
		ArrayList<WitnessInfoResponse> witnessResponse = new ArrayList<WitnessInfoResponse>();
		while (proofItr.hasNext()) {
			ChallengeProof challengeProof = proofItr.next();
			WitnessInfoResponse proof = new WitnessInfoResponse();
			proof.setProofCount(proofs.size());
			proof.setProofId(challengeProof.getId());
			proof.setWitnessFbId(challengeProof.getWitnessFbId());
			proof.setWitnessName(challengeProof.getWitnessName());
			witnessResponse.add(proof);
		}
		return witnessResponse;
	}

	@Override
	@Transactional
	public Response challengeInvite(ChallengeInfo challengeInfo) {
		log.info("executing ---> challengeInvite() ");
		String key = challengeInfo.getKey();
		Account account = null;
		Challenge challenge = null;
		ChallengeUser challengeUser = new ChallengeUser();
		// String language=null;

		if (isKeyValid(key)) {
			if (isUsernameExists(challengeInfo.getUserId())) {
				// language=challengeInfo.getLanguage();
				challenge = new Challenge();
				account = daoAccount.findByUsername(challengeInfo.getUserId());
				challenge.setCreatedBy(account);
				challenge.setDescription(challengeInfo.getMessage());

				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				if (challengeInfo.getStartDate() != null) {
					String startDate = challengeInfo.getStartDate();
					try {
						Date date = formatter.parse(startDate);
						challenge.setCreated(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (challengeInfo.getEndDate() != null) {
					String endDate = challengeInfo.getEndDate();
					try {
						Date date = formatter.parse(endDate);
						challenge.setEndDate(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}/*
				 * String res=HttpRequestPoster.urlGetRequest();
				 * 
				 * System.out.println("ewweeweww  "+res);
				 */
				String path1 = "";
				if (challengeInfo.getImageBanner() != null
						&& !challengeInfo.getImageBanner().equals("")) {
					String imageCode = getRandomString(8);
					if (challengeInfo.getLanguage().equals("nl_NL")) {
						path1 = request.getServletContext().getRealPath(
								"/files/duchLanguage.jpg");
						challenge.setLanguage(challengeInfo.getLanguage());
					} else {
						path1 = request.getServletContext().getRealPath(
								"/files/banner.jpg");

						challenge.setLanguage(challengeInfo.getLanguage());
					}
					String path2 = "/challengeImages";
					String imagePath = convertImage1(
							challengeInfo.getImageBanner(), imageCode,
							challengeInfo.getMessage(), path1, path2);
					imagePath = "imgServe?c=" + imagePath;
					imagePath = request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getServerPort()
							+ request.getServletContext().getContextPath()
							+ "/" + imagePath;
					// imagePath = request.getScheme() + "://" +
					// "emoapi.emodoki.com" + "/" + imagePath;
					challenge.setImage(imagePath);
					String rawString = challengeInfo.getImageBanner();
					challenge.setChallengeImage(rawString);
				}

				challenge.setStatus(1);
				challenge.setStatusDescription("Challenge is Open");
				daoChallenge.save(challenge);
				if (challengeInfo.getToUser() != null
						&& !challengeInfo.getToUser().equals("")) {
					Iterator<String> iterator = challengeInfo.getToUser()
							.iterator();
					while (iterator.hasNext()) {
						// UserConnection uc =
						// daoUserConnection.findByFacebookId(iterator.next());
						// if(uc!=null && !uc.equals("")){

						challengeUser.setInvitedUser(iterator.next());
						challengeUser.setChallenge(challenge);
						String challegeUserName = null;
						challegeUserName = challengeInfo
								.getChallengedUserName();
						if (challegeUserName == null)
							challegeUserName = "";
						challengeUser.setInvitedUserName(challegeUserName);
						daoChallengeUser.save(challengeUser);

						/*
						 * }else{ log.info("challenge --> Failed");
						 * NotAcceptable(); }
						 */
					}
				}
				challengeInviteSuccess();
				challengeResponse.setMessage(message);
				challengeResponse.setImagePath(challenge.getImage());
				challengeResponse.setChallengeId(challenge.getSeq());
				String challegeIdGCM = "" + challenge.getSeq();
				builder.entity(challengeResponse);
				builder.type(getJsonMediaType());
				UserConnection conn = daoUserConnection
						.findByFacebookId(challengeInfo.getToUser().get(0));
				if (conn != null) {
					account = daoAccount.findByUsername(conn.getId()
							.getUserId());
					String regId = account.getDeviceKey();

					if (regId != null) {
						account = daoAccount.findByUsername(challengeInfo
								.getUserId());
						String firstName = account.getFirstName();
						String lastName = account.getLastName();
						gcmServiceProvider(regId, firstName, lastName,
								challegeIdGCM);
					}
				} else {
					log.info("challenge --> Failed");
					NotAcceptable();
				}

			} else {
				log.info("authenticate() --> Invalid Key. ");
				Forbidden();
			}

		}
		return builder.build();
	}

	@Override
	@Transactional
	public Response login(Login login) {
		log.info("executing ---> login() ");
		String key = login.getKey();
		Account account = null;
		UserConnection user = null;

		if (isKeyValid(key)) {
			locale = login.getLocale();
			if (login.getEmail() != null && login.getFacebookId() != null
					&& !"".equals(login.getEmail())
					&& !"".equals(login.getFacebookId())) {
				CommonInUtil inUtil = new CommonInUtil();
				user = daoUserConnection
						.findByFacebookId(login.getFacebookId());
				account = daoAccount.findByEmail(login.getEmail());
				if (user != null
						&& account != null
						&& account.getUsername().equals(
								user.getId().getUserId())) {
					account.setIsNewUser(0);
					account.setDeviceKey(login.getApiKey());
					daoAccount.update(account);
					challengeSuccess();
					loginResponse.setMessage(message);
					loginResponse.setIsNewUser(account.getIsNewUser());
					loginResponse.setEmail(account.getEmail());
					loginResponse.setUserId(account.getUsername());
					builder.entity(loginResponse);
					builder.type(getJsonMediaType());
				} else if (user == null && account != null) {
					user = daoUserConnection
							.findByUserId(account.getUsername());
					if (user == null) {
						account.setIsNewUser(1);
						account.setDeviceKey(login.getApiKey());
						daoAccount.update(account);
						// UserConnection userconn =
						// daoUserConnection.findByUserId(account.getUsername());

						UserConnectionId id = new UserConnectionId();
						id.setProvideruserId(login.getFacebookId());
						id.setUserId(account.getUsername());
						id.setProviderId("facebook");
						user = new UserConnection();
						user.setId(id);
						String random = getRandomString(12);
						user.setAccessToken(random);
						user.setAccount(account);
						daoUserConnection.save(user);
						challengeSuccess();

						loginResponse.setMessage(message);
						loginResponse.setIsNewUser(account.getIsNewUser());
						loginResponse.setEmail(account.getEmail());
						loginResponse.setUserId(account.getUsername());
						builder.entity(loginResponse);
						builder.type(getJsonMediaType());
					} else {
						NotAcceptable();
					}
				} else {
					account = new Account();
					String newUserId = inUtil.SubEmailId(login.getEmail());
					newUserId = newUserId + "_" + getRandomString(3);
					account.setUsername(newUserId);
					account.setIsNewUser(1);
					account.setEmail(login.getEmail());
					account.setDeviceKey(login.getApiKey());
					UserConnectionId id = new UserConnectionId();
					id.setProvideruserId(login.getFacebookId());
					id.setProviderId("facebook");
					id.setUserId(newUserId);
					user = new UserConnection();
					user.setId(id);
					String random = getRandomString(12);
					user.setAccessToken(random);
					user.setAccount(account);
					daoUserConnection.save(user);
					challengeSuccess();
					loginResponse.setMessage(message);
					loginResponse.setIsNewUser(account.getIsNewUser());
					loginResponse.setEmail(account.getEmail());
					loginResponse.setUserId(account.getUsername());
					builder.entity(loginResponse);
					builder.type(getJsonMediaType());

				}
			} else {
				log.info("authenticate() --> Failed");
				NoContent();
			}
		} else {
			log.info("authenticate() --> Invalid Key. ");
			Forbidden();
		}

		return builder.build();

	}

	@Override
	@Transactional
	public Response friendList(FriendListInfo friendListInfo) {
		log.info("executing ---> friendList() ");
		String key = friendListInfo.getKey();
		Account account = null;
		ArrayList<String> emails = new ArrayList<String>();
		ArrayList<String> contacts = new ArrayList<String>();
		UserConnection user = null;
		if (isKeyValid(key)) {
			if (friendListInfo.getFb_id() != null
					&& !friendListInfo.getFb_id().equals("")) {
				Iterator<String> iterator = friendListInfo.getFb_id()
						.iterator();
				while (iterator.hasNext()) {
					user = daoUserConnection.findByFacebookId(iterator.next());
					if (user != null) {
						String userId = user.getId().getUserId();

						account = daoAccount.findByUsername(userId);
						if (account != null) {
							emails.add(account.getEmail() != null ? account
									.getEmail() : "");
							contacts.add(account.getContact() != null ? account
									.getContact() : "");
							challengeFriendSuccess();
							friendListResponse.setMessage(message);
							friendListResponse.setContactList(contacts);
							friendListResponse.setEmailList(emails);
							builder.entity(friendListResponse);
							builder.type(getJsonMediaType());
						} else {
							log.info("challenge --> Failed");
							NotAcceptable();
						}
					} else {
						log.info("challenge --> Failed");
						challengeFriendSuccess();
						friendListResponse.setMessage(message);
						friendListResponse.setContactList(contacts);
						friendListResponse.setEmailList(emails);
						builder.entity(friendListResponse);
						builder.type(getJsonMediaType());
					}
				}
			} else {
				log.info("friendList() --> Failed");
				NoContent();
			}
		} else {
			log.info("friendList() --> Invalid Key. ");
			Forbidden();
		}
		return builder.build();
	}

	@Override
	@Transactional
	public Response userInfo(UserInfo userInfo) {
		log.info("executing ---> login() ");
		String key = userInfo.getKey();
		Account account = null;
		UserConnection user = null;

		if (isKeyValid(key)) {
			if (userInfo.getFacebookId() != null
					&& !userInfo.getFacebookId().equals("")) {
				user = daoUserConnection.findByFacebookId(userInfo
						.getFacebookId());
				if (user != null) {
					String userId = user.getId().getUserId();

					account = daoAccount.findByUsername(userId);
					if (account != null) {
						challengeFriendSuccess();
						UserDetailResponse info = new UserDetailResponse();
						info.setFirstName(account.getFirstName() != null ? account
								.getFirstName() : "");
						info.setLastName(account.getLastName() != null ? account
								.getLastName() : "");
						if (account.getDob() != null) {
							info.setDob(account.getDob().toString());
						} else {
							info.setDob("");
						}
						info.setEmail(account.getEmail());
						info.setUsername(account.getUsername());
						info.setAbout(account.getAbout() != null ? account
								.getAbout() : "");
						info.setImageUrl(account.getEmodokiImageUrl() != null ? account
								.getEmodokiImageUrl() : "");
						info.setContact(account.getContact() != null ? account
								.getContact() : "");
						info.setGender(account.getGender() != null ? account
								.getGender() : "");
						info.setLocale(account.getLocale() != null ? account
								.getLocale() : "");
						setEntity(info);
					} else {
						log.info("challenge --> Failed");
						NotAcceptable();
					}
				} else {
					log.info("challenge --> Failed");
					NotAcceptable();
				}
			} else {
				log.info("friendList() --> Failed");
				NoContent();
			}
		} else {
			log.info("authenticate() --> Invalid Key. ");
			Forbidden();
		}

		return builder.build();

	}

	@Override
	@Transactional
	public Response challengeInfo(UserInfo userInfo) { // userwall
		log.info("executing ---> login() ");

		String key = userInfo.getKey();
		UserConnection user = null;
		Account account = null;
		ChallengeUser challengeUser = null;
		ArrayList<Challenge> challenges = null;
		List<ChallengeUser> challengeUsers = null;
		ArrayList<CommentResponse> comments = null;
		ArrayList<WitnessInfoResponse> witnessResponse = null;
		List<UserWallResponse> response = new ArrayList<UserWallResponse>();
		ArrayList<ChallengeUserInfo> challengeUserInfo = null;
		ChallengeUserInfo cUserInfo = null;
		Challenge challenge = null;
		if (isKeyValid(key)) {
			if (userInfo.getFacebookId() != null) {
				user = daoUserConnection.findByFacebookId(userInfo
						.getFacebookId());
				if (user != null) {
					String userId = user.getId().getUserId();
					challenges = daoChallenge.findByUsername(userId);
					Iterator<Challenge> iterator = challenges.iterator();
					while (iterator.hasNext()) {
						challenge = iterator.next();
						if (challenge.getStatus() != 0) {
							account = daoAccount.findByUsername(challenge
									.getCreatedBy().getUsername());
							challengeUsers = challenge.getChallegeUsers();
							comments = getCommentsResponse(challenge);
							witnessResponse = getWitnessResponse(challenge);
							UserWallResponse info = new UserWallResponse();
							info.setChallengeId(challenge.getSeq());
							info.setChallengeCreatedBy(account.getFirstName());
							info.setCommentResponse(comments);
							info.setWitnessResponse(witnessResponse);
							info.setStatus(challenge.getStatus());
							info.setStatusDescription(challenge
									.getStatusDescription());
							info.setMessage(challenge.getDescription() != null ? challenge
									.getDescription() : "");
							info.setImageUrl(challenge.getImage() != null ? challenge
									.getImage() : "");
							info.setInvitedUsers(challengeUsers.size());
							Iterator<ChallengeUser> itr = challenge
									.getChallegeUsers().iterator();
							challengeUserInfo = new ArrayList<ChallengeUserInfo>();
							while (itr.hasNext()) {
								challengeUser = itr.next();
								cUserInfo = new ChallengeUserInfo();
								cUserInfo
										.setFdId(challengeUser.getInvitedUser() != null ? challengeUser
												.getInvitedUser() : "");
								String invitedUserName = null;
								invitedUserName = challengeUser
										.getInvitedUserName();
								if (invitedUserName == null)
									invitedUserName = "";

								cUserInfo.setUserName(invitedUserName);
								challengeUserInfo.add(cUserInfo);
								info.setIsAccept(challengeUser.getIsAccept() != null ? challengeUser
										.getIsAccept() : 2);
							}
							// For single invited User isAccept Status.

							info.setChallengeUserInfo(challengeUserInfo);
							if (challenge.getCreated() != null) {
								info.setCreatedDate(challenge.getCreated()
										.toString());
							}
							if (challenge.getEndDate() != null) {
								info.setExpireDate(challenge.getEndDate()
										.toString());
							}
							info.setChallengeType("Created");
							response.add(info);

						}
					}

					ArrayList<ChallengeUser> users = daoChallengeUser
							.findByFbId(userInfo.getFacebookId());
					Iterator<ChallengeUser> itr1 = users.iterator();
					while (itr1.hasNext()) {
						ChallengeUser user1 = itr1.next();
						Challenge challenge1 = daoChallenge.findOne(user1
								.getChallenge().getSeq());
						if (challenge1.getStatus() != 0) {
							account = daoAccount.findByUsername(challenge1
									.getCreatedBy().getUsername());
							comments = getCommentsResponse(challenge1);
							witnessResponse = getWitnessResponse(challenge1);
							UserWallResponse info = new UserWallResponse();

							info.setChallengeId(challenge1.getSeq());
							info.setChallengeCreatedBy(account.getFirstName());
							info.setCommentResponse(comments);
							info.setWitnessResponse(witnessResponse);
							info.setStatus(challenge1.getStatus());
							info.setStatusDescription(challenge1
									.getStatusDescription());
							info.setIsAccept(user1.getIsAccept() != null ? user1
									.getIsAccept() : 2);
							if (challenge1.getCreated() != null) {
								info.setCreatedDate(challenge1.getCreated()
										.toString());
							}
							Iterator<ChallengeUser> itr = challenge1
									.getChallegeUsers().iterator();
							challengeUserInfo = new ArrayList<ChallengeUserInfo>();
							while (itr.hasNext()) {
								challengeUser = itr.next();

							}
							// ChallengeUserInfo challengeUserInfo1=new
							// ChallengeUserInfo();
							String username = challengeUser.getChallenge()
									.getCreatedBy().getUsername();
							user = daoUserConnection.findByUserId(username);
							ChallengeUserInfo cUserInfo1 = new ChallengeUserInfo();
							if(user!=null){
							cUserInfo1
									.setFdId(user.getId().getProvideruserId());
							}else{
								cUserInfo1
								.setFdId("");
						
							}
							String invitedUserNames = null;
							invitedUserNames = challengeUser
									.getInvitedUserName();
							if (invitedUserNames == null)
								invitedUserNames = "";
							cUserInfo1.setUserName(invitedUserNames);
							challengeUserInfo.add(cUserInfo1);
							info.setChallengeUserInfo(challengeUserInfo);
							info.setImageUrl(challenge1.getImage());
							if(challenge!=null){
							info.setInvitedUsers(challenge.getChallegeUsers()
									.size());}else{
										info.setInvitedUsers(0);
									}
							info.setMessage(challenge1.getDescription());
							if (challenge1.getCreated() != null) {
								info.setCreatedDate(challenge1.getCreated()
										.toString());
							}
							if (challenge1.getEndDate() != null) {
								info.setExpireDate(challenge1.getEndDate()
										.toString());
							}
							info.setChallengeType("Invited");
							response.add(info);
						}
					}
					Collections.sort(response);
					Success();
					setEntity(response);

				} else {
					log.info("challengeInfo() --> No content. ");
					NoContent();
				}
			} else {
				log.info("challengeInfo() --> No content. ");
				NoContent();
			}

		} else {
			log.info("challengeInfo() --> Invalid Key. ");
			Forbidden();
		}
		return builder.build();
	}

	@Override
	@Transactional
	public Response addComment(CommentInput commentInput) {
		log.info("executing ---> addComment() ");
		if (isKeyValid(commentInput.getKey())) {
			if (StringUtils.isNotBlank(commentInput.getUserName())
					&& commentInput.getChallengeId() != null) {
				if (isUsernameExists(commentInput.getUserName())) {
					Account account = getAccount(commentInput.getUserName());
					Challenge challenge = daoChallenge.findOne(commentInput
							.getChallengeId());
					if (account != null && challenge != null) {

						Date date = new Date();
						ChallengeComment comment = new ChallengeComment();
						comment.setChallenge(challenge);
						comment.setAccount(account);
						comment.setCreated(date);
						comment.setText(commentInput.getCommentText());
						if (commentInput.getProofImage() != null
								&& !commentInput.getProofImage().equals("")) {
							String imageCode = getRandomString(8);
							String imagePath = convertProofImage(
									commentInput.getProofImage(), imageCode);
							imagePath = "proofImageServlet?c=" + imagePath;
							imagePath = request.getScheme()
									+ "://"
									+ request.getServerName()
									+ ":"
									+ request.getServerPort()
									+ request.getServletContext()
											.getContextPath() + "/" + imagePath;
							// imagePath = request.getScheme() + "://" +
							// "emoapi.emodoki.com" + "/" + imagePath;
							comment.setProofImage(imagePath);
						}
						daoChallengeComment.save(comment);
						challenge.getComments().add(comment);
						Success();
						CommentResponse commentResponse = new CommentResponse();
						int count = challenge.getComments().size();
						commentResponse.setCommentCount(count);
						commentResponse.setCommentId(comment.getId());
						commentResponse.setMessage("success");
						commentResponse.setProofImageUrl(comment
								.getProofImage());

						if (comment.getCreated() != null) {
							SimpleDateFormat formatter = new SimpleDateFormat(
									"yyyy-MM-dd hh:mm:ss");
							String date1 = formatter.format(comment
									.getCreated());
							commentResponse.setCreatedDate(date1);
						}
						commentResponse.setCommentedUser(commentInput
								.getUserName());
						setEntity(commentResponse);
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

	@Override
	@Transactional
	public Response acceptInfo(AcceptInfo acceptInfo) {
		log.info("executing ---> addComment() ");
		ChallengeUser challengeUser = null;
		Challenge challenge = null;
		Account account = null;
		UserConnection userConnection = null;
		if (isKeyValid(acceptInfo.getKey())) {
			if (acceptInfo.getChallengeId() != null
					&& acceptInfo.getIsAccepted() != null) {
				challenge = daoChallenge.findOne(acceptInfo.getChallengeId());
				userConnection = daoUserConnection.findByFacebookId(acceptInfo
						.getFacebookId());
				if (userConnection != null) {
					challengeUser = daoChallengeUser.getByFbIdAndCid(
							acceptInfo.getFacebookId(),
							acceptInfo.getChallengeId());
					String language = challenge.getLanguage();

					String imageText = challenge.getChallengeImage();
					String challengeText = challenge.getDescription();
					String code = getRandomString(8);
					String path1 = "";
					String path12 = "";
					String path2 = "";
					String path21 = "";
					if (language.equals("nl_NL")) {
						path1 = request.getServletContext().getRealPath(
								"/files/dutchChallengeAccepted.jpg");
						path12 = "/acceptChallengeImagesDir";
					} else {
						path1 = request.getServletContext().getRealPath(
								"/files/acceptChallenge.jpg");

						path12 = "/acceptChallengeImagesDir";

					}

					if (language.equals("nl_NL")) {
						path2 = request.getServletContext().getRealPath(
								"/files/dutchChallengeRejected.jpg");
						path21 = "/rejectChallengeImagesDir";
					} else {
						path2 = request.getServletContext().getRealPath(
								"/files/rejectChallenge.jpg");

						path21 = "/rejectChallengeImagesDir";

					}
					String imagePath = convertImage1(imageText, code,
							challengeText, path1, path12);

					String rejectPath = convertImage1(imageText, code,
							challengeText, path2, path21);
					String acceptUrl = getUrl(imagePath);
					String rejectUrl = getRejectUrl(rejectPath);
					account = daoAccount.findByUsername(userConnection.getId()
							.getUserId());
					String firstName = account.getFirstName();
					account = daoAccount.findByUsername(challenge
							.getCreatedBy().getUsername());
					String deviceKey = account.getDeviceKey();
					String challegeIdGCM = ""
							+ challengeUser.getChallenge().getSeq();
					if (challengeUser != null) {
						if (acceptInfo.getIsAccepted() == 1) {
							gcmServiceProvider(deviceKey, firstName, true,
									challegeIdGCM);
							challengeUser.setImageUrl(acceptUrl);
						} else {
							gcmServiceProvider(deviceKey, firstName, false,
									challegeIdGCM);
							challengeUser.setImageUrl(rejectUrl);
						}

						challengeUser.setIsAccept(acceptInfo.getIsAccepted());
						// save imageurl here
						daoChallengeUser.update(challengeUser);
						challenge.setImage(challengeUser.getImageUrl());
						daoChallenge.update(challenge);
						acceptSuccess();
						if (acceptInfo.getIsAccepted() == 0) {
							acceptResponse.setMessage(message
									+ ", Challenge Rejected");
							acceptResponse.setBannerUrl(challengeUser
									.getImageUrl());
						} else {
							acceptResponse.setMessage(message
									+ ", Challenge Accepted");
							acceptResponse.setBannerUrl(acceptUrl);
							// return accept banner in response here
						}
						acceptResponse.setIsAccept(challengeUser.getIsAccept());
						acceptResponse.setChallengeId(challengeUser
								.getChallenge().getSeq());
						builder.entity(acceptResponse);
						builder.type(getJsonMediaType());
					}
				} else {
					log.info("acceptInfo() --> No Acceptable data ");
					NotAcceptable();
				}
			} else {
				log.info("acceptInfo() --> No Acceptable data ");
				NotAcceptable();
			}
		} else {
			log.info("acceptInfo() --> Invalid key. ");
			Forbidden();
		}
		log.info("executed ---> acceptInfo()");
		return builder.build();
	}

	@Override
	@Transactional
	public Response invitedUserInfo(UserInfo userInfo) {
		log.info("executing ---> login() ");
		String key = userInfo.getKey();
		List<ChallengeUser> challengeUsers = null;
		List<InvitedUserInfoResponse> response = new ArrayList<InvitedUserInfoResponse>();
		ChallengeUser challengeUser = null;
		Challenge challenge = null;
		UserConnection userConnection = null;
		if (isKeyValid(key)) {
			if (userInfo.getFacebookId() != null) {
				userConnection = daoUserConnection.findByFacebookId(userInfo
						.getFacebookId());
				if (userConnection != null) {
					challengeUsers = daoChallengeUser.findByFbId(userInfo
							.getFacebookId());
					if (challengeUsers != null) {
						Iterator<ChallengeUser> iterator = challengeUsers
								.iterator();
						while (iterator.hasNext()) {
							challengeUser = iterator.next();
							challenge = daoChallenge.findOne(challengeUser
									.getChallenge().getSeq());
							challengeUsers = challenge.getChallegeUsers();
							Date date = new Date();
							if (challenge.getEndDate() != null) {
								if ((challengeUser.getIsAccept() == null || challengeUser
										.getIsAccept() == 2)
										&& challenge.getEndDate().compareTo(
												date) > 0) {
									InvitedUserInfoResponse info = new InvitedUserInfoResponse();
									info.setChallengeId(challenge.getSeq());
									info.setMessage(challenge.getDescription() != null ? challenge
											.getDescription() : "");
									info.setImageUrl(challenge.getImage() != null ? challenge
											.getImage() : "");
									info.setAccepted(2);
									response.add(info);
								}
							}
						}
						Success();
						setEntity(response);
					} else {
						log.info("challengeInfo() --> No content. ");
						NoContent();
					}
				} else {
					log.info("challengeInfo() --> No acceptable. ");
					NotAcceptable();
				}
			} else {
				log.info("challengeInfo() --> No acceptable. ");
				NotAcceptable();
			}
		} else {
			log.info("challengeInfo() --> Invalid Key. ");
			Forbidden();
		}
		return builder.build();
	}

	@Override
	@Transactional
	public Response witnessInfo(WitnessInfo witnessInfo) {
		log.info("executing ---> witnessInfo() ");
		if (isKeyValid(witnessInfo.getKey())) {

			if (witnessInfo.getChallengeId() != null
					&& witnessInfo.getWitnessFbId() != null) {
				ChallengeProof proof = null;
				Challenge challenge = daoChallenge.findOne(witnessInfo
						.getChallengeId());
				ArrayList<WitnessName> list = witnessInfo.getWitnessFbId();
				Iterator<WitnessName> itr = list.iterator();
				while (itr.hasNext()) {
					WitnessName witnessName = itr.next();

					proof = new ChallengeProof();
					proof.setChallenge(challenge);
					proof.setWitnessFbId(witnessName.getFbId());
					proof.setWitnessName(witnessName.getName());
					daoChallengeProof.save(proof);
					challenge.getWitnessProofs().add(proof);
				}
				Success();
				WitnessResponse response = new WitnessResponse();
				response.setMessage("success");
				response.setChallengeId(proof.getChallenge().getSeq());
				response.setChallengedUserId(witnessInfo.getChallengedUserId());
				response.setProofId(proof.getId());
				setEntity(response);
			} else {
				log.info("witnessInfo() --> No Valid Content. ");
				NoContent();
			}
		} else {
			log.info("witnessInfo() --> Invalid key. ");
			Forbidden();
		}
		log.info("executed ---> witnessInfo()");
		return builder.build();
	}

	@Override
	@Transactional
	public Response doneChallenge(CloseChallenge closeChallenge) {

		log.info("executing ---> witnessInfo() ");
		if (isKeyValid(closeChallenge.getKey())) {

			if (closeChallenge.getChallengeId() != null) {

				Challenge challenge = daoChallenge.findOne(closeChallenge
						.getChallengeId());

				if (challenge != null) {
					Account account = daoAccount.findByUsername(challenge
							.getCreatedBy().getUsername());
					String regId = account.getDeviceKey();

					// daoChallengeUser.findByChallengeIds(closeChallenge.getChallengeId());
					List<ChallengeUser> user = challenge.getChallegeUsers();
					ChallengeUser user1 = user.get(0);
					UserConnection conn = daoUserConnection
							.findByFacebookId(user1.getInvitedUser());
					Account account1 = daoAccount.findByUsername(conn.getId()
							.getUserId());
					String firstName = account1.getFirstName();
					// System.out.println(key);
					// System.out.println(name);

					String imageText = challenge.getChallengeImage();
					String challengeText = challenge.getDescription();
					String code = getRandomString(8);
					String lang = challenge.getLanguage();
					String path1 = "";
					String path2 = "";

					if (lang.equals("nl_NL")) {
						path1 = request.getServletContext().getRealPath(
								"/files/doneLanguage.jpg");
						path2 = "/donechallengeimagesdir";
					} else {
						path1 = request.getServletContext().getRealPath(
								"/files/doneChallenge.jpg");
						path2 = "/donechallengeimagesdir";
					}
					String donePath = convertImage1(imageText, code,
							challengeText, path1, path2);
					String doneUrl = getDoneUrl(donePath);
					/*
					 * challengeUser.setImageUrl(doneUrl);
					 * daoChallengeUser.update(challengeUser);
					 */
					challenge.setStatus(2);
					challenge.setDoneUrl(doneUrl);
					challenge.setStatusDescription("Challenge as Done");
					challenge.setImage(doneUrl);
					daoChallenge.update(challenge);

					Success();
					DoneInfo info = new DoneInfo();
					info.setMessage("success");
					info.setImageUrl(doneUrl);
					info.setChallengeId(closeChallenge.getChallengeId());
					String challegeIdGCM = "" + closeChallenge.getChallengeId();
					setEntity(info);
					gcmServiceProvider(regId, firstName, challegeIdGCM);
				} else {
					log.info("doneChallenge() --> Not Acceptable. ");
					NotAcceptable();
				}
			} else {

				log.info("doneChallenge() --> No Content. ");
				NoContent();
			}
		} else {
			log.info("doneChallenge() --> Invalid key. ");
			Forbidden();
		}
		log.info("executed ---> doneChallenge()");
		return builder.build();
	}

	@Override
	@Transactional
	public Response closeChallenge(CloseChallenge closeChallenge) {
		log.info("executing ---> closeChallenge() ");
		if (isKeyValid(closeChallenge.getKey())) {
			if (closeChallenge.getChallengeId() != null) {
				Challenge challenge = daoChallenge.findOne(closeChallenge
						.getChallengeId());
				if (challenge != null && challenge.getStatus() == 2) {
					challenge.setStatus(3);
					String imageText = challenge.getChallengeImage();
					String challengeText = challenge.getDescription();
					String code = getRandomString(8);
					String lang = challenge.getLanguage();
					String path1 = "";
					String path2 = "";
					if (lang.equals("nl_NL")) {
						path1 = request.getServletContext().getRealPath(
								"/files/dutchclosechallenge.png");
						path2 = "/closechallengeimagesdir";

					} else {
						path1 = request.getServletContext().getRealPath(
								"/files/closechallenge.png");
						path2 = "/closechallengeimagesdir";
					}
					Account account = daoAccount.findByUsername(challenge
							.getCreatedBy().getUsername());
					String firstName = account.getFirstName();
					List<ChallengeUser> users = challenge.getChallegeUsers();
					ChallengeUser user = users.get(0);
					UserConnection conn = daoUserConnection
							.findByFacebookId(user.getInvitedUser());
					Account account1 = daoAccount.findByUsername(conn.getId()
							.getUserId());
					String regId = account1.getDeviceKey();

					String donePath = convertImage1(imageText, code,
							challengeText, path1, path2);
					String closeUrl = getCloseUrl(donePath);
					challenge.setImage(closeUrl);
					challenge.setCloseUrl(closeUrl);
					challenge.setStatusDescription("Challenge is Closed");
					daoChallenge.update(challenge);
					Success();
					CloseInfo info = new CloseInfo();
					info.setMessage("successfuly");
					info.setImageUrl(closeUrl);
					setEntity(info);
					gcmServiceProvider1(regId, firstName);

				} else {
					log.info("closeChallenge() --> Not Acceptable. ");
					NotAcceptable();
				}
			} else {

				log.info("closeChallenge() --> No Content. ");
				NoContent();
			}
		} else {
			log.info("closeChallenge() --> Invalid key. ");
			Forbidden();
		}
		log.info("executed ---> closeChallenge()");
		return builder.build();
	}

	protected void gcmServiceProvider(String regId, String firstName,
			String lastName, String challegeIdGCM) {
		try {

			// String
			// regId=s"APA91bHTvzqi7na3EbB-w9Khroe9rWrtAs966RfKsUYeLYiuQcyFXQbHoLWZakC9MBaULp1pjL2cgT2KPwfaTAACxpsuwhYQceeR_vGiG5CBHuto8P6Rf49L3Qw-YKFJg-P2gI3f2d75mNXiaJkrFzTxDqKnZglcSbd0OBDQZDZRj4IVGCLZsOM";
			String GOOGLE_SERVER_KEY = getMessage("server.api.key");// getMessage("device.regid");
			// System.out.println(regId2);
			String userMessage = firstName + " Challenged You";
			Sender sender = new Sender(GOOGLE_SERVER_KEY);
			Message message = new Message.Builder().collapseKey("price")
					.timeToLive(30).delayWhileIdle(true)
					.addData("price", userMessage)
					.addData("challengeId", challegeIdGCM).build();

			Result result = sender.send(message, regId, 2);

			result.toString();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			request.setAttribute("pushStatus",
					"RegId required: " + ioe.toString());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("pushStatus", e.toString());
		}

	}

	protected void gcmServiceProvider(String regId, String firstName,
			boolean state, String challegeIdGCM) {
		try {

			String GOOGLE_SERVER_KEY = getMessage("server.api.key");// getMessage("device.regid");

			String userMessage = "";
			if (state) {
				userMessage = firstName + " accepted your challenge";
			} else {
				userMessage = firstName + " rejected your challenge ";
			}
			Sender sender = new Sender(GOOGLE_SERVER_KEY);
			Message message = new Message.Builder().collapseKey("price")
					.timeToLive(30).delayWhileIdle(true)
					.addData("price", userMessage)
					.addData("challengeId", challegeIdGCM).build();
			Result result = sender.send(message, regId, 2);
			result.toString();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			request.setAttribute("pushStatus",
					"RegId required: " + ioe.toString());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("pushStatus", e.toString());
		}
	}

	protected void gcmServiceProvider(String regId, String firstName,
			String challegeIdGCM) {
		try {

			String GOOGLE_SERVER_KEY = getMessage("server.api.key");// getMessage("device.regid");

			String userMessage = "";

			userMessage = "Challenge Done By " + firstName;
			Sender sender = new Sender(GOOGLE_SERVER_KEY);
			Message message = new Message.Builder().collapseKey("price")
					.timeToLive(30).delayWhileIdle(true)
					.addData("price", userMessage)
					.addData("challengeId", challegeIdGCM).build();
			Result result = sender.send(message, regId, 2);
			result.toString();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			request.setAttribute("pushStatus",
					"RegId required: " + ioe.toString());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("pushStatus", e.toString());
		}

	}

	protected void gcmServiceProvider1(String regId, String firstName) {
		try {

			String GOOGLE_SERVER_KEY = getMessage("server.api.key");// getMessage("device.regid");

			String userMessage = "";

			userMessage = "Challenge Close By " + firstName;
			Sender sender = new Sender(GOOGLE_SERVER_KEY);
			Message message = new Message.Builder().collapseKey("price")
					.timeToLive(30).delayWhileIdle(true)
					.addData("price", userMessage).build();
			Result result = sender.send(message, regId, 2);
			result.toString();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			request.setAttribute("pushStatus",
					"RegId required: " + ioe.toString());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("pushStatus", e.toString());
		}

	}

	@Override
	@Transactional
	public Response challengeDetail(UserDetailInfo userDetailInfo) {
		log.info("executing ---> challengeDetail() ");
		String key = userDetailInfo.getKey();
		Account account = null;
		Challenge challenge = null;
		ChallengeUser challengeUser = null;
		ArrayList<CommentResponse> comments = null;
		ArrayList<WitnessInfoResponse> witnessResponse = null;
		boolean invited = false;
		if (isKeyValid(key)) {
			if (userDetailInfo.getChallengeId() != null) {
				challenge = daoChallenge.findOne(userDetailInfo
						.getChallengeId());
				if (challenge != null) {
					UserConnection userConnection = daoUserConnection
							.findByFacebookId(userDetailInfo.getFacebookId());
					if (userConnection != null) {
						String userId = userConnection.getId().getUserId();
						account = daoAccount.findByUsername(userId);
						challengeUser = daoChallengeUser.getByFbIdAndCid(
								userDetailInfo.getFacebookId(),
								userDetailInfo.getChallengeId());
						if (challengeUser != null) {
							invited = true;
						}
					}

					Success();
					ChallengeDetail info = new ChallengeDetail();
					info.setChallengeId(challenge.getSeq());
					info.setCreatedBy(challenge.getCreatedBy().getUsername());
					List<ChallengeUser> users = challenge.getChallegeUsers();
					Iterator<ChallengeUser> it = users.iterator();
					ArrayList<String> userList = new ArrayList<String>();
					while (it.hasNext()) {
						ChallengeUser user = it.next();

						userList.add(user.getInvitedUser());

					}
					comments = getCommentsResponse(challenge);
					witnessResponse = getWitnessResponse(challenge);

					info.setCommentResponse(comments);
					info.setWitnessResponse(witnessResponse);
					info.setIsAccept(users.get(0).getIsAccept() != null ? users
							.get(0).getIsAccept() : 2);
					Format formatter = new SimpleDateFormat("dd-mm-yyyy");

					info.setToUser(userList);
					Date startDate = challenge.getCreated();
					Date endDate = challenge.getEndDate();
					String sd = formatter.format(startDate);
					String ed = formatter.format(endDate);
					info.setEndDate(ed);
					info.setStartDate(sd);
					info.setLanguage(challenge.getLanguage());
					info.setDescription(challenge.getDescription());
					info.setImageBanner(challenge.getImage());
					info.setStatus(challenge.getStatus());
					if (invited) {
						info.setChallengeType("Invited");
					} else {
						info.setChallengeType("Created");
					}
					setEntity(info);

				} else {
					log.info("challenge --> Failed");
					NotAcceptable();
				}
			} else {
				log.info("challenge --> Failed");
				NotAcceptable();
			}

		} else {
			log.info("authenticate() --> Invalid Key. ");
			Forbidden();
		}

		return builder.build();

	}

}

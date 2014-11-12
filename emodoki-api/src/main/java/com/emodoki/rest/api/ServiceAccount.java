package com.emodoki.rest.api;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import com.emodoki.model.InvitationId;
import com.emodoki.rest.beans.AccountInfo;
import com.emodoki.rest.beans.AddFriendInfo;
import com.emodoki.rest.beans.BasicInfo;

import com.emodoki.rest.beans.Credential;
import com.emodoki.rest.beans.DefaultMood;
import com.emodoki.rest.beans.FacebookLogin;
import com.emodoki.rest.beans.FindFriends;
import com.emodoki.rest.beans.ForgotPassword;
import com.emodoki.rest.beans.FriendProfileInfo;
import com.emodoki.rest.beans.FriendUserInfo;
import com.emodoki.rest.beans.FriendsInfo;
import com.emodoki.rest.beans.GetRecentMoods;
import com.emodoki.rest.beans.GetUserMoods;
import com.emodoki.rest.beans.ImageUpload;
import com.emodoki.rest.beans.Info;

/**
 * This service has many api such as 1) authenticate() 2) register()
 * 
 * @author Sunny
 * 
 */
@WebService(name = "ServiceAccount", targetNamespace = IConstants.TARGET_NAMESPACE)
@CrossOriginResourceSharing(allowAllOrigins = true)
public interface ServiceAccount {

	@WebMethod
	@POST
	@Path("/authenticate")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used for user autentication", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response authenticate(@Description("Credential") Credential credential);

	@WebMethod
	@POST
	@Path("/register")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to register a user.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response register(
			@Description("Account Information") AccountInfo accountInfo);

	@WebMethod
	@POST
	@Path("/profile")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to get user profile.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response profile(Info info);

	@WebMethod
	@POST
	@Path("/username/exists")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to check if username already exists or not.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response usernameExists(Info info);

	@WebMethod
	@POST
	@Path("/email/exists")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to check if email already exists or not.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response emailExists(Info info);

	@WebMethod
	@POST
	@Path("/mood")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to set user current mood.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response mood(Info info);

	@WebMethod
	@POST
	@Path("/mood/get")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to get user mood.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response getMood(Info info);

	@WebMethod
	@POST
	@Path("/mood/update")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to update user's current mood.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response updateMood(Info info);

	@WebMethod
	@POST
	@Path("/mood/comment/add")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to add comment.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response addComment(Info info);

	@WebMethod
	@POST
	@Path("/mood/now")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to get user current mood.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response getCurrentMood(Info info);

	@WebMethod
	@POST
	@Path("/editProfile")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to edit user's profile.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response editProfile(@Description("accountInfo") AccountInfo accountInfo);

	@WebMethod
	@POST
	@Path("/editPrivacy")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to set privacy.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response editPrivacy(AccountInfo accountInfo);

	@WebMethod
	@POST
	@Path("/editLocation")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to update user location", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response editUserPosition(AccountInfo accountInfo);

	@WebMethod
	@POST
	@Path("/friendsInfo")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to get no. of friends added", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response getFriendsInfo(FriendsInfo accountInfo);

	@WebMethod
	@POST
	@Path("/friendInfoUpdate")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to update friend updates status", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response setFriendUpdateStatus(FriendUserInfo friendUserInfo);

	@WebMethod
	@POST
	@Path("/inviteNotification")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to get no. of friends added", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response getInviteNotification(BasicInfo basicInfo);

	@WebMethod
	@POST
	@Path("/defaultmood")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to get defaultMood", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response getDefaultMood(DefaultMood defaultMood);

	@WebMethod
	@POST
	@Path("/addFriend")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Make a user friend of another user.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response addFriend(AddFriendInfo addFriendInfo);

	@WebMethod
	@POST
	@Path("/sendinvitation")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to send invitation", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response sendInvitation(InvitationId invitationId);

	@WebMethod
	@POST
	@Path("/ignoreinvitation")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to ignore invitation", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response ignoreInvitation(InvitationId invitationId);

	@WebMethod
	@POST
	@Path("/friendProfile")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Get the profile of the another user.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response getFriendProfile(FriendProfileInfo friendProfileInfo);

	@WebMethod
	@POST
	@Path("/acceptInvitation")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Accept the invitation", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response acceptInvitation(InvitationId invitationId);

	@WebMethod
	@POST
	@Path("/forgotPassword")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Forgot password", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response forgotPassword(ForgotPassword forgotPassword);

	@WebMethod
	@POST
	@Path("/facebookLogin")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "To save facebook information", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response facebookLogin(FacebookLogin facebookLogin);

	@WebMethod
	@POST
	@Path("/userMoods")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "get user's moods", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response getUserMoods(GetUserMoods moods);

	@WebMethod
	@POST
	@Path("/recentMoods")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "get user's Recent moods", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response getRecentMoods(GetRecentMoods moods);

	@WebMethod
	@POST
	@Path("/graph")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "get user's graph data", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response getUserGraph(BasicInfo graph);

	@WebMethod
	@POST
	@Path("/searchFriends")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "for search friends", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response searchFriends(FindFriends findFriends);

	@WebMethod
	@POST
	@Path("/imageUpload")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "get user's graph data", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response imageUpload(ImageUpload imageUpload);

	/*@WebMethod
	@POST
	@Path("/challengeInvite")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "for chanllenge invite", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response challengeInvite(ChallengeInvite challengeInvite);*/
}

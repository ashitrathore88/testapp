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

import com.emodoki.rest.beans.AcceptInfo;
import com.emodoki.rest.beans.ChallengeInfo;
import com.emodoki.rest.beans.CloseChallenge;
import com.emodoki.rest.beans.CommentInput;
import com.emodoki.rest.beans.FriendListInfo;
import com.emodoki.rest.beans.Login;
import com.emodoki.rest.beans.UserDetailInfo;
import com.emodoki.rest.beans.UserInfo;
import com.emodoki.rest.beans.WitnessInfo;

@WebService(name = "ChallengeAccount", targetNamespace = IConstants.TARGET_NAMESPACE)
@CrossOriginResourceSharing(allowAllOrigins = true)
public interface ChallengeAccount {
	@WebMethod
	@POST
	@Path("/login")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used for user autentication", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response login(@Description("Login") Login login);

	@WebMethod
	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used for create a challenge", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response challengeInvite(
			@Description("ChallengeInvite") ChallengeInfo challengeInfo);

	@WebMethod
	@POST
	@Path("/friends")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to get friends Info", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response friendList(@Description("friendList") FriendListInfo friendListInfo);

	@WebMethod
	@POST
	@Path("/userInfo")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to get user profile info", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response userInfo(@Description("userInfo") UserInfo userInfo);

	@WebMethod
	@POST
	@Path("/userWall")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to get the challenge info and invited users count", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response challengeInfo(@Description("userInfo") UserInfo userInfo);

	@WebMethod
	@POST
	@Path("/addComment")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to add comment.", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response addComment(CommentInput commentInput);

	@WebMethod
	@POST
	@Path("/acceptInfo")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used for to get information of challenge acceptance", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response acceptInfo(@Description("acceptInfo") AcceptInfo acceptInfo);

	@WebMethod
	@POST
	@Path("/invitedUserInfo")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to get user profile info", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response invitedUserInfo(@Description("invitedUserInfo") UserInfo userInfo);

	@WebMethod
	@POST
	@Path("/witnessInfo")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to get witness info", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response witnessInfo(@Description("witnessInfo") WitnessInfo witnessInfo);

	@WebMethod
	@POST
	@Path("/doneChallenge")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to done the challenge", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response doneChallenge(
			@Description("doneChallenge") CloseChallenge closeChallenge);

	@WebMethod
	@POST
	@Path("/closeChallenge")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to close the challenge after done", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response closeChallenge(
			@Description("closeChallenge") CloseChallenge closeChallenge);
	
	@WebMethod
	@POST
	@Path("/challengeDetail")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Descriptions({
			@Description(value = "Service is used to get the challenge Detail", target = DocTarget.METHOD),
			@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response challengeDetail(
			@Description("userDetailInfo")UserDetailInfo userDetailInfo);
}

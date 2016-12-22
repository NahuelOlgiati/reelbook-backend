package com.reelbook.rest.endpoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.reelbook.core.exception.ValidationException;
import com.reelbook.model.DocumentType;
import com.reelbook.model.Profile;
import com.reelbook.model.User;
import com.reelbook.rest.util.ResponseUtil;
import com.reelbook.service.msg.DBSMsgHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/test")
@Api(value = "test")
public class TestEndPoint {

	@GET
	@Path("/text")
	@ApiOperation(value = "Test Text Plain", response = String.class)
	@Produces(MediaType.TEXT_PLAIN)
	public String text() {
		return "Howdy at " + new Date();
	}

	@GET
	@Path("/model")
	@ApiOperation(value = "Test Text Plain", response = Response.class)
	@Produces(MediaType.APPLICATION_JSON)
	public Response model() {
		List<Profile> profiles = new ArrayList<>();
		profiles.add(new Profile("Perfil", null));
		User newUser = new User("Hola", "caracola", "caracola", "caracola", profiles);
		return Response.ok(newUser).build();
	}

	@GET
	@Path("/auth")
	@ApiOperation(value = "Finds Pets by status", response = String.class)
	@Produces(MediaType.TEXT_PLAIN)
	public String auth() {
		return "Howdy at " + new Date();
	}

	@GET
	@Path("/dbmsg")
	@ApiOperation(value = "Test db msg", response = String.class)
	@Produces(MediaType.TEXT_PLAIN)
	public String dbmsg() {
		return DBSMsgHandler.getMsg("test");
	}

	@GET
	@Path("/reloaddbmsg")
	@ApiOperation(value = "Reload db msg", response = Response.class)
	@Produces(MediaType.TEXT_PLAIN)
	public Response cleardbmsg() {
		DBSMsgHandler.reload();
		return ResponseUtil.success();
	}

	@POST
	@Path("/documentType")
	@ApiOperation(value = "Test document type", response = Response.class)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(DocumentType documentType) {
		return ResponseUtil.success();
	}

	@GET
	@Path("/validation")
	@ApiOperation(value = "Validation msg", response = Response.class)
	@Produces(MediaType.TEXT_PLAIN)
	public Response validation() {
		try {
			List<String> messages = new ArrayList<String>();
			messages.add("PUM1");
			messages.add("PUM2");
			messages.add("PUM3");
			ValidationException v = new ValidationException(messages);
			return ResponseUtil.exceptionMessage(v.getMessages());
		} catch (Exception e) {
			return ResponseUtil.fatalException();
		}
	}
}

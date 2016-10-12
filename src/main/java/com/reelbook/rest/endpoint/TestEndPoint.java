package com.reelbook.rest.endpoint;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.model.DocumentType;
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
	@Produces("text/plain")
	public String text() {
		return "Howdy at " + new Date();
	}

	@GET
	@Path("/auth")
	@ApiOperation(value = "Finds Pets by status", response = String.class)
	@Produces("text/plain")
	public String auth() {
		return "Howdy at " + new Date();
	}

	@GET
	@Path("/dbmsg")
	@ApiOperation(value = "Test db msg", response = String.class)
	@Produces("text/plain")
	public String dbmsg() {
		return DBSMsgHandler.getMsg("test");
	}

	@GET
	@Path("/reloaddbmsg")
	@ApiOperation(value = "Reload db msg", response = String.class)
	@Produces("text/plain")
	public Response cleardbmsg() {
		DBSMsgHandler.reload();
		return ResponseUtil.success();
	}
	
	@POST
	@Path("/documentType")
	@ApiOperation(value = "Test document type", response = String.class)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(DocumentType documentType)
	{
		return ResponseUtil.success();
	}
}

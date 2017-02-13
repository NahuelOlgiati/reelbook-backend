package com.reelbook.rest.app;

import com.reelbook.service.msg.DBSMsgHandler;

public class ParamApp
{
	String clientId = "822657132611-le5ivjjco3upqr3hbqstestj6q2ip2fs.apps.googleusercontent.com";
	String clientSecret = "Q2osaQv70mZZpo5hFgL3wU1z";
	String apiKey = "reelbook-157618";
	
	public static Long getSecondsExpiresRestRegularSession()
	{
		return Long.valueOf(DBSMsgHandler.getMsg("PARAM_SECONDSEXPIRESRESTREGULARSESSION"));
	}
	
	public static String getGoogleApiKey()
	{
		return DBSMsgHandler.getMsg("PARAM_GOOGLE_APIKEY");
	}
	
	public static String getGoogleClientID()
	{
		return DBSMsgHandler.getMsg("PARAM_GOOGLE_CLIENTID");
	}
	
	public static String getGoogleClientSecret()
	{
		return DBSMsgHandler.getMsg("PARAM_GOOGLE_SECRET");
	}
}

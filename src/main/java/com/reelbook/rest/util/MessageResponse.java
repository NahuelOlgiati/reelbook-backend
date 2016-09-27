package com.reelbook.rest.util;

public class MessageResponse {
	private String severity;
	private String summary;
	private String detail;

	MessageResponse(String severity, String summary, String detail) {
		this.severity = severity;
		this.summary = summary;
		this.detail = detail;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}

package com.reelbook.rest.util;

public class ModelResponse {
	private Boolean success;
	private Object model;

	ModelResponse(Boolean success, Object model) {
		this.success = success;
		this.model = model;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}
}

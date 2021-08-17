package com.uxpsystems.assignment.model;

public enum Status {
	Activated("Activated"), Deactivated("Deactivated");
	
	public String status;

	private Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}

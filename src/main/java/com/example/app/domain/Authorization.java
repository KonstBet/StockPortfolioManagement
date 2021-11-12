package com.example.app.domain;

import java.time.LocalDateTime;

public class Authorization {

	private LocalDateTime startdate;
	private LocalDateTime enddate;
	
	public LocalDateTime getStartdate() {
		return startdate;
	}
	public void setStartdate(LocalDateTime startdate) {
		this.startdate = startdate;
	}
	public LocalDateTime getEnddate() {
		return enddate;
	}
	public void setEnddate(LocalDateTime enddate) {
		this.enddate = enddate;
	}
}

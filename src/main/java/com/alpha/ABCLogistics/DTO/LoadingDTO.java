package com.alpha.ABCLogistics.DTO;

public class LoadingDTO {
	private String date;
	private String time;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public LoadingDTO(String date, String time) {
		super();
		this.date = date;
		this.time = time;
	}
	public LoadingDTO() {
		super();
	}
	
	
}

package com.google.codeu.data;

public class UserMarker {
	private double lat;
	private double lng;
	private String name;
	private String time;
	private String date; 
	private String purpose;
	private String content; 

	public UserMarker(double lat, double lng, String name, String time, String date, String content) {
		this.lat = lat;
		this.lng = lng;
		this.content = content;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
	    return lng;
	}

	public String getContent() {
	    return content;
	}
}

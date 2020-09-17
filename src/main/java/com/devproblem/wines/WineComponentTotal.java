package com.devproblem.wines;

public class WineComponentTotal {
	
	private String name;
	private String percentValue;
	
	public WineComponentTotal() {		
	}
	
	public WineComponentTotal(String name, String percentValue) {
		setName(name);
		setPercentValue(percentValue);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPercentValue() {
		return percentValue;
	}
	public void setPercentValue(String percentValue) {
		this.percentValue = percentValue;
	}
}

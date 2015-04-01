package com.gedeng.client.entity;

public class Contact {
	private String telephone;
	private String name;
	private User bindUser;	
	private boolean isFollowed;
	public Contact() {
		telephone = null;
		name = null;
		bindUser = null;
		isFollowed = false;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getBindUser() {
		return bindUser;
	}
	public void setBindUser(User bindUser) {
		this.bindUser = bindUser;
	}
	public boolean getIsFollowed() {
		return isFollowed;
	}
	public void setIsFollowed(boolean isFollowed) {
		this.isFollowed = isFollowed;
	}
}

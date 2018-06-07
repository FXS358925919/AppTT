package com.TT.SincereAgree.amei.entity;

import java.util.HashMap;
import java.util.List;

public class PersonalAlbum {
	// 查看者的ID
	private String watcher;
	// 被查看用户的id
	private String watched;
	private List<Album> freeAlbum;
	// 记录相册是否已经被查看着付费，key: albumid(String), value: 是否已付费(Boolean)
	private HashMap<String, Boolean> charged; 
	private List<Album> chargeAlbum;
	// 查看着与被查看者是否是VIP好友
	private Boolean vipFriend; 
	private List<Album> vipFreeAlbum;
	
	public Boolean getVipFriend() {
		return vipFriend;
	}
	public void setVipFriend(Boolean vipFriend) {
		this.vipFriend = vipFriend;
	}
	public HashMap<String, Boolean> getCharged() {
		return charged;
	}
	public void setCharged(HashMap<String, Boolean> charged) {
		this.charged = charged;
	}
	public String getWatcher() {
		return watcher;
	}
	public void setWatcher(String watcher) {
		this.watcher = watcher;
	}
	public String getWatched() {
		return watched;
	}
	public void setWatched(String watched) {
		this.watched = watched;
	}
	public List<Album> getFreeAlbum() {
		return freeAlbum;
	}
	public void setFreeAlbum(List<Album> freeAlbum) {
		this.freeAlbum = freeAlbum;
	}
	public List<Album> getChargeAlbum() {
		return chargeAlbum;
	}
	public void setChargeAlbum(List<Album> chargeAlbum) {
		this.chargeAlbum = chargeAlbum;
	}
	public List<Album> getVipFreeAlbum() {
		return vipFreeAlbum;
	}
	public void setVipFreeAlbum(List<Album> vipFreeAlbum) {
		this.vipFreeAlbum = vipFreeAlbum;
	}
	
}

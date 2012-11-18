package org.wip.tweetcrawler.model;
import java.util.Date;


public class Tweet {
	private String ID;
	private String Text;
	private Date CreatedAt;
	private String PlaceName;
	private String GeoLatitude;
	private String GeoLongitude;
	
	private String RetweetCount;
	private String RetweetedStatusID;
	private String InReplyToScreenName;
	private String InReplyToStatusID;
	private String InReplyToUserID;
	
	private String UserScreenName;
	private String UserName;
	private String UserLocation;
	private String UserID;
	private String UserLang;
	
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	public Date getCreatedAt() {
		return CreatedAt;
	}
	public void setCreatedAt(Date createdAt) {
		CreatedAt = createdAt;
	}
	public String getPlaceName() {
		return PlaceName;
	}
	public void setPlaceName(String placeName) {
		PlaceName = placeName;
	}
	public String getGeoLatitude() {
		return GeoLatitude;
	}
	public void setGeoLatitude(String geoLatitude) {
		GeoLatitude = geoLatitude;
	}
	public String getGeoLongitude() {
		return GeoLongitude;
	}
	public void setGeoLongitude(String geoLongitude) {
		GeoLongitude = geoLongitude;
	}
	public String getUserScreenName() {
		return UserScreenName;
	}
	public void setUserScreenName(String userScreenName) {
		UserScreenName = userScreenName;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserLocation() {
		return UserLocation;
	}
	public void setUserLocation(String userLocation) {
		UserLocation = userLocation;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getUserLang() {
		return UserLang;
	}
	public void setUserLang(String userLang) {
		UserLang = userLang;
	}
	public String getRetweetCount() {
		return RetweetCount;
	}
	public void setRetweetCount(String retweetCount) {
		RetweetCount = retweetCount;
	}
	public String getRetweetedStatusID() {
		return RetweetedStatusID;
	}
	public void setRetweetedStatusID(String retweetedStatusID) {
		RetweetedStatusID = retweetedStatusID;
	}
	public String getInReplyToScreenName() {
		return InReplyToScreenName;
	}
	public void setInReplyToScreenName(String inReplyToScreenName) {
		InReplyToScreenName = inReplyToScreenName;
	}
	public String getInReplyToStatusID() {
		return InReplyToStatusID;
	}
	public void setInReplyToStatusID(String inReplyToStatusID) {
		InReplyToStatusID = inReplyToStatusID;
	}
	public String getInReplyToUserID() {
		return InReplyToUserID;
	}
	public void setInReplyToUserID(String inReplyToUserID) {
		InReplyToUserID = inReplyToUserID;
	}
	
}

package org.wip.tweetcrawler.model;

public class Relation {
	private String UserOneID="";
	private	String Relation="";
	private	String UserTwoID="";
	
	public Relation (String ID_One, String rlt, String ID_Two){
		UserOneID=ID_One;
		Relation=rlt;
		UserTwoID=ID_Two;
	}
	
	public String getUserOneID() {
		return UserOneID;
	}
	public void setUserOneID(String userOneID) {
		UserOneID = userOneID;
	}
	public String getRelation() {
		return Relation;
	}
	public void setRelation(String relation) {
		Relation = relation;
	}
	public String getUserTwoID() {
		return UserTwoID;
	}
	public void setUserTwoID(String userTwoID) {
		UserTwoID = userTwoID;
	}	
}

package org.wip.tweetcrawler.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import org.wip.tweetcrawler.model.Relation;
import org.wip.tweetcrawler.model.Tweet;

public class DatabaseHandler {
	private String driver = "com.mysql.jdbc.Driver";

	private String url = "jdbc:mysql://10.3.1.158:3306/wip_tweetdata";
	private String user = "wip";
	private String password = "wip";
	private String tweetsTable = "tweets00";
	private String relationsTable = "relations00";
	private String insertTweetSQL = "insert into "
			+ tweetsTable
			+ "(TID,Text,CreatedAt,PlaceName,GeoLatitude,GeoLongitude,RetweetCount,RetweetedStatusID,InReplyToScreenName,InReplyToStatusID,InReplyToUserID,UserScreenName,UserName,UserLocation,UserID,UserLang) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private String insertRelationSQL = "insert into " + relationsTable
			+ "(UserOneID,Relation,UserTwoID) values(?,?,?)";
	private Connection conn = null;
	private java.sql.Timestamp ts = null;
	private PreparedStatement insertTweetPS = null;
	private PreparedStatement insertRelationtPS = null;
	private ArrayList<Tweet> tweetList = null;
	private int tweetCount = 0;
	private int tweetLimit = 200;

	public DatabaseHandler() {
		tweetList = new ArrayList<Tweet>();
		ts = new java.sql.Timestamp(System.currentTimeMillis());
		initDBConn();
	}

	public void initDBConn() {
		try {
			try {
				Class.forName(driver).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(url, user, password);
			insertTweetPS = conn.prepareStatement(insertTweetSQL);
			insertRelationtPS = conn.prepareStatement(insertRelationSQL);
			if (!conn.isClosed())
				System.out.println("Database Connection Success");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeDBConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertTweet(ArrayList<Tweet> l) {
		Tweet t = null;
		long t2 = 0;
		long t1 = System.currentTimeMillis();
		try {
			for (int i = 0; i < l.size(); i++) {
				t = l.get(i);
				insertTweetPS.setString(1, t.getID());
				insertTweetPS.setString(2, t.getText());
				ts.setTime(t.getCreatedAt().getTime());
				insertTweetPS.setTimestamp(3, ts);
				insertTweetPS.setString(4, t.getPlaceName());
				insertTweetPS.setString(5, t.getGeoLatitude());
				insertTweetPS.setString(6, t.getGeoLongitude());
				insertTweetPS.setString(7, t.getRetweetCount());
				insertTweetPS.setString(8, t.getRetweetedStatusID());
				insertTweetPS.setString(9, t.getInReplyToScreenName());
				insertTweetPS.setString(10, t.getInReplyToStatusID());
				insertTweetPS.setString(11, t.getInReplyToUserID());
				insertTweetPS.setString(12, t.getUserScreenName());
				insertTweetPS.setString(13, t.getUserName());
				insertTweetPS.setString(14, t.getUserLocation());
				insertTweetPS.setString(15, t.getUserID());
				insertTweetPS.setString(16, t.getUserLang());
				insertTweetPS.addBatch();
			}
			insertTweetPS.executeBatch();
			insertTweetPS.clearBatch();
			t2 = System.currentTimeMillis();
			System.out.println("insert " + tweetLimit + " tweets in "
					+ (t2 - t1) + " ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Tweet tweet) {
		if (tweetCount < tweetLimit) {
			tweetList.add(tweet);
			tweetCount++;
		} else {
			insertTweet(tweetList);
			tweetList.clear();
			tweetList = null;
			System.gc();
			tweetCount = 0;
			System.out.println(new Date(System.currentTimeMillis()));
			tweetList = new ArrayList<Tweet>();
		}
	}

	public void insertRelation(ArrayList<Relation> list) {
		Relation re = null;
		long t2 = 0;
		long t1 = System.currentTimeMillis();
		try {
			for (int i = 0; i < list.size(); i++) {
				re = list.get(i);
				insertRelationtPS.setString(1, re.getUserOneID());
				insertRelationtPS.setString(2, re.getRelation());
				insertRelationtPS.setString(3, re.getUserTwoID());
				insertRelationtPS.addBatch();
			}
			insertRelationtPS.executeBatch();
			insertRelationtPS.clearBatch();
			t2 = System.currentTimeMillis();
			System.out.println("insert " + list.size() + " relations in "
					+ (t2 - t1) + " ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getTweetsCount() {
		String selectSQL = "select count(*) from " + tweetsTable;
		int count = 0;
		try {
			PreparedStatement selectPS = conn.prepareStatement(selectSQL);
			ResultSet rs = selectPS.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<Long> getUIDsByLimits(int start, int count) {
		String selectSQL = "select UserID from " + tweetsTable + " limit "
				+ start + "," + count;
		ArrayList<Long> list = new ArrayList<Long>();
		try {
			PreparedStatement selectPS = conn.prepareStatement(selectSQL);
			ResultSet rs = selectPS.executeQuery();
			while (rs.next()) {
				list.add(Long.parseLong(rs.getString("UserID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<Tweet> testGetTweets() {
		String selectSQL = "select * from tweetdata where id<=1000";
		ArrayList<Tweet> l = new ArrayList<Tweet>();
		try {
			PreparedStatement selectPS = conn.prepareStatement(selectSQL);
			ResultSet rs = selectPS.executeQuery();
			while (rs.next()) {
				Tweet t = new Tweet();
				t.setID(rs.getString("TID"));
				t.setText(rs.getString("Text"));
				t.setCreatedAt(new Date(rs.getTimestamp("CreatedAt").getTime()));
				t.setPlaceName(rs.getString("PlaceName"));
				t.setGeoLatitude(rs.getString("GeoLatitude"));
				t.setGeoLongitude(rs.getString("GeoLongitude"));
				t.setRetweetCount(rs.getString("RetweetCount"));
				t.setRetweetedStatusID(rs.getString("RetweetedStatusID"));
				t.setInReplyToScreenName(rs.getString("InReplyToScreenName"));
				t.setInReplyToStatusID(rs.getString("InReplyToStatusID"));
				t.setInReplyToUserID(rs.getString("InReplyToUserID"));
				t.setUserScreenName(rs.getString("UserScreenName"));
				t.setUserName(rs.getString("UserName"));
				t.setUserLocation(rs.getString("UserLocation"));
				t.setUserID(rs.getString("UserID"));
				t.setUserLang(rs.getString("UserLang"));
				l.add(t);
			}
			return l;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

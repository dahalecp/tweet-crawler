package org.wip.tweetcrawler.runnable;

import java.nio.charset.Charset;

import org.wip.tweetcrawler.database.DatabaseHandler;
import org.wip.tweetcrawler.model.Tweet;

import twitter4j.*;
import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;

public class TweetCrawler {
	public long count = 0;
	public Tweet tweet = null;
	public TwitterStream twitterStream = null;
	public DatabaseHandler dbh = null;
	public Authorization auth = null;
	public Configuration cfg = null;
	public TwitterImpl ti = null;

	public static void main(String[] args) throws TwitterException {
		TweetCrawler tc = new TweetCrawler();
		tc.init();
		tc.run();
	}

	public void init() {
		
		dbh = new DatabaseHandler();
		tweet = new Tweet();
		initTwitterStream();
	}

	public void initTwitterStream() {
		twitterStream = new TwitterStreamFactory().getInstance();
		auth = twitterStream.getAuthorization();
		cfg = twitterStream.getConfiguration();
		ti = new TwitterImpl(cfg, auth);
		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
				// count++;
				// System.out.println(count);
				tweet = new Tweet();
				// ID
				tweet.setID(String.valueOf(status.getId()));
				// CreatedAt
				tweet.setCreatedAt(status.getCreatedAt());
				// Text
				tweet.setText(convertEncoding(status.getText()));
				// Place Name
				if (status.getPlace() != null) {
					tweet.setPlaceName(status.getPlace().getFullName());
				} else {
					tweet.setPlaceName("");
				}
				// GeoLocation
				if (status.getGeoLocation() != null) {
					tweet.setGeoLatitude(String.valueOf(status.getGeoLocation()
							.getLatitude()));
					tweet.setGeoLongitude(String.valueOf(status
							.getGeoLocation().getLongitude()));
				} else {
					tweet.setGeoLatitude("");
					tweet.setGeoLongitude("");
				}
				// RetweetCount
				tweet.setRetweetCount(String.valueOf(status.getRetweetCount()));
				// RetweetedStatus
				if (status.getRetweetedStatus() != null) {
					tweet.setRetweetedStatusID(String.valueOf(status
							.getRetweetedStatus().getId()));
				} else {
					tweet.setRetweetedStatusID("");
				}
				// Reply
				if (status.getInReplyToScreenName() == null) {
					tweet.setInReplyToScreenName("");
				} else {
					tweet.setInReplyToScreenName(status
							.getInReplyToScreenName());
				}
				if (status.getInReplyToStatusId() == -1) {
					tweet.setInReplyToStatusID("");
				} else {
					tweet.setInReplyToStatusID(String.valueOf(status
							.getInReplyToStatusId()));
				}
				if (status.getInReplyToUserId() == -1) {
					tweet.setInReplyToUserID("");
				} else {
					tweet.setInReplyToUserID(String.valueOf(status
							.getInReplyToUserId()));
				}
				// User
				if (status.getUser() != null) {
					tweet.setUserID(String.valueOf(status.getUser().getId()));
					tweet.setUserScreenName(status.getUser().getScreenName());
					tweet.setUserName(status.getUser().getName());
					if (status.getUser().getLocation() != null) {
						tweet.setUserLocation(status.getUser().getLocation());
					} else {
						tweet.setUserLocation("");
					}
					tweet.setUserLang(status.getUser().getLang());
				} else {
					tweet.setUserID("");
					tweet.setUserScreenName("");
					tweet.setUserName("");
					tweet.setUserLocation("");
					tweet.setUserLang("");
				}
				dbh.insert(tweet);
			}

			public void onDeletionNotice(
					StatusDeletionNotice statusDeletionNotice) {
				// System.out.println("Got a status deletion notice id:"
				// + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				// System.out.println("Got track limitation notice:"
				// + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				// System.out.println("Got scrub_geo event userId:" + userId
				// + " upToStatusId:" + upToStatusId);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}
		};
		twitterStream.addListener(listener);
	}

	public String convertEncoding(String s) {
		return new String(s.getBytes(Charset.forName("ISO-8859-1")),
				Charset.forName("UTF-8"));
	}

	public void run() {
		twitterStream.sample();
	}

}

package org.wip.tweetcrawler.runnable;

import java.util.ArrayList;

import org.wip.tweetcrawler.database.DatabaseHandler;
import org.wip.tweetcrawler.model.Relation;

import twitter4j.IDs;
import twitter4j.TwitterException;
import twitter4j.TwitterImpl;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;

public class RelationCrawler {

	public ArrayList<Relation> rltList = null;
	public TwitterStream twitterStream = null;
	public DatabaseHandler dbh = null;
	public Authorization auth = null;
	public Configuration cfg = null;
	public TwitterImpl ti = null;
	public int count = 0;
	public int pages = 0;
	public int limit = 1000;

	public static void main(String[] args) {
		RelationCrawler rc = new RelationCrawler();
		rc.init();
		rc.run();
	}

	public void init() {
		dbh = new DatabaseHandler();
	}

	public void initTwitterImpl() {
		twitterStream = new TwitterStreamFactory().getInstance();
		auth = twitterStream.getAuthorization();
		cfg = twitterStream.getConfiguration();
		ti = new TwitterImpl(cfg, auth);

	}

	public ArrayList<Relation> getRelationsByUID(long uid) {
		rltList = new ArrayList<Relation>();
		try {
			IDs ids = ti.getFollowersIDs(uid, -1);
			for (int i = 0; i < ids.getIDs().length; i++) {
				rltList.add(new Relation(String.valueOf(ids.getIDs()[i]),
						"follows", String.valueOf(uid)));
			}
			return rltList;
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return rltList;
	}

	public void run() {
		count = dbh.getTweetsCount();
		pages = count / limit;
		for (int i = 0; i < pages; i++) {
			ArrayList<Long> list = dbh.getUIDsByLimits(i * limit, limit);
			System.out.println(list.size());
			for (int j = 0; j < list.size(); j++) {
				dbh.insertRelation(getRelationsByUID(list.get(j)));
			}
		}

	}
}

package helper;

import java.net.UnknownHostException;

import PO.News;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoHelper {
	private Mongo mongo = null;

	public MongoHelper() {
		try {
			mongo = new Mongo("115.29.242.187", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void addNews(News news) {
		if (mongo == null) {
			return;
		}
		DB db = mongo.getDB("newspaper");
		DBCollection collection = db.getCollection("xinhuaNews");
		collection.ensureIndex("url");
		collection.insert(news.toMongo());
	}

	public boolean existNews(String url) {
		DB db = mongo.getDB("newspaper");
		DBCollection collection = db.getCollection("xinhuaNews");
		DBObject obj = new BasicDBObject();
		obj.put("url", url);
		DBCursor cur = collection.find(obj);
		if (cur.hasNext()) {
			return true;
		}
		return false;
	}

}

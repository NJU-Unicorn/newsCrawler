package sample;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import PO.News;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class Sample {
	public static String XINHUA = "xinhuaNews";
	private DBCollection collection = null;

	public Sample(String name) {
		try {
			Mongo mongo = new Mongo("115.29.242.187", 27017);
			DB db = mongo.getDB("newspaper");
			collection = db.getCollection(name);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void showAllNews() {
		showNews(new HashMap<String,String>());
	}
	
	public void showNews(HashMap<String,String> cond) {
		ArrayList<News> list = new ArrayList<News>();
		DBObject query = new BasicDBObject(cond);
		DBCursor cur = collection.find(query);
		while (cur.hasNext()) {
			News news = new News(cur.next());
			list.add(news);
			System.out.println(news);
		}
	}
	
	public static void main(String[] args) {
		Sample s = new Sample(Sample.XINHUA);
		s.showAllNews();
	}
}

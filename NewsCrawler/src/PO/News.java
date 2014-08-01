package PO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class News {
	public String nid;
	public String url;
	public String date;
	public String layout;
	public String title;
	public String subTitle = "";
	public String content = "";

	
	public News() {
		
	}
	
	public News(DBObject o) {
		nid = o.get("news_id").toString();
		url = o.get("url").toString();
		date = o.get("date").toString();
		layout = o.get("layout").toString();
		title = o.get("title").toString();
		subTitle = o.get("subTitle").toString();
		content = o.get("content").toString();
	}

	@Override
	public String toString() {
		return "id=" + nid + "\nurl=" + url + "\ndate=" + date + "\nlayout="
				+ layout + "\ntitle=" + title + "\nsubTitle=" + subTitle
				+ "\ncontent=" + content;
	}

	public DBObject toMongo() {
		DBObject o = new BasicDBObject();
		o.put("news_id", nid);
		o.put("url", url);
		o.put("date", date);
		o.put("layout", layout);
		o.put("title", title);
		o.put("subTitle", subTitle);
		o.put("content", content);
		return o;
	}
}

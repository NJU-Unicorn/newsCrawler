package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import PO.News;

public interface Crawler {
	public ArrayList<News> getNews(Date date) throws IOException;
}

package launcher;

import helper.LogHelper;
import helper.MongoHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import logic.Crawler;
import logic.NewXinhuaCrawler;
import logic.OldXinhuaCrawler;
import PO.News;
import constant.Const;

public class Main {

	static MongoHelper mh = new MongoHelper();

	public static void main(String[] args) {

		int year = 2014;
		int month = 7;
		int day = 1;
		try {
			BufferedReader bfr = new BufferedReader(new FileReader("tmp"));
			year = 2014;
			month = 7;
			day = 1;
			try {
				year = Integer.parseInt(bfr.readLine());
				month = Integer.parseInt(bfr.readLine());
				day = Integer.parseInt(bfr.readLine());
			} catch (Exception e) {
				year = 2014;
				month = 7;
				day = 1;
			}
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		GregorianCalendar calendar = new GregorianCalendar(year, month, day);
		while (true) {
			try {
				ArrayList<News> newsList = getNews(calendar.getTime());
				for (News news : newsList) {
					System.out.print(news.date + "-" + news.title);
					if (!mh.existNews(news.url)) {
						System.out.println("...ok");
						mh.addNews(news);
					} else {
						System.out.println("...already ok");
					}
				}
			} catch (Exception e) {
				calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
				LogHelper.log("Error:" + year + "-" + month + "-" + day);
				continue;
			}
			calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
			LogHelper.logThis(calendar.get(GregorianCalendar.YEAR),
					calendar.get(GregorianCalendar.MONTH),
					calendar.get(GregorianCalendar.DAY_OF_MONTH));
		}
	}

	public static ArrayList<News> getNews(Date date) throws IOException {
		Crawler crawler = null;
		if(date.before(Const.XINHUA_POINT.getTime())) {
			crawler = new OldXinhuaCrawler();
		} else {
			crawler = new NewXinhuaCrawler();
		}
		return crawler.getNews(date);
	}
}

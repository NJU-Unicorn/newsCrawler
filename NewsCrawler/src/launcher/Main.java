package launcher;

import helper.LogHelper;
import helper.MongoHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
				getNews(calendar.getTime());
			} catch (Exception e) {
				calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
				LogHelper.log("Error:" + year + "-" + month + "-" + day);
				continue;
			}
			calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
			LogHelper.logThis(year, month, day);
		}
	}

	public static void getNews(Date date) throws IOException {
		ArrayList<String> titleList = new ArrayList<String>();
		URL listUrl = new URL(Const.XINHUA_LIST_URL(date));
		Document listdom = Jsoup.parse(listUrl, 5000);
		Elements layoutBlocks = listdom.getElementsByAttributeValue("width",
				"710");
		for (Element layoutBlock : layoutBlocks) {
			Element layoutName = layoutBlock.getElementsByAttributeValue(
					"color", "#FFFFFF").get(0);
			String currentLayout = layoutName.text().replace(";", "")
					.replace(" ", "");
			Elements reports = layoutBlock.getElementsByTag("a");
			for (Element report : reports) {
				News news = new News();
				// set time
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateStr = dateFormat.format(date);
				news.date = dateStr;
				// set layout
				news.layout = currentLayout;
				// set url
				String subUrl = report.attr("href");
				news.url = Const.XINHUA_NEWS_URL(date, subUrl);
				// set id
				Pattern p = Pattern.compile("[0-9]+");
				Matcher m = p.matcher(subUrl);
				if (m.find()) {
					news.nid = m.group();
				}
				// set title
				news.title = report.text();
				// System.out.println(news);

				// access to news page
				URL newsUrl = new URL(news.url);
				Document newsdom = Jsoup.parse(newsUrl, 5000);

				Element content = newsdom.getElementById("ozoom");
				Elements title = newsdom.getElementsByClass("font01");
				Elements subtitles = newsdom.getElementsByClass("font02");

				// update title
				if (title != null
						&& !title.toString().replaceAll("<.+?>", "")
								.replaceAll("&[a-z]*;", "").trim().equals("")) {
					news.title = title.text();
				}

				// set sub-title
				String subTitleTxt = "";
				for (Element subtitle : subtitles) {
					String text = subtitle.toString().replaceAll("<.+?>", "")
							.replace("&nbsp;", " ").replaceAll("&[a-z]*;", "");
					if (!text.equals("")) {
						subTitleTxt += text + "\n";
					}
				}
				news.subTitle = subTitleTxt.trim();

				// set content
				String contentTxt = "";
				Elements paras = content.getElementsByTag("p");
				for (Element para : paras) {
					contentTxt += para.toString().replaceAll("<.+?>", "")
							.replace("&nbsp;", "").replaceAll("&[a-z]*;", "")
							+ "\n";
				}
				contentTxt = contentTxt.replaceAll("上接[0-9]*版", "").replaceAll(
						"下转[0-9]*版", "");
				news.content = contentTxt.trim();

				System.out.print(news.date + "-" + news.title);
				if (news.title.equals("") || news.content.equals("")) {
					System.out.println("...empty");
					LogHelper.log("Empty:" + news.date + ":" + news.url);
					break;
				}
				if (titleList.contains(news.title)) {
					System.out.println("...existed");
					LogHelper.log("Existed:" + news.date + ":" + news.url);
				} else {
					titleList.add(news.title);
				}
				if (!mh.existNews(news.url)) {
					System.out.println("...ok");
					mh.addNews(news);
				} else {
					System.out.println("...downloaded");
				}
			}
		}
	}
}

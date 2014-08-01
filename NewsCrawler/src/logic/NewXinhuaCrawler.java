package logic;

import helper.LogHelper;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import PO.News;
import constant.Const;

public class NewXinhuaCrawler implements Crawler {

	@Override
	public ArrayList<News> getNews(Date date) throws IOException {
		ArrayList<News> newsList = new ArrayList<News>();
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
				contentTxt += content.toString().replaceAll("<.+?>", "")
						.replace("&nbsp;", "").replaceAll("&[a-z]*;", "")
						+ "\n";
				contentTxt = contentTxt.replaceAll("上接[0-9]*版", "").replaceAll(
						"下转[0-9]*版", "");
				news.content = contentTxt.trim();
//				System.out.println(news.content);

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
					System.out.println("...added");
					newsList.add(news);
				}
			}
		}
		return newsList;

	}

}

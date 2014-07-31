package launcher;

import helper.IOHelper;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import PO.News;
import constant.Const;

public class Hello {

	public static void main(String[] args) throws IOException {
		GregorianCalendar calendar = new GregorianCalendar(2014, 6, 3);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateFormat.format(calendar.getTime());
		System.out.println(dateStr);
		getList(calendar.getTime());
	}

	public static ArrayList<News> getList(Date date) throws IOException {
		URL listUrl = new URL(Const.XINHUA_LIST_URL(date));
		Document dom = Jsoup.parse(listUrl, 5000);
		Elements layoutBlocks = dom.getElementsByAttributeValue("width", "710");
		for (Element layoutBlock : layoutBlocks) {
			Element layoutName = layoutBlock.getElementsByAttributeValue(
					"color", "#FFFFFF").get(0);
			IOHelper.println("Layout: " + layoutName.text().replace(";", ""));
			Elements reports = layoutBlock.getElementsByTag("a");
			for (Element report : reports) {
				IOHelper.println(report.text() + " - " + report.attr("href"));
			}
			IOHelper.println("=======");
		}
		return null;
	}

}

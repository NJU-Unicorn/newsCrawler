package constant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Const {
	private static final String XINHUA_LIST_URL = "http://xh.xhby.net/newxh/html/DATE/node_1.htm";
	private static final String XINHUA_NEWS_URL = "http://xh.xhby.net/newxh/html/DATE/SUBURL";

	public static String XINHUA_LIST_URL(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM/dd");
		String dateStr = dateFormat.format(date);
		return XINHUA_LIST_URL.replace("DATE", dateStr);
	}

	public static String XINHUA_NEWS_URL(Date date, String subUrl) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM/dd");
		String dateStr = dateFormat.format(date);
		return XINHUA_NEWS_URL.replace("DATE", dateStr).replace("SUBURL",
				subUrl);
	}
}

package constant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Const {
	private static final String XINHUA_LIST_URL = "http://xh.xhby.net/TYPE/html/DATE/INDEX";
	private static final String XINHUA_NEWS_URL = "http://xh.xhby.net/TYPE/html/DATE/SUBURL";
	public static final GregorianCalendar XINHUA_POINT = new GregorianCalendar(
			2006, 4, 1);

	public static String XINHUA_LIST_URL(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM/dd");
		String dateStr = dateFormat.format(date);
		if (date.before(XINHUA_POINT.getTime())) {
			return XINHUA_LIST_URL.replace("DATE", dateStr)
					.replace("TYPE", "mp1").replace("INDEX", "index.htm");
		} else {
			return XINHUA_LIST_URL.replace("DATE", dateStr).replace("TYPE",
					"mp2").replace("INDEX", "node_1.htm");
		}
	}

	public static String XINHUA_NEWS_URL(Date date, String subUrl) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM/dd");
		String dateStr = dateFormat.format(date);
		if (date.before(XINHUA_POINT.getTime())) {
			return XINHUA_NEWS_URL.replace("DATE", dateStr)
					.replace("SUBURL", subUrl).replace("TYPE", "mp1");
		} else {
			return XINHUA_NEWS_URL.replace("DATE", dateStr)
					.replace("SUBURL", subUrl).replace("TYPE", "mp2");
		}
	}
}

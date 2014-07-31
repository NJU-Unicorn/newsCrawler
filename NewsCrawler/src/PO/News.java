package PO;

public class News {
	public String id;
	public String url;
	public String date;
	public String layout;
	public String title;
	public String subTitle;
	public String author;
	public String content;

	@Override
	public String toString() {
		return "id=" + id + "\nurl=" + url + "\ndate=" + date + "\nlayout="
				+ layout + "\ntitle=" + title + "\nsubTitle=" + subTitle
				+ "\nauthor=" + author + "\ncontent=" + content;
	}

}

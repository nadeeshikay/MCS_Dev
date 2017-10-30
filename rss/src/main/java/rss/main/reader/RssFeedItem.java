package rss.main.reader;

import java.util.Date;

public class RssFeedItem {

	private final String title;
	private final String author;
	private final Date publishedDate;
	private final String description;
	private final String link;

	public RssFeedItem(String title, String author, Date publishedDate, String content, String link) {
		this.title = title;
		this.author = author;
		this.publishedDate = publishedDate;
		this.description = content;
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public String getDescription() {
		return description;
	}

	public String getLink() {
		return link;
	}

}

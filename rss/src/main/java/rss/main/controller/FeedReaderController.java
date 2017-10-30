package rss.main.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import rss.main.reader.RssFeedItem;

@RestController
public class FeedReaderController
{

	public static final String RSS_FILE_1 = "F://MCS Research/RSS Samples/smartraveller-countries-2017-10-18.rss";
	public static final String RSS_FILE_2 = "F://MCS Research/RSS Samples/travelweekly-destinations-2017-10-18.xml";

	@RequestMapping("/getFeedText")
	public List<RssFeedItem> getFeedText()
	{

		String feedUrlText = "http://www.smartraveller.gov.au/countries/documents/index.rss";
		// String feedUrlText =
		// "http://www.travelweekly.co.uk/rss/destinations.xml";

		List<RssFeedItem> rssFeedList = new ArrayList<RssFeedItem>();

		boolean ok = false;
		SyndFeed feed = null;

		try
		{
			feed = getFeedByUrl(feedUrlText);

			System.out.println(feed);

			ok = true;

			ListIterator<SyndEntry> feedEntryItr = feed.getEntries().listIterator();

			while (feedEntryItr.hasNext())
			{

				SyndEntry feedEntry = feedEntryItr.next();
				String title = feedEntry.getTitle();
				String author = feedEntry.getAuthor();
				Date publishedDate = feedEntry.getPublishedDate();
				String description = feedEntry.getDescription().getValue();
				String link = feedEntry.getLink();

				rssFeedList.add(new RssFeedItem(title, author, publishedDate, description, link));

			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("ERROR: " + ex.getMessage());
		}

		if (!ok)
		{
			System.out.println();
			System.out.println("FeedReader reads and prints any RSS/Atom feed type.");
			System.out.println("The first parameter must be the URL of the feed to read.");
			System.out.println();
		}

		return rssFeedList;
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
	}

	@RequestMapping("/getFeedText/{country}/{city}")
	public List<RssFeedItem> getFeedTextByCountry(@PathVariable String country, @PathVariable String city)
	{

		String feedUrlText = "http://www.smartraveller.gov.au/countries/documents/index.rss";
		// String feedUrlText =
		// "http://www.travelweekly.co.uk/rss/destinations.xml";

		List<RssFeedItem> rssFeedList = new ArrayList<RssFeedItem>();

		boolean ok = false;
		SyndFeed feed = null;

		try
		{
			// feed = getFeedByUrl(feedUrlText);
			feed = getFeedByFileName(RSS_FILE_1);

			System.out.println(feed);

			ok = true;

			ListIterator<SyndEntry> feedEntryItr = feed.getEntries().listIterator();

			while (feedEntryItr.hasNext())
			{
				SyndEntry feedEntry = feedEntryItr.next();
				String title = feedEntry.getTitle();
				String author = feedEntry.getAuthor();
				Date publishedDate = feedEntry.getPublishedDate();
				String description = feedEntry.getDescription().getValue();
				String link = feedEntry.getLink();

				if (country.equalsIgnoreCase(title) || city.equalsIgnoreCase(title))
				{
					rssFeedList.add(new RssFeedItem(title, author, publishedDate, description, link));
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("ERROR: " + ex.getMessage());
		}

		if (!ok)
		{
			System.out.println();
			System.out.println("FeedReader reads and prints any RSS/Atom feed type.");
			System.out.println("The first parameter must be the URL of the feed to read.");
			System.out.println();
		}

		return rssFeedList;
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
	}

	// String fileName = "c://lines.txt";
	private BufferedReader readRSSFileOffline(String fileName)
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(fileName));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return br;
	}

	private SyndFeed getFeedByFileName(String feedFileName)
	{
		SyndFeed feed = null;

		SyndFeedInput input = new SyndFeedInput();
		try
		{
			feed = input.build(readRSSFileOffline(feedFileName));
		}
		catch (IllegalArgumentException | FeedException e)
		{
			e.printStackTrace();
		}

		return feed;
	}

	private SyndFeed getFeedByUrl(String feedUrlText)
	{
		SyndFeed feed = null;

		URL feedUrl;
		try
		{
			feedUrl = new URL(feedUrlText);

			SyndFeedInput input = new SyndFeedInput();
			feed = input.build(new XmlReader(feedUrl));

		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FeedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return feed;
	}

}

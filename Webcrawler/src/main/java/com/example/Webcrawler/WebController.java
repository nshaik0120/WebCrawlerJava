package com.crawler.WebCrawlerApp;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

	@GetMapping("search")
	public String searchString(@RequestParam("keyword") String keyword) {
		
               String pageContent = getPageLinks("http://espn.com");

		if (pageContent.contains(keyword)) {

			System.out.println("Search keyword Found");
		}
		return pageContent;

	}

	public String getPageLinks(String URL) {

		StringBuilder pageContent = new StringBuilder();
		try {

			int count = 0;
			Document document = Jsoup.connect(URL).get();
			Elements linksOnPage = document.select("link[href]");

			for (Element page : linksOnPage) {

				// Limiting no of sub links traverse to 5
				if (count < 5) {

					String subURL = page.attr("href");

					Document subDocument = Jsoup.connect(subURL).get();

					String content = subDocument.body().text();

					pageContent.append(content);

					count++;
				}

			}

		} catch (IllegalArgumentException e) {

		} catch (IOException e) {
			System.err.println("For '" + URL + "': " + e.getMessage());
		}

		return pageContent.toString();
	}


}

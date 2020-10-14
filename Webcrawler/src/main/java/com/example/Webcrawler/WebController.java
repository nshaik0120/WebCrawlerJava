package com.example.Webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

	@GetMapping("search")
	public String searchString(@RequestParam("keyword") String keyword) {

		List<String> urlList = getPageLinks("http://espn.com");

		String pageContent = getPageContent(urlList);

		if (pageContent.contains(keyword)) {

			System.out.println("Search keyword Found");
		}
		return pageContent;

	}

	public List<String> getPageLinks(String URL) {

		List<String> urlList = new ArrayList<String>();
		try {

			int count = 0;
			Document document = Jsoup.connect(URL).get();
			Elements linksOnPage = document.select("link[href]");

			for (Element page : linksOnPage) {

				// Limiting no of sub links to 5
				if (count < 5) {

					String subURL = page.attr("href");

					urlList = new ArrayList<String>();

					urlList.add(subURL);

					count++;
				}

			}

		} catch (IllegalArgumentException e) {

		} catch (IOException e) {
			System.err.println("For '" + URL + "': " + e.getMessage());
		}

		return urlList;
	}

	public String getPageContent(List<String> urls) {

		StringBuilder pageContent = new StringBuilder();

		for (String url : urls) {
			Document subDocument = null;
			try {
				subDocument = Jsoup.connect(url).get();
				String content = subDocument.body().text();

				pageContent.append(content);

			} catch (IllegalArgumentException e) {

			}

			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String content = subDocument.body().text();

			pageContent.append(content);

		}
		return pageContent.toString();
	}

}

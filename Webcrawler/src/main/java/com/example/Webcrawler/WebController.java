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
	
	
	
	@RequestMapping("/search")
	public void getData(@RequestParam(value = "query", defaultValue = "Java" ) String query){
		
		Set<String> result=getDataFromGoogle(query);
		for(String temp : result){
			System.out.println(temp);
		}
		
		
	}
	
	  private static Pattern patternDomainName;
	  private Matcher matcher;
	  private static final String DOMAIN_NAME_PATTERN
		= "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
	  static {
		patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
	  }
	  
	  
	  public String getDomainName(String url){

			String domainName = "";
			matcher = patternDomainName.matcher(url);
			if (matcher.find()) {
				domainName = matcher.group(0).toLowerCase().trim();
			}
			return domainName;

		  }

	  private Set<String> getDataFromGoogle(String query) {

			Set<String> result = new HashSet<String>();
			String request = "https://www.google.com/search?q=" + query + "&num=5";
			System.out.println("Sending request..." + request);

			try {

				// need http protocol, set this as a Google bot agent :)
				Document doc = Jsoup
					.connect(request)
					.userAgent(
					  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
					.timeout(5000).get();

				// get all links
				Elements links = doc.select("a[href]");
				for (Element link : links) {

					String temp = link.attr("href");
					if(temp.startsWith("/url?q=")){
		                                //use regex to get domain name
						result.add(getDomainName(temp));
					}

				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			return result;
		  }

}

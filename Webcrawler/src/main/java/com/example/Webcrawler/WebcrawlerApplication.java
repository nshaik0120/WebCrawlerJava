package com.example.Webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebcrawlerApplication {
	
 //Using Yahoo Site 
    private HashSet<String> links;

	
	  public WebcrawlerApplication() {
	        links = new HashSet<>();
	    }

	
	 public HashSet<String> getPageLinks(String URL, int depth) {
		 //Limiting the size of  10 URLS from yahoo site
	        if (!links.contains(URL) && links.size()<10 ) {
	            System.out.println("Fecthing the Urls from Yahoo Site"+ "  "+depth+" :"+ "[" + URL + "]");
	            try {
	                links.add(URL);

	                Document document = Jsoup.connect(URL).get();
	                Elements linksOnPage = document.select("a[href]");

	                depth++;
	                for (Element page : linksOnPage) {
	                    getPageLinks(page.attr("abs:href"), depth);
	                }
	            } catch (IOException e) {
	                System.err.println("For '" + URL + "': " + e.getMessage());
	            }
	        }
			return links;
	    }
	 
	 public static String getText(String url) throws Exception {
		    URL website = new URL(url);
		    URLConnection connection = website.openConnection();
		    BufferedReader in = new BufferedReader(
		                                new InputStreamReader(
		                                    connection.getInputStream()));

		    StringBuilder response = new StringBuilder();
		    String inputLine;

		    while ((inputLine = in.readLine()) != null) 
		        response.append(inputLine);

		    in.close();

		    return response.toString();
		}


	public static void main(String[] args) {
		SpringApplication.run(WebcrawlerApplication.class, args);
		
		String content = null;
		try {
			content = new WebcrawlerApplication().getText("https://sg.yahoo.com/?p=us&guccounter=1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(content.contains("smartphone"))
		{
		    // content of url contains keyword
			
			System.out.println("content="+content);
		}
		
	   
	        new WebcrawlerApplication().getPageLinks("http://yahoo.com/", 0);
	    }
	
		
		
	

}

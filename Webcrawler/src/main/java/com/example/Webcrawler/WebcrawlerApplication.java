package com.example.Webcrawler;

import java.io.IOException;
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

	
	 public void getPageLinks(String URL, int depth) {
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
	    }


	public static void main(String[] args) {
		SpringApplication.run(WebcrawlerApplication.class, args);
		
	   
	        new WebcrawlerApplication().getPageLinks("http://yahoo.com/", 0);
	    }
	
		
		
	

}

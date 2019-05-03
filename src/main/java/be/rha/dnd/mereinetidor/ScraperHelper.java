package be.rha.dnd.mereinetidor;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ScraperHelper {

    public static final WebClient CLIENT = new WebClient();
    public static final String SITE_URL = "http://mereinetidor.fr/";
    public static final String SEARCH_URL = SITE_URL + "encyclodd/sorts/sorts-dd3-5.html?filter_84[1]=G%C3%A9n%C3%A9riques&listall=1&cc=p&start=<start>";

    static {
        CLIENT.getOptions().setCssEnabled(false);
        CLIENT.getOptions().setJavaScriptEnabled(false);
    }

    public static HtmlPage getPage(String searchUrl) throws java.io.IOException, InterruptedException {
        long delay = (long) (Math.random() * 3000 + 2);
        System.out.println(String.format("Will wait for %s s before getting the page", delay));
        Thread.sleep(delay);
        return CLIENT.getPage(searchUrl);
    }
}

package be.rha.dnd.mereinetidor;

import com.gargoylesoftware.htmlunit.WebClient;

public class ScraperHelper {

    public static final WebClient CLIENT = new WebClient();
    public static final String SITE_URL = "http://mereinetidor.fr/";
    public static final String PRIEST_URL = SITE_URL + "encyclodd/sorts/sorts-dd3-5.html?filter_74=Pr%C3%AAtre&filter_84[1]=G%C3%A9n%C3%A9riques&listall=1&start=<start>";

    static {
        CLIENT.getOptions().setCssEnabled(false);
        CLIENT.getOptions().setJavaScriptEnabled(false);
    }
}

package http;

import dto.UniprotSwissProt;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KEGGRequest {

    private String URL_KEGG_MODULE = "https://www.genome.jp";

    public void getModules() {

        System.setProperty("phantomjs.binary.path", "/home/milene.guimaraes/dev/programas/phantomjs-2.1.1-linux-x86_64/bin/phantomjs");
        WebDriver driver  = new PhantomJSDriver();
        driver.get("https://www.genome.jp/kegg/tool/map_module1.html");
        WebElement inputTextGoogle = driver.findElement(By.name("unclassified"));
        inputTextGoogle.sendKeys("ko:K01803");
        inputTextGoogle.submit();

        String HTMLPage = driver.getPageSource();
        Pattern linkPattern = Pattern.compile("(<a[^>]+>.+?</a>)",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
        Matcher pageMatcher = linkPattern.matcher(HTMLPage);
        ArrayList<String> links = new ArrayList<String>();
        while(pageMatcher.find()){
            if (pageMatcher.group().contains("kegg-bin"))
                links.add(pageMatcher.group());
        }

        for (int i = 0; i < links.size(); i++) {
            String[] href = links.get(i).split("/");
            int indArgs = links.get(i).indexOf(".args")+5;
            String urlModule = links.get(i).substring(href.length-1, indArgs).replaceAll("ref=\"","");;

            try {
                BufferedReader in = doKEGGRequest(urlModule);
                String inputLine;
                StringBuffer content = new StringBuffer();
                boolean startKOs = false;
                ArrayList<String> listKOs = new ArrayList<String>();
                while ((inputLine = in.readLine()) != null) {

                    if (startKOs && !inputLine.toUpperCase().contains("BR") && !inputLine.contains("<td>")) {
                        listKOs.add(inputLine);
                    }else if (startKOs && inputLine.toUpperCase().contains("BR")){
                        startKOs = false;
                        in.close();
                        in = null;
                    }

                    if (inputLine.contains("<td>Definition</td>")){
                        startKOs = true;
                    }
                    System.out.println(inputLine);
                }
                in.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private BufferedReader doKEGGRequest(String urlModule) throws IOException {
        URL url = new URL(URL_KEGG_MODULE + urlModule);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        return new BufferedReader(
                new InputStreamReader(con.getInputStream()));
    }
}

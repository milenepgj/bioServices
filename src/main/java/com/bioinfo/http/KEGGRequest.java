package com.bioinfo.http;

import bio.domain.KEGGModule;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KEGGRequest {

    private String URL_KEGG_MODULE = "https://www.genome.jp";

    public List<KEGGModule> getModules(String kosToSend) {

        List<KEGGModule> modules = new ArrayList<KEGGModule>();

        kosToSend = "ko:K01803\n" +
                "ko:K00134\n" +
                "ko:K00150\n" +
                "ko:K00927\n" +
                "ko:K11389\n" +
                "ko:K01834\n" +
                "ko:K15633\n" +
                "ko:K15634\n" +
                "ko:K15635\n" +
                "ko:K01689\n" +
                "ko:K00873\n" +
                "ko:K12406";

        System.setProperty("phantomjs.binary.path", "/home/milene.guimaraes/dev/programas/phantomjs-2.1.1-linux-x86_64/bin/phantomjs");
        WebDriver driver  = new PhantomJSDriver();
        driver.get("https://www.genome.jp/kegg/tool/map_module1.html");
        WebElement inputTextGoogle = driver.findElement(By.name("unclassified"));
        inputTextGoogle.sendKeys(kosToSend);
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
            int indStartArgs = links.get(i).indexOf(".args");
            int indArgs = indStartArgs+5;
            String moduleCode = links.get(i).substring(indStartArgs-6, indStartArgs);
            String urlModule = links.get(i).substring(href.length-1, indArgs).replaceAll("ref=\"","");;

            try {
                BufferedReader in = doKEGGRequest(urlModule);
                ArrayList<String> listKOs = getModuleKOsList(moduleCode, in);

                if (in != null)
                    in.close();

                if (listKOs.size() > 0){
                    boolean isComplete = false;

                    KEGGModule moduleKegg = new KEGGModule();
                    moduleKegg.setModule(moduleCode);
                    moduleKegg.setKos(getKoCodeList(listKOs));
                    moduleKegg.setComplete(isComplete(moduleKegg.getKos(), kosToSend));
                    modules.add(moduleKegg);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return modules;
        //TODO: ver como retornar a lista

    }

    private boolean isComplete(List<String> listKOs, String kosToSend) {

        return getMinusList(listKOs, getKosToCompareList(kosToSend)).size()>0?false:true;
    }

    private List<String> getMinusList(List<String> listOne, List<String> listTwo) {
        List<String> added = new ArrayList<String>();
        added.addAll(listOne);
        added.removeAll(listTwo);
        return added;
    }

    private List<String> getKosToCompareList(String kosToSend) {
        List kosToSendList = Arrays.asList(kosToSend.replaceAll("ko:", "").split("\n"));
        kosToSendList.sort(Comparator.naturalOrder());
        return kosToSendList;
    }

    private ArrayList<String> getModuleKOsList(String moduleCode, BufferedReader in) throws IOException {
        String inputLine;
        boolean startKOs = false;
        ArrayList<String> listKOs = new ArrayList<String>();
        while ((inputLine = in.readLine()) != null) {

            if (startKOs && (inputLine.contains("/dbget-bin/www_bget"))) {
                listKOs.add(inputLine);
            }else if (startKOs && inputLine.contains(moduleCode)){
                break;
            }

            if (inputLine.contains("<td>Definition</td>")){
                startKOs = true;
            }
        }
        return listKOs;
    }

    private List<String> getKoCodeList(ArrayList<String> listKOs) {

        List<String> listKosCode = new ArrayList<>();

        if (listKOs.size() > 0){

            String hrefs = listKOs.toString();

            String[] allKos = hrefs.split("<a href");

            for (int j = 0; j < allKos.length; j++) {
                String lineKO = allKos[j];


                if (lineKO.indexOf("</a>") > 0) {
                    String strKO = lineKO.substring(lineKO.indexOf("</a>") - 6, lineKO.indexOf("</a>"));
                    listKosCode.add(strKO);
                }
            }

        }

        return listKosCode;
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

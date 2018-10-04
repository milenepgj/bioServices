package com.bioinfo.http;

import bio.domain.Expasy;
import bio.domain.KEGGData;
import bio.domain.UniprotSwissProt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

public class KEGGApiRequest {

    private String URL_GET_TXT_DATA = "http://rest.kegg.jp/get/";
    private String EXTENSION_TXT_DATA = ".txt";

    public KEGGData getKeggApiInfo(String hitBlastKegg){

        KEGGData keggData = null;

        try {
            BufferedReader in = doKeggApiGet(hitBlastKegg);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                //System.out.println(inputLine);
                keggData = getKEGGData(keggData, inputLine);
                if (keggData.getPathway() != null){
                    break;
                }
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (keggData != null)
            keggData.setBlastHitId(hitBlastKegg);

        return keggData;
    }

    private BufferedReader doKeggApiGet(String hitBlastKegg) throws IOException {
        URL url = new URL( URL_GET_TXT_DATA + hitBlastKegg);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        return new BufferedReader(
                new InputStreamReader(con.getInputStream()));
    }

    private KEGGData getKEGGData(KEGGData keggData, String line){

        try{

            String identification = line.substring(0,line.indexOf(" "));

            if (identification != ""){

                if (keggData == null)
                    keggData = new KEGGData();

                if (identification.equals("ENTRY")) {
                    keggData.setEntry(getData(line, identification, " "));
                }else if (identification.equals("DEFINITION")) {
                    keggData.setDefinition(getData(line, identification, " "));
                }else if (identification.equals("ORTHOLOGY")){
                    keggData.setOrthologyKo(getKOData(line, identification,"|"));
                }else if (identification.equals("ORGANISM")){
                    keggData.setOrganism(getData(line, identification," "));
                }else if (identification.equals("PATHWAY")){
                    keggData.setPathway(getData(line, identification, " "));
                }
            }
        }catch (StringIndexOutOfBoundsException e){
            return keggData;
        }

        return keggData;

    }

    private String getData(String line, String identification, String delimiter) {
        return Arrays.asList(line.replaceAll(identification,"")
                .split(" "))
                .stream()
                .filter(
                        item -> !item.isEmpty())
                .collect(Collectors.joining(delimiter));
    }

    private String getKOData(String line, String identification, String delimiter) {
        return Arrays.asList(line.replaceAll(identification,"")
                .split(" "))
                .stream()
                .filter(
                        item -> (!item.isEmpty() && item.substring(0,1).equals("K")))
                .collect(Collectors.joining(delimiter));
    }


    private void getUniprotSwissProtAnnotation(Expasy expasy, String uniProtLine) {
        String[] uniprotKey = uniProtLine.split(";");

        for (int i = 0; i < uniprotKey.length; i++) {

            String[] uniProtData = uniprotKey[i].split(",");

            String uniprotId = uniProtData[0].trim();
            UniprotSwissProt uniprot = new UniprotSwissProtRequest().getUniprotSwissProtDescription(uniprotId);

            if (uniprot != null)
                expasy.getUniprotSwissProtData().add(uniprot);
        }
    }

    public StringBuffer getExpasyECDescriptionExample(){
        URL url = null;
        try {
            url = new URL("https://enzyme.expasy.org/EC/1.1.1.100.txt");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                content.append(System.getProperty("line.separator"));
            }
            in.close();
            return content;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

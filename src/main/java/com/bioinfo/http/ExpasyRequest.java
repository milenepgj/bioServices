package com.bioinfo.http;

import com.bioinfo.dto.Expasy;
import com.bioinfo.dto.UniprotSwissProt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ExpasyRequest {

    private String URL_GET_TXT_DATA = "https://enzyme.expasy.org/EC/";
    private String EXTENSION_TXT_DATA = ".txt";

    public Expasy getExpasyECData(String ec, boolean isGetUniProtAnnotation){

        try {
            Expasy expasy = new Expasy();
            BufferedReader in = doExpasyRequest(ec);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                expasy = getExpasy(expasy, inputLine, isGetUniProtAnnotation);
            }
            in.close();
            return expasy;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private BufferedReader doExpasyRequest(String ec) throws IOException {
        URL url = new URL( URL_GET_TXT_DATA + ec.toUpperCase().replaceAll("EC:","").replaceAll("-","").trim() + EXTENSION_TXT_DATA);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        return new BufferedReader(
                new InputStreamReader(con.getInputStream()));
    }

    private Expasy getExpasy(Expasy expasy, String line, boolean isGetUniProtAnnotation){
        String identification = line.substring(0,2);
        String[] data = line.split("   ");

        if (identification.equals("ID")) {
            expasy.setEc(data[1]);
        }else if (identification.equals("DE")) {
            expasy.setDescription(data[1]);
        }else if (identification.equals("DR")){
            if (data[1].contains("BRENDA")) {
                expasy.setBrenda(data[1]);
            } else if (data[1].contains("KEGG")) {
                expasy.setKegg(data[1]);
            } else if (data[1].toUpperCase().contains("METACYC")) {
                expasy.setKegg(data[1]);
            } else if (data[1].toUpperCase().contains("IUBMB")) {
                expasy.setIubmb(data[1]);
            } else if (data[1].toUpperCase().contains("MEDLINE")) {
                expasy.setMedline(data[1]);
            } else if (data[1].toUpperCase().contains("INTENZ")) {
                expasy.setIntEnz(data[1]);
            } else {

                if (isGetUniProtAnnotation) {

                    getUniprotSwissProtAnnotation(expasy, data[1]);
                }
            }
        }

        return expasy;

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

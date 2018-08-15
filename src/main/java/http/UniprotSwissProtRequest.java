package http;

import dto.UniprotSwissProt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class UniprotSwissProtRequest {

    private String URL_GET_TXT_DATA = "https://www.uniprot.org/uniprot/";
    private String EXTENSION_TXT_DATA = ".fasta";

    public UniprotSwissProt getUniprotSwissProtDescription(String id){
        try {
            UniprotSwissProt uniprot = new UniprotSwissProt();
            BufferedReader in = doUniprotSwissProtRequest(id);
            String inputLine;
            StringBuffer content = new StringBuffer();
            uniprot.setId(id);
            while ((inputLine = in.readLine()) != null) {
                uniprot = getUniprotSwissProt(uniprot, inputLine);
                content.append(inputLine);
            }
            in.close();
            return uniprot;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private BufferedReader doUniprotSwissProtRequest(String id) throws IOException {
        URL url = new URL(URL_GET_TXT_DATA + id + EXTENSION_TXT_DATA);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        return new BufferedReader(
                new InputStreamReader(con.getInputStream()));
    }

    public UniprotSwissProt getUniprotSwissProt(UniprotSwissProt uniprot, String line) {

        String identification = line.substring(0, 2);
        String[] data = line.split("   ");

        switch (identification) {
            case "GN":
                uniprot.setGene(data[1].replaceAll("Name=", ""));
                break;
            case "OS":
                uniprot.setOrganism(data[1]);
                break;
            case "SQ":
                uniprot.setSequence(data[1]);
                break;
            case "DE":
                if (data[1].contains("AltName:")) {
                    uniprot.setProteinName(data[1].replaceAll("AltName: Full=", ""));
                }
                break;
        }

        return uniprot;
    }

}

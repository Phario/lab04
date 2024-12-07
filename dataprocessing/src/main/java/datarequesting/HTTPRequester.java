package datarequesting;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataparsingutils.DrugProgramCostsData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


public class HTTPRequester {
    private HTTPRequester() {}
    public static ArrayList<DrugProgramCostsData> parseJSONResponse(URL url){
        var dataList = new ArrayList<DrugProgramCostsData>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(url);
            JsonNode data = root.path("data");
            Iterator<JsonNode> elements = data.elements();
            while (elements.hasNext()) {
                JsonNode entry = elements.next();
                int year = entry.path("year").asInt();
                int month = entry.path("month").asInt();
                int numberOfPatients = entry.path("number-of-patients").asInt();
                double refund = entry.path("refund").asDouble();
                dataList.add(new DrugProgramCostsData(year, month, numberOfPatients, refund));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return dataList;
    }
}

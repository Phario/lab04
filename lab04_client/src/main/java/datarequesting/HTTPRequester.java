package datarequesting;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataparsingutils.DrugProgramCostsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


public class HTTPRequester {
    private static final Logger logger = LoggerFactory.getLogger(HTTPRequester.class);
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
            logger.error(e.getMessage());
        }
        return dataList;
    }
}

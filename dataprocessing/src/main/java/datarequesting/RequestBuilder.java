package datarequesting;

import org.apache.hc.core5.net.URIBuilder;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class RequestBuilder {
    private RequestBuilder() {}
    private static final String BASE_URL = "https://api.nfz.gov.pl/app-stat-api-pl/monthly-drug-costs";
    public static URL buildRefundInfoURL(String province, String year, String drugProgramName, String drugName, String gender){
        try {
            if (province == null) {
                province = "00";
            }
            if (year == null) {
                throw new IllegalArgumentException();
            }
            if (drugProgramName == null && drugName == null) {
                throw new IllegalArgumentException();
            }
            URIBuilder uriBuilder = new URIBuilder(BASE_URL);
            uriBuilder.addParameter("limit", "12");
            uriBuilder.addParameter("format", "json");
            uriBuilder.addParameter("province", province);
            uriBuilder.addParameter("dateFrom", year + "-01-01T00:00:00.511Z");
            uriBuilder.addParameter("drugProgram", drugProgramName);
            uriBuilder.addParameter("activeSubstance", drugName);
            if (Objects.equals(gender, "M") || Objects.equals(gender, "F")) {
                uriBuilder.addParameter("gender", gender);
            }
            return uriBuilder.build().toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}

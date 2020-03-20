package StepDefs;

import Utils.Driver;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.Map;


public class SingleUserStepDefs {
    HttpClient httpClient = HttpClientBuilder.create().build();
    URIBuilder uriBuilder = new URIBuilder();
    HttpResponse response;
    ObjectMapper objectMapper;
    Map<String, Map<String,Object>> userDataMap;
    Map<String,Object> userData;
    @Given("User navigates the {string}{string}{string}")
    public void user_navigates_the(String scheme, String host, String path) {

        uriBuilder.setScheme(scheme).setHost(host).setPath(path);
    }



    @When("The user sends the get request")
    public void the_user_sends_the_get_request() throws URISyntaxException, IOException {
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Accept","application/json");
        response = httpClient.execute(httpGet);
        objectMapper = new ObjectMapper();
        Assert.assertTrue(response.getEntity().getContentType().getValue().contains("application/json"));

    }

    @Then("The user must see {int} status code")
    public void the_user_must_see_status_code(Integer STATUS_OK) throws IOException {
        Assert.assertTrue("Status code assertion failed ",
                STATUS_OK==response.getStatusLine().getStatusCode());
        userDataMap= objectMapper.readValue(response.getEntity().getContent(),
                new TypeReference <Map<String, Map<String,Object>>>(){ });
       userData= userDataMap.get("data");

    }@Then("match response {string}")
    public void match_response(String key) throws IOException {
        String [] keys = key.split("=");


        Assert.assertTrue(userData.get(keys[0]).toString().equals(keys[1]));

    }

}

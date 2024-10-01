import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;

public class apiTest {

    @Test
    @SuppressWarnings("unchecked")
    void apiTests() {

        int year = 2023;
        float price = 7999.99F;
        String model = "Apple ARM A7";
        String size = "1 TB";
        String name = "Apple Max Pro 1TB";
        String url = "https://api.restful-api.dev/objects";

        JSONObject requestBody = new JSONObject();
        JSONObject data = new JSONObject();

        data.put("year",year);
        data.put("price", price);
        data.put("CPU model", model);
        data.put("Hard disk size", size);

        requestBody.put("name", name);
        requestBody.put("data", data);

        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(requestBody.toString())
                .log().uri()
                .urlEncodingEnabled(true)
                .post(url);

        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.path("name"), name);
        Assert.assertEquals((Integer) response.path("data.year"), year);
        Assert.assertEquals((float)response.path("data.price"), price);
        Assert.assertEquals(response.path("data.'CPU model'"), model);
        Assert.assertEquals(response.path("data.'Hard disk size'"), size);
        Assert.assertNotEquals(Optional.ofNullable(response.path("id")), null);
        Assert.assertNotEquals(Optional.ofNullable(response.path("createdAt")), null);
    }
}

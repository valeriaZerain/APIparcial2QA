package testSuite;

import config.ApiConfig;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;

public class UserTest {
    public RequestInfo request = new RequestInfo();
    public Response response;
    public String auth="";
    public String valueAuth="";
    public JSONObject body = new JSONObject();

    @BeforeEach
    public void before() {
        body.put("Content","UPB2024");
        body.put("Email",new Date().getTime()+"@upb2028.com");
        body.put("FullName","upb1");
        body.put("Password","12345!");

        auth="Authorization";
        valueAuth="Basic "+ Base64.getEncoder().encodeToString((body.get("Email")+":"+body.get("Password")).getBytes());

    }

    @Test
    @DisplayName("Verify the create, read, update, delete user - todo.ly")
    public void createProject() {
        request.setUrl(ApiConfig.CREATE_USER)
                .setBody(body.toString());

        response = FactoryRequest.make("post").send(request);
        response.then().statusCode(200).body("Email",equalTo(body.get("Email")));

        body.put("FullName","UPB2025");
        request.setUrl(ApiConfig.UPDATE_USER)
                .setHeaders(auth,valueAuth)
                .setBody(body.toString());
        response = FactoryRequest.make("put").send(request);
        response.then().statusCode(200).body("FullName",equalTo(body.get("FullName")));

        request.setUrl(ApiConfig.DELETE_USER);
        response = FactoryRequest.make("delete").send(request);
        response.then().statusCode(200).body("Email",equalTo(body.get("Email")));
    }

}

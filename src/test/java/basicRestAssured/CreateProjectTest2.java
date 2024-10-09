package basicRestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CreateProjectTest2 {

    @Test
    @DisplayName("Verify Create Project by API - Todo.ly JSONObject")
    public void verifyCreateProjectJSONObject(){
        JSONObject body = new JSONObject();
        body.put("Content","RESTJsonBody");
        body.put("Icon",7);

        given()
                .auth()
                .preemptive()
                .basic("api2024@2024.com","12345")
                .body(body.toString())
                .log().all()
       .when()
                .post("https://todo.ly/api/projects.json")
       .then()
                .statusCode(200)
                .body("Content",equalTo(body.get("Content")))
                .body("Icon",equalTo(7))
                .log().all();
    }


    @Test
    @DisplayName("Verify Create Project by API - Todo.ly Extract Element")
    public void verifyCreateProjectExtractElement(){
        JSONObject body = new JSONObject();
        body.put("Content","RESTJsonBody");
        body.put("Icon",7);

        Response response = given()
                                    .auth()
                                    .preemptive()
                                    .basic("api2024@2024.com","12345")
                                    .body(body.toString())
                                    .log().all()
                            .when()
                                    .post("https://todo.ly/api/projects.json");
        response.then()
                .statusCode(200)
                .body("Content",equalTo(body.get("Content")))
                .body("Icon",equalTo(7))
                .log().all();

        int id = response.then().extract().path("Id");
        String name = response.then().extract().path("Content");

        System.out.println("id: "+id);
        System.out.println("name: "+name);

    }
}

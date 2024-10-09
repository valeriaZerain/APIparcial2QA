package basicRestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProjectTest {
    @Test
    @DisplayName("Verify Create Read Update Delete Project - Todo.ly")
    public void verifyCRUDProject(){
        JSONObject body = new JSONObject();
        body.put("Content","RESTJsonBody");
        body.put("Icon",7);

         // create
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
         // read
         response = given()
                        .auth()
                        .preemptive()
                        .basic("api2024@2024.com","12345")
                        .log().all()
                .when()
                        .get("https://todo.ly/api/projects/"+id+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo(body.get("Content")))
                .body("Icon",equalTo(7))
                .log().all();
         // update
        body.put("Content","RESTUPDATE");
        body.put("Icon",10);
        response = given()
                    .auth()
                    .preemptive()
                    .basic("api2024@2024.com","12345")
                    .body(body.toString())
                    .log().all()
                .when()
                     .put("https://todo.ly/api/projects/"+id+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("RESTUPDATE"))
                .body("Icon",equalTo(10))
                .log().all();
         // delete

         response = given()
                    .auth()
                    .preemptive()
                    .basic("api2024@2024.com","12345")
                     .log().all()
                .when()
                     .delete("https://todo.ly/api/projects/"+id+".json");
        response.then()
                .statusCode(200)
                .body("Content",equalTo("RESTUPDATE"))
                .body("Icon",equalTo(10))
                .body("Deleted",equalTo(true))
                .log().all();
    }
}

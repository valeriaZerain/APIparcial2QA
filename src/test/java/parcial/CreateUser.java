package parcial;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateUser {
    @Test
    @DisplayName("Verify create an user is successfull")
    public void createUserSuccessfully(){
        JSONObject body = new JSONObject();
        body.put("Email","valeria.parcial@qa.com");
        body.put("FullName","Valeria Zerain");
        body.put("Password","Pruebas123");

        Response response = given()
                .body(body.toString())
                .post("https://todo.ly/api/user.json");

        response.then()
                .statusCode(200)
                .body("Email", equalTo(body.get("Email")))
                .log().all();
    }

    @Test
    @DisplayName("Create a project with token")
    public void createAProjectWithToken(){
        Response tokenResponse = given()
                .auth()
                .preemptive()
                .basic("valeria.parcial@qa.com","Pruebas123")
                .log().all()
                .when()
                .get("https://todo.ly/api/authentication/token.json");

        tokenResponse.then()
                .statusCode(200)
                .log().all();

        String token = tokenResponse.then().extract().path("TokenString");
        String expirationTime = tokenResponse.then().extract().path("ExpirationTime");

        JSONObject bodyProject = new JSONObject();
        bodyProject.put("Content","Valeria Proyecto de Prueba");

        // create a project
        Response responseProject = given()
                .header("Token", token)
                .body(bodyProject.toString())
                .log().all()
                .when()
                .post("https://todo.ly/api/projects.json");
        responseProject.then()
                .statusCode(200)
                .body("Content",equalTo(bodyProject.get("Content")))
                .log().all();

        //delete token
        JSONObject bodyToken = new JSONObject();
        bodyToken.put("TokenString", token);
        bodyToken.put("UserEmail","valeria.parcial@qa.com");
        bodyToken.put("ExpirationTime", expirationTime);

        Response responseDeleteToken = given()
                .header("Token", token)
                .body(bodyToken.toString())
                .log().all()
                .when()
                .delete("https://todo.ly/api/authentication/token.json");
        responseDeleteToken.then()
                .statusCode(200)
                .body("TokenString",equalTo(bodyToken.get("TokenString")));

        //Create project with deleted token
        Response responseProject2 = given()
                .header("Token", token)
                .body(bodyProject.toString())
                .log().all()
                .when()
                .post("https://todo.ly/api/projects.json");
        responseProject2.then()
                .statusCode(200)
                .body("Content",equalTo(bodyProject.get("Content")))
                .log().all();
    }

}

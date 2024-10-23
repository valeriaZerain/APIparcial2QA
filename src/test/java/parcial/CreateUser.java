package segundoParcial;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateUser {
    @Test
    @DisplayName("Create user is successfull")
    public void createUser(){
        JSONObject body = new JSONObject();
        body.put("Email","valeria.parcial@qa.com");
        body.put("FullName","Valeria Zerain");
        body.put("Password","Pruebas123");

        Response response = given()
                .body(body.toString())
                .post("https://todo.ly/api/items.json");

        response.then()
                .statusCode(200)
                .body("Email", equalTo(body.get("Email")))
                .log().all();

        
    }
}

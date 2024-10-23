package parcial;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateItems {
    @Test
    @DisplayName("Create and delete items")
    public void createAndDeleteItems(){
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

        JSONObject body = new JSONObject();
        body.put("Content","CreateItem");
        int[] idArray = new int [4];

        // create
        for(int i = 0; i<4; i++){
            Response response = given()
                    .header("Token", token)
                    .body(body.toString())
                    .log().all()
                    .when()
                    .post("https://todo.ly/api/items.json");
            response.then()
                    .statusCode(200)
                    .body("Content",equalTo(body.get("Content")))
                    .log().all();

            int id = response.then().extract().path("Id");

            Response response1 = given()
                    .header("Token", token)
                    .log().all()
                    .when()
                    .delete("https://todo.ly/api/items/"+id+".json");
            response1.then()
                    .statusCode(200)
                    .body("Deleted",equalTo(true))
                    .log().all();
        }

        
    }
}

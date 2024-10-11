package testSuite;

import config.ApiConfig;
import factoryRequest.FactoryRequest;
import org.junit.jupiter.api.Test;

public class CreateProjectTest extends BasicAuthTestBase{

    @Test
    public void createProject() {
        request.setUrl(ApiConfig.CREATE_PROJECT)
               .setHeaders(auth, valueAuth).setBody(body.toString());
        response = FactoryRequest.make("post").send(request);
        response.then().statusCode(200);
    }

}

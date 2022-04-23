package starter.account;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.equalTo;

public class PostGenerateToken {

    private String userName, password;
    private static String base_url = "https://demoqa.com/Account/v1/";

    @Step("I set an endpoint for POST new generate token user")
    public String setPostEndpointGenerateTokenUser(){
        return base_url + "GenerateToken";
    }

    @Step("I request POST generate token user")
    public void requestPostGenerateTokenUser() throws Exception {
        userName = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "//src//test//resources//filejson//username.json"), StandardCharsets.UTF_8);
        password = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "//src//test//resources//filejson//password.json"), StandardCharsets.UTF_8);
        JSONObject requestData = new JSONObject();
        requestData.put("userName", userName);
        requestData.put("password", password);

        SerenityRest.given()
                .header("Content-Type", "application/json")
                .body(requestData.toJSONString())
                .when().post(setPostEndpointGenerateTokenUser());
    }

    @Step("I validate the status code is {int}")
    public void validateStatusCode(int statusCode){
        SerenityRest.then().statusCode(equalTo(statusCode));
    }

    @Step("validate the data detail after generate token user")
    public void validateDataDetailAfterGenerateTokenUser(){
        SerenityRest.then()
                .body("result", equalTo("User authorized successfully."));
    }

    @Step("get token for other request")
    public String getToken() {
        Response response = SerenityRest.lastResponse();
        String token = response.body().path("token");
        System.out.println("Token: " + token);
        try (FileWriter file = new FileWriter("src//test//resources//filejson//token.json")) {
            file.write(token);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }
}

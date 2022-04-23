package starter.account;

import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.equalTo;

public class PostAuthorized {

    private String userName, token, password;
    private static String base_url = "https://demoqa.com/Account/v1/";

    @Step("I set an endpoint for POST authorize user")
    public String setPostEndpointAuthorizeUser(){
        return base_url + "Authorized";
    }

    @Step("I request POST authorize user")
    public void requestPostAuthorizeUser() throws Exception {
        userName = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "//src//test//resources//filejson//username.json"), StandardCharsets.UTF_8);
        token = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "//src//test//resources//filejson//token.json"), StandardCharsets.UTF_8);
        password = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "//src//test//resources//filejson//password.json"), StandardCharsets.UTF_8);

        JSONObject requestData = new JSONObject();
        requestData.put("userName", userName);
        requestData.put("password", password);

        SerenityRest.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestData.toJSONString())
                .when().post(setPostEndpointAuthorizeUser());
    }

    @Step("I validate the status code is {int}")
    public void validateStatusCode(int statusCode){
        SerenityRest.then().statusCode(equalTo(statusCode));
    }

    @Step("validate the data detail after authorize user")
    public void validateDataDetailAfterAuthorizeUser(){
        SerenityRest.then().body(equalTo("true"));
    }
}
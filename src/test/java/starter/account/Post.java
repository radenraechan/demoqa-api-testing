package starter.account;

import Utils.General;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class Post {

    General general = new General();

    private String userName, password;
    private static String base_url = "https://demoqa.com/Account/v1/";

    @Step("I set an endpoint for POST new user")
    public String setPostEndpoint(){
        return base_url + "User";
    }

    @Step("I set an endpoint for POST new user")
    public String getUserName(){
        return this.userName;
    }

    @Step("I request POST detail user")
    public void requestPostDetailUser(String userName, String password){
        JSONObject requestData = new JSONObject();
        if (userName.equals("new")){
            this.userName = general.randomUsername();
            this.password = password;

            try (FileWriter file = new FileWriter("src//test//resources//filejson//username.json")){
                file.write(this.userName);
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (FileWriter file = new FileWriter("src//test//resources//filejson//password.json")){
                file.write(this.password);
                file.flush();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else if (userName.equals("same")){
            this.userName = "string123";
            this.password = password;
        }

        System.out.println("UserName: " + this.userName);
        System.out.println("Password: " + password);

        requestData.put("userName", this.userName);
        requestData.put("password", password);

        SerenityRest.given()
                .header("Content-Type", "application/json")
                .body(requestData.toJSONString())
                .when().post(setPostEndpoint());
    }

    @Step("I validate the status code is {int}")
    public void validateStatusCode(int statusCode) {
        SerenityRest.then().statusCode(equalTo(statusCode));
    }

    @Step("validate the data detail after create user")
    public void validateDataDetail(String message){
        if (message.equals("success")){
            SerenityRest.then().body("username", equalTo(this.userName));
        } else {
            SerenityRest.then().body("username", equalTo(null));
        }
    }

    @Step("get userId for other request")
    public String getUserId() {
        Response response = SerenityRest.lastResponse();
        String userId = response.body().path("userID");
        System.out.println("User ID: " + userId);
        try (FileWriter file = new FileWriter("src//test//resources//filejson//userId.json")){
            file.write(userId);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userId;
    }
}

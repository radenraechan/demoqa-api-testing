package starter.account;

import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import static org.hamcrest.Matchers.equalTo;

public class Delete {

    private static String base_url = "https://demoqa.com/Account/v1/";

    // Scenario 5.1: Positive
    @Step("I set an endpoint for DELETE user")
    public String setAnEndpointForDelete(String userId){
        return base_url + "User/" + userId;
    }

    @Step("I request DELETE user")
    public void requestDeleteUser(String userId, String token){
        SerenityRest.given()
                .header("Authorization", token)
                .when().get(setAnEndpointForDelete(userId));
    }

    @Step("I validate the status code is {int}")
    public void validateStatusCode(int statusCode){
        SerenityRest.then().statusCode(statusCode);
    }

    @Step("validate the data detail after delete user")
    public void validateDataDetailAfterDeleteUser() {
        SerenityRest.then().body("code", equalTo("1200"));
    }
}

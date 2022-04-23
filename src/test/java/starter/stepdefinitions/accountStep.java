package starter.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import starter.account.PostAuthorized;
import starter.account.PostGenerateToken;
import starter.account.Delete;
import starter.account.Get;
import starter.account.Post;

import java.io.IOException;

public class accountStep {

    public String userId, token, userName;

    @Steps
    Post post;

    @Steps
    PostGenerateToken postGenerateToken;

    @Steps
    PostAuthorized postAuthorized;

    @Steps
    Get get;

    @Steps
    Delete delete;

    // Scenario 1: POST - User
    @Given("I set an endpoint for POST new user")
    public void iSetAnEndpointForPOSTNewUser() {
        post.setPostEndpoint();
    }

    @When("I request POST detail user are {string} username and {string} password")
    public void iRequestPOSTDetailUserAreAnd(String arg0, String arg1) {
        post.requestPostDetailUser(arg0, arg1);
    }

    @Then("I validate the status code is {int}")
    public void iValidateTheStatusCodeIs(int arg0) {
        post.validateStatusCode(arg0);
        postGenerateToken.validateStatusCode(arg0);
        postAuthorized.validateStatusCode(arg0);
        get.validateStatusCode(arg0);
    }

    @And("validate the {string} after create user")
    public void validateTheAfterCreateUser(String arg0) {
        this.userName = post.getUserName();
        post.validateDataDetail(arg0);
    }

    @And("get userId if {string} for other request")
    public void getUserIdIfForOtherRequest(String arg0) {
        if (arg0.equals("success")){
            post.getUserId();
//            this.userId = post.getUserId();
        }
    }

    // Scenario 2: POST - Generate Token
    @Given("I set an endpoint for POST generate token")
    public void iSetAnEndpointForPOSTGenerateToken() {
        postGenerateToken.setPostEndpointGenerateTokenUser();
    }

    @When("I request POST generate token")
    public void iRequestPOSTGenerateToken() throws Exception {
        postGenerateToken.requestPostGenerateTokenUser();
    }

    @And("validate the data detail after generate token")
    public void validateTheDataDetailAfterGenerateToken() {
        postGenerateToken.validateDataDetailAfterGenerateTokenUser();
    }

    @And("get token for other request")
    public void getTokenForOtherRequest() {
        postGenerateToken.getToken();
    }

    // Scenario 3: POST - Authorized
    @Given("I set an endpoint for POST authorize")
    public void iSetAnEndpointForPOSTAuthorize() {
        postAuthorized.setPostEndpointAuthorizeUser();
    }

    @When("I request POST authorize")
    public void iRequestPOSTAuthorize() throws Exception {
        postAuthorized.requestPostAuthorizeUser();
    }

    @And("validate the data detail after authorize")
    public void validateTheDataDetailAfterAuthorize() {
        postAuthorized.validateDataDetailAfterAuthorizeUser();
    }

    // Scenario 4: GET - Detail User
    @Given("I set an endpoint for GET detail user")
    public void iSetAnEndpointForGETDetailUser() {
        get.setAnEndpointForGet(userId);
    }

    @When("I request GET detail user")
    public void iRequestGETDetailUser() throws IOException {
        get.requestGetDetailUser();
    }

    @And("validate the data detail")
    public void validateTheDataDetail() {
        get.validateDataDetail();
    }
}

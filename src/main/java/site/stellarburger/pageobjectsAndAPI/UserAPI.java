package site.stellarburger.pageobjectsAndAPI;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;



public class UserAPI {

    public static final String newUserAPIPath = "/api/auth/register";
    public static final String loginUserAPIPath = "/api/auth/login";
    public static final String logoutUserAPIPath = "/api/auth/logout";
    public static final String updateUserAPIPath = "/api/auth/user"; // + PATCH
    public static final String deleteUserAPIPath = "/api/auth/user"; // + DELETE

    @Step("Create new user")
    public static Response createUser(User user) {
        Response responseCreate = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(newUserAPIPath);
        return responseCreate;
    }

    @Step("Login user and get authToken in response")
    public static Response loginUserAndGetToken(User user) {

        Response responseLogin = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(loginUserAPIPath);
        return responseLogin;

    }
    @Step("Delete user")
    public static void deleteUser(User user, String token) {
        Response responseDelete = given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(user)
                .when()
                .delete(deleteUserAPIPath);
    }
}

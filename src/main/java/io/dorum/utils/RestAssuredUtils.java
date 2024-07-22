package io.dorum.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;

import java.util.Map;

@Log4j2
public class RestAssuredUtils {

    public static Map<String, Object> fetchCredentials() {
        Response response = RestAssured.given().filter(new AllureRestAssured()).get(Config.getMockUrl() + "/api/credentials");
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to fetch credentials: " + response.getStatusLine());
        }
        String responseBody = response.getBody().asString();
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject.toMap();
    }
}

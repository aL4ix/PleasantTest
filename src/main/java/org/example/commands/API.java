package org.example.commands;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.glue.Glue;
import org.example.glue.GlueAnnotation;

import static io.restassured.RestAssured.given;

@SuppressWarnings("unused")
public class API extends Glue {
    private final RequestSpecification given;
    Response response;

    public API() {
        given = given();
    }

    private int getStatusCode() {
        return response.getStatusCode();
    }

    private void header(String key, String value) {
        given.header(key, value);
    }

    private void get(String url) {
        response = given.get(url);
    }

    private Object extractFromBody(String jsonPath) {
        return response.body().jsonPath().get(jsonPath);
    }

    @GlueAnnotation("Extract,{}")
    public void extract(String variable) {

    }
}

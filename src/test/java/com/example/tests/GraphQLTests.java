package com.example.tests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class GraphQLTests {


    @Test
    public void gqlPostApiTest() {

        RestAssured.baseURI = "https://swapi-graphql.netlify.app";
        String payload = "{\"query\":\"{\\n  allFilms{\\n    films{\\n      title\\n    }\\n  }\\n}\",\"variables\":null}";

        RestAssured.given().contentType(ContentType.JSON).body(payload).log().all().when().post("/.netlify/functions/index").then().log().all()
                .assertThat().statusCode(200).and().body("data.allFilms.films[1].title", equalTo("The Empire Strikes Back"));
    }

    @Test
    public void hasuraGqlPostAPITest() {

        RestAssured.baseURI = "https://hasura.io";
        String payload = "{\"query\":\"{\\n  users(limit: 10) {\\n    id\\n    name\\n  }\\n}\\n\",\"variables\":null}";
        String authorization = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDYwZjlhOGRlZmMzYzZjMDA3NTczYzY2ZiJ9LCJuaWNrbmFtZSI6InZydmluYXlyYXRob2Q0MyIsIm5hbWUiOiJ2cnZpbmF5cmF0aG9kNDNAZ21haWwuY29tIiwicGljdHVyZSI6Imh0dHBzOi8vcy5ncmF2YXRhci5jb20vYXZhdGFyLzc2OThmMGI4NzQwODMyNzJmODQ5ZjdhZjM4ZDUzNTNmP3M9NDgwJnI9cGcmZD1odHRwcyUzQSUyRiUyRmNkbi5hdXRoMC5jb20lMkZhdmF0YXJzJTJGdnIucG5nIiwidXBkYXRlZF9hdCI6IjIwMjEtMDctMjJUMTc6MjA6MzEuNTY0WiIsImlzcyI6Imh0dHBzOi8vZ3JhcGhxbC10dXRvcmlhbHMuYXV0aDAuY29tLyIsInN1YiI6ImF1dGgwfDYwZjlhOGRlZmMzYzZjMDA3NTczYzY2ZiIsImF1ZCI6IlAzOHFuRm8xbEZBUUpyemt1bi0td0V6cWxqVk5HY1dXIiwiaWF0IjoxNjI2OTc0NDMzLCJleHAiOjE2MjcwMTA0MzMsImF0X2hhc2giOiI5eVVpRTd2dE9PWVlpeXRpVG9UQVVBIiwibm9uY2UiOiJLUzNwc2ZtSENxZ0gueGRyd01kOExnWkczVml4bjZYRyJ9.qkRYwSQ_wqFa7LBauA3cI9cqJNgDQB16WyXkiFcD32PgPvIG8EPNXEv8DcNUPlk3ZLrSYugrLrx1Vb3m0U45vfL1VDmidmTDX_XXAZmFqpEiWydNbGQ1PNgWl9PShQHiVzgr2Kviz2WX5fRbZqPP5jH6FMUmgz2UlfMoWDr8U-B0TtDRDGW10_10HUfJuG85f76K_vXOcizTyZ92-6ZDjq3gMP537kBfK4n2ijuf0Jo3y-rVy5Cua50Y4BYYLeqHdY0eMcSAq9Y56-iyqb4z2LWstzJKbkdi7Te1yN9WL02ZmNShq0STLuXWNZ2XXEl7Ktf9gi5Cdpz8yLSl-qejDA";

        RestAssured.given().contentType(ContentType.JSON).header("Authorization", authorization).body(payload).log().all().when().post("/learn/graphql").then().log().all()
                .assertThat().statusCode(200).and().body("data.users[1].name", equalTo("dassad"));
    }

    @DataProvider
    public Object[] getQueryData() {
        return new Object[][]{{"9", "akshayapsangi123"}, {"5", "dassad"}};
    }

    // GQL with parametrised query
    @Test(dataProvider = "getQueryData")
    public void hasuraGqlPostAPIWithParameterisedQueryTest(String limit, String name) {

        RestAssured.baseURI = "https://hasura.io";
        String payload = "{\"query\":\"{\\n  users(limit: " + limit + ", where: {name: {_eq: \\\"" + name + "\\\"}}) {\\n    id\\n    name\\n  }\\n}\\n\",\"variables\":null}";
        String authorization = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDYwZjlhOGRlZmMzYzZjMDA3NTczYzY2ZiJ9LCJuaWNrbmFtZSI6InZydmluYXlyYXRob2Q0MyIsIm5hbWUiOiJ2cnZpbmF5cmF0aG9kNDNAZ21haWwuY29tIiwicGljdHVyZSI6Imh0dHBzOi8vcy5ncmF2YXRhci5jb20vYXZhdGFyLzc2OThmMGI4NzQwODMyNzJmODQ5ZjdhZjM4ZDUzNTNmP3M9NDgwJnI9cGcmZD1odHRwcyUzQSUyRiUyRmNkbi5hdXRoMC5jb20lMkZhdmF0YXJzJTJGdnIucG5nIiwidXBkYXRlZF9hdCI6IjIwMjEtMDctMjJUMTc6MjA6MzEuNTY0WiIsImlzcyI6Imh0dHBzOi8vZ3JhcGhxbC10dXRvcmlhbHMuYXV0aDAuY29tLyIsInN1YiI6ImF1dGgwfDYwZjlhOGRlZmMzYzZjMDA3NTczYzY2ZiIsImF1ZCI6IlAzOHFuRm8xbEZBUUpyemt1bi0td0V6cWxqVk5HY1dXIiwiaWF0IjoxNjI2OTc0NDMzLCJleHAiOjE2MjcwMTA0MzMsImF0X2hhc2giOiI5eVVpRTd2dE9PWVlpeXRpVG9UQVVBIiwibm9uY2UiOiJLUzNwc2ZtSENxZ0gueGRyd01kOExnWkczVml4bjZYRyJ9.qkRYwSQ_wqFa7LBauA3cI9cqJNgDQB16WyXkiFcD32PgPvIG8EPNXEv8DcNUPlk3ZLrSYugrLrx1Vb3m0U45vfL1VDmidmTDX_XXAZmFqpEiWydNbGQ1PNgWl9PShQHiVzgr2Kviz2WX5fRbZqPP5jH6FMUmgz2UlfMoWDr8U-B0TtDRDGW10_10HUfJuG85f76K_vXOcizTyZ92-6ZDjq3gMP537kBfK4n2ijuf0Jo3y-rVy5Cua50Y4BYYLeqHdY0eMcSAq9Y56-iyqb4z2LWstzJKbkdi7Te1yN9WL02ZmNShq0STLuXWNZ2XXEl7Ktf9gi5Cdpz8yLSl-qejDA";

        RestAssured.given().contentType(ContentType.JSON).header("Authorization", authorization).body(payload).log().all().when().post("/learn/graphql").then().log().all()
                .assertThat().statusCode(200);
    }


}

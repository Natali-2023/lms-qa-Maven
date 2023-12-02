package de.aittr.lms.restAssuredTests;

import de.aittr.lms.dto.NewUserWithRoleDto;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;

public class CreateNewUserByAdminRATest extends  TestBaseRA{

    private Cookie cookie;
    private NewUserWithRoleDto newUser;
    private String mail = "lilu@mal.com";

    @BeforeMethod
    public void precondition(){
        cookie = user.getLoginCookie("admin@mail.com", "Admin123!");
        newUser = user.userWithRoleBuilder("Cohort 34.2", mail, "Lilu", "Test",
                "Germany", "STUDENT", "+490123456789");
    }

    @AfterMethod
    public void postCondition() throws SQLException {
        user.deleteUser(mail);
    }

    @Test
    public void createNewUserByAdminPositiveTest() throws SQLException {
        given().contentType(ContentType.JSON).body(newUser).cookie(cookie).when().post("/users/create-user")
                .then().assertThat().statusCode(201);
    }


    @Test
    public void createNewUserWithoutAdminNegativeTest() {
        given().contentType(ContentType.JSON).body(newUser).when().post("/users/create-user")
                .then().assertThat().statusCode(403);
    }

    @Test
    public void createUserWithWrongRoleByAdminNegativeTest() {
        NewUserWithRoleDto notValidUser = user.userWithRoleBuilder("Cohort 34.2", mail, "Lilu",
                "Test", "Germany", "user", "+490123456789");
        given().contentType(ContentType.JSON).body(notValidUser).cookie(cookie).when().post("/users/create-user")
                .then().assertThat().statusCode(400);
    }

    @Test
    public void createUserWithWrongCohortByAdminNegativeTest() {
        NewUserWithRoleDto notValidUser = user.userWithRoleBuilder("Cohort", mail, "Lilu",
                "Test", "Germany", "STUDENT", "+490123456789");
        given().contentType(ContentType.JSON).body(notValidUser).cookie(cookie).when().post("/users/create-user")
                .then().assertThat().statusCode(400);
    }

    @Test
    public void createUserWithWrongPhoneByAdminNegativeTest() {
        NewUserWithRoleDto notValidUser = user.userWithRoleBuilder("Cohort 34.2", mail, "Lilu",
                "Test", "Germany", "STUDENT", "Phone number");
        given().contentType(ContentType.JSON).body(notValidUser).cookie(cookie).when().post("/users/create-user")
                .then().assertThat().statusCode(400);
    }

    @Test
    public void createUserWithWrongFirstNameByAdminNegativeTest() throws SQLException {
        NewUserWithRoleDto notValidUser = user.userWithRoleBuilder("Cohort 34.2", mail, "+490123456789",
                "Test", "Germany", "STUDENT", "+490123456789");
        given().contentType(ContentType.JSON).body(notValidUser).cookie(cookie).when().post("/users/create-user")
                .then().assertThat().statusCode(400);
    }

    @Test
    public void createUserWithWrongCountryByAdminNegativeTest() throws SQLException {
        NewUserWithRoleDto notValidUser = user.userWithRoleBuilder("Cohort 34.2", mail, "Lilu",
                "Test", "Germany777", "STUDENT", "+490123456789");
        given().contentType(ContentType.JSON).body(notValidUser).cookie(cookie).when().post("/users/create-user")
                .then().assertThat().statusCode(400);
    }

    @Test
    public void createExistedUserByAdminNegativeTest() throws SQLException {
        NewUserWithRoleDto notValidUser = user.userWithRoleBuilder("Cohort 34.2", "student@mail.com",
                "Student","Test", "Germany", "STUDENT", "+490123456789");
        given().contentType(ContentType.JSON).body(notValidUser).cookie(cookie).when().post("/users/create-user")
                .then().assertThat().statusCode(409);
    }

}

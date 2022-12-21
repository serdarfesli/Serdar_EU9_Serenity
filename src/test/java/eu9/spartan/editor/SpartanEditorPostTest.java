package eu9.spartan.editor;

import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utilities.SpartanNewBase;
import utilities.SpartanUtil;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import static org.hamcrest.Matchers.*;
import static net.serenitybdd.rest.SerenityRest.*;

import java.util.Map;

@SerenityTest
public class SpartanEditorPostTest extends SpartanNewBase {

   @Test
   public void postSpartanASEditor() {

      Map<String, Object> randomSpartanMap = SpartanUtil.getRandomSpartanMap();

      System.out.println("randomSpartanMap = " + randomSpartanMap);
      given().contentType(ContentType.JSON)
              .auth().basic("editor", "editor")
              .accept(ContentType.JSON)
              .body(randomSpartanMap)
              .when().post("/spartans")
              .prettyPrint();

      // status code is 201
                Ensure.that("status code is 201",v->v.statusCode(201));
      // content type is Json
      Ensure.that("content type is Json",v->v.contentType(ContentType.JSON));

//                success message is A Spartan is Born!
      Ensure.that("success message",v->v.body("success",is("A Spartan is Born!")));
//                id is not null
      Ensure.that("id not null",v->v.body("data.id",notNullValue()));

//                name is correct
      Ensure.that("name is correct",v->v.body("data.name",is(randomSpartanMap.get("name"))));
//                gender is correct
      Ensure.that("gender is correct",v->v.body("data.gender",is(randomSpartanMap.get("gender"))));
//                phone is correct
      Ensure.that("phone is correct",v->v.body("data.phone",is(randomSpartanMap.get("phone"))));

      String id = lastResponse().jsonPath().getString("data.id");
      //  check location header ends with newly generated id

      Ensure.that("location header ends with newly generated id",v->v.header("location",contains(id)));


   }

   @ParameterizedTest
   @CsvFileSource(resources = "/SpartanData.csv", numLinesToSkip = 1)
   public void postSpartanWithCSV(String name, String gender,long phone){
      System.out.println("name = " + name);
      System.out.println("gender = " + gender);
      System.out.println("phone = " + phone);

   }

}

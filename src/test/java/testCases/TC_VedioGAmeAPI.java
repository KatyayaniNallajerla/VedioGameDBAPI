package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class TC_VedioGAmeAPI {
	@Test(priority=0)
	public void test_GETallVedioGames()
	{
		given()
		.when()
		.get("https://videogamedb.uk:443/api/v2/videogame")
		//.prettyPrint()
		.then()
		.statusCode(200);
		}
	@Test(priority=1)
	public void test_AddaVedioGame()
	{
		HashMap data=new HashMap();
		//data.put("id", 100);
		data.put("category", "Adventure");
		data.put("name","SuperMario");
		data.put("rating", "PG-21");
		data.put("releaseDate", "2022-05-04");
		data.put("reviewScore", 85);
		
		Response res=
		
	given()
		.contentType("application/json")
		.accept("application/json")
		.body(data)
   .when()
		.post("https://videogamedb.uk:443/api/v2/videogame")
		
   .then()
		.log().body()
		.statusCode(200)
		.extract().response();
		String jsonString=res.asString();
		Assert.assertEquals((jsonString.contains("Adventure")),true);
		}
	@Test(priority=2)
	public void test_GETAvediogamebyID()
	{
	given()
	.when()
	    .get("https://videogamedb.uk:443/api/v2/videogame/5")
	.then()
	    .statusCode(200)
	   	.body("id", equalTo(5))
	    .body("name", equalTo("The Legend of Zelda: Ocarina of Time"))
	    .log().body()
		.extract().response();
	}
   @Test(priority=3,enabled = true)
   public void test_UpdateAGameinDB()
   {
	   HashMap updateData=new HashMap();
	   updateData.put("category", "Puzzle");
	   updateData.put("name", "Tetris");
	   updateData.put("rating", "E-8");
	   updateData.put( "releaseDate", "2022-05-04");
	   updateData.put( "reviewScore", 93);
    
	   given()
	     .contentType("application/json")
	     .accept("application/xml")
	     .body(updateData)
	  .when()
         .put("https://videogamedb.uk:443/api/v2/videogame/5")
      .then()
         .statusCode(200)
         .body("VedioGame.id",equalTo("5"))
         .body("VedioGame.name", equalTo("Tetris"))
         .log().body()
         .extract().asPrettyString();
          }
     @Test(priority =4)
     public void test_DeleteGame()
     {
    	 Response res=
     given()
     .when()
     .delete("https://videogamedb.uk:443/api/v2/videogame/5")
     .then()
     .statusCode(200)
     .log().body()
     .extract().response() ;   
     String jsonstrng=res.asString();
    Assert.assertEquals((jsonstrng.contains("Video game deleted")), true);
     }
     
}

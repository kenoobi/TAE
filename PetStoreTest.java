package ip.swagger.petstore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetStoreTest {
    private static final String BASE_URL = "http://localhost:8080";

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    // PUT: /pet - Update an existing pet
    @Test
    public void testUpdateExistingPetSuccess() {
        int petId = 10;
        String updatedName = "Doggie updated";
        String updatedStatus = "available";

        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"id\": " + petId + ", \"name\": \"" + updatedName + "\", \"status\": \"" + updatedStatus + "\" }")
                .when()
                .put("/api/v3/pet")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(updatedName, response.jsonPath().getString("name"));
        assertEquals(updatedStatus, response.jsonPath().getString("status"));
    }

    @Test
    public void testUpdateNonExistentPet() {
        int nonExistentPetId = 9999; // Cambia esto según tus necesidades

        given()
                .contentType(ContentType.JSON)
                .body("{ \"id\": " + nonExistentPetId + ", \"name\": \"New Name\", \"status\": \"available\" }")
                .when()
                .put("/api/v3/pet")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateWithInvalidData() {
        int petId = 123;

        given()
                .contentType(ContentType.JSON)
                .body("{ \"id\": " + petId + ", \"name\": \"\", \"status\": \"invalid_status\" }")
                .when()
                .put("/api/v3/pet")
                .then()
                .statusCode(405);
    }

    // POST: /pet - Add a new pet to the store
    @Test
    public void testAddNewPetSuccess() {
        int petId = 10;
        String petName = "Doogie new";
        String petStatus = "available";

        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"id\": " + petId + ", \"name\": \"" + petName + "\", \"status\": \"" + petStatus + "\" }")
                .when()
                .post("/api/v3/pet")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(petName, response.jsonPath().getString("name"));
        assertEquals(petStatus, response.jsonPath().getString("status"));
    }

    @Test
    public void testAddWithInvalidData() {
        int petId = 6574;

        given()
                .contentType(ContentType.JSON)
                .body("{ \"id\": " + petId + ", \"name\": \"\", \"status\": \"invalid_status\" }")
                .when()
                .post("/api/v3/pet")
                .then()
                .statusCode(400);
    }

    @Test
    public void testAddDuplicatePet() {
        int petId = 10;
        String petName = "Doggie duplicated";
        String petStatus = "available";

        given()
                .contentType(ContentType.JSON)
                .body("{ \"id\": " + petId + ", \"name\": \"" + petName + "\", \"status\": \"" + petStatus + "\" }")
                .when()
                .post("/api/v3/pet")
                .then()
                .statusCode(200); // 500 para indicar un error interno del servidor
    }

    // GET: /pet/findByStatus - Finds Pets by status
    @Test
    public void testFindPetsByValidStatus() {
        String validStatus = "available";

        Response response = given()
                .queryParam("status", validStatus)
                .when()
                .get("/api/v3/pet/findByStatus")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(validStatus, response.jsonPath().getString("[0].status"));
    }

    @Test
    public void testFindPetsByInvalidStatus() {
        String invalidStatus = "invalid_status";

        given()
                .queryParam("status", invalidStatus)
                .when()
                .get("/api/v3/pet/findByStatus")
                .then()
                .statusCode(400);
    }

    @Test
    public void testFindPetsWithoutStatus() {
        given()
                .when()
                .get("/api/v3/pet/findByTags")
                .then()
                .statusCode(400);
    }

    // // GET: /pet/findByTags - Finds Pets by tags
    @Test
    public void testFindPetsByValidTags() {
        String validTag = "tag1";

        Response response = given()
                .queryParam("tags", validTag)
                .when()
                .get("/api/v3/pet/findByTags")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(validTag, response.jsonPath().getString("[0].tags[0].name"));
    }

    @Test
    public void testFindPetsByInvalidTags() {
        String invalidTag = "tag_1"; // Cambia esto según tus necesidades

        given()
                .queryParam("tags", invalidTag)
                .when()
                .get("/api/v3/pet/findByTags")
                .then()
                .statusCode(200) // Ajusta el código de estado según tu API (puede ser 400 o 404)
                .extract().response();
    }

    // GET: /pet/{petId} - Find pet by ID:
    @Test
    public void testFindPetByValidId() {
        int validPetId = 123;

        Response response = given()
                .pathParam("petId", validPetId)
                .when()
                .get("/v2/pet/{petId}")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(validPetId, response.jsonPath().getInt("id"));
    }

    @Test
    public void testFindPetWithNonExistentId() {
        int nonExistentPetId = 9999;

        given()
                .pathParam("petId", nonExistentPetId)
                .when()
                .get("/v2/pet/{petId}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindPetWithInvalidId() {
        String invalidPetId = "invalid_id"; // Cambia esto según tus necesidades

        given()
                .pathParam("petId", invalidPetId)
                .when()
                .get("/v2/pet/{petId}")
                .then()
                .statusCode(400);
    }

    // POST: /pet/{petId} - Updates a pet in the store with form data:
    @Test
    public void testUpdatePetWithValidData() {
        int petId = 10;
        String updatedName = "New Name";
        String updatedStatus = "available";

        Response response = given()
                .contentType(ContentType.URLENC)
                .formParam("name", updatedName)
                .formParam("status", updatedStatus)
                .pathParam("petId", petId)
                .when()
                .post("/v2/pet/{petId}")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(updatedName, response.jsonPath().getString("name"));
        assertEquals(updatedStatus, response.jsonPath().getString("status"));
    }

    @Test
    public void testPostUpdateNonExistentPet() {
        int nonExistentPetId = 9999;

        given()
                .contentType(ContentType.URLENC)
                .formParam("name", "New Name")
                .formParam("status", "available")
                .when()
                .post("/v2/pet/{petId}", nonExistentPetId)
                .then()
                .statusCode(404);
    }

    @Test
    public void testPostUpdateWithInvalidData() {
        int petId = 123; // Cambia esto según tus necesidades

        given()
                .contentType(ContentType.URLENC)
                .formParam("name", "")
                .formParam("status", "invalid_status")
                .when()
                .post("/v2/pet/{petId}", petId)
                .then()
                .statusCode(400);
    }

    //DELETE: /pet/{petId} - Deletes a pet
    @Test
    public void testDeletePetWithValidId() {
        int validPetId = 10;
        Response response = given()
                .pathParam("petId", validPetId)
                .when()
                .delete("/v2/pet/{petId}")
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = response.getBody().asString();
        assertEquals("Pet deleted", responseBody);
    }

    @Test
    public void testDeleteNonExistentPet() {
        int nonExistentPetId = 9999;

        given()
                .pathParam("petId", nonExistentPetId)
                .when()
                .delete("/v2/pet/{petId}")
                .then()
                .statusCode(404);
    }

    // POST: /pet/{petId}/uploadImage - uploads an image
    @Test
    public void testUploadImageWithValidId() {
        int validPetId = 123;
        String imagePath = "/path/to/image.jpg";

        Response response = given()
                .pathParam("petId", validPetId)
                .multiPart("file", getClass().getResourceAsStream(imagePath))
                .when()
                .post("/v2/pet/{petId}/uploadImage")
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = response.getBody().asString();
        assertEquals("Image uploaded successfully", responseBody);
    }

    @Test
    public void testUploadImageForNonExistentPet() {
        int nonExistentPetId = 9999;
        String imagePath = "/path/to/image.jpg";

        given()
                .pathParam("petId", nonExistentPetId)
                .multiPart("file", getClass().getResourceAsStream(imagePath))
                .when()
                .post("/v2/pet/{petId}/uploadImage")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUploadInvalidImage() {
        int validPetId = 123;
        String invalidImagePath = "/path/to/nonexistent_image.jpg";

        given()
                .pathParam("petId", validPetId)
                .multiPart("file", getClass().getResourceAsStream(invalidImagePath))
                .when()
                .post("/v2/pet/{petId}/uploadImage")
                .then()
                .statusCode(400);
    }
}

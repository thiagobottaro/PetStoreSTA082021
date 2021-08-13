 //diff online
 // diffnow.com
 // https://mvnrepository.com/artifact/org.testng/testng/7.1.0

// 1 - Pacote
package petstore;

// 2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

 // 3 - Classe
public class Pet {
    // https://petstore.swagger.io/v2/swagger.json
    // 3.1 - Atributos
    String uri="https://petstore.swagger.io/v2/pet"; // endereco da entidade Pet

    // 3.2 - Métodos e Funcoes
    public String readJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test(priority = 1)  // Identifica o metodo ou funcao como um teste para o TestNG
    public void insertPet() throws IOException {
        String jsonBody=readJson("db/pet1.json");

        // Sintaxe Gherkin
        // Dado Um conjunto de condicoes  - Quando Faco Algo  -  Entao acontece tal reacao
        // Given  -  When  -  Then

        // Rest Assured
        //comum em API Rest - antigas era "text/xml"
        given() // Dado
                .contentType("application/json").log().all().body(jsonBody)
        .when() // Quando Faco algo
                .post(uri)
        .then() // Entao
                .log().all()
                .statusCode(200) // status code da conexao com o servidor
                .body("name", is("Kiara"))
                .body("status", is("available"))
                .body("category.name", is("AX2356554874"))
                .body("tags.name", contains("sta")) // contains utilizar para verificar conteudo de array
                //.extract()
        ;

        //id: 5864798277
        //id: 5864798278
        //id: 5864798279
        //id: 5864798358
        //id: 9223372036854775807
    }

    // (priority = 2) => sequencia para executar o teste. Executando pelo build.gradle
    @Test(priority = 2)
    public void getPet(){
        String petId="5864798358";

        String token=
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri+"/"+petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name",is("Kiara"))
                .body("category.name", is("AX2356554874"))
                .body("status", is("available"))
                .body("tags.name", contains("sta"))
        .extract()
                .path("category.name")
        ;
        System.out.println("O token eh:" + token);
    }

     @Test(priority = 3)
    public void updatePet() throws IOException {
        String jsonBody=readJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Kiara"))
                .body("status", is("sold"))
        ;

    }

     @Test(priority = 4)
     public void deletePet(){
         String petId="5864798358";

         given()
                 .contentType("application/json")
                 .log().all()
         .when()
                 .delete(uri+"/"+petId)
         .then()
                 .log().all()
                 .statusCode(200)
                 .body("code", is(200))
                 .body("type", is("unknown"))
                 .body("message", is(petId))
         ;

     }

}

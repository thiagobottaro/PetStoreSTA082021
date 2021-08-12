// 1 - Pacote
package petstore;

// 2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

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
    @Test  // Identifica o metodo ou funcao como um teste para o TestNG
    public void insertPet() throws IOException {
        String jsonBody=readJson("db/pet1.json");

        // Sintaxe Gherkin
        // Dado  - Quando Faco Algo  -  Entao acontece tal reacao
        // Given  -  When  -  Then

        //comum em API Rest - antigas era "text/xml"
        given() // Dado
                .contentType("application/json").log().all().body(jsonBody)
        .when() // Quando Faco algo
                .post(uri)
        .then() // Entao
                .log().all()
                .statusCode(200)
        ;
    }
}

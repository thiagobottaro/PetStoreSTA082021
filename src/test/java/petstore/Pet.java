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
                .body("name", is("Kiara2"))
                .body("status", is("available"))
        //id: 5864798277
        //id: 9223372036854775807
        ;
    }
}

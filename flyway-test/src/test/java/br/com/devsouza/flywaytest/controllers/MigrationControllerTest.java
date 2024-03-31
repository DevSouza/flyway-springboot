package br.com.devsouza.flywaytest.controllers;

import static org.assertj.core.api.Assertions.*;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.devsouza.flywaytest.FlywayTestApplication;
import br.com.devsouza.flywaytest.service.dto.MigratedDTO;
import br.com.devsouza.flywaytest.service.dto.MigrationDTO;

@Tag("IT")
@DisplayName("IT for MigrationController")
@SpringBootTest(classes = FlywayTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MigrationControllerTest {
    
    @Autowired private Flyway flyway;
    @Autowired private TestRestTemplate client;
    
    @Lazy
    @TestConfiguration
    static class Config {
        @Bean
        public TestRestTemplate testRestTemplate(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:"+port);
            return new TestRestTemplate(restTemplateBuilder);
        }
	}

    @BeforeEach
    public void setUp() {
        flyway.clean();
    }

    @Test
    @DisplayName("Should return a list of migrations pending")
	void testGetMigrations() {
		ResponseEntity<MigrationDTO[]> response = client.getForEntity("/v1/migrations", MigrationDTO[].class);
		MigrationDTO[] resultBody = response.getBody();

        assertThat(resultBody.length).isGreaterThan(0);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); 
	}

    @Test
    @DisplayName("Should run migrations with success.")
	void testPostMigrations() {
        ResponseEntity<MigratedDTO[]> response1 = client.exchange("/v1/migrations",HttpMethod.POST, null, MigratedDTO[].class);
		MigratedDTO[] resultBody1 = response1.getBody();

        ResponseEntity<MigratedDTO[]> response2 = client.exchange("/v1/migrations",HttpMethod.POST, null, MigratedDTO[].class);
		MigratedDTO[] resultBody2 = response2.getBody();

        assertThat(resultBody1.length).isGreaterThan(0);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(resultBody2.length).isEqualTo(0);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}

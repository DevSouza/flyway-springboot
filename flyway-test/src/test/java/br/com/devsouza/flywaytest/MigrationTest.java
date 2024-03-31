package br.com.devsouza.flywaytest;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.output.MigrateOutput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.devsouza.flywaytest.service.dto.MigratedDTO;
import br.com.devsouza.flywaytest.service.dto.MigrationDTO;

@Tag("IT")
@DisplayName("IT for MigrationController")
@SpringBootTest
public class MigrationTest {
    
    @Autowired private Flyway flyway;
    
    private RestTemplate client = new RestTemplateBuilder()
        .rootUri("http://localhost:8080")    
        .build();

    @BeforeEach
    public void setUp() {
        flyway.clean();
    }

    @Test
	void testGetMigrations() {
		ResponseEntity<MigrationDTO[]> response = client.getForEntity("/v1/migrations", MigrationDTO[].class);
		MigrationDTO[] resultBody = response.getBody();

        Assertions.assertThat(resultBody.length).isGreaterThan(0); 
	}

    @Test
	void testPostMigrations() {
        ResponseEntity<MigratedDTO[]> response1 = client.exchange("/v1/migrations",HttpMethod.POST, null, MigratedDTO[].class);
		MigratedDTO[] resultBody1 = response1.getBody();

        ResponseEntity<MigratedDTO[]> response2 = client.exchange("/v1/migrations",HttpMethod.POST, null, MigratedDTO[].class);
		MigratedDTO[] resultBody2 = response2.getBody();

        Assertions.assertThat(resultBody1.length).isGreaterThan(0);
        Assertions.assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(resultBody2.length).isEqualTo(0);
        Assertions.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}

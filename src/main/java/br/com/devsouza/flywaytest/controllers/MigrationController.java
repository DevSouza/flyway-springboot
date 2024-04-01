package br.com.devsouza.flywaytest.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.devsouza.flywaytest.service.MigrationService;
import br.com.devsouza.flywaytest.service.dto.MigratedDTO;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("v1/migrations")
public class MigrationController {
    
    @Autowired private MigrationService migrationService;

    @GetMapping
    public ResponseEntity<?> getMigrationsPending() {
        return ResponseEntity.ok(migrationService.getMigrationsPending());
    }

    @PostMapping
    public ResponseEntity<?> runMigrations() {
        List<MigratedDTO> runMigrations = migrationService.runMigrations();

        if(runMigrations.isEmpty()) {
            return ResponseEntity.ok(runMigrations);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(runMigrations);
    }

}

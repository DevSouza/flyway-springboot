package br.com.devsouza.flywaytest.service;

import java.util.ArrayList;
import java.util.List;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.output.MigrateOutput;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devsouza.flywaytest.service.dto.MigratedDTO;
import br.com.devsouza.flywaytest.service.dto.MigrationDTO;

@Service
public class MigrationService {

    @Autowired private Flyway flyway;

    public List<MigrationDTO> getMigrationsPending() {
        MigrationInfo[] pending = flyway.info().pending();

        List<MigrationDTO> migrations = new ArrayList<>();
        for (MigrationInfo migrationInfo : pending) {
            migrations.add(new MigrationDTO(migrationInfo.getDescription(), migrationInfo.getScript(), migrationInfo.getVersion().getVersion()));
        }

        return migrations;
    }

    public List<MigratedDTO> runMigrations() {
        MigrateResult migrate = flyway.migrate();

        return migrate.migrations.stream().map(MigratedDTO::new).toList();
    }
}
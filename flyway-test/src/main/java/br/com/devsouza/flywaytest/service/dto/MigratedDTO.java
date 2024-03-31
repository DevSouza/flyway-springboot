package br.com.devsouza.flywaytest.service.dto;

import org.flywaydb.core.api.output.MigrateOutput;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class MigratedDTO {
    private String category;
    private String version;
    private String description;
    private String type;
    private String filepath;
    private int executionTime;

    public MigratedDTO(MigrateOutput output) {
        this.category = output.category;
        this.version = output.version;
        this.description = output.description;
        this.type = output.type;
        this.filepath = output.filepath;
        this.executionTime = output.executionTime;
    }
}

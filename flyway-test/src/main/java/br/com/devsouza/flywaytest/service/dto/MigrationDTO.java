package br.com.devsouza.flywaytest.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class MigrationDTO {
    
    private String name;
    private String filename;
    private String version;

}

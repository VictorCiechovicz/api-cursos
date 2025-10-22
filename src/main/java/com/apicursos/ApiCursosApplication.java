package com.apicursos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Gestão de Cursos",
                description = "API responsável pela gestão de cursos"
        )
)
public class ApiCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiCursosApplication.class, args);
    }

}

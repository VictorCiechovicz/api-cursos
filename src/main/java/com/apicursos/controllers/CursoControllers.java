package com.apicursos.controllers;


import com.apicursos.Curso;
import com.apicursos.repository.CursoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CursoControllers {

    @Autowired
    private CursoRepository cursoRepository;

    // ✅ Criar cursos
    @PostMapping("/cursos")
    public void create(@RequestBody Curso curso) {
        cursoRepository.save(curso);

    }

    // ✅ Listar cursos com filtros
    @GetMapping("/cursos")
    public List<Curso> list(@RequestParam(required = false) String name,
                            @RequestParam(required = false) String category) {

        if (name != null) {
            return cursoRepository.findByNameContainingIgnoreCase(name);
        } else if (category != null) {
            return cursoRepository.findByCategoryContainingIgnoreCase(category);
        } else {
            return cursoRepository.findAll();
        }
    }

    // ✅ Atualizar curso
    @PutMapping("/cursos/{id}")
    public Curso update(@PathVariable Long id, @RequestBody Curso cursoAtualizado) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.setName(cursoAtualizado.getName());
                    curso.setCategory(cursoAtualizado.getCategory());
                    curso.setActive(cursoAtualizado.getActive());
                    return cursoRepository.save(curso);
                })
                .orElseThrow(() -> new RuntimeException("Curso não encontrado!"));
    }

    // ✅ Deletar curso
    @DeleteMapping("/cursos/{id}")
    @Tag(name = "Exclui curso", description = "Exclui curso de acordo com id")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            Long idLong = Long.parseLong(id); // valida se é numérico
            cursoRepository.deleteById(idLong);
            return ResponseEntity.ok("Curso excluído com sucesso!");
        } catch (NumberFormatException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("id precisa ser numérico");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir curso: " + e.getMessage());
        }
    }

    // ✅ Atualizar o status curso
    @PatchMapping("/cursos/{id}/{active}")
    @Tag(name = "Atualizar status de curso", description = "Atualiza status de curso de acordo com id")
    public void patch(@PathVariable Long id, @PathVariable String active) {

        cursoRepository.findById(id).ifPresent(curso -> {
            if (active.equalsIgnoreCase("true")) {
                curso.setActive(true);
            } else if (active.equalsIgnoreCase("false")) {
                curso.setActive(false);
            }

            cursoRepository.save(curso);
        });
    }

}

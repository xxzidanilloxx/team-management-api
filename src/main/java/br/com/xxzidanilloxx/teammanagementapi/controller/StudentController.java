package br.com.xxzidanilloxx.teammanagementapi.controller;

import br.com.xxzidanilloxx.teammanagementapi.dto.StudentRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.StudentResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO data) {
        StudentResponseDTO result = studentService.createStudent(data);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.id())
                .toUri();
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        validatePaginationParams(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentResponseDTO> result = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(result.getContent());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        StudentResponseDTO result = studentService.getStudentById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/search/name")
    public ResponseEntity<List<StudentResponseDTO>> getStudentByName(@RequestParam String name){
        List<StudentResponseDTO> result = studentService.getStudentByName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/search/cpf")
    public ResponseEntity<Optional<StudentResponseDTO>> getStudentByCpf(@RequestParam String cpf){
        StudentResponseDTO result = studentService.getStudentByCpf(cpf);
        return ResponseEntity.ok(Optional.ofNullable(result));
    }

    @GetMapping(value = "/search/email")
    public ResponseEntity<Optional<StudentResponseDTO>> getStudentByEmail(@RequestParam String email){
        StudentResponseDTO result = studentService.getStudentByEmail(email);
        return ResponseEntity.ok(Optional.ofNullable(result));
    }

    @GetMapping(value = "/search/gender")
    public ResponseEntity<List<StudentResponseDTO>> getStudentByGender(@RequestParam String gender){
        List<StudentResponseDTO> result = studentService.getStudentByGender(gender);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/search/status")
    public ResponseEntity<List<StudentResponseDTO>> findByStudentStatus(@RequestParam String status){
        List<StudentResponseDTO> result = studentService.getStudentByStatus(status);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @RequestBody StudentRequestDTO data) {
        StudentResponseDTO result = studentService.updateStudent(id, data);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<StudentResponseDTO> deactivateStudent(@PathVariable Long id){
        studentService.deactivateStudent(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<StudentResponseDTO> activateStudent(@PathVariable Long id){
        studentService.activateStudent(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudentField(@PathVariable Long id,
                                                              @RequestBody StudentRequestDTO data){
        StudentResponseDTO result = studentService.updateStudentFields(id, data);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    private void validatePaginationParams(int page, int size) {
        if (page < 0 || size <= 0 || size > 100) {
            throw new IllegalArgumentException();
        }
    }
}

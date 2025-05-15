package br.com.xxzidanilloxx.teammanagementapi.controller;

import br.com.xxzidanilloxx.teammanagementapi.dto.ProjectRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.ProjectResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO data) {
        ProjectResponseDTO result = projectService.createProject(data);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<ProjectResponseDTO> result = projectService.getAllProjects();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        ProjectResponseDTO result = projectService.getProjectById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<ProjectResponseDTO>> getProjectByName(@RequestParam String name) {
        List<ProjectResponseDTO> result = projectService.getProjectByName(name);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id,
                                                            @RequestBody ProjectRequestDTO data) {
        ProjectResponseDTO result = projectService.updateProject(id, data);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}

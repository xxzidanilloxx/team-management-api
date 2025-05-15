package br.com.xxzidanilloxx.teammanagementapi.controller;

import br.com.xxzidanilloxx.teammanagementapi.dto.ProjectIdentifierRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.TeamRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.TeamResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody TeamRequestDTO data){
        TeamResponseDTO result = teamService.createTeam(data);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.id())
                .toUri();
        return ResponseEntity.created(location).body(result);
    }

    @PostMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> addProjectToTeam(@PathVariable Long id,
                                                            @RequestBody ProjectIdentifierRequestDTO data){
        TeamResponseDTO result = teamService.addProjectToTeam(id, data);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams(){
        List<TeamResponseDTO> result = teamService.findAllTeams();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> findTeamById(@PathVariable Long id){
        TeamResponseDTO result = teamService.findTeamById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<TeamResponseDTO>> findTeamByName(@RequestParam String name){
        List<TeamResponseDTO> result = teamService.getTeamByName(name);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> updateTeam(@PathVariable Long id, @RequestBody TeamRequestDTO data){
        TeamResponseDTO result = teamService.updateTeam(id, data);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}

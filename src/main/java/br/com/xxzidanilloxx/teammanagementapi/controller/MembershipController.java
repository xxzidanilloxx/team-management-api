package br.com.xxzidanilloxx.teammanagementapi.controller;

import br.com.xxzidanilloxx.teammanagementapi.dto.MembershipRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.MembershipResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.enumeration.Role;
import br.com.xxzidanilloxx.teammanagementapi.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @PostMapping
    public ResponseEntity<MembershipResponseDTO> addMemberToTeam(@RequestBody MembershipRequestDTO data){
        MembershipResponseDTO result = membershipService.addMemberToTeam(data);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.membershipId())
                .toUri();
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("/search/role")
    public ResponseEntity<List<MembershipResponseDTO>> getMembersByRole(@RequestParam Role role){
        List<MembershipResponseDTO> result = membershipService.findMemberByRole(role);
        return ResponseEntity.ok(result);
    }

    @PutMapping("{courseId}/{studentId}/{teamId}")
    public ResponseEntity<Void> updateMemberRole(@PathVariable Long courseId,
                                                 @PathVariable Long studentId,
                                                 @PathVariable Long teamId,
                                                 @RequestBody MembershipRequestDTO data){
        membershipService.updateMemberRole(courseId, studentId, teamId, data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{studentId}/{teamId}")
    public ResponseEntity<Void> removeMemberFromTeam(@PathVariable Long studentId,
                                                     @PathVariable Long teamId) {
        membershipService.removeMemberFromTeam(studentId, teamId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> removeAllMembersFromTeam(@PathVariable Long id) {
        membershipService.removeAllMembersFromTeam(id);
        return ResponseEntity.noContent().build();
    }
}

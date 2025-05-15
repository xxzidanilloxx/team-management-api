package br.com.xxzidanilloxx.teammanagementapi.controller;

import br.com.xxzidanilloxx.teammanagementapi.dto.PartnerRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.PartnerResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @PostMapping
    public ResponseEntity<PartnerResponseDTO> createPartner(@RequestBody PartnerRequestDTO data) {
        PartnerResponseDTO result = partnerService.createPartner(data);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<PartnerResponseDTO>> getAllPartners() {
        List<PartnerResponseDTO> result = partnerService.getAllPartners();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerResponseDTO> getPartnerById(@PathVariable Long id) {
        PartnerResponseDTO result = partnerService.getPartnerById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<PartnerResponseDTO>> getPartnerByName(@RequestParam String name) {
        List<PartnerResponseDTO> result = partnerService.getPartnerByName(name);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartnerResponseDTO> updatePartner(@PathVariable Long id,
                                                            @RequestBody PartnerRequestDTO data) {
        PartnerResponseDTO result = partnerService.updatePartner(id, data);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }
}

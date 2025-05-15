package br.com.xxzidanilloxx.teammanagementapi.service;

import br.com.xxzidanilloxx.teammanagementapi.dto.PartnerRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.PartnerResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.entity.Partner;
import br.com.xxzidanilloxx.teammanagementapi.mapper.PartnerMapper;
import br.com.xxzidanilloxx.teammanagementapi.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;

    @Transactional
    public PartnerResponseDTO createPartner(PartnerRequestDTO data) {
        duplicatePartnerName(data.name());
        Partner partner = Partner.toEntity(data);
        Partner result = partnerRepository.save(partner);
        return partnerMapper.toDto(result);
    }

    @Transactional(readOnly = true)
    public List<PartnerResponseDTO> getAllPartners(){
        List<Partner> result = partnerRepository.findAll();
        return result.stream().map(partnerMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public PartnerResponseDTO getPartnerById(Long id) {
        Partner result = partnerRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return partnerMapper.toDto(result);
    }

    @Transactional(readOnly = true)
    public List<PartnerResponseDTO> getPartnerByName(String name) {
        List<Partner> partners = partnerRepository.searchByName(name);

        if(partners.isEmpty()) {
            throw new NoSuchElementException();
        }

        return partners.stream().map(partnerMapper::toDto).toList();
    }

    @Transactional
    public PartnerResponseDTO updatePartner(Long id, PartnerRequestDTO data) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        duplicatePartnerNameForUpdate(data.name(), id);
        partner.setName(data.name());
        partner.setEmail(data.email());
        partner.setLocation(data.location());
        Partner result = partnerRepository.save(partner);
        return partnerMapper.toDto(result);
    }

    @Transactional
    public void deletePartner(Long id) {
        Partner result = partnerRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        partnerRepository.delete(result);
    }

    public void duplicatePartnerName(String name) {
        boolean partnerExists = partnerRepository.existsByName(name);

        if (partnerExists) {
            throw new IllegalArgumentException();
        }
    }

    private void duplicatePartnerNameForUpdate(String name, Long id) {
        boolean partnerExists = partnerRepository.existsByNameAndIdNot(name, id);
        if (partnerExists) {
            throw new IllegalArgumentException();
        }
    }
}

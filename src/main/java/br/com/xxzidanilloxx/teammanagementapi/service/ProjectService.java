package br.com.xxzidanilloxx.teammanagementapi.service;

import br.com.xxzidanilloxx.teammanagementapi.dto.ProjectRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.ProjectResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.entity.Partner;
import br.com.xxzidanilloxx.teammanagementapi.entity.Project;
import br.com.xxzidanilloxx.teammanagementapi.mapper.ProjectMapper;
import br.com.xxzidanilloxx.teammanagementapi.repository.PartnerRepository;
import br.com.xxzidanilloxx.teammanagementapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final PartnerRepository partnerRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectResponseDTO createProject(ProjectRequestDTO data) {
        Partner partner = partnerRepository.findById(data.partnerId())
                .orElseThrow(NoSuchElementException::new);
        duplicateProjectName(data.name());
        Project project = Project.toEntity(partner, data);
        Project result = projectRepository.save(project);
        return projectMapper.toDto(result);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getAllProjects(){
        List<Project> result = projectRepository.findAll();
        return result.stream().map(projectMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponseDTO getProjectById(Long id) {
        Project result = projectRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return projectMapper.toDto(result);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectByName(String name){
        List<Project> projects = projectRepository.searchByName(name);

        if(projects.isEmpty()) {
            throw new NoSuchElementException();
        }

        return projects.stream().map(projectMapper::toDto).toList();
    }

    @Transactional
    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO data) {
        Project project = projectRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        Partner partner = partnerRepository.findById(data.partnerId())
                .orElseThrow(NoSuchElementException::new);

        duplicateProjectNameForUpdate(data.name(), id);
        project.setPartner(partner);
        project.setName(data.name());
        project.setDescription(data.description());

        Project result = projectRepository.save(project);
        return projectMapper.toDto(result);
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        projectRepository.delete(project);
    }

    public void duplicateProjectName(String name) {
        boolean projectExists = projectRepository.existsByName(name);

        if (projectExists) {
            throw new IllegalArgumentException();
        }
    }

    private void duplicateProjectNameForUpdate(String name, Long id) {
        boolean projectExists = projectRepository.existsByNameAndIdNot(name, id);
        if (projectExists) {
            throw new IllegalArgumentException();
        }
    }
}

package br.com.xxzidanilloxx.teammanagementapi.service;

import br.com.xxzidanilloxx.teammanagementapi.dto.ProjectIdentifierRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.TeamRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.TeamResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.entity.Project;
import br.com.xxzidanilloxx.teammanagementapi.entity.Team;
import br.com.xxzidanilloxx.teammanagementapi.mapper.TeamMapper;
import br.com.xxzidanilloxx.teammanagementapi.repository.ProjectRepository;
import br.com.xxzidanilloxx.teammanagementapi.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final TeamMapper teamMapper;
    
    @Transactional
    public TeamResponseDTO createTeam(TeamRequestDTO data) {
        duplicateTeamName(data.name());
        Team team = Team.toEntity(data);
        Team result = teamRepository.save(team);
        return teamMapper.toDto(result);
    }

    @Transactional
    public TeamResponseDTO addProjectToTeam(Long teamId, ProjectIdentifierRequestDTO data) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(NoSuchElementException::new);
        Project project = projectRepository.findById(data.projectId())
                .orElseThrow(NoSuchElementException::new);
        team.setProject(project);
        Team result = teamRepository.save(team);
        return teamMapper.toDto(result);
    }

    @Transactional(readOnly = true)
    public List<TeamResponseDTO> findAllTeams() {
        List<Team> result = teamRepository.findAll();
        return result.stream().map(teamMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public TeamResponseDTO findTeamById(Long id) {
        Team result = teamRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return teamMapper.toDto(result);
    }

    @Transactional(readOnly = true)
    public List<TeamResponseDTO> getTeamByName(String name) {
        List<Team> teams = teamRepository.searchByName(name);

        if(teams.isEmpty()) {
            throw new NoSuchElementException();
        }

        return teams.stream().map(teamMapper::toDto).toList();
    }

    @Transactional
    public TeamResponseDTO updateTeam(Long id, TeamRequestDTO data) {
        Team team = teamRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        duplicateTeamNameForUpdate(data.name(), id);
        team.setName(data.name());
        Team result = teamRepository.save(team);
        return teamMapper.toDto(result);
    }

    @Transactional
    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        if (!team.getMembers().isEmpty()) {
            throw new IllegalStateException();
        }
        teamRepository.delete(team);
    }

    public void duplicateTeamName(String name) {
        boolean teamExists = teamRepository.existsByName(name);

        if (teamExists) {
            throw new IllegalArgumentException();
        }
    }

    private void duplicateTeamNameForUpdate(String name, Long id) {
        boolean teamExists = teamRepository.existsByNameAndIdNot(name, id);
        if (teamExists) {
            throw new IllegalArgumentException();
        }
    }
}

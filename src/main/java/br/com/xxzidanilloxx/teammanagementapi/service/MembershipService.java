package br.com.xxzidanilloxx.teammanagementapi.service;

import br.com.xxzidanilloxx.teammanagementapi.dto.MembershipRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.MembershipResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.entity.Course;
import br.com.xxzidanilloxx.teammanagementapi.entity.Membership;
import br.com.xxzidanilloxx.teammanagementapi.entity.Student;
import br.com.xxzidanilloxx.teammanagementapi.entity.Team;
import br.com.xxzidanilloxx.teammanagementapi.enumeration.Role;
import br.com.xxzidanilloxx.teammanagementapi.mapper.MembershipMapper;
import br.com.xxzidanilloxx.teammanagementapi.repository.CourseRepository;
import br.com.xxzidanilloxx.teammanagementapi.repository.MembershipRepository;
import br.com.xxzidanilloxx.teammanagementapi.repository.StudentRepository;
import br.com.xxzidanilloxx.teammanagementapi.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final StudentRepository studentRepository;
    private final TeamRepository teamRepository;
    private final CourseRepository courseRepository;
    private final MembershipMapper membershipMapper;

    @Transactional
    public MembershipResponseDTO addMemberToTeam(MembershipRequestDTO data){
        Course course = courseRepository.findById(data.courseId())
                .orElseThrow(NoSuchElementException::new);

        Student student = studentRepository.findById(data.studentId())
                .orElseThrow(NoSuchElementException::new);

        Team team = teamRepository.findById(data.teamId())
                .orElseThrow(NoSuchElementException::new);

        checkUserAlreadyInTeam(data.studentId());
        checkCourseConsistency(data.teamId(), data.courseId());
        checkUniqueRoles(data.teamId(), data.role());

        Membership membership = Membership.toEntity(course, student, team, data);
        membershipRepository.save(membership);
        return membershipMapper.toDto(membership);
    }

    @Transactional(readOnly = true)
    public List<MembershipResponseDTO> findMemberByRole(Role role){
        List<Membership> membership = membershipRepository.findMemberByRole(role);
        return membership.stream().map(membershipMapper::toDto).toList();
    }

    @Transactional
    public void updateMemberRole(Long courseId, Long studentId, Long teamId, MembershipRequestDTO data){
        Membership membership = membershipRepository.findByCourseIdAndStudentIdAndTeamId(courseId, studentId, teamId)
                .orElseThrow(NoSuchElementException::new);
        checkUniqueRoles(data.teamId(), data.role());
        membership.setRole(data.role());
        membershipRepository.save(membership);
    }

    @Transactional
    public void removeMemberFromTeam(Long studentId, Long teamId){
        Membership membership = membershipRepository.findByStudentIdAndTeamId(studentId, teamId)
                .orElseThrow(NoSuchElementException::new);

        membershipRepository.delete(membership);
    }

    @Transactional
    public void removeAllMembersFromTeam(Long teamId){
        teamRepository.findById(teamId)
                .orElseThrow(NoSuchElementException::new);
        membershipRepository.deleteByTeamId(teamId);
    }

    private void checkUserAlreadyInTeam(Long studentId){
        if(membershipRepository.existsByStudentId(studentId)){
            throw new IllegalStateException();
        }
    }

    private void checkCourseConsistency(Long teamId, Long courseId) {
        List<Membership> members = membershipRepository.findByTeamId(teamId);
        if (!members.isEmpty()) {
            boolean allSameCourse = members.stream()
                    .allMatch(member -> member.getCourse().getId().equals(courseId));

            if (!allSameCourse) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkUniqueRoles(Long teamId, Role role) {
        if (role == Role.SCRUM_MASTER || role == Role.PRODUCT_OWNER) {
            boolean roleExists = membershipRepository.existsByTeamIdAndRole(teamId, role);
            if (roleExists) {
                throw new IllegalArgumentException();
            }
        }
    }
}

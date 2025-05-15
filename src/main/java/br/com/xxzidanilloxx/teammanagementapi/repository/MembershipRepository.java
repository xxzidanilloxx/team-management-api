package br.com.xxzidanilloxx.teammanagementapi.repository;

import br.com.xxzidanilloxx.teammanagementapi.entity.Membership;
import br.com.xxzidanilloxx.teammanagementapi.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    boolean existsByStudentId(Long studentId);
    boolean existsByTeamIdAndRole(Long teamId, Role role);
    Optional<Membership> findByCourseIdAndStudentIdAndTeamId(Long courseId, Long studentId, Long teamId);
    Optional<Membership> findByStudentIdAndTeamId(Long studentId, Long teamId);
    List<Membership> findMemberByRole(Role role);
    List<Membership> findByTeamId(Long teamId);

    @Modifying
    void deleteByTeamId(Long teamId);
}

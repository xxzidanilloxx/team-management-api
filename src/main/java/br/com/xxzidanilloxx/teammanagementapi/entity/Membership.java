package br.com.xxzidanilloxx.teammanagementapi.entity;

import br.com.xxzidanilloxx.teammanagementapi.dto.MembershipRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.enumeration.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_membership")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_role", nullable = false)
    private Role role;

    public static Membership toEntity(Course course, Student student, Team team, MembershipRequestDTO data){
        Membership membership = new Membership();
        membership.setCourse(course);
        membership.setStudent(student);
        membership.setTeam(team);
        membership.setRole(data.role());
        return membership;
    }
}

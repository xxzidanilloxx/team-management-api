package br.com.xxzidanilloxx.teammanagementapi.entity;

import br.com.xxzidanilloxx.teammanagementapi.enumeration.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_first_name", nullable = false)
    private String firstName;

    @Column(name = "student_last_name", nullable = false)
    private String lastName;

    @Column(name = "student_cpf", unique = true, nullable = false)
    private String cpf;

    @Column(name = "student_email", unique = true, nullable = false)
    private String email;

    @Column(name = "student_birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_gender", nullable = false)
    private Gender gender;

    @Column(name = "student_ra", nullable = false, unique = true)
    private String ra;

    @Column(name = "student_status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}

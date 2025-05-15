package br.com.xxzidanilloxx.teammanagementapi.repository;

import br.com.xxzidanilloxx.teammanagementapi.entity.Student;
import br.com.xxzidanilloxx.teammanagementapi.enumeration.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

    @Query("SELECT s FROM Student s " +
            "WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> searchByName(@Param("name") String name);

    Optional<Student> findByCpf(String cpf);
    Optional<Student> findByEmail(String email);
    List<Student> findByGender(Gender gender);
    List<Student> findByStatus(boolean status);
}

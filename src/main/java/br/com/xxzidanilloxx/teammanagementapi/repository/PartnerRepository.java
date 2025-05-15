package br.com.xxzidanilloxx.teammanagementapi.repository;

import br.com.xxzidanilloxx.teammanagementapi.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);

    @Query("SELECT p FROM Partner p " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Partner> searchByName(@Param("name") String name);
}

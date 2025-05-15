package br.com.xxzidanilloxx.teammanagementapi.entity;

import br.com.xxzidanilloxx.teammanagementapi.dto.ProjectRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "project_name", nullable = false)
    private String name;

    @Column(name = "project_description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Team> teams = new ArrayList<>();

    public static Project toEntity(Partner partner, ProjectRequestDTO data){
        Project project = new Project();
        project.setPartner(partner);
        project.setName(data.name());
        project.setDescription(data.description());
        return project;
    }
}

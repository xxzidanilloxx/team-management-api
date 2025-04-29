package br.com.xxzidanilloxx.teammanagementapi.entity;

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
@Table(name = "tb_partner")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "partner_name", nullable = false)
    private String name;

    @Column(name = "partner_email", nullable = false)
    private String email;

    @Column(name = "partner_location", nullable = false)
    private String location;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Project> projects = new ArrayList<>();
}

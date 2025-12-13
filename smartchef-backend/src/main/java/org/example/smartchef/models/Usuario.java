package org.example.smartchef.models;


import jakarta.persistence.*;
import lombok.*;

import java.security.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"favoritos"})
@EqualsAndHashCode(exclude = {"favoritos"})
@Entity
@Table(name = "usuario", catalog = "smartchef_db", schema = "public")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "pais")
    private String pais;

    @Column(name = "codigo_postal")
    private String codigo_postal;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fecha_registro")
    private LocalDate fecha_registro;


    @ManyToOne
    @JoinColumn(name = "id_foto")
    private Foto foto;


    @OneToMany(mappedBy = "usuario_subidor_id")
    private Set<Foto> fotoUsuario;

    @OneToMany(mappedBy = "usuario_creador_id")
    private Set<Receta> receta;

    @OneToMany(mappedBy = "usuario")
    private Set<Favorito> favoritos = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "usuario_preferencia",
                joinColumns = @JoinColumn(name = "id_usuario"),
                inverseJoinColumns = @JoinColumn(name = "id_preferencia"))
    private Set<Preferencia> preferencias = new HashSet<>();




}

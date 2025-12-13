package org.example.smartchef.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "historial", catalog = "smartchef_db", schema = "public")

public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fecha_realizacion")
    private LocalDateTime fecha_realizacion;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "calificacion")
    private Double calificacion;


    @Column(name = "comentario")
    private String comentario;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario id_usuario;

    @ManyToOne
    @JoinColumn(name = "id_receta")
    private Receta id_receta;

}

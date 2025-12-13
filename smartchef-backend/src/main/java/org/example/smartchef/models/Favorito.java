package org.example.smartchef.models;


import jakarta.persistence.*;
import lombok.*;

import java.security.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "favorito", catalog = "smartchef_db", schema = "public")
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fecha_guardado")
    private LocalDate fecha_guardado;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_receta")
    private Receta receta;
}

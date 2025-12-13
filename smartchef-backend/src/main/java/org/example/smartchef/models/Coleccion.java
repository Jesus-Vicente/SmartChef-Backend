package org.example.smartchef.models;


import jakarta.persistence.*;
import lombok.*;

import java.security.Timestamp;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "coleccion", catalog = "smartchef_db", schema = "public")

public class Coleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre_coleccion")
    private String nombreColeccion;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_creacion")
    private Timestamp fechaCreacion;

    //Traemos el ID de USUARIO
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario id_usuario;

    //Enviamos el ID de COLECCION a la tabla 'coleccion_receta'
    @OneToMany(mappedBy = "id_coleccion")
    private Set<ColeccionReceta> coleccion_receta;
}

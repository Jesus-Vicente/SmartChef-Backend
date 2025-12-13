package org.example.smartchef.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "ingrediente", catalog = "smartchef_db", schema = "public")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "unidad_medida")
    private String unidad_medida;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "coste")
    private Double coste;

    @Column(name = "disponible")
    private Boolean disponible;

    @ManyToOne
    @JoinColumn(name = "id_foto")
    private Foto id_foto;   

}

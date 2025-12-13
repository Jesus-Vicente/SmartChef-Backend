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
@Table(name = "receta_ingrediente", catalog = "smartchef_db", schema = "public")

public class RecetaIngrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cantidad", columnDefinition = "double precision")
    private Double cantidad;

    @Column(name = "unidad")
    private String unidad;


    @ManyToOne
    @JoinColumn(name = "id_receta")
    private Receta id_receta;

    @ManyToOne
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente id_ingrediente;
}

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
@Table(name = "coleccion_receta", catalog = "smartchef_db", schema = "public")

public class ColeccionReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //Traemos el ID de COLECCION
    @ManyToOne
    @JoinColumn(name = "id_coleccion")
    private Coleccion id_coleccion;

    //Traemos el ID de RECETA
    @ManyToOne
    @JoinColumn(name = "id_receta")
    private Receta id_receta;
}

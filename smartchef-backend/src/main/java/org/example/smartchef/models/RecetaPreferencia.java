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
@Table(name = "receta_preferencia", catalog = "smartchef_db", schema = "public")

public class RecetaPreferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_preferencia")
    private Preferencia id_preferencia;

    @ManyToOne
    @JoinColumn(name = "id_receta")
    private Receta id_receta;
}

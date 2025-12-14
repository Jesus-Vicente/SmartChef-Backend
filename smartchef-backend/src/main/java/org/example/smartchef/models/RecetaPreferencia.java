package org.example.smartchef.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"idPreferencia", "idReceta"})
@Entity
@Table(name = "receta_preferencia", catalog = "smartchef_db", schema = "public")

public class RecetaPreferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_preferencia")
    private Preferencia idPreferencia;

    @ManyToOne
    @JoinColumn(name = "id_receta")
    private Receta idReceta;


    public RecetaPreferencia(Receta recetaGuardada, Preferencia pref) {
        this.idReceta = recetaGuardada;
        this.idPreferencia = pref;
    }
}

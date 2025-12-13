package org.example.smartchef.models;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "foto", catalog = "smartchef_db", schema = "public")
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "url")
    private String url;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_subida")
    private LocalDateTime fecha_subida;

    @Column(name = "tipo_foto")
    @Enumerated(EnumType.ORDINAL)
    private TipoFoto tipo_foto;


    @ManyToOne
    @JoinColumn(name = "usuario_subidor_id")
    private Usuario usuario_subidor_id;


    @OneToMany(mappedBy = "id_foto")
    private Set<Receta> receta;

}

package org.example.smartchef.models;


import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"id_foto", "usuario_creador_id",
        "coleccion_receta", "receta_ingrediente",
        "usuarioQueFavorito", "recetaPreferencias",
        "ingredientes"})
@Entity
@Table(name = "receta", catalog = "smartchef_db", schema = "public")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "instrucciones")
    private String instrucciones;

    @Column(name = "tiempo_preparacion")
    private Integer tiempo_preparacion;

    @Column(name = "dificultad")
    @Enumerated(EnumType.ORDINAL)
    private Dificultad dificultad;

    @Column(name = "costo_estimado")
    private Double costo_estimado;

    @Column(name = "porciones")
    private Integer porciones;

    @Column(name = "valoracion_promedio")
    private Double valoracion_promedio;

    @Column(name = "fecha_creacion")
    private LocalDate fecha_creacion;

    @Column(name = "activa")
    private Boolean activa;

    //Traemos el ID de FOTO
    @ManyToOne
    @JoinColumn(name = "id_foto")
    private Foto id_foto;

    //Traemos el ID de USUARIO
    @ManyToOne
    @JoinColumn(name = "usuario_creador_id")
    private Usuario usuario_creador_id;

    //Envaimos el ID de RECETA a la tabla 'carrito_ingrediente'
    @OneToMany(mappedBy = "id_receta")
    private Set<ColeccionReceta> coleccion_receta;

    @OneToMany(mappedBy = "id_receta")
    private Set<RecetaIngrediente> receta_ingrediente;

    @OneToMany(mappedBy = "receta")
    private Set<Favorito> usuarioQueFavorito = new HashSet<>();

    @OneToMany(mappedBy = "idReceta")
    private Set<RecetaPreferencia> recetaPreferencias = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "receta_ingrediente",
                joinColumns = @JoinColumn(name = "id_receta"),
                inverseJoinColumns = @JoinColumn(name = "id_ingrediente"))
    private Set<Ingrediente> ingredientes = new HashSet<>();
}

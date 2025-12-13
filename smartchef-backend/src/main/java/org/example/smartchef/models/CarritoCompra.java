package org.example.smartchef.models;

import jakarta.persistence.*;
import lombok.*;

import java.security.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"carritoIngrediente", "id_usuario"})
@EqualsAndHashCode(exclude = {"carritoIngrediente", "id_usuario"})
@Entity
@Table(name = "carritocompra", catalog = "smartchef_db", schema = "public")

public class CarritoCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fecha_creacion")
    private LocalDate fecha_creacion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "total_estimado")
    private Double total_estimado;

    @Column(name = "direccion_envio")
    private String direccion_envio;

    @Column(name = "notas")
    private String notas;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario id_usuario;

    @OneToMany(mappedBy = "id_carrito")
    private Set<CarritoIngrediente> carritoIngrediente;

}

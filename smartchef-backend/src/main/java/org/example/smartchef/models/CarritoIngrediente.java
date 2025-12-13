package org.example.smartchef.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "carrito_ingrediente", catalog = "smartchef_db", schema = "public")

public class CarritoIngrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cantidad", columnDefinition = "double precision")
    private Double cantidad;

    @Column(name = "unidad")
    private String unidad;

    @Column(name = "comprado")
    private Boolean comprado;

    @ManyToOne
    @JoinColumn(name = "id_carrito")
    @JsonIgnore
    private CarritoCompra id_carrito;

    @ManyToOne
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente id_ingrediente;
}

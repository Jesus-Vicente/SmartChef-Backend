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
@Table(name = "preferencia_usuario", catalog = "smartchef_db", schema = "public")
public class Preferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre_preferencia")
    private String nombrePreferencia;

    @Column(name = "descripcion")
    private String descripcion;

}


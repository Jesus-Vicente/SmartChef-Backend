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
@Table(name = "usuario_preferencia", catalog = "smartchef_db", schema = "public")
public class UsuarioPreferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario id_usuario;

    @ManyToOne
    @JoinColumn(name = "id_preferencia")
    private Preferencia id_preferencia;
}

package org.example.smartchef.services;

import lombok.AllArgsConstructor;
import org.example.smartchef.converters.UsuarioMapper;
import org.example.smartchef.dto.CrearUsuarioDTO;
import org.example.smartchef.dto.UsuarioDTO;
import org.example.smartchef.models.Preferencia;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.repositories.IPreferenciaRepository;
import org.example.smartchef.repositories.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UsuarioService {

    private IUsuarioRepository repository;

    private UsuarioMapper mapper;

    private IPreferenciaRepository preferenciaRepository;

    public List<UsuarioDTO> obtenerUsuarios(){
        return mapper.convertirADTO(repository.findAll());
    }

    public void crearUsuario(CrearUsuarioDTO dto){
        repository.save(mapper.convertirAEntityCrearUsuario(dto));
    }

    public List<UsuarioDTO> obtenerUsuarioPreferencias(){
        return mapper.convertirADTO(repository.findAll());
    }

    public void crearUsuarioConPreferencias(CrearUsuarioDTO dto){
        Usuario usuario = mapper.convertirAEntityCrearUsuario(dto);
        Set<Integer> idsPreferencias = dto.getPreferenciasID();

        List<Preferencia> preferencias = preferenciaRepository.findAllById(idsPreferencias);
        usuario.setPreferencias(new HashSet<>(preferencias));

        repository.save(usuario);
    }

    public void modificarUsuario(Integer id, CrearUsuarioDTO dto){

        Optional<Usuario> usuario = repository.findById(id);

        if(usuario.isPresent()) {
            Usuario usuarioAModificar = usuario.get();
            usuarioAModificar.setNombre(dto.getNombre());
            usuarioAModificar.setEmail(dto.getEmail());
            usuarioAModificar.setPassword(dto.getPassword());
            usuarioAModificar.setDireccion(dto.getDireccion());
            usuarioAModificar.setCiudad(dto.getCiudad());
            usuarioAModificar.setPais(dto.getPais());
            usuarioAModificar.setCodigo_postal(dto.getCodigo_postal());
            usuarioAModificar.setTelefono(dto.getTelefono());
            repository.save(usuarioAModificar);
        }

    }

    public void eliminarUsuario(Integer id){
        repository.deleteById(id);
    }


}

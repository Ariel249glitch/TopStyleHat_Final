package topstylehut.bloquecliente.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import topstylehut.bloquecliente.DTO.ComunaDTO;
import topstylehut.bloquecliente.Model.Comuna;
import topstylehut.bloquecliente.Repository.ComunaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    //Mostrar todas las comunas 
    public List<ComunaDTO> MostrarTodas(){
        List<ComunaDTO> comunas = new ArrayList<>();
        for (Comuna comuna : comunaRepository.findAll()) {
            comunas.add(convertirADTO(comuna));
        }
        return comunas;
    }

    //Convertir DTO
    private ComunaDTO convertirADTO (Comuna comuna){
        ComunaDTO comunaDTO = new ComunaDTO();
        comunaDTO.setId(comuna.getId());
        comunaDTO.setNombre(comuna.getNombre());
        return comunaDTO;
    }

    //buscar por id
    public ComunaDTO buscarPorId(Integer id){
        Comuna comuna = comunaRepository.findById(id).orElseThrow(() -> new RuntimeException("Comuna no encontrada"));
        return convertirADTO(comuna);
    }

    //Guardar Comuna
    public ComunaDTO guardarComuna(Comuna nuevoComuna){
        Comuna comunaGuardada = comunaRepository.save(nuevoComuna);
        return convertirADTO(comunaGuardada);
    }

    //Actualizar Comuna
    public Comuna actualizarComu(Integer id, Comuna comuna){
        Comuna Comu = comunaRepository.findById(id).orElseThrow(() ->  new RuntimeException("La comuna no existe"));
        if (comuna.getNombre() != null) {
            Comu.setNombre(comuna.getNombre());
        }
        return comunaRepository.save(Comu);
    }

    //Eliminar
    public String EliminarComuna(Integer id){
        Comuna comuna = comunaRepository.findById(id).orElseThrow(() -> new RuntimeException("La comuna no existe"));
        comunaRepository.delete(comuna);
        return "Comuna Eliminada";
    }
    
}
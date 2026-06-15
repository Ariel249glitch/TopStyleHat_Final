package topstylehut.bloquecliente.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import topstylehut.bloquecliente.DTO.RegionDTO;
import topstylehut.bloquecliente.Model.Region;
import topstylehut.bloquecliente.Repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class Regionservice {
    
    @Autowired
    private RegionRepository regionRepository;

    //Mostrar todos los tipos 
    public List<RegionDTO> MostrarTodas(){
        List<RegionDTO> Regions = new ArrayList<>();
        for (Region region : regionRepository.findAll()) {
            Regions.add(convertirADTO(region));
        }
        return Regions;
    }

    //Convertir DTO
    private RegionDTO convertirADTO (Region region){
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(region.getId());
        regionDTO.setNombre(region.getNombre());
        return regionDTO;
    }

    //buscar por id
    public RegionDTO buscarPorId(Integer id){
        Region region = regionRepository.findById(id).orElseThrow(() -> new RuntimeException("Region no encontrada"));
        return convertirADTO(region);
    }

    //Guardar Region
    public RegionDTO guardarRegion(Region nuevoRegion){
        Region regionGuardada = regionRepository.save(nuevoRegion);
        return convertirADTO(regionGuardada);
    }

    //Actualizar Region
    public Region actualizarRegion(Integer id, Region region){
        Region Regio = regionRepository.findById(id).orElseThrow(() ->  new RuntimeException("La region no existe"));
        if (region.getNombre() != null) {
            Regio.setNombre(region.getNombre());
        }
        return regionRepository.save(Regio);
    }

    //Eliminar Region
    public String EliminarRegion(Integer id){
        Region region = regionRepository.findById(id).orElseThrow(() -> new RuntimeException("La region no existe"));
        regionRepository.delete(region);
        return "Region Eliminada";
    }
}

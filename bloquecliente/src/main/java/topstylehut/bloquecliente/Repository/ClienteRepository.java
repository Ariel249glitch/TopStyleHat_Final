package topstylehut.bloquecliente.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import topstylehut.bloquecliente.Model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("""
        SELECT c
        FROM Cliente c
        WHERE c.comuna.nombre = :comuna
        """)
    List<Cliente> buscarPorComuna(@Param("comuna") String comuna);

    @Query("""
        SELECT c
        FROM Cliente c
        WHERE c.comuna.region.nombre = :region
        """)
    List<Cliente> buscarPorRegion(@Param("region") String region);
}

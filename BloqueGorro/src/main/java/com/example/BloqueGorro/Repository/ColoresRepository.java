package com.example.BloqueGorro.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BloqueGorro.Model.Colores;

public interface ColoresRepository extends JpaRepository<Colores, Integer> {

    List<Colores> findByGorroId(Integer gorroId);

    List<Colores> findByColorId(Integer colorId);
}

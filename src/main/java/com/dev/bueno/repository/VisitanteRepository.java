package com.dev.bueno.repository;

import com.dev.bueno.entity.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VisitanteRepository extends JpaRepository<Visitante, Long> , JpaSpecificationExecutor<Visitante> {
    Visitante findByRg(String rg);
}

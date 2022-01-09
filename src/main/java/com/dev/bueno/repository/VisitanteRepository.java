package com.dev.bueno.repository;

import com.dev.bueno.entity.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitanteRepository extends JpaRepository<Visitante, Long> {
    Visitante findByRg(String rg);
}

package com.dev.bueno.repository;

import com.dev.bueno.entity.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VisitaRepository extends JpaRepository<Visita, Long> , JpaSpecificationExecutor<Visita> {
}

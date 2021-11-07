package com.dev.bueno.repository;

import com.dev.bueno.entity.Morador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoradorRepository extends JpaRepository<Morador, Long> {
}

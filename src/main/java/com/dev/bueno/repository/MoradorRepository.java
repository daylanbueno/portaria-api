package com.dev.bueno.repository;

import com.dev.bueno.entity.Morador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MoradorRepository extends JpaRepository<Morador, Long> {

    @Query(value = "select m from Morador m where UPPER(m.nome) like %:nome%")
    Page<Morador> findByNome(String nome, Pageable pageable);
}

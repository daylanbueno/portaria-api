package com.dev.bueno.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Visita {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Visita visita;
    @ManyToOne
    private Morador morador;
    private LocalDateTime dataHora;
}

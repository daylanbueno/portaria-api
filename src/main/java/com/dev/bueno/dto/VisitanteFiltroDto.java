package com.dev.bueno.dto;

import com.dev.bueno.entity.Visitante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitanteFiltroDto {
    private String nome;
    private String rg;

    public Specification<Visitante> toSpecification() {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nome != null) {
                Path<String> campoNome = root.<String>get("nome");
                Predicate predicateNome = builder.equal(campoNome, nome);
                predicates.add(predicateNome);
            }
            if (rg != null) {
                Path<String> campoRg = root.<String>get("rg");
                Predicate predicateNome = builder.equal(campoRg, rg);
                predicates.add(predicateNome);
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

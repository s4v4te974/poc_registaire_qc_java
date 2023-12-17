package com.registraire.step.processor;

import com.registraire.model.Entreprise;
import com.registraire.model.Nom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NomProcessor implements ItemProcessor<Nom, Nom> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Nom process(Nom item) throws Exception {
        log.info("Start nom process");
        List<Entreprise> entreprises = jdbcTemplate.queryForList("SELECT * FROM NOM", Entreprise.class);
        Map<String, Entreprise> entreprisesByNeq = entreprises.stream()
                .collect(Collectors.toMap(Entreprise::neq, Function.identity()));
        if (entreprisesByNeq.containsKey(item.neq())) {
            return item;
        }
        return null;
    }
}

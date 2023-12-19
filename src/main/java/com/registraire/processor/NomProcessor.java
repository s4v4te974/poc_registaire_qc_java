package com.registraire.processor;

import com.registraire.model.Entreprise;
import com.registraire.model.Nom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.registraire.utils.BatchUtils.REQUEST_NEQ;

@Component
@Slf4j
public class NomProcessor implements ItemProcessor<Nom, Nom> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Nom process(Nom item) {
        List<Entreprise> entrepriseList = jdbcTemplate.query(REQUEST_NEQ, new BeanPropertyRowMapper<>(Entreprise.class));
        Map<String, Entreprise> entreprisesByNeq = entrepriseList.stream()
                .collect(Collectors.toMap(
                        Entreprise::getNeq,
                        Function.identity(),
                        (existing, replacement) -> existing
                        ));
        if (entreprisesByNeq.containsKey(item.neq())) {
            return item;
        }
        return null;
    }
}

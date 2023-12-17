package com.registraire.step.processor;

import com.registraire.model.Entreprise;
import com.registraire.model.Etablissement;
import lombok.RequiredArgsConstructor;
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
public class EtablissementProcessor implements ItemProcessor<Etablissement, Etablissement> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Etablissement process(Etablissement item) throws Exception {
        List<Entreprise> entrepriseList = jdbcTemplate.query(REQUEST_NEQ, new BeanPropertyRowMapper<>(Entreprise.class));
        Map<String, Entreprise> entreprisesByNeq = entrepriseList.stream()
                .collect(Collectors.toMap(Entreprise::getNeq, Function.identity()));
        if (entreprisesByNeq.containsKey(item.neq())) {
            return item;
        }
        return null;
    }
}

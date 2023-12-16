package com.registraire.step.processor;

import com.registraire.model.Entreprise;
import com.registraire.model.Etablissement;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.registraire.utils.BatchUtils.REQUEST_NEQ;

@Component
public class EtablissementProcessor implements ItemProcessor<Etablissement, Etablissement> {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Etablissement process(Etablissement item) throws Exception {
        List<Entreprise> entrepriseList = jdbcTemplate.queryForList(REQUEST_NEQ, Entreprise.class);
        Map<String, Entreprise> entreprisesByNeq = entrepriseList.stream()
                .collect(Collectors.toMap(Entreprise::neq, Function.identity()));
        if(entreprisesByNeq.containsKey(item.neq())){
            return item;
        }
        return null;
    }
}

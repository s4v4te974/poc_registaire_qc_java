package com.registraire.step.processor;

import com.registraire.model.DomaineValeur;
import com.registraire.model.Entreprise;
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

import static com.registraire.utils.BatchUtils.REQUEST_NEQ;

@Component
@Slf4j
public class DomaineValeurProcessor implements ItemProcessor<DomaineValeur, DomaineValeur> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public DomaineValeur process(DomaineValeur item) throws Exception {
        log.info("Start FusionScission process");
        List<Entreprise> entrepriseList = jdbcTemplate.queryForList(REQUEST_NEQ, Entreprise.class);
        Map<String, Entreprise> entrepriseByValue = entrepriseList.stream()
                .collect(Collectors.toMap(Entreprise::codActEconCae, Function.identity()));
        if(entrepriseByValue.containsKey(item.codDomVal())){
            return item;
        }
        return null;
    }
}

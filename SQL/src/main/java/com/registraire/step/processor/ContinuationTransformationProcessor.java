package com.registraire.step.processor;

import com.registraire.model.ContinuationTransformation;
import com.registraire.model.Entreprise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.registraire.utils.BatchUtils.REQUEST_NEQ;

@Component
@Slf4j
@RequiredArgsConstructor
public class ContinuationTransformationProcessor
        implements ItemProcessor<ContinuationTransformation, ContinuationTransformation> {

    private JdbcTemplate jdbcTemplate;

    @Override
    public ContinuationTransformation process(ContinuationTransformation item) throws Exception {
        log.info("Start ContinuationTransformation process");
        List<Entreprise> entrepriseList = jdbcTemplate.queryForList(REQUEST_NEQ, Entreprise.class);
        Map<String, Entreprise> entreprisesByNeq = entrepriseList.stream()
                .collect(Collectors.toMap(Entreprise::neq, Function.identity()));
        if (entreprisesByNeq.containsKey(item.neq())) {
            return item;
        }
        return null;
    }
}
package com.democom.news.config.jobConfig;


import com.democom.news.entity.Subscription;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class ItemWriterConfiguration implements ItemWriter<Subscription> {

    private static final String UPDATE_SUB = "UPDATE TBL_SUBSCRIPTION SET STATE_SUBSCRIPTION = 0 WHERE STATE_SUBSCRIPTION = 1 AND END_DATE <= CURRENT_DATE";

    private JdbcTemplate jdbcTemplate;

    public ItemWriterConfiguration(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void write(List<? extends Subscription> list) throws Exception {
        jdbcTemplate.update(UPDATE_SUB);
    }
}

package com.democom.news.config.jobConfig;

import com.democom.news.entity.Subscription;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class ItemReaderConfiguration extends JdbcCursorItemReader<Subscription> implements ItemReader<Subscription> {

    public ItemReaderConfiguration(@Autowired DataSource newsDB) {
        setDataSource(newsDB);
        setSql("select ID, END_DATE, STATE_SUBSCRIPTION from TBL_SUBSCRIPTION where STATE_SUBSCRIPTION = 1 AND END_DATE <= CURRENT_DATE");
        setFetchSize(100);
        setRowMapper(new SubRowMapper());

    }

    public class SubRowMapper implements RowMapper<Subscription>  {
        @Override
        public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
            Subscription subscription = new Subscription();
            subscription.setId(rs.getLong("ID"));
            subscription.setEnd_date(rs.getDate("END_DATE"));
            subscription.setStateSubscription(rs.getBoolean("STATE_SUBSCRIPTION"));
            return subscription;
        }
    }
}

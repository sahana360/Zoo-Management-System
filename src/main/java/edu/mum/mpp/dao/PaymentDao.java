package edu.mum.mpp.dao;

import edu.mum.mpp.model.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class PaymentDao extends AbstractDao<AbstractModel> {

    private SimpleJdbcCall sample;

    @Autowired
    @Override

    public void setDataSource(@Qualifier(value = "DS") DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);


    }
}

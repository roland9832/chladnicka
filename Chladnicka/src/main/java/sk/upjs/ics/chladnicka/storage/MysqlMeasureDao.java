package sk.upjs.ics.chladnicka.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MysqlMeasureDao implements MeasureDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlMeasureDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Measure> getAll() {
		String sql = "SELECT measure_id, unit  FROM measure";
		List<Measure> measure = jdbcTemplate.query(sql, new MeasureRowMapper());
		return measure;
	}

	public Measure getByID(long id) {
		String sql = "SELECT measure_id, unit  FROM measure WHERE measure_id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new MeasureRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchElementException("Measure with id " + id + " not in DB");

		}
	}

	private class MeasureRowMapper implements RowMapper<Measure> {

		public Measure mapRow(ResultSet rs, int rowNum) throws SQLException {
			Measure measure = new Measure();
			measure.setId(rs.getLong("measure_id"));
			measure.setUnit(rs.getString("unit"));
			return measure;
		}
	};

}

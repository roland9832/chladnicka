package sk.upjs.ics.chladnicka.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MysqlDietDao implements DietDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlDietDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Diet> getAll() {
		String sql = "SELECT diet_id, diet_name FROM diet";
		List<Diet> diet = jdbcTemplate.query(sql, new DietRowMapper());
		return diet;

	}

//	@Override
//	public Diet getByName(String name) {
//		String sql = "SELECT diet_id, diet_name FROM diet WHERE diet_name  =?";
//		try {
//			return jdbcTemplate.queryForObject(sql, new DietRowMapper(), name);
//		} catch (EmptyResultDataAccessException e) {
//			throw new NoSuchElementException("");
//		}
//	}

	public Diet getByID(long id) {
		String sql = "SELECT diet_id, diet_name FROM diet WHERE diet_id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new DietRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchElementException("Allergie with id " + id + " not in DB");
		}
	}

	private class DietRowMapper implements RowMapper<Diet> {

		public Diet mapRow(ResultSet rs, int rowNum) throws SQLException {
			Diet diet = new Diet();
			diet.setId(rs.getLong("DIET_ID"));
			diet.setName(rs.getString("DIET_NAME"));
			return diet;
		}
	}

}

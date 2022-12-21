package sk.upjs.ics.chladnicka.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MysqlAllergieDao implements AllergieDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlAllergieDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Allergie> getAll() {
		String sql = "SELECT allergie_id, category, information FROM allergie";
		List<Allergie> allergie = jdbcTemplate.query(sql, new AllergieRowMapper());
		return allergie;
	}

	public Allergie getByID(long id) {
		String sql = "SELECT allergie_id, category, information FROM allergie WHERE allergie_id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new AllergieRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchElementException("Allergie with id " + id + " not in DB");
		}
	}

	private class AllergieRowMapper implements RowMapper<Allergie> {

		public Allergie mapRow(ResultSet rs, int rowNum) throws SQLException {
			Allergie allergie = new Allergie();
			allergie.setId(rs.getLong("allergie_id"));
			allergie.setCategory(rs.getString("category"));
			allergie.setInformation(rs.getString("information"));
			return allergie;
		}
	};

}

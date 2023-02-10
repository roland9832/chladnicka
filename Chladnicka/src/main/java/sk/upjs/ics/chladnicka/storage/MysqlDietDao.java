package sk.upjs.ics.chladnicka.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

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

	public Diet save(Diet diet) {
		if (diet == null) {
			throw new NullPointerException("Cannot save null Favourite");
		}
		if (diet.getId() == null) {
			SimpleJdbcInsert saveInsert = new SimpleJdbcInsert(jdbcTemplate);
			saveInsert.withTableName("diet");
			saveInsert.usingColumns("diet_name");
			saveInsert.usingGeneratedKeyColumns("diet_id");
			Map<String, Object> values = new HashMap<>();
			values.put("diet_name", diet.getName());
			long id = saveInsert.executeAndReturnKey(values).longValue();
			return new Diet(id, diet.getName());

		} else {
			String sql = "UPDATE diet SET diet_name= ?" + " WHERE id= ? ";
			int updated = jdbcTemplate.update(sql, diet.getName(), diet.getId());
			if (updated == 1) {
				return diet;
			} else {
				throw new NoSuchElementException("No diet with id " + diet.getId() + " in DB");
			}
		}
	}

	@Override
	public boolean delete(long id) throws EntityUndeletableException {
		int changed;
		try {
			changed = jdbcTemplate.update("DELETE FROM diet WHERE diet_id = " + id);
		} catch (DataIntegrityViolationException e) {
			throw new EntityUndeletableException("Diet with id " + id + " is part of some recipe, cannot be deleted");
		}
		return changed == 1;
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

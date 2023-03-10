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

public class MysqlFavouriteDao implements FavouriteDao {
	private JdbcTemplate jdbcTemplate;

	public MysqlFavouriteDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Favourite getByID(long id) {
		String sql = "SELECT favourite_id, hodnotenie, recipe_recipe_id FROM favourite WHERE favourite_id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new FavouriteRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchElementException("Favourite with id " + id + " not in DB");
		}
	}
	

	@Override
	public List<Favourite> getAll() {
//		String sql = "SELECT hodnotenie, recipe_recipe_id FROM favourite";
//		return jdbcTemplate.query(sql, new RowMapper<Favourite>() {
//
//			@Override
//			public Favourite mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Favourite favourite = new Favourite();
//				favourite.setHodnotenie(rs.getInt("hodnotenie"));
//				long id = rs.getLong("recipe_recipe_id");
//				favourite.setId(id);
//				favourite.setRecipe(DaoFactory.INSTANCE.getRecipeDao().getByID(id));
//				return favourite;
//			}
//		});
		String sql = "SELECT favourite_id, hodnotenie, recipe_recipe_id FROM favourite";
		List<Favourite> favourite = jdbcTemplate.query(sql, new FavouriteRowMapper());
		return favourite;
	}

	@Override
	public Favourite getByRecipe(Recipe recipe) {
		String sql = "SELECT hodnotenie, recipe_recipe_id FROM favourite " + "WHERE recipe_recipe_id = "
				+ recipe.getId();
		try {
			return jdbcTemplate.queryForObject(sql, new RowMapper<Favourite>() {

				@Override
				public Favourite mapRow(ResultSet rs, int rowNum) throws SQLException {
					Favourite favourite = new Favourite();
					favourite.setId(rs.getLong("favourite_id"));
					favourite.setHodnotenie(rs.getInt("hodnotenie"));
					favourite.setRecipe(DaoFactory.INSTANCE.getRecipeDao().getByID(rs.getLong("recipe_recipe_id")));
					
					return favourite;
				}

			});
		} catch (Exception e) {
			throw new NoSuchElementException("Favourite with recipe " + recipe + " not in DB");
		}
	}

	public Favourite save(Favourite favourite) {
		if (favourite == null) {
			throw new NullPointerException("Cannot save null Favourite");
		}
		if (favourite.getRecipe() == null) {
			throw new NullPointerException("Cannot save null Recipe");
		}
		if(favourite.getId() == null) {
			SimpleJdbcInsert saveInsert = new SimpleJdbcInsert(jdbcTemplate);
			saveInsert.withTableName("favourite");
			saveInsert.usingColumns("hodnotenie", "recipe_recipe_id");
			saveInsert.usingGeneratedKeyColumns("favourite_id");
			Map<String, Object> values = new HashMap<>();
			values.put("hodnotenie", favourite.getHodnotenie());
			values.put("recipe_recipe_id", favourite.getRecipe().getId());
			long id = saveInsert.executeAndReturnKey(values).longValue();
			return new Favourite(id,favourite.getRecipe(),favourite.getHodnotenie());
		}
		else {
			String sql = "UPDATE favourite SET hodnotenie= ?, recipe_recipe_id= ?"
					+" WHERE favourite_id = ? ";
			int updated = jdbcTemplate.update(sql, favourite.getHodnotenie(), favourite.getRecipe().getId(), favourite.getId());
			if (updated == 1) {
				return favourite;
			} else {
				throw new NoSuchElementException("Ingredient with id: " + favourite.getId() + " not in DB.");
			}
		}
	
	}

	@Override
	public boolean delete(Recipe recipe) throws EntityUndeletableException {
		int wasDeleted;
		try {
			wasDeleted = jdbcTemplate.update("DELETE FROM favourite WHERE recipe_recipe_id = " + recipe.getId());
		} catch (DataIntegrityViolationException e) {
			throw new EntityUndeletableException("Recipe with id: " + recipe.getId()
					+ "Favourite is already in use");
		}

		return wasDeleted == 1;
	}
	
	private class FavouriteRowMapper implements RowMapper<Favourite> {

		public Favourite mapRow(ResultSet rs, int rowNum) throws SQLException {
			Favourite favourite = new Favourite();
			favourite.setId(rs.getLong("favourite_id"));
			favourite.setRecipe(DaoFactory.INSTANCE.getRecipeDao().getByID(rs.getLong("recipe_recipe_id")));
			favourite.setHodnotenie(rs.getInt("hodnotenie"));
			return favourite;
		}
	}

}

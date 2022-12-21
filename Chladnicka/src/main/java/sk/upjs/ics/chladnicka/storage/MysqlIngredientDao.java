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

public class MysqlIngredientDao implements IngredientDao {
	private JdbcTemplate jdbcTemplate;

	public MysqlIngredientDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Ingredient getByID(long id) {
		String sql = "SELECT ingredient_id, ingredient_name, quantity_fridge, allergie_allergie_id, measure_measure_id FROM INGREDIENT "
				+ "WHERE ingredient_id = " + id;

		try {
			return jdbcTemplate.queryForObject(sql, new IngredientRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchElementException("Allergie with id " + id + " not in DB");
		}

	};

	@Override
	public List<Ingredient> getAll() {
		String sql = "SELECT ingredient_id, ingredient_name, quantity_fridge, allergie_allergie_id, measure_measure_id FROM INGREDIENT";
		List<Ingredient> ingredient = jdbcTemplate.query(sql, new IngredientRowMapper());
		return ingredient;

	}
	
	@Override
	public Ingredient getByName(String name) {
		String sql = "SELECT ingredient_id, ingredient_name, quantity_fridge, allergie_allergie_id, measure_measure_id FROM INGREDIENT WHERE ingredient_name =?";
		try {
			return jdbcTemplate.queryForObject(sql, new IngredientRowMapper(), name);
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchElementException("Allergie with name " + name + " not in DB");
		}
	}

	@Override
	public List<Ingredient> getByAllergie(Allergie allergie) {
		String sql = "SELECT ingredient_id, ingredient_name, quantity_fridge,allergie_allergie_id, measure_measure_id FROM INGREDIENT "
				+ "LEFT JOIN allergie al ON allergie_allergie_id = al.allergie_id " + "WHERE allergie_allergie_id = "
				+ allergie.getId();
		return jdbcTemplate.query(sql, new RowMapper<Ingredient>() {
			@Override
			public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ingredient ingredient = new Ingredient();
				ingredient.setId(rs.getLong("ingredient_id"));
				ingredient.setName(rs.getString("ingredient_name"));
				ingredient.setQuantity_fridge(rs.getDouble("quantity_fridge"));
				ingredient.setAlergie(DaoFactory.INSTANCE.getAllergieDao().getByID(rs.getLong("allergie_allergie_id")));
				ingredient.setMeasure(DaoFactory.INSTANCE.getMeasureDao().getByID(rs.getLong("measure_measure_id")));
				return ingredient;
			}
		});
	}

	public List<Ingredient> getByMeasure(Measure measure) {
		String sql = "SELECT ingredient_id, ingredient_name, quantity_fridge,allergie_allergie_id, measure_measure_id FROM INGREDIENT "
				+ "LEFT JOIN measure me ON measure_measure_id = me.measure_id " + "WHERE measure_measure_id = "
				+ measure.getId();
		System.out.println(measure.getId());
		return jdbcTemplate.query(sql, new RowMapper<Ingredient>() {
			@Override
			public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ingredient ingredient = new Ingredient();
				ingredient.setId(rs.getLong("ingredient_id"));
				ingredient.setName(rs.getString("ingredient_name"));
				ingredient.setQuantity_fridge(rs.getDouble("quantity_fridge"));
				ingredient.setAlergie(DaoFactory.INSTANCE.getAllergieDao().getByID(rs.getLong("allergie_allergie_id")));
				ingredient.setMeasure(DaoFactory.INSTANCE.getMeasureDao().getByID(rs.getLong("measure_measure_id")));
				return ingredient;
			}
		});
	}

	public List<Ingredient> getByRecipe(Recipe recipe) {
		if (recipe == null || recipe.getId() == null) {
			throw new NullPointerException("Cannot extract id from subject");
		}
		String sql = "SELECT ingredient_id, ingredient_name, quantity_fridge, allergie_allergie_id, measure_measure_id FROM INGREDIENT "
				+ "LEFT JOIN recipe_has_ingredient rhi ON ingredient_id = rhi.ingredient_ingredient_id "
				+ "LEFT JOIN recipe re on re.recipe_id = rhi.recipe_recipe_id " + "WHERE re.recipe_id = "
				+ recipe.getId();
		return jdbcTemplate.query(sql, new RowMapper<Ingredient>() {
			@Override
			public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ingredient ingredient = new Ingredient();
				ingredient.setId(rs.getLong("ingredient_id"));
				ingredient.setName(rs.getString("ingredient_name"));
				ingredient.setQuantity_fridge(rs.getDouble("quantity_fridge"));
				ingredient.setAlergie(DaoFactory.INSTANCE.getAllergieDao().getByID(rs.getLong("allergie_allergie_id")));
				ingredient.setMeasure(DaoFactory.INSTANCE.getMeasureDao().getByID(rs.getLong("measure_measure_id")));
				return ingredient;
			}
		});
	}
	
	
	

	public Ingredient save(Ingredient ingredient) throws NoSuchElementException,NullPointerException {
		if (ingredient == null) {
			throw new NullPointerException("Cannot save null Ingredient");
		}
		if (ingredient.getId() == null) {
			SimpleJdbcInsert saveInsert = new SimpleJdbcInsert(jdbcTemplate);
			saveInsert.withTableName("ingredient");
			saveInsert.usingColumns("ingredient_name", "quantity_fridge", "allergie_allergie_id", "measure_measure_id");
			saveInsert.usingGeneratedKeyColumns("ingredient_id");
			Map<String, Object> values = new HashMap<>();
			values.put("ingredient_name", ingredient.getName());
			values.put("quantity_fridge", ingredient.getQuantity_fridge());
			values.put("allergie_allergie_id", ingredient.getAlergie().getId());
			values.put("measure_measure_id", ingredient.getMeasure().getId());
			long id = saveInsert.executeAndReturnKey(values).longValue();
			return new Ingredient(id,ingredient.getName(), ingredient.getQuantity_fridge(), ingredient.getAlergie(), ingredient.getMeasure());
		} else {
			String sql = "UPDATE ingredient SET ingredient_name= ?, quantity_fridge= ?, allergie_allergie_id= ?, measure_measure_id= ? " + " WHERE ingredient_id = ?";
			int updated = jdbcTemplate.update(sql, ingredient.getName(), ingredient.getQuantity_fridge(), ingredient.getAlergie().getId(), ingredient.getMeasure().getId(), ingredient.getId());
			if (updated == 1) {
				return ingredient;
			} else {
				throw new NoSuchElementException("Ingredient with id: " + ingredient.getId() + " not in DB.");
			}
		}
	}

	@Override
	public boolean delete(Ingredient ingredient) {
		int wasDeleted;
		try {
			wasDeleted = jdbcTemplate.update("DELETE FROM ingredient WHERE ingredient_id = " + ingredient.getId());
		} catch (DataIntegrityViolationException e) {
			throw new EntityUndeletableException("Ingredient with id: " + ingredient.getId()
					+ "cannot be deleted, because it is used in recipe");
		}
		return wasDeleted == 1;
	}

	private class IngredientRowMapper implements RowMapper<Ingredient> {

		public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
			Ingredient ingredient = new Ingredient();
			ingredient.setId(rs.getLong("ingredient_id"));
			ingredient.setName(rs.getString("ingredient_name"));
			ingredient.setQuantity_fridge(rs.getDouble("quantity_fridge"));
			ingredient.setAlergie(DaoFactory.INSTANCE.getAllergieDao().getByID(rs.getLong("allergie_allergie_id")));
			ingredient.setMeasure(DaoFactory.INSTANCE.getMeasureDao().getByID(rs.getLong("measure_measure_id")));
			return ingredient;
		}
	}

}

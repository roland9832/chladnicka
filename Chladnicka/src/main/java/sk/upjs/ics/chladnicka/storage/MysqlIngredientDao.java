package sk.upjs.ics.chladnicka.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MysqlIngredientDao implements IngredientDao {
	private JdbcTemplate jdbcTemplate;

	public MysqlIngredientDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Ingredient getByID(long id) {
		String sql = "SELECT ingredient_id, ingredient_name, quantity_fridge, allergie_allergie_id, measure_measure_id FROM INGREDIENT "
				+ "WHERE ingredient_id = "+ id;
		
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
	public List<Ingredient> getByAllergie(Allergie allergie) {
		String sql = "SELECT ingredient_id, ingredient_name, quantity_fridge,allergie_allergie_id, measure_measure_id FROM INGREDIENT "
				+ "LEFT JOIN allergie al ON allergie_allergie_id = al.allergie_id " 
				+ "WHERE allergie_allergie_id = "
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

	public Ingredient save(Ingredient ingredient) {
		// TODO Auto-generated method stub
		return null;
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

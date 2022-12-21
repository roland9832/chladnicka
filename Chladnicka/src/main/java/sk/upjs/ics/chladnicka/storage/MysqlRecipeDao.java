package sk.upjs.ics.chladnicka.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MysqlRecipeDao implements RecipeDao {
	private JdbcTemplate jdbcTemplate;

	public MysqlRecipeDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Recipe> getAll() {
		String sql = "SELECT recipe_id, recipe_name, calorific_value, description, diet_diet_id FROM recipe";
		return jdbcTemplate.query(sql, new RowMapper<Recipe>() {

			@Override
			public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
				Recipe recipe = new Recipe();
				recipe.setId(rs.getLong("recipe_id"));
				recipe.setRecipe_name(rs.getString("recipe_name"));
				recipe.setCalorific(rs.getDouble("calorific_value"));
				recipe.setDescription(rs.getString("description"));
				recipe.setDiet(DaoFactory.INSTANCE.getDietDao().getByID(rs.getLong("diet_diet_id")));
				List<Ingredient> ingredient = DaoFactory.INSTANCE.getIngredientDao().getByRecipe(recipe);
				recipe.setIngredient(ingredient);
				return recipe;
			}
		});
	}

	public List<Recipe> getByDiet(Diet diet) {
		String sql = "SELECT recipe_id, recipe_name, calorific_value, description, diet_diet_id FROM recipe "
				+ "WHERE diet_diet_id = " + diet.getId();
		return jdbcTemplate.query(sql, new RowMapper<Recipe>() {

			@Override
			public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
				Recipe recipe = new Recipe();
				recipe.setId(rs.getLong("recipe_id"));
				recipe.setRecipe_name(rs.getString("recipe_name"));
				recipe.setCalorific(rs.getDouble("calorific_value"));
				recipe.setDescription(rs.getString("description"));
				recipe.setDiet(DaoFactory.INSTANCE.getDietDao().getByID(rs.getLong("diet_diet_id")));
				List<Ingredient> ingredient = DaoFactory.INSTANCE.getIngredientDao().getByRecipe(recipe);
				recipe.setIngredient(ingredient);
				return recipe;
			}
		});
	}

	@Override
	public List<Recipe> getByIngredient(Ingredient ingredient) {
		String sql = "SELECT recipe_id, recipe_name, calorific_value, description, diet_diet_id FROM recipe "
				+ "LEFT JOIN recipe_has_ingredient rhi ON recipe_id = rhi.recipe_recipe_id "
				+ "LEFT JOIN ingredient ig ON ig.ingredient_id = rhi.ingredient_ingredient_id "
				+ "WHERE ig.ingredient_id = " + ingredient.getId();

		return jdbcTemplate.query(sql, new RowMapper<Recipe>() {

			@Override
			public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
				Recipe recipe = new Recipe();
				recipe.setId(rs.getLong("recipe_id"));
				recipe.setRecipe_name(rs.getString("recipe_name"));
				recipe.setCalorific(rs.getDouble("calorific_value"));
				recipe.setDescription(rs.getString("description"));
				recipe.setDiet(DaoFactory.INSTANCE.getDietDao().getByID(rs.getLong("diet_diet_id")));
				List<Ingredient> ingredient = DaoFactory.INSTANCE.getIngredientDao().getByRecipe(recipe);
				recipe.setIngredient(ingredient);
				return recipe;
			}
		});
	}

	@Override
	public Recipe getByID(long id) {
		String sql = "SELECT recipe_id, recipe_name, calorific_value, description, diet_diet_id FROM recipe "
				+ "WHERE recipe_id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new RecipeRowMapper());
		} catch (Exception e) {
			throw new NoSuchElementException("Recipe with id " + id + " not in DB");
		}

	}

	public Map<Ingredient, Double> getAmountByRecipe(Recipe recipe) {
		List<Ingredient> ingredients = DaoFactory.INSTANCE.getIngredientDao().getByRecipe(recipe);
		Map<Ingredient, Double> values = new HashMap<>();

		for (Ingredient ingredient : ingredients) {
			String sql = "SELECT recipe_amount FROM recipe_has_ingredient " + "WHERE recipe_recipe_id = "
					+ recipe.getId() + " AND ingredient_ingredient_id = " + ingredient.getId();

			double amount = jdbcTemplate.queryForObject(sql, new RowMapper<Double>() {

				@Override
				public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
					double rec_amount = rs.getDouble("recipe_amount");
					return rec_amount;
				}

			});
			values.put(ingredient, amount);
		}
		return values;
	}
	@Override
	public void save(Recipe recipe, Map<Ingredient, Double> ingredientMap, List<Ingredient> ingredients) throws NoSuchElementException, NullPointerException {
		if(recipe == null) {
			throw new NullPointerException("Cannot save null Ingredient");
		}
		if(recipe.getId() == null) {
			SimpleJdbcInsert saveInsert = new SimpleJdbcInsert(jdbcTemplate);
			saveInsert.withTableName("recipe");
			saveInsert.usingColumns("recipe_name","calorific_value","description","diet_diet_id");
			saveInsert.usingGeneratedKeyColumns("recipe_id");
			saveInsert.usingGeneratedKeyColumns("ingredient_id");
			Map<String, Object> values = new HashMap<>();
			values.put("recipe_name", recipe.getRecipe_name());
			values.put("calorific_value", recipe.getCalorific());
			values.put("description", recipe.getDescription());
			values.put("diet_diet_id", recipe.getDiet().getId());
			long id = saveInsert.executeAndReturnKey(values).longValue();
			for (Ingredient ingredient : ingredients) {
				StringBuilder sb = new StringBuilder();
				sb.append("INSERT INTO recipe_has_ingredient (recipe_recipe_id, ingredient_ingredient_id, recipe_amount) VALUES ");
				sb.append("(").append(id);
				sb.append(" , ").append(ingredient.getId());
				sb.append(" , ").append(ingredientMap.get(ingredient));
				sb.append(")");
				System.out.println(sb.toString());
				String sql = sb.substring(0,sb.length());
				System.out.println(sql);
				jdbcTemplate.update(sql);
			}
			
		}	
	}
	
	@Override
	public boolean delete(Recipe recipe) throws EntityUndeletableException {
		int wasDeleted;
		try {
			wasDeleted = jdbcTemplate.update("DELETE FROM recipe_has_ingredient WHERE recipe_recipe_id = " + recipe.getId());
		} catch (DataIntegrityViolationException e) {
			throw new EntityUndeletableException("Recipe with id: " + recipe.getId()
					+ "Favourite is already in use");
		}
		try {
			wasDeleted = jdbcTemplate.update("DELETE FROM recipe WHERE recipe_id = " + recipe.getId());
		} catch (DataIntegrityViolationException e) {
			throw new EntityUndeletableException("Recipe with id: " + recipe.getId()
					+ "Favourite is already in use");
		}
		return wasDeleted == 1;
		
	}

	private class RecipeRowMapper implements RowMapper<Recipe> {

		@Override
		public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
			Recipe recipe = new Recipe();
			recipe.setId(rs.getLong("recipe_id"));
			recipe.setRecipe_name(rs.getString("recipe_name"));
			recipe.setCalorific(rs.getDouble("calorific_value"));
			recipe.setDescription(rs.getString("description"));
			recipe.setDiet(DaoFactory.INSTANCE.getDietDao().getByID(rs.getLong("diet_diet_id")));
			List<Ingredient> ingredient = DaoFactory.INSTANCE.getIngredientDao().getByRecipe(recipe);
			recipe.setIngredient(ingredient);
			return recipe;
		}

	}

	

}

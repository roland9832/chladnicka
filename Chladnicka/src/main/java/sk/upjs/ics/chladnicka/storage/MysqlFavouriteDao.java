package sk.upjs.ics.chladnicka.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class MysqlFavouriteDao implements FavouriteDao {
	private JdbcTemplate jdbcTemplate;

	public MysqlFavouriteDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Favourite> getAll() {
		String sql = "SELECT hodnotenie, recipe_recipe_id FROM favourite";
		return jdbcTemplate.query(sql, new RowMapper<Favourite>() {

			@Override
			public Favourite mapRow(ResultSet rs, int rowNum) throws SQLException {
				Favourite favourite = new Favourite();
				favourite.setHodnotenie(rs.getInt("hodnotenie"));
				long id = rs.getLong("recipe_recipe_id");
				favourite.setId(id);
				favourite.setRecipe(DaoFactory.INSTANCE.getRecipeDao().getByID(id));
				return favourite;
			}
		});
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
					favourite.setHodnotenie(rs.getInt("hodnotenie"));
					long id = rs.getLong("recipe_recipe_id");
					favourite.setId(id);
					favourite.setRecipe(DaoFactory.INSTANCE.getRecipeDao().getByID(id));
					return favourite;
				}

			});
		} catch (Exception e) {
			throw new NoSuchElementException("Favourite with recipe "+ recipe +" not in DB");
		}
	}

	@Override
	public boolean delete(Recipe recipe) throws ObjectUndeletableException {
		int wasDeleted;
		try {
			wasDeleted = jdbcTemplate.update("DELETE FROM favourite WHERE id = " + recipe.getId());
		} catch (DataIntegrityViolationException e) {
			throw new ObjectUndeletableException("Term with id: " + recipe.getId() + "cannot be deleted, some candidate/program already has this term");
		}
				
		return wasDeleted == 1;
	}

}

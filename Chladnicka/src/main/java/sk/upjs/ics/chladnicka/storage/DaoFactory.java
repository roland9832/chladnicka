package sk.upjs.ics.chladnicka.storage;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;
	

	private JdbcTemplate jdbcTemplate;
	private DietDao dietDao;
	private AllergieDao allergieDao;
	private MeasureDao measureDao;
	private IngredientDao ingredientDao;
	private RecipeDao recipeDao;
	private FavouriteDao favouriteDao;
	
	public DietDao getDietDao() {
		if(dietDao == null) {
			dietDao = new MysqlDietDao(getJdbcTemplate());
		}
		return dietDao;
	}
	
	public AllergieDao getAllergieDao() {
		if(allergieDao == null) {
			allergieDao = new MysqlAllergieDao(getJdbcTemplate());
		}
		return allergieDao;
	}
	
	public MeasureDao getMeasureDao() {
		if(measureDao == null) {
			measureDao = new MysqlMeasureDao(getJdbcTemplate());
		}
		return measureDao;
	}
	
	public IngredientDao getIngredientDao() {
		if(ingredientDao == null) {
			ingredientDao = new MysqlIngredientDao(getJdbcTemplate());
		}
		return ingredientDao;
	}
	
	public RecipeDao getRecipeDao() {
		if(recipeDao == null) {
			recipeDao = new MysqlRecipeDao(getJdbcTemplate());
		}
		return recipeDao;
	}
	
	public FavouriteDao getFavouriteDao() {
		if(favouriteDao == null) {
			favouriteDao = new MysqlFavouriteDao(getJdbcTemplate());
		}
		return favouriteDao;
	}
	
	
	private boolean testing = true;
	
	public void setTesting() {
		this.testing = true;
	}
	
	
	
	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			if (testing) {
				dataSource.setDatabaseName("chladnickatest");
				dataSource.setUrl("jdbc:mysql://localhost/chladnickatest?serverTimezone=Europe/Bratislava");
			} else {
				dataSource.setDatabaseName("chladnicka");
				dataSource.setUrl("jdbc:mysql://localhost/chladnicka?serverTimezone=Europe/Bratislava");
			}
			dataSource.setUser("Admin");
			dataSource.setPassword("Chladnicka2022");
			
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}
}

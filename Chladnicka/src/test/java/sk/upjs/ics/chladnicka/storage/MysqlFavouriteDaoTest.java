package sk.upjs.ics.chladnicka.storage;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MysqlFavouriteDaoTest {

	private FavouriteDao favouriteDao;
	private RecipeDao recipeDao;

	private Recipe recipe;

	public MysqlFavouriteDaoTest() {
		DaoFactory.INSTANCE.setTesting();
		favouriteDao = DaoFactory.INSTANCE.getFavouriteDao();
		recipeDao = DaoFactory.INSTANCE.getRecipeDao();
		recipe = recipeDao.getByID(1);
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAll() {
		List<Favourite> favourite = favouriteDao.getAll();
		assertTrue(favourite.size() > 0);
		assertNotNull(favourite.get(0).getHodnotenie());
		assertNotNull(favourite.get(0).getRecipe());
	}

	@Test
	void testGetByRecipe() {
		assertThrows(NoSuchElementException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				Recipe recipe = new Recipe();
				recipe.setId(-1l);
				favouriteDao.getByRecipe(recipe);
			}
		});
		Favourite newFavourite = new Favourite(this.recipe,5);
		favouriteDao.save(newFavourite);
		List<Favourite> favourite = favouriteDao.getAll();
		Favourite savedFavourite = favourite.get(favourite.size()-1);
		Favourite byRecipe = favouriteDao.getByRecipe(savedFavourite.getRecipe());
		assertEquals(savedFavourite.getRecipe(), byRecipe.getRecipe());
		assertEquals(newFavourite.getHodnotenie(), byRecipe.getHodnotenie());
		favouriteDao.delete(savedFavourite.getRecipe());
	}

}

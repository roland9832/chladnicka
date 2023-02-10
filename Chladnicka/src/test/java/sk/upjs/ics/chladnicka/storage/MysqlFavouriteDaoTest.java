package sk.upjs.ics.chladnicka.storage;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MysqlFavouriteDaoTest {

	private FavouriteDao favouriteDao;
	private Favourite savedFavourite;
	private RecipeDao recipeDao;
	private Recipe savedRecipe;

	public MysqlFavouriteDaoTest() {
		DaoFactory.INSTANCE.setTesting();
		favouriteDao = DaoFactory.INSTANCE.getFavouriteDao();
		recipeDao = DaoFactory.INSTANCE.getRecipeDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		Favourite favourite = new Favourite();
		favourite.setHodnotenie(0);
		Recipe recipe = new Recipe();
		recipe = recipeDao.getByID(1);
		favourite.setRecipe(recipe);
		savedFavourite = favouriteDao.save(favourite);
	}

	@AfterEach
	void tearDown() throws Exception {
		favouriteDao.delete(savedFavourite.getRecipe());
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
		Recipe recipe = new Recipe();
		recipe = recipeDao.getByID(1);
		Favourite newFavourite = new Favourite(recipe,5);
		favouriteDao.save(newFavourite);
		List<Favourite> favourite = favouriteDao.getAll();
		Favourite savedFavourite = favourite.get(favourite.size()-1);
		Favourite byRecipe = favouriteDao.getAll().get(0);
		assertEquals(savedFavourite.getRecipe(), byRecipe.getRecipe());
		favouriteDao.delete(savedFavourite.getRecipe());
	}

	@Test
	void testSave() {
		assertThrows(NullPointerException.class, () -> favouriteDao.save(null), "Cannot save null");
		Favourite favourite = new Favourite();
		favourite.setRecipe(recipeDao.getByID(1));
		favourite.setHodnotenie(2);
		int size = favouriteDao.getAll().size();
		Favourite saved = favouriteDao.save(favourite);
		Assert.assertEquals(size + 1 , favouriteDao.getAll().size());
		assertNotNull(saved.getId());
		Assert.assertEquals(favourite.getRecipe(), saved.getRecipe());
		favouriteDao.delete(saved.getRecipe());
		assertThrows(NullPointerException.class,
				() -> favouriteDao.save(new Favourite()),"Favourite recipe name cannot be null");

	}
}

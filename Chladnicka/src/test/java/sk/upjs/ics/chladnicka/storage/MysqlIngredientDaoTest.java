package sk.upjs.ics.chladnicka.storage;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MysqlIngredientDaoTest {

    private IngredientDao ingredientDao;
    private Ingredient savedIngredient;
    private RecipeDao recipeDao;
    private Recipe globalRecipe;
    private MeasureDao measureDao;
    private Measure globalMeasure;
    private AllergieDao allergieDao;

    private Allergie globalAllergie;

    public MysqlIngredientDaoTest() {
        DaoFactory.INSTANCE.setTesting();
        ingredientDao = DaoFactory.INSTANCE.getIngredientDao();
        recipeDao = DaoFactory.INSTANCE.getRecipeDao();
        measureDao = DaoFactory.INSTANCE.getMeasureDao();
        allergieDao = DaoFactory.INSTANCE.getAllergieDao();
    }

    @BeforeEach
    void setUp() throws Exception {
        Ingredient ingredient = new Ingredient();
        globalAllergie = allergieDao.getAll().get(0);
        globalMeasure = measureDao.getAll().get(0);
        globalRecipe = recipeDao.getAll().get(0);
        ingredient.setName("Test");
        ingredient.setAlergie(globalAllergie);
        ingredient.setMeasure(globalMeasure);
        savedIngredient = ingredientDao.save(ingredient);
    }

    @AfterEach
    void tearDown() throws Exception {
        ingredientDao.delete(savedIngredient);
    }

    @Test
    void testGetAll() {
        List<Ingredient> ingredients = ingredientDao.getAll();
        assertTrue(ingredients.size() > 0);
        assertNotNull(ingredients.get(0).getName());
        assertNotNull(ingredients.get(0).getMeasure());
    }

    @Test
    void testGetByID() {
        Ingredient fromDB = ingredientDao.getByID(savedIngredient.getId());
        assertEquals(savedIngredient.getId(), fromDB.getId());
        assertEquals(savedIngredient.getName(), fromDB.getName());
        assertEquals(savedIngredient.getMeasure(), fromDB.getMeasure());
        assertEquals(savedIngredient.getAlergie(), fromDB.getAlergie());
        assertThrows(NoSuchElementException.class,()->ingredientDao.getByID(-1));
    };

    @Test
    void testGetByName() {
        Ingredient fromDb = ingredientDao.getByName("Test");
        assertEquals(savedIngredient.getId(), fromDb.getId());
        assertEquals(savedIngredient.getName(), fromDb.getName());
        assertThrows(NoSuchElementException.class,()->ingredientDao.getByName(null));

    }

    @Test
    void testGetByAllergie() {
        List<Ingredient> fromDb = ingredientDao.getByAllergie(globalAllergie);
        for (int i = 0; i < fromDb.size(); i++) {
            if (savedIngredient.getId() == fromDb.get(i).getId()) {
                assertEquals(savedIngredient.getName(), fromDb.get(i).getName());
                assertEquals(savedIngredient.getMeasure(), fromDb.get(i).getMeasure());
                assertEquals(savedIngredient.getAlergie(), fromDb.get(i).getAlergie());
            }
        }
    }

    @Test
    void testGetByMeasure() {
        List<Ingredient> fromDb = ingredientDao.getByMeasure(globalMeasure);
        for (int i = 0; i < fromDb.size(); i++) {
            if (savedIngredient.getMeasure() == fromDb.get(i).getMeasure()) {
                assertEquals(savedIngredient.getName(), fromDb.get(i).getName());
                assertEquals(savedIngredient.getMeasure(), fromDb.get(i).getMeasure());
                assertEquals(savedIngredient.getAlergie(), fromDb.get(i).getAlergie());
            }
        }
    }

    @Test
    void testGetByRecipe() {
        List<Ingredient> fromDb = ingredientDao.getByRecipe(globalRecipe);
        for (int i = 0; i < fromDb.size(); i++) {
            if (savedIngredient == fromDb.get(i)) {
                assertEquals(savedIngredient.getName(), fromDb.get(i).getName());
                assertEquals(savedIngredient.getMeasure(), fromDb.get(i).getMeasure());
                assertEquals(savedIngredient.getAlergie(), fromDb.get(i).getAlergie());
            }
        }
    }

    @Test
    void testSave() {
        assertThrows(NullPointerException.class, () -> ingredientDao.save(null), "Cannot save null");
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Test");
        ingredient.setMeasure(globalMeasure);
        ingredient.setAlergie(globalAllergie);
        int size = ingredientDao.getAll().size();
        Ingredient saved = ingredientDao.save(ingredient);
        Assert.assertEquals(size + 1, ingredientDao.getAll().size());
        assertNotNull(saved.getId());
        Assert.assertEquals(ingredient.getName(), saved.getName());
        Assert.assertEquals(ingredient.getMeasure(), saved.getMeasure());
        Assert.assertEquals(ingredient.getAlergie(), saved.getAlergie());
        ingredientDao.delete(saved);
        assertThrows(NullPointerException.class,
                () -> ingredientDao.save(new Ingredient()), "Ingredient measure cannot be null");
    }

    @Test
    void updateTest() {
        Ingredient updated = new Ingredient(savedIngredient.getId(), "Changed name", 0, globalAllergie, globalMeasure);
        int size = ingredientDao.getAll().size();
        ingredientDao.save(updated);
        assertEquals(size, ingredientDao.getAll().size());

        Ingredient fromDb = ingredientDao.getByID(savedIngredient.getId());

        assertEquals(updated.getId(), fromDb.getId());
        assertEquals(updated.getName(), fromDb.getName());
        assertThrows(NoSuchElementException.class,
                ()->ingredientDao.save(new Ingredient(-1L, "Changed", 0, globalAllergie, globalMeasure)));
    }


}

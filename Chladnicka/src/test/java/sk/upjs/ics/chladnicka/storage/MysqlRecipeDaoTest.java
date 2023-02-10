package sk.upjs.ics.chladnicka.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MysqlRecipeDaoTest {

     private RecipeDao recipeDao;
     private Recipe savedRecipe;

     private DietDao dietDao;
     private Diet savedDiet;

     private IngredientDao ingredientDao;
     private Ingredient savedIngredient;

    public MysqlRecipeDaoTest() {
        DaoFactory.INSTANCE.setTesting();
        recipeDao = DaoFactory.INSTANCE.getRecipeDao();
        dietDao = DaoFactory.INSTANCE.getDietDao();
        ingredientDao = DaoFactory.INSTANCE.getIngredientDao();
    }

    @BeforeEach
    void setUp() throws Exception {
            Recipe recipe = new Recipe();
            recipe.setRecipe_name("Test recipe");
            recipe.setCalorific(100);
            Diet diet = new Diet();
            diet.setName("vegan2");
            //savedDiet = dietDao.save(diet);
            Ingredient ingredient = new Ingredient();
            List<Ingredient> ingredients = new ArrayList<>();
            Map<Ingredient, Double> ingredientMap = new HashMap<>();
            List<Ingredient> ingredientToSave = new ArrayList<>();
            diet.setName("Test diet");
            savedDiet = dietDao.getByID(1);
            ingredient.setName("Test Ingredient");
            ingredient.setAlergie(ingredientDao.getByID(1).getAlergie());
            ingredient.setMeasure(ingredientDao.getByID(1).getMeasure());
            savedIngredient = ingredientDao.save(ingredient);
            ingredients.add(ingredient);
            recipe.setDiet(savedDiet);
            recipe.setDescription("Test Recipe Description");
            recipe.setIngredient(ingredients);
            savedRecipe = recipeDao.save(recipe, ingredientMap, ingredientToSave);
    }

    @AfterEach
    void tearDown() throws Exception {
           recipeDao.delete(savedRecipe);
    }

    @Test
    void testGetAll() {
        List<Recipe> recipe = recipeDao.getAll();
        assertTrue(recipe.size() > 0);
        assertNotNull(recipe.get(0).getId());
        assertNotNull(recipe.get(0).getRecipe_name());
    }

    @Test
    void testGetByID() {
        Recipe fromDB = recipeDao.getByID(savedRecipe.getId());
        assertEquals(savedRecipe.getId(), fromDB.getId());
        assertEquals(savedRecipe.getRecipe_name(), fromDB.getRecipe_name());
        assertThrows(NoSuchElementException.class,()->recipeDao.getByID(-1));
    }

    @Test
    void testGetByDiet() {
        List<Recipe> fromDB = recipeDao.getByDiet(savedDiet);
        for (int i = 0; i < fromDB.size();i++) {
            if (savedRecipe == fromDB.get(i)) {
                assertEquals(savedRecipe, fromDB.get(i));
                assertEquals(savedRecipe.getRecipe_name(), fromDB.get(i).getRecipe_name());
                assertEquals(savedRecipe.getId(), fromDB.get(i).getId());
                assertEquals(savedRecipe.getDescription(), fromDB.get(i).getDescription());
                assertThrows(NoSuchElementException.class, () -> recipeDao.getByIngredient(null));
            }
        }
    }

    @Test
    void testGetByIngredient() {
        List<Recipe> fromDb = recipeDao.getByIngredient(savedIngredient);
        for (int i = 0; i < fromDb.size(); i++) {
            if (savedRecipe == fromDb.get(i)) {
                assertEquals(savedRecipe.getRecipe_name(), fromDb.get(i).getRecipe_name());
                assertEquals(savedRecipe.getDescription(), fromDb.get(i).getDescription());
                assertEquals(savedRecipe.getCalorific(), fromDb.get(i).getCalorific());
                assertEquals(savedRecipe.getIngredient(), fromDb.get(i).getIngredient());
            }
        }
    }

    @Test
    void testGetAmountByRecipe() {
        Map<Ingredient, Double> fromDB = recipeDao.getAmountByRecipe(savedRecipe);
        for (int i = 0; i < fromDB.size(); i++) { // prechadzam cey fromDB teda cez vsetky
            assertEquals(fromDB, recipeDao.getAmountByRecipe(savedRecipe));
            //assertEquals(savedRecipe.getRecipe_name(), fromDB.get(i).getRecipe_name());
            //assertEquals(savedRecipe.getId(), fromDB.get(i).getId());
            //assertEquals(savedRecipe.getDescription(), fromDB.get(i).getDescription());
            assertThrows(NullPointerException.class,()->recipeDao.getAmountByRecipe(null));
        }
    }


    @Test
    void testSave() {
        assertThrows(NullPointerException.class, () -> recipeDao.save(null, null, null), "Cannot save null");
        Recipe recipe = new Recipe();
        recipe.setRecipe_name("Test recipe");
        recipe.setDiet(savedDiet);
        int size = recipeDao.getAll().size();
        Map<Ingredient, Double> ingredientMap = new HashMap<>();
        List<Ingredient> ingredientToSave = new ArrayList<>();
        Recipe saved = recipeDao.save(recipe, ingredientMap, ingredientToSave);
        assertEquals(size + 1 , recipeDao.getAll().size());
        if (recipe.getId() != null) {
            assertNotNull(saved.getId());
        }
        assertEquals(recipe.getRecipe_name(), saved.getRecipe_name());
        recipeDao.delete(saved); // my nemazeme podla id ale podla celeho receptu trochu zvlastne
        assertThrows(NullPointerException.class,
                () -> recipeDao.save(new Recipe(null, 0, null, null), null, null),"Recipe cannot be null");
    }
}



package sk.upjs.ics.chladnicka.storage;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MysqlDietDaoTest {

	private DietDao dietDao;

	private Diet savedDiet;
	
	public MysqlDietDaoTest() {
		DaoFactory.INSTANCE.setTesting();
		dietDao = DaoFactory.INSTANCE.getDietDao();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		Diet diet = new Diet();
		diet.setName("Test diet");
		savedDiet = dietDao.save(diet);

	}

	@AfterEach
	void tearDown() throws Exception {
		dietDao.delete(savedDiet.getId());
	}

	@Test
	void testGetAll() {
		List<Diet> diet = dietDao.getAll();
		assertTrue(diet.size() > 0);
		assertNotNull(diet);
		assertNotNull(diet.get(0));
	}

	@Test
	void testGetByID() {
		Diet fromDb = dietDao.getByID(savedDiet.getId());
		assertEquals(savedDiet.getId(), fromDb.getId());
		assertEquals(savedDiet.getName(), fromDb.getName());
		assertThrows(NoSuchElementException.class, () -> dietDao.getByID(-1));
	}

	@Test
	void testSave() {
		assertThrows(NullPointerException.class, () -> dietDao.save(null), "Cannot save null");
		Diet diet = new Diet();
		diet.setName("New diet");
		int size = dietDao.getAll().size();
		Diet saved = dietDao.save(diet);
		assertEquals(size + 1 , dietDao.getAll().size());
		assertNotNull(saved.getId());
		assertEquals(diet.getName(), saved.getName());
		dietDao.delete(saved.getId());
		assertThrows(NullPointerException.class,
				() -> dietDao.save(new Diet()),"Diet name cannot be null");

	}

	@Test
	void testUpdate() {
		assertThrows(NullPointerException.class, () -> dietDao.save(null), "Cannot save null");
		Diet diet = new Diet();
		diet.setName("New diet");
		int size = dietDao.getAll().size();
		Diet saved = dietDao.save(diet);
		assertEquals(size + 1 , dietDao.getAll().size());
		assertNotNull(saved.getId());
		assertEquals(diet.getName(), saved.getName());
		dietDao.delete(saved.getId());
		assertThrows(NullPointerException.class,
				() -> dietDao.save(new Diet()),"Diet name cannot be null");

	}

	@Test
	void updateTest() {
		Diet updated = new Diet(savedDiet.getId(), "Changed name");
		int size = dietDao.getAll().size();
		dietDao.save(updated);
		assertEquals(size, dietDao.getAll().size());

		Diet fromDb = dietDao.getByID(savedDiet.getId());

		assertEquals(updated.getId(), fromDb.getId());
		assertEquals(updated.getName(), fromDb.getName());
		assertThrows(NoSuchElementException.class,
				()->dietDao.save(new Diet(-1L, "Changed")));
	}


}

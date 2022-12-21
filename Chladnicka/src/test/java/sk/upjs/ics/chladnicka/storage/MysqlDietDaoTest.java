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

class MysqlDietDaoTest {

	private DietDao dietDao;
	
	public MysqlDietDaoTest() {
		DaoFactory.INSTANCE.setTesting();
		dietDao = DaoFactory.INSTANCE.getDietDao();
	}
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAll() {
		List<Diet> diet = dietDao.getAll();
		assertTrue(diet.size() > 0);
		assertNotNull(diet.get(0).getId());
		assertNotNull(diet.get(0).getName());
	}

	@Test
	void testGetByID() {
		assertThrows(NoSuchElementException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				dietDao.getByID(-3l);
			}
		});
	}
}

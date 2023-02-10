package sk.upjs.ics.chladnicka.storage;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MysqlAllergieDaoTest {

	private AllergieDao allergieDao;

	private Allergie globalAllergie;
	
	
	public MysqlAllergieDaoTest() {
		DaoFactory.INSTANCE.setTesting();
		allergieDao = DaoFactory.INSTANCE.getAllergieDao();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		globalAllergie = allergieDao.getByID(allergieDao.getAll().size()-1);
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	@Test
	void testGetAll() {
		List<Allergie> allergie = allergieDao.getAll();
		assertTrue(allergie.size() > 0);
		assertNotNull(allergie.get(0).getCategory());
		assertNotNull(allergie.get(0).getId());
		assertNotNull(allergie.get(0).getInformation());
	}

	@Test
	void testGetByID() {
		Allergie fromDB = allergieDao.getByID(allergieDao.getAll().size()-1);
		assertEquals(globalAllergie.getId(), fromDB.getId());
		assertEquals(globalAllergie.getCategory(), fromDB.getCategory());
		assertEquals(globalAllergie.getClass(), fromDB.getClass());
		assertEquals(globalAllergie.getCategory(), fromDB.getCategory());
		assertThrows(NoSuchElementException.class,()->allergieDao.getByID(-1));
	}
}

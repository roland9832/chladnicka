package sk.upjs.ics.chladnicka.storage;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MysqlAllergieDaoTest {

	private AllergieDao allergieDao;
	
	
	public MysqlAllergieDaoTest() {
		DaoFactory.INSTANCE.setTesting();
		allergieDao = DaoFactory.INSTANCE.getAllergieDao();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		
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
		assertThrows(NoSuchElementException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				allergieDao.getByID(-1l);	
			}
		});
	}
}

package pl.playerony.cantor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.playerony.cantor.database.models.Role;
import pl.playerony.cantor.database.services.RoleService;
import pl.playerony.cantor.exceptions.CantorRestApiException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRoleService {
	@Autowired
	private RoleService roleService;
	
	@Test
	public void fetchRoleByRoleIdTest() {
		try {
			Role role = roleService.fetchRoleByRoleId(1L);
			
			assertNotNull(role);
			assertTrue(role.getRoleName().equals("USER"));
		} catch (CantorRestApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

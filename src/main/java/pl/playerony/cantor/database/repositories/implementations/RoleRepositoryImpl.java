package pl.playerony.cantor.database.repositories.implementations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.playerony.cantor.database.models.Role;
import pl.playerony.cantor.database.repositories.RoleRepository;
import pl.playerony.cantor.exceptions.CantorRestApiException;

@Repository
@Transactional
public class RoleRepositoryImpl implements RoleRepository {
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public Role fetchRoleByRoleId(Long roleId) throws CantorRestApiException {
		try {
			Role role = entityManager.find(Role.class, roleId);
			entityManager.flush();
			
			return role;
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by fetching roles", e);
		}
	}

}

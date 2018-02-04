package pl.playerony.cantor.database.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.playerony.cantor.database.models.Role;
import pl.playerony.cantor.database.repositories.RoleRepository;
import pl.playerony.cantor.database.services.RoleService;
import pl.playerony.cantor.exceptions.CantorRestApiException;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role fetchRoleByRoleId(Long roleId) throws CantorRestApiException {
		Role role = roleRepository.fetchRoleByRoleId(roleId);
		
		if(role != null)
			return role;
		else
			throw new CantorRestApiException("This roleId[" + roleId + "] doesnt exist in database.");
	}

}

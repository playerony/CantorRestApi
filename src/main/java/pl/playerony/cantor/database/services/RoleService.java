package pl.playerony.cantor.database.services;

import pl.playerony.cantor.database.models.Role;
import pl.playerony.cantor.exceptions.CantorRestApiException;

public interface RoleService {
	Role fetchRoleByRoleId(Long roleId) throws CantorRestApiException;
}	

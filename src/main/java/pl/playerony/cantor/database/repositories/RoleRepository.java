package pl.playerony.cantor.database.repositories;

import pl.playerony.cantor.database.models.Role;
import pl.playerony.cantor.exceptions.CantorRestApiException;

public interface RoleRepository {
	Role fetchRoleByRoleId(Long roleId) throws CantorRestApiException;
}

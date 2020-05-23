package pe.edu.tecsup.productosapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.tecsup.productosapi.models.Usuario;
import pe.edu.tecsup.productosapi.repositories.UserRespository;

@Service
public class UserService {
	
	@Autowired
	private UserRespository userRespository;
	
	public Usuario auth(String email, String password) {
		return userRespository.user(email, password);
	}
}

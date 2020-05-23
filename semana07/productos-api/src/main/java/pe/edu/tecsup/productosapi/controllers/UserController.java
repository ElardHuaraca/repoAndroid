package pe.edu.tecsup.productosapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.tecsup.productosapi.models.Usuario;
import pe.edu.tecsup.productosapi.services.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/loguin/auth")
	public Usuario auth(@RequestParam("email") String email,@RequestParam("password") String password) {
		Usuario usuario = userService.auth(email, password);
		return usuario;
	}
}

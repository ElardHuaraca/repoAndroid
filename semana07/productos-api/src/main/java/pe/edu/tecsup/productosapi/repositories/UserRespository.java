package pe.edu.tecsup.productosapi.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pe.edu.tecsup.productosapi.models.Usuario;


@Repository
public class UserRespository {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRespository.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Usuario user(String email,String password){
		logger.info("call user()");
		String sql = "select * from usuarios where correo = ? and password = ?";
		Usuario usuario= jdbcTemplate.queryForObject(sql, new RowMapper<Usuario>() {
			public Usuario mapRow(ResultSet rs,int rowNum) throws SQLException{
				Usuario usuario=new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setUsername(rs.getString("username"));
				usuario.setNombres(rs.getString("nombres"));
				usuario.setCorreo(rs.getString("correo"));
				usuario.setImagen(rs.getString("imagen"));
				usuario.setEstado(rs.getString("estado"));
				return usuario;
			}
		}, email,password);
		logger.info("usuario: " + usuario);
		return usuario;
	}
}

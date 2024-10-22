package co.edu.unbosque.shopease_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Data
@Entity
@Getter
@Setter
@ToString
@Table(name="usuarios")
public class UsuarioModel {

	@Id
	private int id_usuario;
//prueba
	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String email;

	private String telefono;

	private String direccion;

	@Column(nullable = false)
	private String contraseña;

	private Date fecha_registro;
}

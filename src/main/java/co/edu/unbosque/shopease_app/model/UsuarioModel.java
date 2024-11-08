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

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String email;

	private String telefono;

	private String direccion;

	@Column(nullable = false)
	private String contraseña;

	private Date fecha_registro;

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Date getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	@Override
	public String toString() {
		return "UsuarioModel [id_usuario=" + id_usuario + ", nombre=" + nombre + ", email=" + email + ", telefono="
				+ telefono + ", direccion=" + direccion + ", contraseña=" + contraseña + ", fecha_registro="
				+ fecha_registro + "]";
	}
	
	
}

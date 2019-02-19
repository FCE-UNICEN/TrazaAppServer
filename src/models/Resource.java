package models;

public class Resource {
	private Integer id;
	private String nombre;
	private boolean fraccionario;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isFraccionario() {
		return fraccionario;
	}
	public void setFraccionario(boolean fraccionario) {
		this.fraccionario = fraccionario;
	}
	
	
}

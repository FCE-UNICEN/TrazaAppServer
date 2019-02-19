package models;

public class PetitionToken {
	
	private String mensaje;
	private int status;
	private Peticion[] peticiones;
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Peticion[] getPeticiones() {
		return peticiones;
	}
	public void setPeticiones(Peticion[] peticiones) {
		this.peticiones = peticiones;
	}
	
}

package models;

public class InstitucionToken {
	private String mensaje;
	private int status;
	private Institucion[] instituciones;
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
	public Institucion[] getInstituciones() {
		return instituciones;
	}
	public void setInstituciones(Institucion[] instituciones) {
		this.instituciones = instituciones;
	}
	
	

}

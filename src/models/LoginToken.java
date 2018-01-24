package models;

public class LoginToken {
	private String mensaje;
	private int status;
	private Persona usuario;
	
//	public LoginToken(String mensaje, int status, Persona usuario) {
//		super();
//		this.mensaje = mensaje;
//		this.status = status;
//		this.usuario = usuario;
//	}
	
//	public LoginToken(String mensaje, int status) {
//		super();
//		this.mensaje = mensaje;
//		this.status = status;
//		this.usuario = null;
//	}

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

	public Persona getUsuario() {
		return usuario;
	}

	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}
	
	
	
	

}

package models;

public class RouteToken {
	private String mensaje;
	private int status;
	private Route[] nodos;
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
	public Route[] getNodos() {
		return nodos;
	}
	public void setNodos(Route[] nodos) {
		this.nodos = nodos;
	}

	
	
}

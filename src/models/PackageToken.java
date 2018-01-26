package models;

public class PackageToken {
	private int status;
	private String mensaje;
	private Paquete[] packages;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Paquete[] getPackages() {
		return packages;
	}
	public void setPackages(Paquete[] packages) {
		this.packages = packages;
	}
	 
	
}

package models;

import java.sql.Date;

public class Route {
	private int id;
	private int id_origen;
	private String nombre_origen;
	private int id_destino;
	private String nombre_destino;
	private String address;
	private double latitude;
	private double longitude;
	private String fecha;
	private int id_package;
	private int id_package_padre;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_origen() {
		return id_origen;
	}
	public void setId_origen(int id_origen) {
		this.id_origen = id_origen;
	}
	public String getNombre_origen() {
		return nombre_origen;
	}
	public void setNombre_origen(String nombre_origen) {
		this.nombre_origen = nombre_origen;
	}
	public int getId_destino() {
		return id_destino;
	}
	public void setId_destino(int id_destino) {
		this.id_destino = id_destino;
	}
	public String getNombre_destino() {
		return nombre_destino;
	}
	public void setNombre_destino(String nombre_destino) {
		this.nombre_destino = nombre_destino;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String adress) {
		this.address = adress;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getId_package() {
		return id_package;
	}
	public void setId_package(int id_package) {
		this.id_package = id_package;
	}
	public int getId_package_padre() {
		return id_package_padre;
	}
	public void setId_package_padre(int id_package_padre) {
		this.id_package_padre = id_package_padre;
	}
	
	

}

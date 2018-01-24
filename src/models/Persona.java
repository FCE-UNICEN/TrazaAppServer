package models;

public class Persona {

    private int id; //identificador unico
    private String pass;
    private String user;
    private String nombre;
    private String telefono;
    private int ciudad;
    private boolean admin;
    private String email;
    private String direccion;
	private String Web;
	private double latitude;
	private double longitude;

//    public Persona(int id, String u, String pass, String nombre, String telefono, String domicilio,
//                   int ciudad, boolean admin, String email, double lat, double lon, String web) {
//        this.id        = id;
//        this.pass      = pass;
//        this.nombre    = nombre;
//        this.telefono  = telefono;
//        this.domicilio = domicilio;
//        this.ciudad    = ciudad;
//        this.user      = u;
//        this.admin     = admin;
//        this.email     = email;
//        this.latitude  = lat;
//        this.longitude = lon;
//        this.Web = web;
//    }

//	public Persona(CharSequence user, String pass, CharSequence nombre,
//				   CharSequence email, CharSequence address, CharSequence tel,
//				   CharSequence web, int itemSpinner) {
//		this.user = String.valueOf(user);
//		this.pass = pass;
//		this.nombre = String.valueOf(nombre);
//		this.email = String.valueOf(email);
//		this.direccion = String.valueOf(address);
//		this.telefono = String.valueOf(tel);
//		this.Web = String.valueOf(web);
//	}


	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getUser() {
		return user;
	}

	public int getCiudad() {
		return ciudad;
	}

	public void setCiudad(int ciudad) {
		this.ciudad = ciudad;
	}


	public void setUser(String user) {
		this.user = user;
	}

	public boolean isAdmin() {
		return admin;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getWeb() {
		return Web;
	}

	public void setWeb(String web) {
		Web = web;
	}
}


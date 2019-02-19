package models;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import asignacion.Decision;
import asignacion.PorFecha;

public class ModeloTrazaapp {

	private MySQL sql = new MySQL("trazaapp2", "juan", "juan");
	private Decision asignacionPeticiones;

	// ------- patron singleton -------
	static ModeloTrazaapp model = null;

	public static ModeloTrazaapp getInstanceTrazaapp() {
		if (model == null) {
			model = new ModeloTrazaapp();
		}
		return model;
	}

	private ModeloTrazaapp() {
	}

	// -------- +++++++++++++ --------
	
	public Decision getAsignacionPeticiones() {
		return asignacionPeticiones;
	}

	public void setAsignacionPeticiones(Decision asignacionPeticiones) {
		this.asignacionPeticiones = asignacionPeticiones;
	}
	
	
	
	// ------- ++++++++++++++++ -------

	public LoginToken attemptLogin(String email, String password) {

		try {
			String query = "SELECT * FROM USERS " + "WHERE email='" + email + "'" + "  AND PASSWORD='" + password + "'";
			System.out.println(query);
			ResultSet resultSet = sql.getResultset(query);

			if (resultSet.next()) {
				System.out.println("Existe el usuario");
				// conn.close();
				boolean admin = false;
				System.out.println("Es admin:" + resultSet.getString("role_id"));
				if (resultSet.getInt("role_id") == 1) {
					admin = true;
				}

				Persona usuario = new Persona();
				usuario.setId(resultSet.getInt("id"));
				usuario.setUsername(resultSet.getString("username"));
				usuario.setPassword(resultSet.getString("password"));
				usuario.setName(resultSet.getString("name"));
				usuario.setPhone(resultSet.getString("phone"));
				usuario.setDireccion(resultSet.getString("address"));
				usuario.setCiudad(resultSet.getInt("location_id"));
				usuario.setAdmin(admin);
				usuario.setEmail(resultSet.getString("email"));
				usuario.setLatitude(resultSet.getDouble("latitude"));
				usuario.setLongitude(resultSet.getDouble("longitude"));
				LoginToken result = new LoginToken();
				result.setMensaje("login Exitoso");
				result.setStatus(200);
				result.setUsuario(usuario);
				return result;
			} else {
				System.out.println("Usuario no-valido");
				LoginToken result = new LoginToken();
				result.setMensaje("Usuario y contraseña no coinciden");
				result.setStatus(401);
				result.setUsuario(new Persona());
				return result;

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}



	public ResourceToken getResources() {

		String query = "SELECT id,name,fraccionario FROM RESOURCES";
		System.out.println(query);
		ResultSet resultSet = sql.getResultset(query);
		ArrayList<Resource> recursos = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Resource rec = new Resource();
				rec.setId(resultSet.getInt("id"));
				rec.setNombre(resultSet.getString("name"));
				rec.setFraccionario(resultSet.getBoolean("fraccionario"));
				recursos.add(rec);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ResourceToken rt = new ResourceToken();
		rt.setMensaje("Recursos cargados");
		rt.setResources(recursos.toArray(new Resource[recursos.size()]));
		// rt.setResources((Resource[]) recursos.toArray());
		rt.setStatus(200);

		return rt;

	}

	public boolean existeUsuario(String email) {

		try {
			String query = "SELECT * FROM USERS " + "WHERE email='" + email + "'";
			System.out.println(query);
			ResultSet resultSet = sql.getResultset(query);

			if (resultSet.next()) {
				System.out.println("Existe el usuario");
				return true;
			} else {
				System.out.println("Usuario no existe");
				return false;

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}

	public SimpleToken registerUser(String email, String password, String usuario, String nombre, String direccion,
			String telefono, int institucion, String web) {
		// TODO Auto-generated method stub
		if (existeUsuario(email)) {
			SimpleToken result = new SimpleToken();
			result.setMensaje("El email ya existe");
			result.setStatus(400);
			// mandar token diciendo q no se pudo registrar usuario
			return result;
		} else {
			String query = "";
			if (!web.equals("")) {
				query = "INSERT INTO USERS(role_id,username,password,name,email,address,phone,"
						+ "id_institucion) " + "VALUES(2,'" + usuario + "','" + password + "'," + "'" + nombre + "','"
						+ email + "'," + "'" + direccion + "','" + telefono + "'," + institucion + ",'" + web + "')";
			} else {
				query = "INSERT INTO USERS(role_id,username,password,name,email,address,phone,"
						+ "id_institucion) " + "VALUES(2,'" + usuario + "','" + password + "'," + "'" + nombre + "','"
						+ email + "'," + "'" + direccion + "','" + telefono + "'," + institucion + ")";
			}
			sql.executeQuery(query);
			SimpleToken result = new SimpleToken();
			result.setMensaje("Usuario creado correctamente");
			result.setStatus(200);

			return result;
		}
	}

	public SimpleToken addPackage(double cantidad, Integer idPadre, int idResource, int user) throws SQLException {
		String query;
		if (idPadre != null) {
			query = "INSERT INTO PACKAGE(cantidad,id_package_padre,id_resource) " + "VALUES('" + cantidad + "','"
					+ idPadre.intValue() + "','" + idResource + "');";
		} else {
			query = "INSERT INTO PACKAGE(cantidad,id_resource,enUso) " + "VALUES('" + cantidad + "','" + idResource
					+ "','0');";
		}

		String query2 = "SELECT id_package AS LastID FROM package WHERE id_package = @@Identity;";
		ResultSet resultSet = sql.compoundQuery(query, query2);
		SimpleToken result = new SimpleToken();
		if (resultSet.next())
			result.setId(resultSet.getInt("LastID"));
		result.setMensaje("Paquete creado correctamente");
		result.setStatus(200);
		sendPackage(resultSet.getInt("LastID"), idResource, cantidad, user, user);
		return result;

	}

	public SimpleToken sendPackage(int idPackage, int resource, double cantidad, int origen, int destino) {
		// TODO Auto-generated method stub
		String query = "CALL envioProcedure(" + idPackage + "," + resource + "," + cantidad + "," + origen + ","
				+ destino + ");";
		sql.executeQuery(query);
		SimpleToken result = new SimpleToken();
		result.setMensaje("Envio registrado correctamente");
		result.setStatus(200);
		return result;
	}

	public SimpleToken addPeticion(int idResource, int idUser, double cantidad){
		String query;

		query = "INSERT INTO PETICION(id_resource,id_user,cantidad) " + "VALUES('" + idResource + "','" + idUser + "','"
				+ cantidad + "')";

		sql.executeQuery(query);
		SimpleToken result = new SimpleToken();
		result.setMensaje("Peticion creada exitosamente");
		result.setStatus(200);

		return result;
	}

	public PackageToken getOwnPackage(int p) {

		String query = "SELECT e.id_envio, MAX(e.fecha) as fecha_reciente,rr.id_origen, rr.id_destino "
				+ ", p.* from ENVIO e JOIN Resource_Route rr ON (e.id_envio = rr.id_envio) JOIN Package p"
				+ " ON(e.id_package = p.id_package) WHERE rr.id_destino = '" + p + "' GROUP BY (e.id_package)";
		System.out.println(query);
		ResultSet resultSet = sql.getResultset(query);
		ArrayList<Paquete> paquetes = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Paquete paq = new Paquete();
				System.out.println("--");
				paq.setId(resultSet.getInt("id_package"));
				paq.setCantidad(resultSet.getDouble("cantidad"));
				paq.setIdPadre(resultSet.getInt("id_package_padre"));
				paq.setIdResource(resultSet.getInt("id_resource"));
				paq.setEnUso(resultSet.getDouble("enUso"));
				paquetes.add(paq);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PackageToken pt = new PackageToken();
		pt.setMensaje("Paquetes obtenidos");
		pt.setPackages(paquetes.toArray(new Paquete[paquetes.size()]));
		// rt.setResources((Resource[]) recursos.toArray());
		pt.setStatus(200);

		return pt;

	}

	public SimpleToken fillPeticion(int id_peticion, int id_paquete) {
		// id_resource cantidad
		String query;
		query = "UPDATE PETICION " + "SET id_paquete = " + id_paquete + " WHERE id_peticion = '" + id_peticion + "'";

		sql.executeQuery(query);
		SimpleToken result = new SimpleToken();
		result.setMensaje("Peticion correspondida");
		result.setStatus(200);
		return result;

	}

	public PetitionToken getOwnPetitions(int user) {
		String query = "SELECT r.id as id_resource,r.name AS recurso, r.fraccionario, pe.id_peticion,pe.cantidad AS cantidadPedida, pa.id_package,pa.cantidad,"
				+ " rr.id_destino FROM (((SELECT * FROM PETICION WHERE id_user = " + user
				+ " AND completada = FALSE) AS pe INNER JOIN (SELECT * from envio e where NOT EXISTS"
				+ " (SELECT 1 from envio e2 where e.id_package = e2.id_package AND e2.fecha > e.fecha)) AS e ON (pe.id_paquete = e.id_package)"
				+ " INNER JOIN resource_route rr ON(e.id_envio = rr.id_envio)) INNER JOIN resources r ON (pe.id_resource =r.id))"
				+ " INNER JOIN package pa ON(pe.id_paquete = pa.id_package)";
		System.out.println(query);
		ResultSet resultSet = sql.getResultset(query);
		ArrayList<Peticion> peticiones = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Peticion pet = new Peticion();
				System.out.println("--");
				pet.setId_peticion(resultSet.getInt("id_peticion"));
				pet.setCantidad(resultSet.getDouble("cantidadPedida"));
				pet.setRecurso(resultSet.getString("recurso"));
				pet.setId_origen(resultSet.getInt("id_destino"));
				pet.setId_package(resultSet.getInt("id_package"));
				pet.setId_recurso(resultSet.getInt("id_resource"));
				pet.setFraccionario(resultSet.getBoolean("fraccionario"));
				peticiones.add(pet);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PetitionToken pt = new PetitionToken();
		pt.setMensaje("Peticiones obtenidas");
		pt.setPeticiones(peticiones.toArray(new Peticion[peticiones.size()]));
		// rt.setResources((Resource[]) recursos.toArray());
		pt.setStatus(200);

		return pt;

	}

	public LoginToken getUserInfo(int user) {
		String query = "SELECT id,name,phone,address,location_id FROM users where id = " + user;
		System.out.println(query);
		ResultSet resultSet = sql.getResultset(query);
		try {
			if (resultSet.next()) {
				Persona usuario = new Persona();
				usuario.setId(resultSet.getInt("id"));
				usuario.setName(resultSet.getString("name"));
				usuario.setPhone(resultSet.getString("phone"));
				usuario.setDireccion(resultSet.getString("address"));
				usuario.setCiudad(resultSet.getInt("location_id"));
				LoginToken result = new LoginToken();
				result.setMensaje("Datos del usuario recuperados exitosamente");
				result.setStatus(200);
				result.setUsuario(usuario);
				return result;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public RouteToken getPackageRoute(int paquete) {
		String query = "CALL getPackageRouteProcedure(" + paquete + ");";
		System.out.println(query);
		ResultSet resultSet = sql.getResultset(query);
		ArrayList<Route> rutas = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Route ruta = new Route();
				ruta.setId(resultSet.getInt("id_envio"));
				ruta.setAddress(resultSet.getString("address"));
				ruta.setFecha(resultSet.getString("fecha"));
				ruta.setId_destino(resultSet.getInt("id_destino"));
				ruta.setId_origen(resultSet.getInt("id_origen"));
				ruta.setLatitude(resultSet.getDouble("latitude"));
				ruta.setLongitude(resultSet.getDouble("longitude"));
				ruta.setNombre_destino(resultSet.getString("nombre_origen"));
				ruta.setNombre_origen(resultSet.getString("nombre_destino"));
				ruta.setId_package(resultSet.getInt("id_package"));
				ruta.setId_package_padre(resultSet.getInt("id_package_padre"));
				rutas.add(ruta);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RouteToken rt = new RouteToken();
			rt.setMensaje("Ha ocurrido un error en la base de datos.");
			rt.setStatus(404);
		}

		RouteToken rt = new RouteToken();
		rt.setMensaje("La ruta del paquete fue obtenida exitosamente");
		rt.setNodos(rutas.toArray(new Route[rutas.size()]));
		rt.setStatus(200);

		return rt;
	}

	public ResourceToken getOwnResources(int user) {

		String query = "SELECT r.id,r.name FROM (SELECT id_envio " + " FROM resource_route as RR WHERE id_origen = "
				+ user + " OR id_destino = " + user + ") as RR"
				+ " JOIN envio e ON(RR.id_envio = e.id_envio) JOIN Package p ON (e.id_package = p.id_package)"
				+ " JOIN Resources r ON(r.id = p.id_resource) " + " GROUP BY r.name,r.id" + " ORDER BY r.name";
		System.out.println(query);
		ResultSet resultSet = sql.getResultset(query);
		ArrayList<Resource> recursos = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Resource rec = new Resource();
				rec.setId(resultSet.getInt("id"));
				rec.setNombre(resultSet.getString("name"));
				recursos.add(rec);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ResourceToken rt = new ResourceToken();
		rt.setMensaje("Recursos cargados");
		rt.setResources(recursos.toArray(new Resource[recursos.size()]));
		rt.setStatus(200);
		return rt;

	}

	public PackageToken getOwnPackageByResource(int user, int resource) {

		String query = "SELECT p.* FROM (SELECT id_envio " + " FROM resource_route as RR WHERE id_origen = " + user
				+ " OR id_destino = " + user + ") as RR"
				+ " INNER JOIN envio e ON(RR.id_envio = e.id_envio) INNER JOIN Package p ON (e.id_package = p.id_package)"
				+ " INNER JOIN Resources r ON(r.id = p.id_resource) " + " WHERE r.id = " + resource
				+ " GROUP BY p.id_package";
		System.out.println(query);
		ResultSet resultSet = sql.getResultset(query);
		ArrayList<Paquete> paquetes = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Paquete paq = new Paquete();
				System.out.println("--");
				paq.setId(resultSet.getInt("id_package"));
				paq.setCantidad(resultSet.getDouble("cantidad"));
				paq.setIdPadre(resultSet.getInt("id_package_padre"));
				paq.setIdResource(resultSet.getInt("id_resource"));
				paq.setEnUso(resultSet.getDouble("enUso"));
				paquetes.add(paq);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PackageToken pt = new PackageToken();
		pt.setMensaje("Paquetes obtenidos");
		pt.setPackages(paquetes.toArray(new Paquete[paquetes.size()]));
		// rt.setResources((Resource[]) recursos.toArray());
		pt.setStatus(200);

		return pt;

	}

	public ResourceToken getCurrentOwnResources(int user) {
		String query = "SELECT p.id_package, r.id, r.name, r.fraccionario FROM (SELECT id_envio "
				+ " FROM resource_route as RR WHERE id_destino = " + user + ") as RR"
				+ " JOIN envio e ON(RR.id_envio = e.id_envio) JOIN Package p ON (e.id_package = p.id_package)"
				+ " JOIN Resources r ON(r.id = p.id_resource)" + " WHERE p.alive AND NOT EXISTS ( select 1"
				+ " FROM envio e2" + " WHERE e.id_package = e2.id_package AND e.fecha < e2.fecha)"
				+ " GROUP BY p.id_package,r.name,r.id" + " ORDER BY r.name";
		System.out.println(query);
		ResultSet resultSet = sql.getResultset(query);
		ArrayList<Resource> recursos = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Resource rec = new Resource();
				rec.setId(resultSet.getInt("id"));
				rec.setNombre(resultSet.getString("name"));
				rec.setFraccionario(resultSet.getBoolean("fraccionario"));
				recursos.add(rec);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ResourceToken rt = new ResourceToken();
		rt.setMensaje("Recursos cargados");
		rt.setResources(recursos.toArray(new Resource[recursos.size()]));
		rt.setStatus(200);
		return rt;

	}

	public PackageToken getCurrentOwnPackageByResource(int user, int resource) {

		String query = "SELECT p.id_package,p.cantidad,p.id_package_padre,p.id_resource,p.enUso FROM (SELECT id_envio "
				+ " FROM resource_route as RR WHERE id_origen = " + user + " OR id_destino = " + user + ") as RR"
				+ " INNER JOIN envio e ON(RR.id_envio = e.id_envio) INNER JOIN Package p ON (e.id_package = p.id_package)"
				+ " INNER JOIN Resources r ON(r.id = p.id_resource)" + " WHERE r.id = " + resource
				+ " AND p.alive AND NOT EXISTS ( select 1" + " FROM envio e2"
				+ " WHERE e.id_package = e2.id_package AND e.fecha < e2.fecha)"
				+ " GROUP BY p.id_package,p.cantidad,p.id_package_padre,p.id_resource,p.enUso";
		System.out.println(query);
		ResultSet resultSet = sql.getResultset(query);
		ArrayList<Paquete> paquetes = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Paquete paq = new Paquete();
				System.out.println("--");
				paq.setId(resultSet.getInt("id_package"));
				paq.setCantidad(resultSet.getDouble("cantidad"));
				paq.setIdPadre(resultSet.getInt("id_package_padre"));
				paq.setIdResource(resultSet.getInt("id_resource"));
				paq.setEnUso(resultSet.getDouble("enUso"));
				paquetes.add(paq);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PackageToken pt = new PackageToken();
		pt.setMensaje("Paquetes obtenidos");
		pt.setPackages(paquetes.toArray(new Paquete[paquetes.size()]));
		// rt.setResources((Resource[]) recursos.toArray());
		pt.setStatus(200);

		return pt;

	}

	public SimpleToken updateCurrentAmountPackage(int id_package, double enUso) {
		String query;

		query = "UPDATE PACKAGE  SET enUso = " + enUso + " where id_package = " + id_package;
		
	
		sql.executeQuery(query);
		SimpleToken result = new SimpleToken();
		result.setMensaje("Paquete actualizado exitosamente");
		result.setStatus(200);

		return result;
	}

	public SimpleToken unsubscribeObject(int id_package, double cantidad, double enUso, int resource, int user) {
		// TODO Auto-generated method stub
		String query = "CALL brokenObjectProcedure(" + id_package + "," + resource + "," + cantidad + "," + enUso + ","
				+ user + ");";
		sql.executeQuery(query);
		SimpleToken result = new SimpleToken();
		result.setMensaje("Objeto defectuoso reportado correctamente");
		result.setStatus(200);
		return result;

	}

	public InstitucionToken getInstituciones() {
		String query = "SELECT id,name FROM institutions ORDER BY name;";
		System.out.println(query);
		ResultSet resultSet = sql.getResultset(query);
		ArrayList<Institucion> instituciones = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Institucion inst = new Institucion();
				inst.setId(resultSet.getInt("id"));
				inst.setNombre(resultSet.getString("name"));
				instituciones.add(inst);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RouteToken it = new RouteToken();
			it.setMensaje("Ha ocurrido un error en la base de datos.");
			it.setStatus(404);
		}

		InstitucionToken rt = new InstitucionToken();
		rt.setMensaje("Las instituciones fueron obtenidas exitosamente exitosamente");
		rt.setInstituciones(instituciones.toArray(new Institucion[instituciones.size()]));
		rt.setStatus(200);

		return rt;
	}

	public SimpleToken checkPeticion(int peticion) {
		String query;

		query = "UPDATE PETICION  SET completada = TRUE " + " where id_peticion = " + peticion;

		sql.executeQuery(query);
		SimpleToken result = new SimpleToken();
		result.setMensaje("Peticion actualizada exitosamente");
		result.setStatus(200);

		return result;
	}
}

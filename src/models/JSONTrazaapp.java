package models;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;



@Path("/trazaapp")
public class JSONTrazaapp {


	@POST
	@Path("/attemptlogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LoginToken attemptLogin(String request){
		System.out.println(request);
		new JSONObject();
		JSONObject json;
		try {
			json = new JSONObject(request);
			return ModeloTrazaapp.getInstanceTrazaapp().attemptLogin(json.getString("email"), json.getString("password"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	@POST
	@Path("/registeruser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SimpleToken registerUser(String request){
		System.out.println(request);
		new JSONObject();
		JSONObject json;
		try {
			json = new JSONObject(request);
			return ModeloTrazaapp.getInstanceTrazaapp().registerUser(json.getString("email"), json.getString("password"), json.getString("usuario"), json.getString("nombre"), json.getString("direccion"), json.getString("telefono"), json.getInt("ciudad"), json.getString("web"), json.getDouble("latitud"), json.getDouble("longitud"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@POST
	@Path("/addpackage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SimpleToken addPackage(String request){
		System.out.println(request);
		new JSONObject();
		JSONObject json;
		try {
			json = new JSONObject(request);
//			Integer padre = new Integer(0);
//			if (json.has("padre")){
//				padre = (json.getInt("padre"));
//			}else{
//				padre = null;
//			}
			
			return ModeloTrazaapp.getInstanceTrazaapp().addPackage(json.getInt("cantidad"),null,json.getInt("idResource"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	@GET
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public LoginToken getCulturales(){
		System.out.println("hola");
		return ModeloTrazaapp.getInstanceTrazaapp().attemptLogin("maga@maga.com", "magama");
	}
}
package models;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	
	@POST
	@Path("/addpeticion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SimpleToken addPeticion(String request){
		System.out.println(request);
		new JSONObject();
		JSONObject json;
		try {
			json = new JSONObject(request);
			return ModeloTrazaapp.getInstanceTrazaapp().addPeticion(json.getInt("idResource"), json.getInt("idUser"), json.getInt("cantidad"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@POST
	@Path("/fillpeticion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SimpleToken fillPeticion(String request){
		System.out.println("holis");
		System.out.println(request);
		new JSONObject();
		JSONObject json;
		try {
			json = new JSONObject(request);
			return ModeloTrazaapp.getInstanceTrazaapp().fillPeticion(json.getInt("id_peticion"), json.getInt("id_paquete"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@GET
	@Path("/getresources")
	@Produces(MediaType.APPLICATION_JSON)
	public ResourceToken getResources(){
		System.out.println("--GET Resources--");
		return ModeloTrazaapp.getInstanceTrazaapp().getResources();
	}
	
	@GET
	@Path("/getownpackages/{v}")
	@Produces(MediaType.APPLICATION_JSON)
	public PackageToken getOwnPackages(@PathParam("v") int p){
		System.out.println("--GET own package -User :--" + p);
		return ModeloTrazaapp.getInstanceTrazaapp().getOwnPackage(p);
	}
	
	
	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCulturales(){
		System.out.println("hola");
		return "funciona!";
	}
}
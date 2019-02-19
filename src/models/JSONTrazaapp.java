package models;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import asignacion.PorFecha;

import org.json.*;



@Path("/trazaapp")
public class JSONTrazaapp {
	
	public JSONTrazaapp(){
		System.out.println("creando asignador...<--------");
		ModeloTrazaapp.getInstanceTrazaapp().setAsignacionPeticiones(new PorFecha(new MySQL("trazaapp2", "juan", "juan")));
		(new Thread(ModeloTrazaapp.getInstanceTrazaapp().getAsignacionPeticiones())).start();
	}
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
			return ModeloTrazaapp.getInstanceTrazaapp().registerUser(json.getString("email"), json.getString("password"), json.getString("usuario"), json.getString("nombre"), json.getString("direccion"), json.getString("telefono"), json.getInt("institucion"),json.getString("web"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@GET
	@Path("/getinstituciones")
	@Produces(MediaType.APPLICATION_JSON)
	public InstitucionToken getInstituciones(@PathParam("v") int p){
		System.out.println("--GET own package -User :--" + p);
		return ModeloTrazaapp.getInstanceTrazaapp().getInstituciones();
	}
	
	@POST
	@Path("/addpackage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SimpleToken addPackage(String request) throws SQLException{
		System.out.println("addPackage : "+request);
		new JSONObject();
		JSONObject json;
		try {
			json = new JSONObject(request);			
			return ModeloTrazaapp.getInstanceTrazaapp().addPackage(json.getDouble("cantidad"),null,json.getInt("idResource"),json.getInt("id_user"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SimpleToken error = new SimpleToken();
			error.setStatus(400);
			error.setMensaje("Error al enviar peticion para agregar paquete.");
			return error;
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
			return ModeloTrazaapp.getInstanceTrazaapp().addPeticion(json.getInt("idResource"), json.getInt("idUser"), json.getDouble("cantidad"));
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
	@Path("/getownpetitions/{v}")
	@Produces(MediaType.APPLICATION_JSON)
	public PetitionToken getOwnPetitions(@PathParam("v") int user){
		System.out.println("--GET own petitions -User :--" + user);
		return ModeloTrazaapp.getInstanceTrazaapp().getOwnPetitions(user);
	}
	
	

	@POST
	@Path("/sendpackage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SimpleToken sendPackage(String request){
		System.out.println(request);
		new JSONObject();
		JSONObject json;
		try {
			json = new JSONObject(request);
			return ModeloTrazaapp.getInstanceTrazaapp().sendPackage(json.getInt("id_package"),json.getInt("id_resource"),json.getDouble("cantidad"),json.getInt("id_origen"), json.getInt("id_destino"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@GET
	@Path("/getuserinfo/{v}")
	@Produces(MediaType.APPLICATION_JSON)
	public LoginToken getUserInfo(@PathParam("v") int user){
		System.out.println("--GET user info :--" + user);
		return ModeloTrazaapp.getInstanceTrazaapp().getUserInfo(user);
	}
	
	@GET
	@Path("/getpackageroute/{v}")
	@Produces(MediaType.APPLICATION_JSON)
	public RouteToken getPackageRoute(@PathParam("v") int p){
		System.out.println("--GET package route :--" + p);
		return ModeloTrazaapp.getInstanceTrazaapp().getPackageRoute(p);
	}
	
	@GET
	@Path("/getownresources/{v}")
	@Produces(MediaType.APPLICATION_JSON)
	public ResourceToken getOwnResources(@PathParam("v") int user){
		System.out.println("--GET own resources :--" + user );
		return ModeloTrazaapp.getInstanceTrazaapp().getOwnResources(user);
	}
	
	@POST
	@Path("/getownpackagebyresource")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PackageToken getOwnPackageByResource(String request){
		System.out.println("--GET own packages by resource --" );
		System.out.println(request);
		new JSONObject();
		JSONObject json;
		try {
			json = new JSONObject(request);
			return ModeloTrazaapp.getInstanceTrazaapp().getOwnPackageByResource(json.getInt("user"), json.getInt("resource"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
}
	
	@GET
	@Path("/getcurrentownresources/{v}")
	@Produces(MediaType.APPLICATION_JSON)
	public ResourceToken getCurrentOwnResources(@PathParam("v") int user){
		System.out.println("--GET CURRENT own resources :--" + user );
		return ModeloTrazaapp.getInstanceTrazaapp().getCurrentOwnResources(user);
	}
	
	@POST
	@Path("/getcurrentownpackagebyresource")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PackageToken getCurrentOwnPackageByResource(String request){
		System.out.println("--GET CURRENT own packages by resource --" );
		System.out.println(request);
		new JSONObject();
		JSONObject json;
		try {
			json = new JSONObject(request);
			return ModeloTrazaapp.getInstanceTrazaapp().getCurrentOwnPackageByResource(json.getInt("user"), json.getInt("resource"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
}
	
	@POST
	@Path("/updateamountpackage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SimpleToken updateCurrentAmountPackage(String request){
		System.out.println("--Update Current Amount Package--" );
		System.out.println(request);
		new JSONObject();
		JSONObject json;
		try {
			json = new JSONObject(request);
			return ModeloTrazaapp.getInstanceTrazaapp().updateCurrentAmountPackage(json.getInt("id_package"), json.getDouble("enUso"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
}
	
	@POST
	@Path("/unsubscribeobject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SimpleToken unsubscribeObject(String request){
		System.out.println("--UNSUBSCRIBE Object --" );
		System.out.println(request);
		new JSONObject();
		JSONObject json;
		try {
			json = new JSONObject(request);
			return ModeloTrazaapp.getInstanceTrazaapp().unsubscribeObject(json.getInt("id_package"), json.getDouble("cantidad"),json.getDouble("enUso"),json.getInt("resource"),json.getInt("user"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
}
	@GET
	@Path("/checkpeticion/{v}")
	@Produces(MediaType.APPLICATION_JSON)
	public SimpleToken checkPeticion(@PathParam("v") int peticion){
		System.out.println("--Check peticion :--" + peticion );
		return ModeloTrazaapp.getInstanceTrazaapp().checkPeticion(peticion);
	}
	
	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCulturales(){
		System.out.println("hola");
		return "funciona!";
	}
}
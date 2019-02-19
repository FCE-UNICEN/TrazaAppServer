package asignacion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import models.MySQL;
import models.Paquete;
import models.Peticion;

public abstract class Decision implements Runnable {
	
	protected MySQL sql;

	public ArrayList<Peticion> getPeticiones(){
		String query = "SELECT * FROM PETICION WHERE completada = FALSE AND id_paquete IS NULL" ;
		System.out.println(query);
		ResultSet resultSet = sql.getResultset(query);
		ArrayList<Peticion> peticiones = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Peticion pet = new Peticion();
				System.out.println("--");
				pet.setId_peticion(resultSet.getInt("id_peticion"));
				pet.setCantidad(resultSet.getDouble("cantidad"));
				pet.setId_recurso(resultSet.getInt("id_resource"));
				peticiones.add(pet);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return peticiones;
	}
	
	public ArrayList<Paquete> getPaquetes(int recurso){
		String query = "SELECT * FROM PACKAGE WHERE id_resource = " + recurso + " AND ALIVE = TRUE";
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
		sql.CloseConecction();
		return paquetes;
	}
	
	public void asignarPaquete(int peticion, int paquete){
		String query;
		System.out.println("asignando paquete " + paquete + " a la peticion " + peticion);

		query = "UPDATE PETICION  SET id_paquete = " + paquete + " where id_peticion = " + peticion;

		sql.executeQuery(query);
	}
	
	public abstract void priorizarPeticiones(ArrayList<Peticion> peticiones);
	
	@Override
	public void run() {
		while(true){
			System.out.println("Buscando Paquetes que cumplan peticiones pendientes...");
			ArrayList<Peticion> peticiones = getPeticiones();
			ArrayList<Peticion> peticionesParciales = new ArrayList<>();
			int idRecurso = 0;
			int idPeticion = 0;
			for(Peticion p : peticiones){
				idRecurso = p.getId_recurso();
				idPeticion = p.getId_peticion();
				peticionesParciales.add(p);
				//peticiones.remove(p);
				for(Peticion p2 : peticiones){
					if(p2.getId_recurso()==idRecurso && p2.getId_peticion()!=idPeticion){
						peticionesParciales.add(p2);
						peticiones.remove(p2);
					}
				}
				
				ArrayList<Paquete> paquetes = getPaquetes(idRecurso);
				Collections.sort(paquetes, new Comparator<Paquete>() {
				    public int compare(Paquete p1, Paquete p2) {
				        return new Double(p1.getCantidad()).compareTo(new Double(p2.getCantidad()));
				    }
				});
				
				priorizarPeticiones(peticionesParciales);
				
				for(Peticion p2: peticionesParciales){
					for(Paquete pa : paquetes){
						if(p2.getCantidad() <= pa.getCantidad()){
							asignarPaquete(p2.getId_peticion(),pa.getId()); 
							paquetes.remove(pa);
							break;
						}
					}
				}
			}
			
			try {
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}

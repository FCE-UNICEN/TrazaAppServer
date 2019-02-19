package asignacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.jar.Pack200;

import models.MySQL;
import models.Paquete;
import models.Peticion;

public class PorFecha extends Decision {

	public PorFecha(MySQL sql) {
		super();
		this.sql = sql;
		// TODO Auto-generated constructor stub
	}

	
	public void priorizarPeticiones(ArrayList<Peticion> peticionesParciales) {
		Collections.sort(peticionesParciales, new Comparator<Peticion>() {
		    public int compare(Peticion p1, Peticion p2) {
		        return (-1)*new Integer(p1.getId_peticion()).compareTo(new Integer(p2.getId_peticion()));
		    }
		});
	}

}

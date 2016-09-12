package app;

import jello.model.JelloEntity;
import jello.model.JelloModel;
import jello.rest.IllegalRequestResource;
import jello.annotation.Expose;
import jello.security.Accessible;
import jello.ux.Validation;
import jello.annotation.KeyElement;
import jello.security.Role;
import jello.annotation.Required;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Accessible( Role.ALL )public class Mesa extends JelloEntity {

	private static final long serialVersionUID = -9059203288971562512L;
	
	@Validation(min=2,max=6)
	@Expose
	@Required
	public Integer cantidad_personas;

	@Validation(minlength=1,maxlength=25)
	@Expose
	@KeyElement
	public String nro_mesa;

	@Validation(min=1,max=2)
	@Expose
	public Integer ubicacion;

	
	@Expose
	@Required
	public Boolean reservado;
	
	@Expose
	@Accessible
	public static Boolean esta_disponible(String nro_mesa) throws IllegalRequestResource{
		//Verifica si una mesa esta disponible, si lo esta retorna true, si no retorna false
		Mesa mesa = (Mesa) JelloModel.getInstance(Mesa.class, nro_mesa);
		if(mesa.reservado){//Si esta reservado, la mesa no esta disponible
			return false;
		}
		else{//si no esta reservado, la mesa esta dispobnible
			return true;
		}
	}
	
	@Expose
	@Accessible
	public static String liberar_mesa(String nro_mesa) throws IllegalRequestResource{
		//libera una mesa cambiando su estado
		Mesa mesa = (Mesa) JelloModel.getInstance(Mesa.class, nro_mesa);
		if(mesa.reservado){
			mesa.reservado = false;
			mesa.update(mesa, mesa.toJson());
		}
		return mesa.toJson();
	}
	
	@Expose
	@Accessible
	public static Mesa get_mesa(String nro_mesa)throws IllegalRequestResource{
		//Retorna una mesa por su nro de mesa
		Mesa mesa = (Mesa)JelloModel.getInstance(Mesa.class, nro_mesa);
		return mesa;
	}
	
	@Expose
	@Accessible
	public static List<Mesa> consultar_mesas_libres(){
		//Retorna todas las mesas libres
		@SuppressWarnings("unchecked")
		List<Mesa> mesas_disponibles = (List<Mesa>) JelloModel.select(Mesa.class, "reservado==false");
		return mesas_disponibles;
	}

}

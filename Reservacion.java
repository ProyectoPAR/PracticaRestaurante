package app;

import jello.model.JelloEntity;
import jello.model.JelloModel;
import jello.rest.IllegalRequestResource;
import jello.annotation.Expose;
import jello.security.Accessible;
import jello.ux.Preview;
import com.google.appengine.api.datastore.Key;
import jello.ux.Validation;
import jello.annotation.Reference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jello.annotation.KeyElement;
import jello.security.Role;
import jello.annotation.Required;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Accessible( Role.ALL )public class Reservacion extends JelloEntity {

	private static final long serialVersionUID = -7977800024408889126L;

	@Expose
	@Required
	public Date fecha;

	@Expose
	@Required
	public String hora;

	@Validation(min=1)
	@Expose
	@KeyElement
	public Integer id_reservacion;
	
	@Validation(min=1)
	@Expose
	@NotPersistent
	public Double monto_total;

	@Reference(Mesa.class)
	@Preview(element="nro_mesa")
	@Expose
	public Key nro_mesa;

	@Reference(Cliente.class)
	@Preview(element="id_cliente")
	@Expose
	@Required
	public Key reservado_a;
	
	@Expose
	@NotPersistent
	@Reference(Orden.class)
	public String nro_orden;
	
	@Expose
	@Accessible
	public String hacer_pedido(String cod_menu) throws IllegalRequestResource{
		//Reescribir el metodo, no funciona
		Menu menu = (Menu)JelloModel.getInstance(Menu.class, cod_menu);
		Orden orden = new Orden();
		orden.id_cliente = this.reservado_a;
		orden.retirado =  false;
		orden.nro_orden = orden.id_cliente.getName()+Integer.toString(id_reservacion);
		orden.create();
		String orden_json = Orden.ordenar(orden.nro_orden, menu);
		this.nro_orden = orden.nro_orden;
		System.out.println(orden.toJson());
		this.update(this, this.toJson());
		return this.toJson();
	}
	
	@Expose
	@Accessible
	public String cancelar_reservacion() throws IllegalRequestResource{
		//Cancela una reserva eliminandola del kind
		this.delete();
		return "Reservacion Eliminada";
	}
	
	@Expose
	@Accessible
	public String hacer_reserva(String nro_mesa)throws IllegalRequestResource{
		//Reserva una mesa si esta libre
		if(Mesa.esta_disponible(nro_mesa)){
			this.nro_mesa = Mesa.get_mesa(nro_mesa).getKey();
			System.out.println(Mesa.get_mesa(nro_mesa).getKey());
			this.save();
		}
		return this.toJson();
	}
	
	//Se encarga de verificar si la mesa selecciona esta libre, si no lo esta no guarda ninguna mesa
	@Override
	protected JelloEntity beforeSave() throws IllegalRequestResource {
		// TODO Auto-generated method stub
		System.out.println("Hola carajo!!!"+this.nro_mesa.getName());
		if(!Mesa.esta_disponible(nro_mesa.getName())){
			this.nro_mesa = null;
		}
		return this;
	}
	

}

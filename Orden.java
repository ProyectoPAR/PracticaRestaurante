package app;

import jello.model.JelloEntity;
import jello.model.JelloModel;
import jello.rest.IllegalRequestResource;
import jello.annotation.Expose;
import jello.security.Accessible;
import jello.ux.Validation;
import jello.ux.Preview;
import com.google.appengine.api.datastore.Key;
import jello.annotation.Reference;
import jello.annotation.KeyElement;
import jello.security.Role;
import jello.annotation.Required;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Accessible( Role.ALL )public class Orden extends JelloEntity {

	private static final long serialVersionUID = -6703429874798960818L;

	
	@Reference(Cliente.class)
	@Preview(element="id_cliente")
	@Expose
	@Required
	public Key id_cliente;

	

	@Validation(min=1)
	@Expose
	@KeyElement
	public String nro_orden;

	@Expose
	@Required
	public Boolean retirado;
	
	@Expose
	List<MenuOrden> menus_ordenados = new ArrayList<MenuOrden>();
	
	@PersistenceCapable
	static class MenuOrden{
		Double precio;
		String nombre;
		String ilustracion;
		Integer id_producto;
		String descripcion;
	}
	
	@Expose
	@Accessible
	public static String ordenar(String nro_orden, Menu menu) throws IllegalRequestResource{
		Orden orden = (Orden) JelloModel.getInstance(Orden.class, nro_orden);
		MenuOrden menu_orden = new MenuOrden();
		menu_orden.id_producto = menu.id_producto;
		menu_orden.descripcion = menu.descripcion;
		menu_orden.ilustracion = menu.ilustracion;
		menu_orden.nombre = menu.nombre;
		menu_orden.precio = menu.precio;
		orden.menus_ordenados.add(menu_orden);
		System.out.println(orden.toJson());
		orden.update(orden, orden.toJson());
		return orden.toJson();
	}
	
	@Expose
	@Accessible
	public String eliminar_orden() throws IllegalRequestResource{
		this.delete();
		return this.toJson();
	}
	
	@Override
	protected JelloEntity beforeSave() throws IllegalRequestResource {
		// TODO Auto-generated method stub
		return this;
	}



}

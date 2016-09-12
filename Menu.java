package app;

import jello.model.JelloEntity;
import jello.rest.IllegalRequestResource;
import jello.annotation.Expose;
import jello.annotation.KeyElement;
import jello.security.Accessible;
import jello.ux.Validation;
import jello.annotation.Attachment;
import jello.security.Role;
import jello.annotation.Required;

import javax.jdo.annotations.PersistenceCapable;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Accessible( Role.ALL )public class Menu extends JelloEntity {

	private static final long serialVersionUID = -9022992697771519746L;
	
	
	@Expose
	public String descripcion;

	@Validation(min=1)
	@Expose
	@Required
	@KeyElement
	public Integer id_producto;

	@Attachment(accept="image/*")
	@Expose
	public String ilustracion;

	@Expose
	@Required
	public String nombre;

	@Validation(min=1)
	@Expose
	@Required
	public Double precio;

	@Override
	protected JelloEntity beforeSave() throws IllegalRequestResource {
		// TODO Auto-generated method stub
		return this;
	}



}

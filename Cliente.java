package app;

import jello.model.JelloEntity;
import jello.annotation.Expose;
import jello.security.Accessible;
import jello.ux.Validation;
import jello.annotation.KeyElement;
import jello.security.Role;
import jello.annotation.Required;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Accessible( Role.ALL )public class Cliente extends JelloEntity {

	private static final long serialVersionUID = -7039543333970062495L;
	
	@Expose
	@Required
	public String Apellido;

	@Expose
	@Required
	public String Nombre;

	@Validation(min=1)
	@Expose
	@KeyElement
	public Integer id_cliente;

}

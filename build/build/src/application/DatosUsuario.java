package application;

import java.io.Serializable;

public class DatosUsuario implements Serializable {

	private static final long serialVersionUID = 7606464277157558453L;

	public static enum type{
		register,
		sessionstart,
		chatPrivate
	}
	private type asunto;
	private String usuario;
	private String password;
	/**
	 * @param asunto
	 * @param usuario
	 * @param password
	 */
	public DatosUsuario(type asunto, String usuario, String password) {
		
		this.setAsunto(asunto);
		this.setUsuario(usuario);
		this.setPassword(password);
	}
	public type getAsunto() {
		return asunto;
	}
	public void setAsunto(type asunto) {
		this.asunto = asunto;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}


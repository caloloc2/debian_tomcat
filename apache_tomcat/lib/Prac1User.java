public class Prac1User(){
	private String usuario;
	private String cargo;
	private String login;
	private String hash;
	private String salt;
	private String roles;

	public Prac1User(String usuario, String cargo, String login, String hash, String salt, String roles){
		this.usuario = usuario;
		this.cargo = cargo;
		this.login = login;
		this.hash = hash;
		this.salt = salt;
		this.roles = roles;
	}
	
	public Prac1User(String login, String hash, String salt){
		this.login = login;
		this.hash = hash;
		this.salt = salt;
	}

	public Prac1User(String usuario, String cargo, String login, String roles){
		this.usuario = usuario;
		this.cargo = cargo;
		this.login = login;
		this.roles = roles;
	}

	public String getUsuario(){
		return usuario;
	}

	public String getCargo(){
		return cargo;
	}

	public String getLogin(){
		return login;
	}

	public String getHash(){
		return hash;
	}
	
	public String getSalt(){
		return salt;
	}

	public String getRoles(){
		return roles;
	}
}

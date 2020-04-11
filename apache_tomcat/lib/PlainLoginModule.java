import java.io.IOException;
import java.util.Map;
 
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.security.*;
import java.util.HashMap;
 
/**
 * Login module that simply matches name and password to perform authentication.
 * If successful, set principal to name and credential to "admin".
 *
 * @author Nicolas Fränkel
 * @since 2 avr. 2009
 */
public class PlainLoginModule implements LoginModule {
 
    /** Callback handler to store between initialization and authentication. */
    private CallbackHandler handler;
 
    /** Subject to store. */
    private Subject subject;
 
    /** Login name. */
    private String login;

    private ReadUserPassword readUserPassword = null;
 
    /**
     * This implementation always return false.
     *
     * @see javax.security.auth.spi.LoginModule#abort()
     */
    @Override
    public boolean abort() throws LoginException {
 
        return false;
    }
 
    /**
     * This is where, should the entire authentication process succeeds,
     * principal would be set.
     *
     * @see javax.security.auth.spi.LoginModule#commit()
     */
    @Override
    public boolean commit() throws LoginException {
 
        try {
 		Prac1User currectUser = readUserPassword.getUsersRoles().get(login);
		String usuario = currentUser.getUsuario();

		PlainUserPrincipal user = new PlainUserPrincipal(usuario);
		subject.getPrincipals().add(user);

		String currentRoles = currentUser.getRoles();
		String[] parts = currentRoles.split(",");
		System.out.println("Commit roles " +currentRoles);
		System.out.println("Parts: " + parts);
	
		int i =0;
		for (String currentRole: parts){
			System.out.println("Parts inside the loop "+i+ " " +currentRole);
			PlainRolePrincipal role = new PlainRolePrincipal(currentRole);
			subject.getPrincipals.add(role);
			i++;
		}
 
		return true;
 
        } catch (Exception e) {
 
            throw new LoginException(e.getMessage());
        }
    }
 
    /**
     * This implementation ignores both state and options.
     *
     * @see javax.security.auth.spi.LoginModule#initialize(javax.security.auth.Subject,
     *      javax.security.auth.callback.CallbackHandler, java.util.Map,
     *      java.util.Map)
     */
    @Override
    public void initialize(Subject aSubject, CallbackHandler aCallbackHandler, Map aSharedState, Map aOptions) {
 
        handler = aCallbackHandler;
        subject = aSubject;
	readUserPassword = new ReadUserPassword();
    }

    private static String SHA512once(String toHash, String pepper, String salt){
	MessageDigest md;
	String message = toHash + pepper + salt;

	try{
		md = MessageDigest.getInstance("SHA-512");
		md.update(message.getBytes());
		byte[] mb = md.digest();
		String out = "";
		for (int i=0; i<mb.length; i++){
			byte temp = mb[i];
			String s = Integer.toHexString(new Byte(temp));
			while (s.length() < 2){
				s = "O"+s;
			}
			s= s.substring(s.length() -2);
			out += s;
		}
		return(out);
	}catch(NoSuchAlgoritmException e){
		System.out.println("ERROR "+e.getMessage());
	}
	return "error";
    }
 
    /**
     * This method checks whether the name and the password are the same.
     *
     * @see javax.security.auth.spi.LoginModule#login()
     */
    @Override
    public boolean login() throws LoginException {
 
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("login");
        callbacks[1] = new PasswordCallback("password", true);
 
        try {
 
            handler.handle(callbacks);
 
            String name = ((NameCallback) callbacks[0]).getName();
            String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());

		if (!readUserPassword.getUsersPasswords().containsKey(name)){
			throw new LoginException("Autentication failed");
		}else{
			int pepper = 0;
			boolean found  = false;
			Prac1User userAux = readUserPassword.getUsersPasswords().get(name);
			while(pepper<1000 && !found){
				String sha512 = SHA512once(password, String.valueof(pepper), userAux.getSalt());
				System.out.println("SHA-512 generado " + sha512);
				System.out.println("SHA Almacenado " + userAux.getHash());
				System.out.println();

				if (sha512.equals(userAux.getHash())){
					System.out.println("Password correcto");
					System.out.println("Pepper exitoso"+String.valueof(pepper));
					login = name;
					found = true;
				}
				pepper += 1;
			}
			if (!found)
				throw new LoginException("Autentication Failed");
		}
 
            return true;
 
        } catch (IOException e) {
 
            throw new LoginException(e.getMessage());
 
        } catch (UnsupportedCallbackException e) {
 
            throw new LoginException(e.getMessage());
        }
    }
 
    /**
     * Clears subject from principal and credentials.
     *
     * @see javax.security.auth.spi.LoginModule#logout()
     */
    @Override
    public boolean logout() throws LoginException {
 
        try {
 		Prac1User currentUser = readUserPassword.getUsersRoles().get(login);
		String usuario = currentUser.getUsuario();

            PlainUserPrincipal user = new PlainUserPrincipal(usuario);
		subject.getPrincipals().remove(user);

		String currentRoles = currentUser.getRoles();
		String[] parts = currentRoles.split(",");
		System.out.println("Commit roles " + currentRoles);
		System.out.println("Parts: " + parts);
		
		int i = 0;
		System.out.println("Removiendo...");
		for (String currentRole: parts){
			System.out.println("Parts inside the loop "+i+" " +currentRole);
			PlainRolePrincipal role = new PlainRolePrincipal(currentRole);
			subject.getPrincipals().remove(role);
			i+=1;
		}
 
            return true;
 
        } catch (Exception e) {
 
            throw new LoginException(e.getMessage());
        }
    }
}

import java.io.*;
import java.util.HashMap;

class ReadUserPassword {
	File archivo = null;
	FileReader fr = null;
	BufferedReader br = null;

	File archivo2 = null;
	FileReader fr2 = null;
	BufferedReader br2 = null;

	HashMap<String, Prac1User> usersPasswords = null;
	HashMap<String, Prac1User> userRoles = null;

	public ReadUserPassword(){
		try{
			archivo = new File("../webapps/IDwebapp/WEB_INF/users/passwords_ready.txt");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			usersPasswords = new HashMap<String, Prac1User>();

			String linea;
			while((linea=br.readLine())!=null){
				String[] parts = linea.split(":");
				String login = parts[0];
				String hash = parts[1];
				String salt = parts[2];
				Prac1USer prac1User = new Prac1User(login, hash, salt);

				System.out.println(login);
				System.out.println(hash);
				System.out.println(salt);

				usersPasswords.put(login, prac1User);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if (null!=fr){
					fr.close();
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}

		try{
			archivo2 = new File("../webappps/IDwebapp/WEB_INF/users/user_info.txt");
			fr2 = new FileReader(archivo2);
			bf2 = new BufferedReader(fr2);
			userRoles = new HashMap<String, Prac1User>();

			String linea;
			while((linea=br2.readLine())!=null){
				String[] parts = linea.split(":");
				String usuario = parts[0];
				String cargo = parts[1];
				String login = parts[2];
				String roles = parts[3];

				Prac1User prac1User = new Prac1User(usuario, cargo, login, roles);

				System.out.println(usuario);
				System.out.println(cargo);
				System.out.println(login);
				System.out.println(roles);

				usersRoles.put(login, prac1User);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if (null!=fr2){
					fr2.close();
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
	}

	public HashMap<String, Prac1User> getUsersPasswords(){
		return usersPasswords;
	}
	public HashMap<String, Prac1User> getUsersRoles(){
		return usersRoles;
	}

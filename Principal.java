import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
/**
 * 
 * @author danifrutos
 * 
 * CREATE USER IF NOT EXISTS 'dani'@'localhost' IDENTIFIED BY '12345';
 * GRANT ALL PRIVILEGES ON hospital_p07B.* TO 'dani'@'localhost';
 *
 */
public class Principal {
	public static void main(String[] args) {
		//Instrucciones:
		System.out.println("IMPORTANTE: \nPara poder ejecutar este programa correctamente, existen dos opciones:\n1. Ejecutar desde eclipse, añadiendo previamente la libreria de jdbc\n2. Ejecutar desde la linea de comandos con la siguiente instruccion:\njava -cp mysql-connector-j-8.0.32.jar Principal.java");
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("Las secuencias SQL están escritas a mano en el codigo por simplicidad, se puede expandir la funcionalidad añadiendo variables.");
		System.out.println("Para poder comprobar todo el potencial del programa, el orden ideal de ejecucion será el siguiente: "
				+ "\n1. Ejecuta la opcion 2. SELECT para ver los registros existentes"
				+ "\n2. Ejecuta la opcion 4. DELETE para eleiminar un registro y a continuacion de nuevo 2. SELECT para ver los cambios"
				+ "\n3. Ejecuta 3. UPDATE para actualizar un registro"
				+ "\n4. La opcion 4. DELETE seguida de 2. SELECT mostrara la correcta eliminacion del registro.");
		System.out.println("-----------------------------------------------------------------------------");
		Connection conexion = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} 
		String url="jdbc:mysql://localhost:3306/hospital_p07B";
		/**Scanner scCont = new Scanner(System.in);
		Scanner scUser = new Scanner(System.in);
		String usu, cont;
		System.out.println("Escribe el usuario: ");
		usu=scCont.nextLine();
		System.out.println("Escribe la contraseña: ");
		cont = scCont.nextLine();*/
		try {
			conexion = DriverManager.getConnection(url, "root", "");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		boolean salir=false;
		while(!salir) {
			System.out.println("Elige una opcion: \n1. INSERT\n2. SELECT\n3. UPDATE\n4. DELETE\n5. SALIR");
			Scanner scMenu = new Scanner(System.in);
			String respuesta=scMenu.nextLine();
			if(respuesta.equals("5")) {
				salir=true;
			}
			if(respuesta.equals("1")) {
				try {  
					
					try {
						Statement st = conexion.createStatement();
						String sql = "INSERT INTO `INFORMES` (`NUMERO_INFORME`, `CODIGO_EMPLEADO`, `NUMERO_SEGURIDAD_SOCIAL_PACIENTE`, `FECHA_INFORME`, `HORA_INFORME`, `SUPONE_INGRESO`, `TIPO_INFORME`) VALUES ('00000000000000000001', 'ENF001', '123726354097', '2023-02-14', '13:13', '0', 'I');";
						System.out.println(sql);
						st.executeUpdate(sql);
						System.out.println("SE HA AÑADIDO UNA FILA");
					} catch (SQLException s) {
						System.out.println("ERROR EN LA SECUENCIA SQL " + s.getMessage());
					}
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
			else if(respuesta.equals("2")) {
				try {  
					try {
						Statement st = conexion.createStatement();
						ResultSet resultSet = st.executeQuery("SELECT * from INFORMES");
						ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
						int columnsNumber = rsmd.getColumnCount();
						while (resultSet.next()) {
						    for (int i = 1; i <= columnsNumber; i++) {
						        if (i > 1) System.out.print(",  ");
						        String columnValue = resultSet.getString(i);
						        System.out.print(columnValue + " " + rsmd.getColumnName(i));
						    }
						    System.out.println("");
						}
					} catch (SQLException s) {
						System.out.println("ERROR EN LA SECUENCIA SQL " + s.getMessage());
					}
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
			else if(respuesta.equals("3")) {
				try {  
					try {
						Statement st = conexion.createStatement();
						String sql = "UPDATE `INFORMES` SET `SUPONE_INGRESO` = '0' WHERE `INFORMES`.`NUMERO_INFORME` = '00000000000000000001';";
						System.out.println(sql);
						st.executeUpdate(sql);
						System.out.println("SE HA ACTUALIZADO UNA FILA");
					} catch (SQLException s) {
						System.out.println("ERROR EN LA SECUENCIA SQL " + s.getMessage());
					}
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
			else if(respuesta.equals("4")) {
try {  
					
					try {
						Statement st = conexion.createStatement();
						String sql = "DELETE FROM `INFORMES` WHERE `INFORMES`.`NUMERO_INFORME` = '00000000000000000001'";
						System.out.println(sql);
						st.executeUpdate(sql);
						System.out.println("SE HA ELIMINADO UNA FILA");
					} catch (SQLException s) {
						System.out.println("ERROR EN LA SECUENCIA SQL " + s.getMessage());
					}
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
		
	}
}

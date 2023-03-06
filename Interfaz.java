import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class Interfaz {
	static Connection conexion = null;
	public static void main(String[] args) {
		//Conexion
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} 
		String url="jdbc:mysql://localhost:3306/hospital_p07B";
		try {
			conexion = DriverManager.getConnection(url, "root", "");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		//Interfaz
		JFrame frame = new JFrame("Acceso a Datos");
		frame.setSize(500, 400);
		JPanel pane = new JPanel(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(pane);
		
		JPanel botones = new JPanel(new GridLayout(1, 5));
		pane.add(botones, BorderLayout.NORTH);
		
		JButton select = new JButton("SELECT");
		botones.add(select);
		
		JButton insert = new JButton("INSERT");
		botones.add(insert);
		
		JButton update = new JButton("UPDATE");
		botones.add(update);
		
		JButton delete = new JButton("DELETE");
		botones.add(delete);
		
		JButton informes = new JButton("INFORMES");
		botones.add(informes);
		
		JPanel central = new JPanel(new GridLayout(1,2));
		
		central.setPreferredSize(new Dimension(500, 150));
		pane.add(central, BorderLayout.CENTER);
		
		JPanel centralIzqdo = new JPanel();
		centralIzqdo.setBorder(BorderFactory.createTitledBorder("Busca una tabla"));
		JButton buscarTabla = new JButton("SELECT TABLA");
		JButton nombres = new JButton("Nombre");
		String[] tablasDisponibles = listaTablas();
		JComboBox nombresTablas = new JComboBox(tablasDisponibles);
		centralIzqdo.add(nombresTablas);
		centralIzqdo.add(buscarTabla);
		central.add(centralIzqdo);
		
		JPanel centralDcho = new JPanel(new GridLayout(7,2));
		centralDcho.setBorder(BorderFactory.createTitledBorder("Introduce informes"));
		central.add(centralDcho);
		JLabel codInforme = new JLabel("Codigo");
		JLabel codEmpleado = new JLabel("Empleado");
		JLabel numSS = new JLabel("Numero ss");
		JLabel fechaInforme = new JLabel("Fecha informe");
		JLabel horaInforme = new JLabel("Codigo");
		JLabel suponeIngreso = new JLabel("Supone ingreso");
		JLabel tipoInforme = new JLabel("Tipo informe");
		
		String[] siNo = {"Sí", "No"};
		String[] listaTiposInforme = {"I", "A", "U"};
		
		JTextArea informe = new JTextArea();
		informe.setPreferredSize(new Dimension(25, 50));
		centralDcho.add(codInforme);
		centralDcho.add(informe);
		
		
		JTextArea salida = new JTextArea();
		JScrollPane sp = new JScrollPane(salida);
		sp.setPreferredSize(new Dimension(500, 200));
		pane.add(sp, BorderLayout.SOUTH);
		
		ActionListener escuchador = new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton b = (JButton) e.getSource();
						String texto=salida.getText();
						if(b==select) {
							salida.setText(texto+secuenciaSELECT()+"\n------------------------------\n");
						}
						else if(b==insert) {
							salida.setText(texto+secuenciaINSERT()+"\n------------------------------\n");
						}
						else if(b==update) {
							salida.setText(texto+secuenciaUPDATE()+"\n------------------------------\n");
						}
						else if(b==delete) {
							salida.setText(texto+secuenciaDELETE()+"\n------------------------------\n");
						}
						else if(b==informes) {
							salida.setText(texto+mostrarInformes()+"\n------------------------------\n");
						}
						else if(b==buscarTabla) {
							String queTabla = (String)nombresTablas.getSelectedItem();
							salida.setText(texto+"TABLA: "+queTabla+"\n"+secuenciaSELECTTabla(queTabla)+"\n------------------------------\n");
						}
						else if(b==nombres) {
							
							//salida.setText(texto+nombreTablas()+"\n------------------------------\n");
							String[] lista = listaTablas();
							//salida.setText(texto+lista);
							String[] tablas=listaTablas();
							for(int i = 0; i<tablas.length; i++) {
								texto=salida.getText();
								salida.setText(texto+"Tabla "+i+": "+tablas[i]+"\n");
							}
						}
					}
					
				};
		select.addActionListener(escuchador);
		insert.addActionListener(escuchador);
		update.addActionListener(escuchador);
		delete.addActionListener(escuchador);
		informes.addActionListener(escuchador);
		buscarTabla.addActionListener(escuchador);
		nombres.addActionListener(escuchador);
		frame.setVisible(true);
		
	}
	public static String secuenciaSELECT() {
		String salida="";
		try {  
			try {
				Statement st = conexion.createStatement();
				ResultSet resultSet = st.executeQuery("SELECT * from INFORMES");
				ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (resultSet.next()) {
				    for (int i = 1; i <= columnsNumber; i++) {
				        if (i > 1) salida=salida+(",  ");
				        String columnValue = resultSet.getString(i);
				        //System.out.print(columnValue + " " + rsmd.getColumnName(i));
				        salida=salida+rsmd.getColumnName(i)+" "+columnValue+" ";
				    }
				    salida=salida+"\n";
				    //System.out.println("");
				}
			} catch (SQLException s) {
				System.out.println("ERROR EN LA SECUENCIA SQL " + s.getMessage());
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return salida;
	}
	
	public static String secuenciaSELECTTabla(String campo) {
		String salida="";
		try {  
			try {
				Statement st = conexion.createStatement();
				ResultSet resultSet = st.executeQuery("SELECT * from "+campo);
				ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (resultSet.next()) {
				    for (int i = 1; i <= columnsNumber; i++) {
				        if (i > 1) salida=salida+(",  ");
				        String columnValue = resultSet.getString(i);
				        //System.out.print(columnValue + " " + rsmd.getColumnName(i));
				        salida=salida+rsmd.getColumnName(i)+": "+columnValue+" ";
				    }
				    salida=salida+"\n";
				    //System.out.println("");
				}
			} catch (SQLException s) {
				System.out.println("ERROR EN LA SECUENCIA SQL " + s.getMessage());
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return salida;
	}
	
	
	public static String secuenciaSELECTDatos(String campo) {
		String salida="";
		try {  
			try {
				Statement st = conexion.createStatement();
				ResultSet resultSet = st.executeQuery("SELECT "+campo+" from INFORMES");
				ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (resultSet.next()) {
				    for (int i = 1; i <= columnsNumber; i++) {
				        if (i > 1) salida=salida+(",  ");
				        String columnValue = resultSet.getString(i);
				        //System.out.print(columnValue + " " + rsmd.getColumnName(i));
				        salida=salida+columnValue+",";
				    }
				}
			} catch (SQLException s) {
				System.out.println("ERROR EN LA SECUENCIA SQL " + s.getMessage());
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return salida;
	}
	
	public static String secuenciaINSERT() {
		String salida="";
		try {  
			
			try {
				Statement st = conexion.createStatement();
				String sql = "INSERT INTO `INFORMES` (`NUMERO_INFORME`, `CODIGO_EMPLEADO`, `NUMERO_SEGURIDAD_SOCIAL_PACIENTE`, `FECHA_INFORME`, `HORA_INFORME`, `SUPONE_INGRESO`, `TIPO_INFORME`) VALUES ('00000000000000000001', 'ENF001', '123726354097', '2023-02-14', '13:13', '0', 'I');";
				st.executeUpdate(sql);
				salida="SE HA AÑADIDO UNA FILA: "+sql;
			} catch (SQLException s) {
				salida="ERROR EN LA SECUENCIA SQL " + s.getMessage();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return salida;
	}
	
	public static String secuenciaUPDATE() {
		String salida="";
		try {  
			try {
				Statement st = conexion.createStatement();
				String sql = "UPDATE `INFORMES` SET `SUPONE_INGRESO` = '0' WHERE `INFORMES`.`NUMERO_INFORME` = '00000000000000000001';";
				st.executeUpdate(sql);
				salida="SE HA ACTUALIZADO UNA FILA: "+sql;
			} catch (SQLException s) {
				salida="ERROR EN LA SECUENCIA SQL " + s.getMessage();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return salida;
	}
	
	public static String secuenciaDELETE() {
		String salida="";
		try {  
			
			try {
				Statement st = conexion.createStatement();
				String sql = "DELETE FROM `INFORMES` WHERE `INFORMES`.`NUMERO_INFORME` = '00000000000000000001'";
				//System.out.println(sql);
				st.executeUpdate(sql);
				salida="SE HA ELIMINADO UNA FILA: "+sql;
			} catch (SQLException s) {
				salida="ERROR EN LA SECUENCIA SQL " + s.getMessage();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return salida;
	}
	public static String[] informes() {
		String[] salida;
		String datos=secuenciaSELECTDatos("NUMERO_INFORME");
		salida=datos.split(",");
		return salida;
	}
	public static String mostrarInformes() {
		String salida="";
		String[] datos=informes();
		for(int i = 0; i<datos.length; i++) {
			salida=salida+datos[i]+"\n";
		}
		return salida;
	}
	public static String nombreTablas() {
		String salida="";
		try {
			DatabaseMetaData metaData = conexion.getMetaData();
			String[] types= {"TABLE"};
			ResultSet tablas = metaData.getTables("hospital_p07b",null,   "%", types);
			while(tablas.next()) {
				salida=salida+tablas.getString("TABLE_NAME")+"\n";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			salida="ERROR";
		}
		return salida;
	}
	
	public static String[] listaTablas() {
		int tam=0;
		try {
			DatabaseMetaData metaData = conexion.getMetaData();
			String[] types= {"TABLE"};
			ResultSet tablas = metaData.getTables("hospital_p07b",null,   "%", types);
			while(tablas.next()) {
				tam++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tablas=nombreTablas();
		String[] salida=new String[tam];
		salida=tablas.split("\n");
		return salida;
	}
	
	/**public static String[] ListaEmpleados() {
		int tam=0;
		
		try {  
			try {
				Statement st = conexion.createStatement();
				ResultSet resultSet = st.executeQuery("SELECT CODIGO_EMPLEADO from EMPLEADOS");
				ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (resultSet.next()) {
				    tam++;
				}
				String[] salida=new String[tam];
				while(resultSet.next()) {
					
				}
			} catch (SQLException s) {
				System.out.println("ERROR EN LA SECUENCIA SQL " + s.getMessage());
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return salida;
	}*/
	}


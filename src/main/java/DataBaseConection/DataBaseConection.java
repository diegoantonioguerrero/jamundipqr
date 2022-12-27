// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataBaseConection.java

package DataBaseConection;

import Objetos.ComunicacionPQRD;
import Objetos.NumeracionPQRD;
import Utilidades.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConection {
	public Connection conex;
	public ResultSet result;
	private Statement st;

	public DataBaseConection() {
		loginDB();
	}

	public final void loginDB() {
		try {
			String cadena = (new StringBuilder()).append(Util.getProperties("rutaDB"))
					.append(Util.getProperties("ipDB")).append(":").append(Util.getProperties("puertoDB")).append("/")
					.append(Util.getProperties("nameDB")).toString();
			String usuario = Util.getProperties("usuarioDB");
			String contrasena = Util.getProperties("contrasenaDB");
			Class.forName("org.postgresql.Driver");
			conex = DriverManager.getConnection(cadena, usuario, contrasena);
		} catch (Exception ex) {
			Logger.getLogger(DataBaseConection.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void logoutDB() {
		try {
			conex.close();
		} catch (SQLException ex) {
			Logger.getLogger(DataBaseConection.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void consultarDB(String query) throws SQLException {
		st = conex.createStatement();
		result = st.executeQuery(query);
	}

	public int insertarDB(String query, String idLlave) throws SQLException {
		PreparedStatement preparedStatement = conex.prepareStatement(query, 1);
		long id = 0L;
		preparedStatement.execute();
		ResultSet rs = preparedStatement.getGeneratedKeys();
		int generatedKey = 0;
		if (rs.next())
			generatedKey = rs.getInt(idLlave);
		return generatedKey;
	}

	public ResultSet getResult() {
		return result;
	}

	public boolean insertarNumeracion(NumeracionPQRD numeracionPQRD) {
		String queryNumeracion = "UPDATE numeracionpqrd\nset ano = ?,\nproximonroradicacion = ?\nWHERE fldidnumeracionpqrd = ?;";
		try {
			PreparedStatement statementNumeracion = conex.prepareStatement(queryNumeracion);
			statementNumeracion.setInt(1, numeracionPQRD.getAno());
			statementNumeracion.setInt(2, numeracionPQRD.getProximonroradicacion() + 1);
			statementNumeracion.setInt(3, numeracionPQRD.getFldidnumeracionpqrd());
			statementNumeracion.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(DataBaseConection.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

	/*
	 * public boolean insertarComunicacion(ComunicacionPQRD comunicacionPQRD,
	 * NumeracionPQRD numeracionPQRD) { String query =
	 * "INSERT INTO comunicacionprqd(\n            fldidcomunicacionprqd, fecha, hora, nombre1, nombre2, apellido1, \n            apellido2, razonsocial, celular, direccion, email, asunto, detalledelapqr, \n            archivoadjunto, nombrearchivoarchivoadjunto, nroradicacion, \n            tipopantalla, dependenciadestino, clasificacionpqr, tipoidentificacion, \n            tipopersona, nroidentificacion, ciudad, estado, archivoadjuntorar, \n            nombrearchivoarchivoadjuntorar, tipopqr, tiporespuesta, numeroverificacion)\n    VALUES (?, ?, ?, trim(?), trim(?), trim(?), \n            trim(?), trim(?), trim(?), trim(?), trim(?), TRIM(?), TRIM(REGEXP_REPLACE(?,'( ){2,}|\\\\t+',' ','g')), \n            ?, ?, ?, \n            ?, ?, ?, ?, \n            ?, trim(?), ?, ?, ?, \n            ?, ?, ?, ?);"
	 * ; Calendar calendar = Calendar.getInstance(); Date date = new
	 * Date(calendar.getTime().getTime()); PreparedStatement preparedStatement =
	 * conex.prepareStatement(query); preparedStatement.setInt(1,
	 * comunicacionPQRD.getId_comunicacionpqrd()); preparedStatement.setDate(2,
	 * date); preparedStatement.setObject(3,
	 * comunicacionPQRD.getHoraRealizacion(), 92);
	 * preparedStatement.setString(4, comunicacionPQRD.getPrimer_nombre());
	 * preparedStatement.setString(5, comunicacionPQRD.getSegundo_nombre());
	 * preparedStatement.setString(6, comunicacionPQRD.getPrimer_apellido());
	 * preparedStatement.setString(7, comunicacionPQRD.getSegundo_apellido());
	 * preparedStatement.setString(8, comunicacionPQRD.getRazon_social());
	 * preparedStatement.setString(9, comunicacionPQRD.getCelular());
	 * preparedStatement.setString(10, comunicacionPQRD.getDireccion());
	 * preparedStatement.setString(11, comunicacionPQRD.getEmail()); String
	 * asunto = comunicacionPQRD.getAsunto(); asunto = asunto.replaceAll("'",
	 * "."); asunto = asunto.replaceAll("\"", ".");
	 * preparedStatement.setString(12, asunto); String detalle =
	 * comunicacionPQRD.getDetalle(); detalle = detalle.replaceAll("'", ".");
	 * preparedStatement.setString(13, detalle); preparedStatement.setNull(14,
	 * -2); preparedStatement.setNull(15, 12); preparedStatement.setString(16,
	 * comunicacionPQRD.getNro_radicacion()); preparedStatement.setString(17,
	 * comunicacionPQRD.getTipo_identificacion()); preparedStatement.setInt(18,
	 * comunicacionPQRD.getId_dependencia()); preparedStatement.setInt(19,
	 * comunicacionPQRD.getId_clasificacion_pqrd());
	 * preparedStatement.setInt(20, comunicacionPQRD.getId_tipo_documento());
	 * preparedStatement.setString(21, comunicacionPQRD.getTipo_persona());
	 * preparedStatement.setString(22,
	 * comunicacionPQRD.getNro_identificacion()); preparedStatement.setInt(23,
	 * comunicacionPQRD.getId_ciudad()); preparedStatement.setInt(24,
	 * comunicacionPQRD.getId_estado()); if(comunicacionPQRD.getFileZip() ==
	 * null) preparedStatement.setNull(25, -2); else
	 * preparedStatement.setBinaryStream(25, new
	 * FileInputStream(comunicacionPQRD.getFileZip()),
	 * comunicacionPQRD.getLongitudBytesRar());
	 * if(comunicacionPQRD.getNombrearchivoarchivoadjuntorar() == null)
	 * preparedStatement.setNull(26, 12); else preparedStatement.setString(26,
	 * comunicacionPQRD.getNombrearchivoarchivoadjuntorar());
	 * preparedStatement.setInt(27, comunicacionPQRD.getId_tipo_pqrd());
	 * preparedStatement.setInt(28, comunicacionPQRD.getId_tipo_respuesta());
	 * preparedStatement.setString(29,
	 * comunicacionPQRD.getNumero_verificacion()); consultarDB(
	 * "SELECT MAX(fldidcomunicacionprqd) FROM comunicacionprqd;"); int
	 * cantidadRegistros; for(cantidadRegistros = 0; result.next();
	 * cantidadRegistros = result.getInt("max"));
	 * comunicacionPQRD.setId_comunicacionpqrd(cantidadRegistros + 1);
	 * preparedStatement.execute(); if(insertarNumeracion(numeracionPQRD)) {
	 * logoutDB(); return true; } try { logoutDB(); Util.errorMessage("Error",
	 * "Inserci\363n nueva numeraci\363n"); return false; } catch(Exception ex)
	 * { Logger.getLogger(DataBaseConection.class.getName()).log(Level.SEVERE,
	 * null, ex); } return false; }
	 */
	public boolean insertarComunicacion(ComunicacionPQRD comunicacionPQRD, NumeracionPQRD numeracionPQRD) {
		String query = "INSERT INTO comunicacionprqd(\n            fldidcomunicacionprqd, fecha, hora, nombre1, nombre2, apellido1, \n            apellido2, razonsocial, celular, direccion, email, asunto, detalledelapqr, \n            archivoadjunto, nombrearchivoarchivoadjunto, nroradicacion, \n            tipopantalla, dependenciadestino, clasificacionpqr, tipoidentificacion, \n            tipopersona, nroidentificacion, ciudad, estado, archivoadjuntorar, \n            nombrearchivoarchivoadjuntorar, tipopqr, tiporespuesta, numeroverificacion)\n    VALUES (?, ?, ?, trim(?), trim(?), trim(?), \n            trim(?), trim(?), trim(?), trim(?), trim(?), TRIM(?), TRIM(REGEXP_REPLACE(?,'( ){2,}|\\\\t+',' ','g')), \n            ?, ?, ?, \n            ?, ?, ?, ?, \n            ?, trim(?), ?, ?, ?, \n            ?, ?, ?, ?);";

		try {
			Calendar calendar = Calendar.getInstance();
			Date date = new Date(calendar.getTime().getTime());
			PreparedStatement preparedStatement = this.conex.prepareStatement(query);
			preparedStatement.setInt(1, comunicacionPQRD.getId_comunicacionpqrd());
			preparedStatement.setDate(2, date);
			preparedStatement.setObject(3, comunicacionPQRD.getHoraRealizacion(), 92);
			preparedStatement.setString(4, comunicacionPQRD.getPrimer_nombre());
			preparedStatement.setString(5, comunicacionPQRD.getSegundo_nombre());
			preparedStatement.setString(6, comunicacionPQRD.getPrimer_apellido());
			preparedStatement.setString(7, comunicacionPQRD.getSegundo_apellido());
			preparedStatement.setString(8, comunicacionPQRD.getRazon_social());
			preparedStatement.setString(9, comunicacionPQRD.getCelular());
			preparedStatement.setString(10, comunicacionPQRD.getDireccion());
			preparedStatement.setString(11, comunicacionPQRD.getEmail());
			String asunto = comunicacionPQRD.getAsunto();
			asunto = asunto.replaceAll("'", ".");
			asunto = asunto.replaceAll("\"", ".");
			preparedStatement.setString(12, asunto);
			String detalle = comunicacionPQRD.getDetalle();
			detalle = detalle.replaceAll("'", ".");
			preparedStatement.setString(13, detalle);
			preparedStatement.setNull(14, -2);
			preparedStatement.setNull(15, 12);
			preparedStatement.setString(16, comunicacionPQRD.getNro_radicacion());
			preparedStatement.setString(17, comunicacionPQRD.getTipo_identificacion());
			preparedStatement.setInt(18, comunicacionPQRD.getId_dependencia());
			preparedStatement.setInt(19, comunicacionPQRD.getId_clasificacion_pqrd());
			preparedStatement.setInt(20, comunicacionPQRD.getId_tipo_documento());
			preparedStatement.setString(21, comunicacionPQRD.getTipo_persona());
			preparedStatement.setString(22, comunicacionPQRD.getNro_identificacion());
			preparedStatement.setInt(23, comunicacionPQRD.getId_ciudad());
			preparedStatement.setInt(24, comunicacionPQRD.getId_estado());
			if (comunicacionPQRD.getFileZip() == null) {
				preparedStatement.setNull(25, -2);
			} else {
				preparedStatement.setBinaryStream(25, new FileInputStream(comunicacionPQRD.getFileZip()),
						comunicacionPQRD.getLongitudBytesRar());
			}

			if (comunicacionPQRD.getNombrearchivoarchivoadjuntorar() == null) {
				preparedStatement.setNull(26, 12);
			} else {
				preparedStatement.setString(26, comunicacionPQRD.getNombrearchivoarchivoadjuntorar());
			}

			preparedStatement.setInt(27, comunicacionPQRD.getId_tipo_pqrd());
			preparedStatement.setInt(28, comunicacionPQRD.getId_tipo_respuesta());
			preparedStatement.setString(29, comunicacionPQRD.getNumero_verificacion());
			this.consultarDB("SELECT MAX(fldidcomunicacionprqd) FROM comunicacionprqd;");

			int cantidadRegistros;
			for (cantidadRegistros = 0; this.result.next(); cantidadRegistros = this.result.getInt("max")) {
			}

			comunicacionPQRD.setId_comunicacionpqrd(cantidadRegistros + 1);
			preparedStatement.execute();
			if (this.insertarNumeracion(numeracionPQRD)) {
				this.logoutDB();
				return true;
			} else {
				this.logoutDB();
				Util.errorMessage("Error", "Inserción nueva numeración");
				return false;
			}
		} catch (FileNotFoundException | SQLException var10) {
			Logger.getLogger(DataBaseConection.class.getName()).log(Level.SEVERE, (String) null, var10);
			return false;
		}
	}

	public boolean insertarZip(ComunicacionPQRD comunicacionPQRD) throws SQLException, FileNotFoundException {
		String query = "UPDATE comunicacionprqd SET nombrearchivoarchivoadjuntorar = ?, archivoadjuntorar = ? WHERE fldidcomunicacionprqd = ?";
		PreparedStatement preparedStatement = conex.prepareStatement(query);
		InputStream inputStream1 = null;
		if (comunicacionPQRD.getFileZip() == null) {
			preparedStatement.setNull(1, 12);
			preparedStatement.setNull(2, -2);
			preparedStatement.setInt(3, comunicacionPQRD.getId_comunicacionpqrd());
		} else {
			if (comunicacionPQRD.getFileZip().getName() == null)
				preparedStatement.setNull(1, 12);
			else
				preparedStatement.setString(1, comunicacionPQRD.getNombrearchivoarchivoadjuntorar());
			if (comunicacionPQRD.getFileZip() == null) {
				preparedStatement.setNull(2, -2);
			} else {
				int longitudBytes = (int) comunicacionPQRD.getFileZip().length();
				inputStream1 = new FileInputStream(comunicacionPQRD.getFileZip());
				preparedStatement.setBinaryStream(2, inputStream1, longitudBytes);
			}
			preparedStatement.setInt(3, comunicacionPQRD.getId_comunicacionpqrd());
		}
		preparedStatement.execute();
		if (inputStream1 != null)
			try {
				inputStream1.close();
			} catch (IOException ex) {
				Logger.getLogger(DataBaseConection.class.getName()).log(Level.SEVERE, null, ex);
			}
		return true;
	}

	/*
	 * public boolean temporal() throws SQLException, FileNotFoundException,
	 * IOException { InputStream is; FileOutputStream outputStream; Throwable
	 * throwable; String query =
	 * "SELECT nombrearchivoarchivoadjuntorar, archivoadjuntorar FROM comunicacionprqd WHERE fldidcomunicacionprqd = 1;"
	 * ; st = conex.createStatement(); result = st.executeQuery(query); is =
	 * null; String nombre = ""; while(result.next()) { nombre =
	 * result.getString(1); is = result.getBinaryStream(2); } nombre =
	 * "prueba.zip"; File archivo = new File("temporalFile"); String path =
	 * archivo.getAbsolutePath(); path = path.replace("temporalFile",
	 * "webapps\\"); path = path.replace("bin\\temporalFile", "webapps\\"); try
	 * { path = (new StringBuilder()).append(path).append(Util.getProperties(
	 * "nombreAplicacion")).toString(); } catch(Exception ex) {
	 * Logger.getLogger(DataBaseConection.class.getName()).log(Level.SEVERE,
	 * null, ex); } path = (new
	 * StringBuilder()).append(path).append("\\resources\\radicados\\").toString
	 * (); outputStream = new FileOutputStream(new File((new
	 * StringBuilder()).append(path).append(nombre).toString())); throwable =
	 * null; try { byte bytes[] = new byte[1024]; int read; while((read =
	 * is.read(bytes)) != -1) outputStream.write(bytes, 0, read); }
	 * catch(Throwable throwable2) { throwable = throwable2; throw throwable2; }
	 * if(outputStream != null) if(throwable != null) try {
	 * outputStream.close(); } catch(Throwable throwable1) {
	 * throwable.addSuppressed(throwable1); } else outputStream.close(); break
	 * MISSING_BLOCK_LABEL_342; Exception exception; exception; if(outputStream
	 * != null) if(throwable != null) try { outputStream.close(); }
	 * catch(Throwable throwable3) { throwable.addSuppressed(throwable3); } else
	 * outputStream.close(); throw exception; return true; }
	 */
	public boolean temporal() throws SQLException, FileNotFoundException, IOException {
		String query = "SELECT nombrearchivoarchivoadjuntorar, archivoadjuntorar FROM comunicacionprqd WHERE fldidcomunicacionprqd = 1;";
		this.st = this.conex.createStatement();
		this.result = this.st.executeQuery(query);
		InputStream is = null;

		String nombre;
		for (nombre = ""; this.result.next(); is = this.result.getBinaryStream(2)) {
			nombre = this.result.getString(1);
		}

		nombre = "prueba.zip";
		File archivo = new File("temporalFile");
		String path = archivo.getAbsolutePath();
		path = path.replace("temporalFile", "webapps\\");
		path = path.replace("bin\\temporalFile", "webapps\\");

		try {
			path = path + Util.getProperties("nombreAplicacion");
		} catch (Exception var18) {
			Logger.getLogger(DataBaseConection.class.getName()).log(Level.SEVERE, (String) null, var18);
		}

		path = path + "\\resources\\radicados\\";
		FileOutputStream outputStream = new FileOutputStream(new File(path + nombre));
		Throwable var7 = null;

		try {
			byte[] bytes = new byte[1024];

			int read;
			while ((read = is.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (Throwable var19) {
			var7 = var19;
			throw var19;
		} finally {
			if (outputStream != null) {
				if (var7 != null) {
					try {
						outputStream.close();
					} catch (Throwable var17) {
						var7.addSuppressed(var17);
					}
				} else {
					outputStream.close();
				}
			}

		}

		return true;
	}

	public boolean actualizarDB(String query, String nroRadicado) {
		int affectedrows = 0;
		try {
			PreparedStatement pstmt = conex.prepareStatement(query);
			pstmt.setString(1, "SI");
			Calendar calendar = Calendar.getInstance();
			Date date = new Date(calendar.getTime().getTime());
			pstmt.setDate(2, date);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:00");
			pstmt.setObject(3, LocalTime.now().format(dtf), 92);
			pstmt.setString(4, nroRadicado);
			affectedrows = pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return affectedrows > 0;
	}

	public boolean actualizarConsultasPQRD(String query) {
		int affectedrows = 0;
		try {
			PreparedStatement pstmt = conex.prepareStatement(query);
			affectedrows = pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return affectedrows > 0;
	}
}

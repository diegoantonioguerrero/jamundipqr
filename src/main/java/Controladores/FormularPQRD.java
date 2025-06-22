package Controladores;

import DataBaseConection.DataBaseConection;
import Objetos.Clasificacion;
import Objetos.ComunicacionPQRD;
import Objetos.Dependencia;
import Objetos.Municipio;
import Objetos.NumeracionPQRD;
import Objetos.TipoAdjunto;
import Objetos.TipoDocumento;
import Objetos.TipoPqrd;
import Objetos.TipoRespuesta;
import Utilidades.Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class FormularPQRD implements Serializable {
   private static final long serialVersionUID = 1L;
   private List<TipoDocumento> tipoDocumentoList;
   private List<TipoRespuesta> tipoRespuestaList;
   private List<TipoPqrd> tipoPqrdList;
   private List<Dependencia> dependenciaList;
   private List<Clasificacion> clasificacionList;
   private List<Municipio> municipioList;
   private String path_background_image;
   private String fondoHeader;
   private String fondoFooter;
   private String nombre_archivo;
   private String urlOrigen;
   private String tipoPersona;
   private String tipoIdenficacion;
   private String tipoPqr;
   private String nro_radicado_generado;
   private String colorFondoBotones;
   private String colorLetraBotones;
   private String textTermsConditions;
   private String fuenteTiulos;
   private String fuenteEtiquetas;
   private String fuenteContenido;
   private int anoActual;
   private int proximonroradicacion;
   private int limiteBytesArchivo;
   private ComunicacionPQRD comunicacionPQRD;
   private NumeracionPQRD numeracionPQRD;
   private static boolean personaNatural = false;
   private static boolean primeraVez = true;
   private transient File file1;
   private transient List<File> filesEmpaquetar;
   private transient List<String> filesAlias;
   private transient File fileTemp;
   private transient UploadedFile filePrime;
   private String textoAlternativoEncabezado;
   private String textoAlternativoPiedepagina;
   private String textoCamposObligatorios;
   private List<TipoPqrd> tiposPorIdentificacion;
   private List<TipoPqrd> tiposAnonimos;
   private List<TipoAdjunto> tiposAdjuntos;
   private transient UploadedFile[] filePrimeArray;
   

   public FormularPQRD() {
      try {
		 System.out.println("Save FormularPQRD");

         this.path_background_image = Util.getProperties("imagenFondo");
         this.fondoHeader = Util.getProperties("imagenFondoHeader");
         this.fondoFooter = Util.getProperties("imagenFondoFooter");
         this.colorFondoBotones = Util.getProperties("colorFondoBotones");
         this.colorLetraBotones = Util.getProperties("colorLetraBotones");
         this.textTermsConditions = Util.getProperties("textTermsConditions");
         this.fuenteTiulos = Util.getProperties("fuenteTiulos");
         this.fuenteEtiquetas = Util.getProperties("fuenteEtiquetas");
         this.fuenteContenido = Util.getProperties("fuenteContenido");
         this.urlOrigen = Util.getProperties("linkOrigen");
         this.limiteBytesArchivo = Integer.parseInt(Util.getProperties("limiteBytes"));
         this.textoAlternativoEncabezado = Util.getProperties("textoAlternativoEncabezado");
         this.textoAlternativoPiedepagina = Util.getProperties("textoAlternativoPiedepagina");
         this.textoCamposObligatorios = Util.getProperties("textoCamposObligatorios");
         this.inicializar();
         DataBaseConection dataBaseConection = new DataBaseConection();
         dataBaseConection.consultarDB("SELECT * FROM tablapqrtipoidentificacio WHERE activaenpqr = 1 ORDER BY tipo ASC;");
         ResultSet resultTipoDocumento = dataBaseConection.getResult();

         while(resultTipoDocumento.next()) {
            TipoDocumento tipoDocumento = new TipoDocumento(resultTipoDocumento.getInt("fldidtablapqrtipoidentificacio"), resultTipoDocumento.getString("tipo"));
            this.tipoDocumentoList.add(tipoDocumento);
         }

         dataBaseConection.consultarDB("SELECT * FROM tablapqrtiporespuesta WHERE activoenpqr = 1 ORDER BY tipo ASC;");
         ResultSet resultTipoRespuesta = dataBaseConection.getResult();

         while(resultTipoRespuesta.next()) {
            TipoRespuesta tipoRespuesta = new TipoRespuesta(resultTipoRespuesta.getInt("fldidtablapqrtiporespuesta"), resultTipoRespuesta.getString("tipo"));
            this.tipoRespuestaList.add(tipoRespuesta);
         }

         this.cargarTodosLosTiposPqrd(dataBaseConection);

         dataBaseConection.consultarDB("SELECT * FROM dependencias WHERE activaenpqr = 1 ORDER BY nombre ASC;");
         ResultSet resultDependencias = dataBaseConection.getResult();

         while(resultDependencias.next()) {
            Dependencia dependencia = new Dependencia(resultDependencias.getInt("fldiddependencias"), resultDependencias.getString("nombre"));
            this.dependenciaList.add(dependencia);
         }

         dataBaseConection.consultarDB("SELECT * FROM tablapqrclasificacion WHERE activaenpqr = 1 ORDER BY clase;");
         ResultSet resultClasificacion = dataBaseConection.getResult();

         while(resultClasificacion.next()) {
            Clasificacion clasificacion = new Clasificacion(resultClasificacion.getInt("fldidtablapqrclasificacion"), resultClasificacion.getString("clase"));
            this.clasificacionList.add(clasificacion);
         }

         dataBaseConection.consultarDB("SELECT * FROM municipios WHERE activoenpqr = 1 ORDER BY nombre ASC;");
         ResultSet resultMunicipios = dataBaseConection.getResult();

         while(resultMunicipios.next()) {
            Municipio municipio = new Municipio(resultMunicipios.getInt("fldidmunicipios"), resultMunicipios.getString("nombre"));
            this.municipioList.add(municipio);
         }

         dataBaseConection.logoutDB();
      } catch (SQLException var9) {
		   System.err.println(var9.getMessage() );

         Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var9);
      } catch (Exception var10) {
		   System.err.println(var10.getMessage() );
         Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var10);
      }

   }

   private void inicializar() {
      this.comunicacionPQRD = new ComunicacionPQRD();
      this.numeracionPQRD = new NumeracionPQRD();
      this.tipoDocumentoList = new ArrayList();
      this.tipoRespuestaList = new ArrayList();
      this.tipoPqrdList = new ArrayList();
      this.dependenciaList = new ArrayList();
      this.clasificacionList = new ArrayList();
      this.municipioList = new ArrayList();
      this.tiposPorIdentificacion = new ArrayList();
      this.tiposAnonimos = new ArrayList();
      this.tiposAdjuntos = new ArrayList();
      this.filePrimeArray = new UploadedFile[10];
      
      if (primeraVez) {
         personaNatural = false;
         primeraVez = false;
         this.tipoIdenficacion = null;
      }

      this.anoActual = 0;
      this.proximonroradicacion = 0;
      this.nro_radicado_generado = null;
   }

   private void cargarTodosLosTiposPqrd(DataBaseConection dataBaseConection) throws SQLException {
      // Cargar tipos por identificación
      dataBaseConection.consultarDB("SELECT * FROM tiposdedocumentos WHERE activoenpqr = 1 ORDER BY nombre ASC;");
      ResultSet resultTipoPqrd = dataBaseConection.getResult();
      this.tiposPorIdentificacion = new ArrayList<>();
      while(resultTipoPqrd.next()) {
         TipoPqrd tipoPqrd = new TipoPqrd(resultTipoPqrd.getInt("fldidtiposdedocumentos"), resultTipoPqrd.getString("nombre"));
         this.tiposPorIdentificacion.add(tipoPqrd);
      }

      // Cargar tipos anónimos
      dataBaseConection.consultarDB("SELECT * FROM tiposdedocumentos WHERE activoenpqranonimo = 1 ORDER BY nombre ASC;");
      resultTipoPqrd = dataBaseConection.getResult();
      this.tiposAnonimos = new ArrayList<>();
      while(resultTipoPqrd.next()) {
         TipoPqrd tipoPqrd = new TipoPqrd(resultTipoPqrd.getInt("fldidtiposdedocumentos"), resultTipoPqrd.getString("nombre"));
         this.tiposAnonimos.add(tipoPqrd);
      }
   }

   public String registarFormulario() {
      try {
    	  // System.out.println("registarFormulario");
    	  // System.out.println(this.toString());
         return this.insertarComunicacionPQRD() ? "enviopqrd.xhtml" : "";
      } catch (SQLException var2) {
    	  System.out.println("SQL Error registerig");
    	  Util.errorMessage("Error", "ComunicacionPQRD en base de datos");
         Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var2);
         return "";
      }
   	  catch (Exception var2) {
    	  System.out.println("Error registerig");
    	  var2.printStackTrace();
    	  Util.errorMessage("Error", "Inesperado");
    	  Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var2);
    	  return "";
      }
   }

   private boolean insertarComunicacionPQRD() throws SQLException {
	   try {
		   
      // Procesar archivos según si hay tipos de adjuntos o no
      if (this.tiposAdjuntos != null && !this.tiposAdjuntos.isEmpty()) {
         this.handleMultipleFileUpload();
      } else if (this.filePrime != null && this.filePrime.getFileName() != null && !this.filePrime.getFileName().isEmpty()) {
         this.handleFileUpload();
      }

      DataBaseConection dataBaseConection = new DataBaseConection();
      dataBaseConection.consultarDB("SELECT MAX(fldidcomunicacionprqd) FROM comunicacionprqd;");
      ResultSet result = dataBaseConection.getResult();

      int cantidadRegistros;
      for(cantidadRegistros = 0; result.next(); cantidadRegistros = result.getInt("max")) {
      }

      this.comunicacionPQRD.setId_tipo_pqrd(Integer.parseInt(this.tipoPqr));
      dataBaseConection.consultarDB("SELECT * FROM tablapqrestado WHERE UPPER(estado) LIKE UPPER('%pendiente%');");
      ResultSet resultEstado = dataBaseConection.getResult();

      while(resultEstado.next()) {
         this.comunicacionPQRD.setId_estado(resultEstado.getInt("fldidtablapqrestado"));
      }

      this.comunicacionPQRD.setId_comunicacionpqrd(cantidadRegistros + 1);
      if (this.nombre_archivo != null && !this.nombre_archivo.isEmpty() && this.file1 != null) {
         this.comunicacionPQRD.setNombre_archivo_adjunto(this.nombre_archivo);
         this.comunicacionPQRD.setNombre_archivo_viejo(this.nombre_archivo);
         this.comunicacionPQRD.setFileAdjunto(this.file1);
      }

      this.comunicacionPQRD.setTipo_identificacion(this.tipoIdenficacion);
      this.comunicacionPQRD.setTipo_persona(this.tipoPersona);
      Iterator var5 = this.municipioList.iterator();

      while(var5.hasNext()) {
         Municipio municipioList1 = (Municipio)var5.next();
         if (municipioList1.getFldidmunicipios() == this.comunicacionPQRD.getId_ciudad()) {
            this.comunicacionPQRD.setNombre_ciudad(municipioList1.getNombre());
            break;
         }
      }

      dataBaseConection.consultarDB("SELECT fldidnumeracionpqrd, ano, proximonroradicacion, prefijo FROM numeracionpqrd;");

      for(ResultSet resultNumeracionPQRD = dataBaseConection.getResult(); resultNumeracionPQRD.next(); this.numeracionPQRD = new NumeracionPQRD(resultNumeracionPQRD.getInt("fldidnumeracionpqrd"), resultNumeracionPQRD.getInt("ano"), resultNumeracionPQRD.getInt("proximonroradicacion"), resultNumeracionPQRD.getString("prefijo"))) {
      }

      if (this.numeracionPQRD == null) {
         return false;
      }
      
      
     this.anoActual = Integer.parseInt((new SimpleDateFormat("yyyy")).format(new Date()));
     if (this.anoActual > this.numeracionPQRD.getAno()) {
        this.numeracionPQRD.setAno(this.anoActual);
        this.proximonroradicacion = 1;
        this.numeracionPQRD.setProximonroradicacion(this.proximonroradicacion);
     }

     DecimalFormat nf;
     if (this.numeracionPQRD.getProximonroradicacion() <= 9999) {
        nf = new DecimalFormat("0000");
     } else {
        nf = new DecimalFormat("00000");
     }

     this.nro_radicado_generado = this.numeracionPQRD.getAno() + "-" + this.numeracionPQRD.getPrefijo() + "-" + nf.format((long)this.numeracionPQRD.getProximonroradicacion());
     this.comunicacionPQRD.setNro_radicacion(this.nro_radicado_generado);
     
     this.empaquetarZip();
     
     
     String numVerificacion = String.valueOf((int)Math.floor(Math.random() * 900000.0D + 100000.0D));
     this.comunicacionPQRD.setNumero_verificacion(numVerificacion);
     Iterator<TipoDocumento> itTipoDocumento = this.tipoDocumentoList.iterator();

     while(itTipoDocumento.hasNext()) {
        TipoDocumento tipoDocumentoList1 = (TipoDocumento)itTipoDocumento.next();
        if (this.comunicacionPQRD.getId_tipo_documento() == tipoDocumentoList1.getId_tipo_documento()) {
           this.comunicacionPQRD.setNombreTipoDocumento(tipoDocumentoList1.getNombre_tipo_documento());
           break;
        }
     }

     Iterator<Dependencia> itDependencia = this.dependenciaList.iterator();

     while(itDependencia.hasNext()) {
        Dependencia dependenciaList1 = (Dependencia)itDependencia.next();
        if (this.comunicacionPQRD.getId_dependencia() == dependenciaList1.getId_dependencia()) {
           this.comunicacionPQRD.setNombreDependencia(dependenciaList1.getNombre_dependencia());
           break;
        }
     }

     Iterator<TipoPqrd> itTipoPqrd = this.tipoPqrdList.iterator();

     while(itTipoPqrd.hasNext()) {
        TipoPqrd tipoPqrdList1 = (TipoPqrd)itTipoPqrd.next();
        if (this.comunicacionPQRD.getId_tipo_pqrd() == tipoPqrdList1.getId_tipo_pqrd()) {
           this.comunicacionPQRD.setNombreTipoPQRD(tipoPqrdList1.getNombre_tipo_pqrd());
           break;
        }
     }

     Iterator<TipoRespuesta> itTipoRespuesta = this.tipoRespuestaList.iterator();

     while(itTipoRespuesta.hasNext()) {
        TipoRespuesta tipoRespuestaList1 = (TipoRespuesta)itTipoRespuesta.next();
        if (this.comunicacionPQRD.getId_tipo_respuesta() == tipoRespuestaList1.getId_tipo_respuesta()) {
           this.comunicacionPQRD.setNombreTipoRespuesta(tipoRespuestaList1.getNombre_tipo_respuesta());
           break;
        }
     }

     Iterator<Clasificacion> itClasificacion = this.clasificacionList.iterator();

     while(itClasificacion.hasNext()) {
        Clasificacion clasificacionList1 = (Clasificacion)itClasificacion.next();
        if (this.comunicacionPQRD.getId_clasificacion_pqrd() == clasificacionList1.getId_clasificacion_pqrd()) {
           this.comunicacionPQRD.setNombreClasificacion(clasificacionList1.getNombre_clasificacion_pqrd());
           break;
        }
     }

     if (this.comunicacionPQRD.getFileAdjunto() != null && this.comunicacionPQRD.getNombre_archivo_adjunto() != null && !this.comunicacionPQRD.getNombre_archivo_adjunto().isEmpty()) {
        String extensionArchivo = this.comunicacionPQRD.getNombre_archivo_adjunto();
        String[] tokens = extensionArchivo.split("\\.(?=[^\\.]+$)");
        this.comunicacionPQRD.setNombre_archivo_adjunto(this.comunicacionPQRD.getNro_radicacion() + "-Anexo." + tokens[1]);
     }

     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:00");
     LocalTime localTime = LocalTime.now();
     String format = localTime.format(dtf);
     this.comunicacionPQRD.setHoraRealizacion(format);
     //asigna fecha formateada
     Date date = new Date();
     SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-MM-dd HH:mm");
     String stringDate= dateFor.format(date);
     this.comunicacionPQRD.setFechaHoraRealizacionFormat(stringDate);
     
     
     if (!dataBaseConection.insertarComunicacion(this.comunicacionPQRD, this.numeracionPQRD)) {
        dataBaseConection.logoutDB();
        Util.errorMessage("Error", "Insercion de PQRD en base de datos");
        return false;
     } else {
        dataBaseConection.logoutDB();
        if (this.comunicacionPQRD.getEmail() != null && !this.comunicacionPQRD.getEmail().isEmpty()) {
           try {
        	   // System.out.println("sendMail");
              Util.sendMail(this.comunicacionPQRD);
           } catch (Exception var19) {
              System.err.println(var19);
              Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var19);
           }
        }

        try {
           (new CreatePDF()).crearPDF(this.comunicacionPQRD);
        } catch (IOException var17) {
           Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var17);
        } catch (Exception var18) {
           Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var18);
        }

        try {
           String crearZip = (new CreateZip()).crearZip(this.comunicacionPQRD);
           File file = new File(crearZip);
           if (file.exists() && file.delete()) {
           }
        } catch (IOException var15) {
           Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var15);
        } catch (Exception var16) {
           Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var16);
        }

        boolean mostrar = this.comunicacionPQRD.getEmail() != null && !this.comunicacionPQRD.getEmail().isEmpty();
        EnvioPQRD envioPQRD = new EnvioPQRD(this.comunicacionPQRD.getNro_radicacion(), mostrar, 
        			this.comunicacionPQRD.getFechaHoraRealizacionFormat(),
        			this.comunicacionPQRD.getNumero_verificacion()
        			
        		);
        System.out.println("RADICADO GENERADO: " + this.comunicacionPQRD.getNro_radicacion() + " Asunto: " + this.comunicacionPQRD.getAsunto());
        this.limiteBytesArchivo = 0;
        this.nombre_archivo = null;
        this.tipoIdenficacion = null;
        primeraVez = true;
        personaNatural = false;
        this.inicializar();
        if (this.file1 != null) {
           this.file1.delete();

           while(this.file1.exists()) {
              try {
                 FileUtils.forceDelete(this.file1);
              } catch (IOException var14) {
                 Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var14);
              }
           }
        }
        
        if (this.filesEmpaquetar != null && this.filesEmpaquetar.size()>0) {
        	
        	for(int i= 0; i < this.filesEmpaquetar.size(); i++) {
        		File fileToDelete = this.filesEmpaquetar.get(i); 
        		fileToDelete.delete();

                while(fileToDelete.exists()) {
                   try {
                      FileUtils.forceDelete(fileToDelete);
                   } catch (IOException var14) {
                      Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var14);
                   }
                }	
        	}
            
         }

        if (this.fileTemp != null) {
           this.fileTemp.delete();

           while(this.fileTemp.exists()) {
              try {
                 FileUtils.forceDelete(this.fileTemp);
              } catch (IOException var13) {
                 Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var13);
              }
           }
        }

        return true;
     }
      
      
	   }catch (Exception ex) {
		   System.err.println("insertarComunicacionPQRD error");
		   System.err.println(ex.getMessage());
		   ex.printStackTrace();
		   return false;
		   }
   }

   private void empaquetarZip() {
	   if (filesEmpaquetar == null || filesEmpaquetar.size() == 0)
	   {
		   return;
	   }
	   
	   File[] archivosParaZip = filesEmpaquetar.toArray(new File[0]);

	        try {
	        	
	        	String dirSalida = null;
	        	String nombre_archivo = null;
	        	if (archivosParaZip.length > 0 )
	        	{
	        		File tempFile = archivosParaZip[0];
	        		dirSalida = tempFile.getParent();
	        		nombre_archivo = this.nro_radicado_generado + ".zip";
	        	}
	        			
	        	dirSalida = dirSalida + "\\" + this.nro_radicado_generado + ".zip";
	        	ajustarNombresALias();
	            CreateZip.crearZip(dirSalida , archivosParaZip, this.filesAlias);
	             
	            this.file1 = new File(dirSalida);
	            this.comunicacionPQRD.setNombre_archivo_adjunto(nombre_archivo);
	            this.comunicacionPQRD.setNombre_archivo_viejo(nombre_archivo);
	            this.comunicacionPQRD.setFileAdjunto(this.file1);
	            
	            System.out.println("ZIP creado con éxito.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally {
	        	archivosParaZip = null;
	        }
	   
}

private void ajustarNombresALias() {
	int maxSize = 100;
	
	List<String> prefijados = prefijarNombres(this.filesAlias);
	List<String> recortados = recortarNombres(prefijados, maxSize);
	this.filesAlias = aplicarSufijos(recortados, maxSize);
	
}

//Fase 0: prefijar nombres
public List<String> prefijarNombres(List<String> nombresOriginales) {
 List<String> prefijos = new ArrayList<>();

 for (String nombre : nombresOriginales) {
     prefijos.add(this.nro_radicado_generado + " - " + nombre);
 }

 return prefijos;
}


// Fase 1: recortar nombres a máximo 10 caracteres
public List<String> recortarNombres(List<String> nombresOriginales, int maxSize) {
    List<String> recortados = new ArrayList<>();

    for (String nombre : nombresOriginales) {
        int punto = nombre.lastIndexOf(".");
        String base = punto != -1 ? nombre.substring(0, punto) : nombre;
        String ext = punto != -1 ? nombre.substring(punto + 1) : "";

        int maxBase = maxSize - ext.length() - 1;
        if (maxBase < 1) throw new RuntimeException("Extensión demasiado larga: " + ext);

        String baseRecortada = base.length() > maxBase ? base.substring(0, maxBase) : base;

        recortados.add(baseRecortada + "." + ext);
    }

    return recortados;
}

// Fase 2: renombrar los repetidos sin pasar los 10 caracteres
public List<String> aplicarSufijos(List<String> nombresRecortados, int maxSize) {
    Map<String, Integer> contador = new HashMap<>();
    List<String> resultado = new ArrayList<>();

    for (String nombre : nombresRecortados) {
        int punto = nombre.lastIndexOf(".");
        String base = nombre.substring(0, punto);
        String ext = nombre.substring(punto + 1);

        String clave = base + "." + ext;

        int cuenta = contador.getOrDefault(clave, 0) + 1;
        contador.put(clave, cuenta);

        if (cuenta == 1) {
            resultado.add(nombre);
        } else {
            String sufijo = "_" + cuenta;
            int maxBase = maxSize - ext.length() - sufijo.length() - 1;

            String nuevaBase = base.length() > maxBase ? base.substring(0, maxBase) : base;
            String nuevoNombre = nuevaBase + sufijo + "." + ext;

            resultado.add(nuevoNombre);
        }
    }

    return resultado;
}


public List<TipoDocumento> getTipoDocumentoList() {
      return this.tipoDocumentoList;
   }

   public List<TipoRespuesta> getTipoRespuestaList() {
      return this.tipoRespuestaList;
   }

   public List<TipoPqrd> getTipoPqrdList() {
      return this.tipoPqrdList;
   }

   public List<Dependencia> getDependenciaList() {
      return this.dependenciaList;
   }

   public List<Clasificacion> getClasificacionList() {
      return this.clasificacionList;
   }

   public List<Municipio> getMunicipioList() {
      return this.municipioList;
   }

   public List<TipoAdjunto> getTiposAdjuntos() {
      return this.tiposAdjuntos;
   }

   public ComunicacionPQRD getComunicacionPQRD() {
      return this.comunicacionPQRD;
   }

   public String getPath_background_image() {
      return this.path_background_image;
   }

   public String getFondoHeader() {
      return this.fondoHeader;
   }

   public String getFondoFooter() {
      return this.fondoFooter;
   }

   public String getColorFondoBotones() {
      return this.colorFondoBotones;
   }

   public String getColorLetraBotones() {
      return this.colorLetraBotones;
   }

   public String getTextTermsConditions() {
      return this.textTermsConditions;
   }

   public String getFuenteTiulos() {
      return this.fuenteTiulos;
   }

   public String getFuenteEtiquetas() {
      return this.fuenteEtiquetas;
   }

   public String getFuenteContenido() {
      return this.fuenteContenido;
   }

   public String getTipoIdenficacion() {
      return this.tipoIdenficacion;
   }

   public void setTipoIdenficacion(String tipoIdenficacion) {
      this.tipoIdenficacion = tipoIdenficacion;
   }
   
   public String getTipoPqr() {
      return this.tipoPqr;
   }

   public void setTipoPqr(String tipoPqr) {
      this.tipoPqr = tipoPqr;
   }


   public String getTipoPersona() {
      return this.tipoPersona;
   }

   public void setTipoPersona(String tipoPersona) {
      personaNatural = !tipoPersona.equals("NATURAL");
      this.tipoPersona = tipoPersona;
   }

   public boolean isPersonaNatural() {
      return personaNatural;
   }

   public String getUrlOrigen() {
      return this.urlOrigen;
   }

   public String getFechaActual() {
      return "Fecha de radicaci\u00f3n: " + (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
   }

   public int getLimiteBytesArchivo() {
      return this.limiteBytesArchivo;
   }

   public UploadedFile getFilePrime() {
      return this.filePrime;
   }

   public void setFilePrime(UploadedFile filePrime) {
      this.filePrime = filePrime;
   }

   public UploadedFile[] getFilePrimeArray() {
      return this.filePrimeArray;
   }

   public void setFilePrimeArray(UploadedFile[] filePrimeArray) {
      this.filePrimeArray = filePrimeArray;
   }

   public UploadedFile getFilePrimeArray(int index) {
      if (this.filePrimeArray != null && index >= 0 && index < this.filePrimeArray.length) {
         return this.filePrimeArray[index];
      }
      return null;
   }

   public void setFilePrimeArray(int index, UploadedFile file) {
      if (this.filePrimeArray != null && index >= 0 && index < this.filePrimeArray.length) {
         this.filePrimeArray[index] = file;
      }
   }

   public void handleFileUpload() {
      try {
         if (this.file1 != null) {
            this.file1.delete();
         }

         if (this.fileTemp != null) {
            this.fileTemp.delete();
         }
      } catch (Exception var36) {
      }

      try {
         this.nombre_archivo = this.filePrime.getFileName();
         Path path = Files.createTempFile("sample", ".txt");
         this.fileTemp = path.toFile();
         String dirSalida = this.fileTemp.getAbsolutePath();
         InputStream inputStream = this.filePrime.getInputstream();
         Throwable var4 = null;

         try {
            File fil = new File(dirSalida + this.filePrime.getFileName());
            FileOutputStream outputStream = new FileOutputStream(fil);
            Throwable var7 = null;

            try {
               int longs = (int)this.filePrime.getSize();
               byte[] bytes = new byte[longs];

               while(true) {
                  int read;
                  if ((read = inputStream.read(bytes)) == -1) {
                     inputStream.close();
                     break;
                  }

                  outputStream.write(bytes, 0, read);
               }
            } catch (Throwable var37) {
               var7 = var37;
               throw var37;
            } finally {
               if (outputStream != null) {
                  if (var7 != null) {
                     try {
                        outputStream.close();
                     } catch (Throwable var35) {
                        var7.addSuppressed(var35);
                     }
                  } else {
                     outputStream.close();
                  }
               }

            }
         } catch (Throwable var39) {
            var4 = var39;
            throw var39;
         } finally {
            if (inputStream != null) {
               if (var4 != null) {
                  try {
                     inputStream.close();
                  } catch (Throwable var34) {
                     var4.addSuppressed(var34);
                  }
               } else {
                  inputStream.close();
               }
            }

         }

         this.file1 = new File(dirSalida + this.filePrime.getFileName());
         FacesMessage msg = new FacesMessage("Adjunto Exitoso", this.filePrime.getFileName() + " ha sido cargado.");
         FacesContext.getCurrentInstance().addMessage((String)null, msg);
      } catch (IOException var41) {
         Util.errorMessage("Error", "Archivo IOException");
         Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var41);
      }

   }

   public void handleMultipleFileUpload() {
      if (this.filePrimeArray != null && this.tiposAdjuntos != null) {
    	  this.filesEmpaquetar = new ArrayList<File>();
    	  this.filesAlias = new ArrayList<String>();
    	  Path path = null;
         for (int i = 0; i < this.filePrimeArray.length && i < this.tiposAdjuntos.size(); i++) {
            UploadedFile file = this.filePrimeArray[i];
            if (file != null && file.getFileName() != null && !file.getFileName().isEmpty()) {
               try {
                  String fileName = this.tiposAdjuntos.get(i).getNombreadjunto();
                  String baseFileName = file.getFileName();
                  if (baseFileName.lastIndexOf(".") < 0) {
                	  baseFileName+=".txt";
                  }
                  String extension = baseFileName.substring(baseFileName.indexOf("."));
                  fileName += extension;
                  if (path == null) {
                	  path = Files.createTempFile("sample", ".txt");
                  }
                  
                  File tempFile = path.toFile();
                  String dirSalida = tempFile.getAbsolutePath();
                  
                  if (this.fileTemp == null) {
                	  this.fileTemp = new File(dirSalida);  
                  }
                  
                  InputStream inputStream = file.getInputstream();
                  
                  File fil = new File(dirSalida + fileName);
                  FileOutputStream outputStream = new FileOutputStream(fil);
                  
                  int longs = (int)file.getSize();
                  byte[] bytes = new byte[longs];
                  
                  int read;
                  while ((read = inputStream.read(bytes)) != -1) {
                     outputStream.write(bytes, 0, read);
                  }
                  
                  inputStream.close();
                  outputStream.close();
                  fil = null;
                  File fileEmpaquetar = new File(dirSalida + fileName);
                  this.filesEmpaquetar.add(fileEmpaquetar);
                  this.filesAlias.add(fileName);
                  
                  // Aquí podrías guardar el archivo en la base de datos o procesarlo
                  FacesMessage msg = new FacesMessage("Adjunto Exitoso", fileName + " ha sido cargado.");
                  FacesContext.getCurrentInstance().addMessage(null, msg);
                  
               } catch (IOException e) {
                  Util.errorMessage("Error", "Error al procesar archivo: " + file.getFileName());
                  Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, "Error processing file", e);
               }
            }
         }
      }
   }

   public String getTextoAlternativoEncabezado() {
		return textoAlternativoEncabezado;
	}

	public String getTextoAlternativoPiedepagina() {
		return textoAlternativoPiedepagina;
	}
	
	public String getTextoCamposObligatorios() {
		return textoCamposObligatorios;
	}
	

   public String getNombre_archivo() {
      return this.nombre_archivo;
   }

   protected void finalize() throws Throwable {
      primeraVez = true;
      this.limiteBytesArchivo = 0;
      this.tipoIdenficacion = null;
      this.nombre_archivo = null;
      personaNatural = false;
      this.inicializar();
      super.finalize();
   }

   
   public void cargarTipos(){
	   
	  this.tipoPqrdList.clear();
	  if ("ANONIMO".equals(this.tipoIdenficacion)) {
		  this.tipoPqrdList.addAll(this.tiposAnonimos);  
	  } else {
		  this.tipoPqrdList.addAll(this.tiposPorIdentificacion);
	  }
      
   }
   
  
   public void recargarAdjuntos() {
	   DataBaseConection dataBaseConection = null;
	   try {
		   this.tiposAdjuntos = new ArrayList<>();
		   if (this.tipoPqr == null || this.tipoPqr.equals("") ) {
			   return; 
		   }
		   String query = "select nombre,posicion,nombreadjunto, obligatorio from tiposdedocumentos td\r\n"
	      		+ "inner join adjuntossolicitadosenpqr asp\r\n"
	      		+ "on td.fldidtiposdedocumentos  = asp.fldidtiposdedocumentos\r\n"
	      		+ "where td.fldidtiposdedocumentos = " + this.tipoPqr  + "\r\n"
	      		+ "order by posicion;";
	   
   		dataBaseConection = new DataBaseConection();
	// Cargar tipos por identificación
	      dataBaseConection.consultarDB(query);
	      
	      ResultSet resultTipoAdjunto = dataBaseConection.getResult();
	      
	      while(resultTipoAdjunto.next()) {
	         TipoAdjunto tipoAdjunto = new TipoAdjunto(resultTipoAdjunto.getString("nombreadjunto"), 
	        		 resultTipoAdjunto.getInt("posicion"),
	        		 resultTipoAdjunto.getInt("obligatorio")
	        		 );
	         this.tiposAdjuntos.add(tipoAdjunto);
	      }
	   
	      
	   } catch (SQLException var9) {
			   System.err.println(var9.getMessage() );
	
	      Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var9);
	   } catch (Exception var10) {
			   System.err.println(var10.getMessage() );
	      Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var10);
	   }
	   finally {
		   if (dataBaseConection != null) {
			   dataBaseConection.logoutDB();   
		   }
	   }
   }

}

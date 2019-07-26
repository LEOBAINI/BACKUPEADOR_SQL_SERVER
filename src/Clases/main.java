package Clases;

import MetodosSql.Credenciales;
import MetodosSql.JavaMail;
import MetodosSql.MetodosSql;

public class main {
	static String rutaArchivo;
	static String horaBBDD;
	static String mensaje="Este es un reporte automático generado en cortesía por parte de su amigo, Leonardo Baini.\n\n";
	static int flagError=0;
	public static void main(String[] args) {
		String from="informescccc@gmail.com";
		
		MetodosSql baseCCCC=new MetodosSql(
				Credenciales.ip,
				Credenciales.base,
				Credenciales.usuario,
				Credenciales.password
				);
		
			
		String queryHoraBBDD="select getdate()";
		horaBBDD=baseCCCC.consultarUnaCelda(queryHoraBBDD);
		horaBBDD=horaBBDD.replaceAll(" ", "").replaceAll("-","").replaceAll(":", "");	
		
		baseCCCC.setDatabase("BASE1");
		backupear(baseCCCC);		
		
		baseCCCC.setDatabase("BASE2");
		backupear(baseCCCC);		
		
		baseCCCC.setDatabase("BASE3");
		backupear(baseCCCC);		
		
		JavaMail mail=new JavaMail(from, "PONER PASSWORD DEL MAIL ACÁ");
		
		if(flagError==0) {			
			mail.enviaStartTLS(from,"MAIL TO", "BACKUP  BBDD OK!", mensaje);
		}else{
			mail.enviaStartTLS(from,"MAIL TO", "¡¡¡PROBLEMA BK  SQL!!!", mensaje);
		};
		

	}

	private static void backupear(MetodosSql baseCCCC) {
		rutaArchivo="F:\\BackupSql\\"+baseCCCC.getDatabase()+horaBBDD+".bak";		
		String SentenciaSqlAFCONTA="BACKUP DATABASE ["+baseCCCC.getDatabase()+"] TO  DISK = N'"+rutaArchivo+"' WITH NOFORMAT, NOINIT,  NAME = N'"+baseCCCC.getDatabase()+"-Full Database Backup', SKIP, NOREWIND, NOUNLOAD,  STATS = 10";
		String resultadoEjecucionAFCONTA=baseCCCC.ejecutarBackup(SentenciaSqlAFCONTA);
		if(baseCCCC.existeArchivo(rutaArchivo)) {
			mensaje=mensaje+"El archivo "+rutaArchivo+" se generó ok \n";
		}else{
			mensaje=mensaje+"El archivo "+rutaArchivo+" no se pudo generar...\n Error->"+resultadoEjecucionAFCONTA;
			flagError++;
		};	
		
	}

}

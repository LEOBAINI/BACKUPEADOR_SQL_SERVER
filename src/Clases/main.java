package Clases;

import MetodosSql.Credenciales;
import MetodosSql.JavaMail;
import MetodosSql.MetodosSql;

public class main {
	static String rutaArchivo;
	static String horaBBDD;
	static String mensaje="Este es un reporte autom�tico generado en cortes�a por parte de su amigo, Leonardo Baini.\n\n";
	static int flagError=0;
	public static void main(String[] args) {
		String from="informescccc@gmail.com";
		
		MetodosSql baseCCCC=new MetodosSql(
				Credenciales.ip_CCCC,
				Credenciales.base_CCCC,
				Credenciales.usuario_CCCC,
				Credenciales.password_CCCC
				);
		
			
		String queryHoraBBDD="select getdate()";
		horaBBDD=baseCCCC.consultarUnaCelda(queryHoraBBDD);
		horaBBDD=horaBBDD.replaceAll(" ", "").replaceAll("-","").replaceAll(":", "");	
		
		baseCCCC.setDatabase("AF_CAMPOCHICO");
		backupear(baseCCCC);		
		
		baseCCCC.setDatabase("AFCOMPRAS");
		backupear(baseCCCC);		
		
		baseCCCC.setDatabase("AFCONTA");
		backupear(baseCCCC);		
		
		JavaMail mail=new JavaMail(from, "PONER PASSWORD DEL MAIL AC�");
		
		if(flagError==0) {			
			mail.enviaStartTLS(from,"bainileonardo@gmail.com", "BACKUP CAMPO CHICO BBDD OK!", mensaje);
		}else{
			mail.enviaStartTLS(from,"bainileonardo@gmail.com", "���PROBLEMA BK CCCC SQL!!!", mensaje);
		};
		

	}

	private static void backupear(MetodosSql baseCCCC) {
		rutaArchivo="F:\\BackupSql\\"+baseCCCC.getDatabase()+horaBBDD+".bak";		
		String SentenciaSqlAFCONTA="BACKUP DATABASE ["+baseCCCC.getDatabase()+"] TO  DISK = N'"+rutaArchivo+"' WITH NOFORMAT, NOINIT,  NAME = N'"+baseCCCC.getDatabase()+"-Full Database Backup', SKIP, NOREWIND, NOUNLOAD,  STATS = 10";
		String resultadoEjecucionAFCONTA=baseCCCC.ejecutarBackup(SentenciaSqlAFCONTA);
		if(baseCCCC.existeArchivo(rutaArchivo)) {
			mensaje=mensaje+"El archivo "+rutaArchivo+" se gener� ok \n";
		}else{
			mensaje=mensaje+"El archivo "+rutaArchivo+" no se pudo generar...\n Error->"+resultadoEjecucionAFCONTA;
			flagError++;
		};	
		
	}

}

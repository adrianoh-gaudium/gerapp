package br.com.gaudium.builder.taximachine;

import java.awt.Desktop;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

public class Util {

	public static final Charset UTF8 = Charset.forName("UTF-8");
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String SOURCE_PROPERTY = "source";
	public static final String KEYSTORE_PROPERTY = "keystore";
	public static final String CUSTOM_PROPERTY = "custom";
	public static final String OUTPUT_PROPERTY = "saida";
	public static final String JSON_TXM_PROPERTY = "jsonTxm";
	public static final String JSON_DRM_PROPERTY = "jsonDrm";
	public static final String VERSION_CODE_PASS = "versionCodePass";
	public static final String VERSION_NAME_PASS = "versionNamePass";
	public static final String VERSION_CODE_TAXISTA = "versionCodeTaxista";
	public static final String VERSION_NAME_TAXISTA = "versionNameTaxista";
	public static final String RELEASE_NOTES_PASS = "releaseNotesPass";
	public static final String RELEASE_NOTES_TAXISTA= "releaseNotesTaxista";

	public static final String DADOS_APPS="dadosApps";
	public static final String PROJS_ANDAMENTO = "projsAndamento";
	public static final String ANDROID_CUSTOM = "androidCustom";
	public static final String IOS_CUSTOM = "iosCustom";
	public static final String ARQ_BASE = "ArqsBase";
	public static final String PROJS_NUMBERS = "projsNumbers";
	public static final String CHK_MARKETING = "chkMarketing";
	public static final String CHK_META = "chkMeta";
	public static final String CHK_PACKAGE = "chkPackage";
	public static final String CHK_PASSAGEIRO = "chkPassageiro";
	public static final String CHK_TAXISTA = "chkTaxista";
	public static final String CHK_RELOAD_META = "chkReloadMeta";
	public static final String CHK_IMAGENS = "chkImagens";
	public static final String CHK_CERTS = "chkCerts";
	public static final String MKT_SUBDIR = "mktSubDir";
	public static final String DADOS_SUBDIR = "dadosSubDir";
	public static final String IMAGENS_SUBDIR = "imagensSubDir";
	public static final String CONTA_FASTLANE = "ctaFastLane";
	public static final String SCRIPTS_PATH = "scriptsPath";
	public static final String API_CLIENTE = "apiCliente";
	public static final String SERVIDOR_CLIENTE = "servidorCliente";
	public static final String SERVIDOR_CONF_API = "servidorConfAPI";
	public static final String URL_API_CONF_API = "apiConfAPI";
	public static final String SENHA_CLIENTE = "senhaCliente";
	public static final String EMAIL_LOGIN_CLIENTE = "emailLoginCliente";
	public static final String NOME_CLIENTE = "nomeCliente";
	public static final String URL_PIN_MAPA = "urlPinMapa";
	public static final String PIN_TAXISTA_LIVRE = "pinTaxistaLivre";
	public static final String PIN_TAXISTA_OCUPADO = "pinTaxistaOcupado";
	public static final String PIN_TAXISTA_CONFIRMADO = "pinTaxistaConfirmado";
	public static final String PIN_MOTOTAXISTA_CONFIRMADO = "pinMotoTaxistaConfirmado";
	public static final String PIN_MOTOTAXISTA_OCUPADO = "pinMotoTaxistaOcupado";
	public static final String PIN_MOTOTAXISTA_LIVRE = "pinMotoTaxistaLivre";
	public static final String PIN_MOTORISTA_CONFIRMADO = "pinMotoristaConfirmado";
	public static final String PIN_MOTORISTA_OCUPADO = "pinMotoristaOcupado";
	public static final String PIN_MOTORISTA_LIVRE = "pinMotoristaLivre";
	public static final String PIN_LOCALIZACAO = "pinLocalizacao";
	public static final String PIN_PASSAGEIRO = "pinPassageiro";
	public static final String LOG_FILENAME = "log_creator.txt";
	public static final String CHK_ANDROID = "chkAndroid";
	public static final String CHK_IOS = "chkIOS";
	public static final String SECRET_DIR = "secretDir";
	
	public static final String RB_CONF_API = "rbConfAPI";

	public static String removerAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public static boolean ehVazio(Object s) {
		if (s==null) return true;
		if (s instanceof String)
			return ("".equals(((String)s).trim()));
		else
			if (s instanceof File)
				return ((File) s).getPath().equals("");
			else
				return false;
	}

	public static boolean ehNumero(String s) {
		try {
			Integer.valueOf(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static void openFile(String fileName) {
		File arq = null;
		if (Desktop.isDesktopSupported()) {
			try {
				arq = new File(fileName);
				Desktop.getDesktop().edit(arq);
			} catch (IllegalArgumentException eArq) {
				try {	 
					ArrayList<String> headerList = new ArrayList<String>();
					headerList.add("# O formato do arquivo contendo as senhas dos keystores é:");
					headerList.add("# <nome arq. keystore sem a extensão>=<storePassw> <keyAlias> <keyPassw>");
					headerList.add("# O nome dos arqs keystore tem que ter a extensão .keystore, por exemplo: bandeira.keystore");
					FileUtils.writeLines(arq, "UTF-8", headerList);
					Desktop.getDesktop().edit(arq);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				showMessage("Erro ao abrir o arquivo "+fileName);
			}
		} else {
			showMessage("Ação não suportada, altere o arquivo manualmente.");
		}
	}

	public static void replaceStringFile(String fileName, String oldString, String newString) throws IOException {
		String content = FileUtils.readFileToString(new File(fileName), Util.UTF8);
		content = content.replaceAll(oldString, Matcher.quoteReplacement(newString));
		FileUtils.writeStringToFile(new File(fileName), content, Util.UTF8);
	}

	private static void showMessage(String string) {
		JOptionPane.showMessageDialog(null, string);		
	}

	public static int contar(String string, String key) {
		int count = 0;
		int index = -1;
		while ((index = string.indexOf(key, index+1))>=0) { 
			count++;
		}
		return count;
	}

	public static String leftpad(String string, String caracter, int tamanho) {
		StringBuilder sb = new StringBuilder(string);
		int charsToGo = tamanho - sb.length();
		while(charsToGo > 0) {
			sb.insert(0, caracter);
			charsToGo--;
		}
		return sb.toString();
	}

	private static IOFileFilter stripGeneratedFilesFilter = new IOFileFilter() {
		@Override
		public boolean accept(File file, String fileString) {
			return false;
		}
		@Override
		public boolean accept(File file) {
			try {
				return !file.getCanonicalPath().contains("build") || 
						file.getCanonicalPath().contains("build.gradle"); 
				//						&& 
				//						!file.getCanonicalPath().contains(FILE_SEPARATOR+"gen"+FILE_SEPARATOR));
			} catch (IOException e) {
				return true;
			} 
		}
	};

	/**
	 * Copiar diretórios 
	 * @param sourceDir diretório de origem 
	 * @param destDir diretório de destino
	 * @return
	 */
	public static String copyDirectories(File sourceDir, File destDir) {
		//Filtro para retirar eventuais diretórios ".svn" e "build".
		FileFilter fileFilter = FileFilterUtils.makeSVNAware(stripGeneratedFilesFilter);
		try {
			FileUtils.copyDirectory(sourceDir, destDir, fileFilter);
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro ao copiar do diretório "+sourceDir.getPath()+" para "+destDir.getPath(); 
		}
		return null;
	}

	/**
	 * Retirar a última barra do texto passado como parâmetro, se houver.
	 * @param text Texto a retirar o 'slash' final
	 * @return
	 */
	public static String retirarBarraFinal(String text) {
		if (ehVazio(text)) {
			return text;
		} else {
			text = text.trim();
			if (text.endsWith("/")) {
				return text.substring(0, text.length()-1);
			}   
			return text;
		} 
	}

	/**
	 * Retirar a primeira barra do texto passado como parâmetro, se houver.
	 * @param text Texto a retirar o 'slash' inicial
	 * @return
	 */
	public static String retirarBarraInicial(String text) {
		if (ehVazio(text)) {
			return text;
		} else {
			text = text.trim();
			if (text.startsWith("/")) {
				return text.substring(1, text.length());
			}   
			return text;
		} 
	}

	/**
	 * Colocar a primeira barra no texto passado como parâmetro, caso ela já não esteja lá
	 * @param text Texto onde o 'slash' deverá ser colocada
	 * @return
	 */
	public static String colocarBarraInicial(String text) {
		if (ehVazio(text)) {
			return text;
		} else {
			text = text.trim();
			if (!text.startsWith("/")) {
				return "/"+text;
			}   
			return text;
		} 
	}

	/**
	 * Colocar a última barra no texto passado como parâmetro, caso ela já não esteja lá
	 * @param text Texto onde o 'slash' deverá ser colocada
	 * @return
	 */
	public static String colocarBarraFinal(String text) {
		if (ehVazio(text)) {
			return text;
		} else {
			text = text.trim();
			if (!text.endsWith("/")) {
				return text+"/";
			}   
			return text;
		} 
	}

	public static void sleep(int segundos) {
		try {
			Thread.sleep(segundos*1000);
		} catch (InterruptedException e) {
			//ignorar
		}
	}

	public static String getDataHora() {
		return getDataHora(null);
	}
	
	public static String getDataHora(Date data) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if (data == null) {
		   return df.format(System.currentTimeMillis());
		} else {
	       return df.format(data);
		}
	}

	public static File createLog(String path, String bandeira) {
		File logFileName = new File(path+Util.FILE_SEPARATOR+Util.LOG_FILENAME);
		FileUtils.deleteQuietly(logFileName);
        StringBuffer data = new StringBuffer("# Arquivo de log para criação de apps para a bandeira "+bandeira);
		data.append("\n# INICIO: "+Util.getDataHora()+"\n------------------");
		try {
			FileUtils.writeStringToFile(logFileName, data.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return logFileName;
	}
	
	public static void writeLog(File logFileName, String texto) {
		if (logFileName==null) return;
		try {
			FileUtils.writeStringToFile(logFileName, texto+"\n",true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void closeLog(File logFileName) {
		if (logFileName==null) return;
		try {
			FileUtils.writeStringToFile(logFileName, "#------------Processamento encerrado em: "+Util.getDataHora(),true);
			logFileName = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

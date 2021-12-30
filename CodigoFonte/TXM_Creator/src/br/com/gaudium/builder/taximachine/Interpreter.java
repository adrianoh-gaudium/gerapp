package br.com.gaudium.builder.taximachine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

public abstract class Interpreter {

	protected static final String YES = "YES";
	protected static final String TIPO = "TIPO";
	protected static final String DRIVERMACHINE = "DRIVERMACHINE";
	protected static final String MOTORISTA = "MOTORISTA";
	protected static final String MACHINE = "MACHINE";
	protected static final String LW_DRIVER = "driver";
	protected static final String LW_TAXI = "taxi";
	protected static final String SITE = "SITE";
	protected static final String LOGINCPF = "LOGINCPF";
	protected static final String MAPKEYTAXISTA = "MAPKEYTAXISTA";
	protected static final String MAPKEYANDROID = "MAPKEYANDROID";
	protected static final String MAPKEYCLIENTE = "MAPKEYCLIENTE";
	protected static final String NOMEAPPPASSAGEIRO = "NOMEAPPPASSAGEIRO";
	protected static final String KEYWORD = "KEYWORD";
	protected static final String NOMEAPP = "NOMEAPP";
	protected static final String NOMEAPPPASSAGEIROMETA = "NOMEAPPPASSAGEIROMETA";
	protected static final String NOMEAPPTAXISTA = "NOMEAPPTAXISTA";
	protected static final String NOMEAPPTAXISTAMETA = "NOMEAPPTAXISTAMETA";
	protected static final String BUNDLEID = "BUNDLEID";
	protected static final String NOMECOOP = "NOMECOOP";
	protected static final String CIDADE = "CIDADE";
	protected static final String UF = "UF";
	protected static final String TELEFONE = "TELEFONE";
	protected static final String BAIRRO = "BAIRRO";
	protected static final String IDIOMA = "IDIOMA";
	protected static final String APPSTAXIBR = "APPSTAXIBR";
	protected static final String ITUNESAPP = "ITUNESAPP";
	protected static final String URLITUNES = "URLITUNES";
	protected static final String ITUNESID = "ITUNESID";
	protected static final String APPKEY= "APPKEY";
	protected static final String FIREBASEPASSAGEIRO = "FIREBASEPASSAGEIRO";
	protected static final String FIREBASEPASSAGEIROPROJ = "FIREBASEPASSAGEIROPROJ";
	protected static final String FIREBASETAXISTA = "FIREBASETAXISTA";
	protected static final String FIREBASETAXISTAPROJ = "FIREBASETAXISTAPROJ";
	protected static final String USUARIOITUNES = "USUARIOITUNES";
	protected static final String ITUNESTEAMNAME = "ITUNESTEAMNAME";
	protected static final String ITUNESTEAMID = "ITUNESTEAMID";
	protected static final String FACEBOOKPASSAGEIRO = "FACEBOOKPASSAGEIRO";
	protected static final String ACCKITKEY = "ACCKITKEY";
	protected static final String EMAIL = "EMAIL";
	protected static final String PROJECTNUM = "PROJECTNUM";
	protected static final String ANALYTICS = "ANALYTICS";
	protected static final String MAPKEYIOS = "MAPKEYIOS";

	protected HashMap<String, String> hashVars;
	protected ICallback callback;

	protected String nomeDir, miscDados;
	protected DataObj data;

	protected ShellExecutor rt = ShellExecutor.getCommandExecutor();

	protected boolean imagens=false;
	private ArrayList<String> aguardandoAtivacao = new ArrayList<String>();

    protected Interpreter(HashMap<String,String> map, DataObj data) {
		hashVars = map;
		this.data = data;
 	    inicializarDados();
    }
    
    protected void intepretarItem(String linha0) {}
    public boolean go(String codigoProj) {return false;}
	
	public boolean getImagens() {
		return imagens;
	}

	public void setImagens(boolean im) {
		imagens = im;
	}

	public void setCallback(ICallback callback) {
		this.callback = callback;
	}

	public ArrayList<String> getAguardandoAtivacao() {
		return aguardandoAtivacao;
	}
	
	public void addClienteAguardandoAtivacao(String coop) {
		this.aguardandoAtivacao.add(coop);
	}

	protected void callback(String text) {
		if (this.callback!=null) this.callback.callback(text);
	}

	protected void ajustar() {
		// Retirar aspas de APPKEY
		String appkeyValue = hashVars.get(APPKEY);
		if (!Util.ehVazio(appkeyValue) && !appkeyValue.matches("^[[a-z]|[0-9]|[A-Z]].*[[a-z]|[0-9]|[A-Z]]$")) {
			hashVars.put(APPKEY, appkeyValue.substring(1, appkeyValue.length()-1));
		}
		
		if (MOTORISTA.equalsIgnoreCase(hashVars.get(TIPO)) && 
				YES.equalsIgnoreCase(hashVars.get(DRIVERMACHINE))) {
			hashVars.put(MACHINE, LW_DRIVER);
		} else {
			hashVars.put(MACHINE, LW_TAXI);
		}
		
		if (hashVars.get(SITE)==null) {
			hashVars.put(SITE, "http://www."+hashVars.get(MACHINE)+"machine.com.br");
		}
		
		if (hashVars.get(LOGINCPF)==null) {
			hashVars.put(LOGINCPF, "NO");
		}

		if (hashVars.get(MAPKEYTAXISTA)==null) {
			hashVars.put(MAPKEYTAXISTA, hashVars.get(MAPKEYANDROID));
			hashVars.put(MAPKEYCLIENTE, hashVars.get(MAPKEYANDROID));
		}

		// Caso o nome do app passageiro não seja personalizado, usar o padrão NOMEAPP
		if (hashVars.get(NOMEAPPPASSAGEIRO)==null) {
			hashVars.put(NOMEAPPPASSAGEIRO,hashVars.get(NOMEAPP));
		}
		
		// O nome do app passageiro do meta é sempre igual ao dos arquivos do builder
		hashVars.put(NOMEAPPPASSAGEIROMETA, hashVars.get(NOMEAPPPASSAGEIRO));

		// Verifica se o nome do app taxista é personalizado ou não
		if (hashVars.get(NOMEAPPTAXISTA)==null) {
			// Caso não seja, usa TIPO e NOMEAPP para definir o nome app do builder e do meta
			hashVars.put(NOMEAPPTAXISTA,hashVars.get(TIPO)+" "+hashVars.get(NOMEAPP));
			hashVars.put(NOMEAPPTAXISTAMETA, hashVars.get(NOMEAPP)+" - "+hashVars.get(TIPO));
		} else {
			// Caso o nome do app taxista seja personalizado, o meta tem o mesmo nome do nome do builder
			hashVars.put(NOMEAPPTAXISTAMETA,hashVars.get(NOMEAPPTAXISTA));
		}
		
		if (hashVars.get(BUNDLEID)==null) {
			hashVars.put(BUNDLEID, "br.com."+hashVars.get(MACHINE)+"machine."+hashVars.get(NOMECOOP).toLowerCase());
		}
		
		if (hashVars.get(APPSTAXIBR)==null) {
			hashVars.put(APPSTAXIBR,  Util.removerAcentos(hashVars.get(NOMEAPP).toLowerCase().replace(" ","").replace("'","")));
		}

		if (hashVars.get(ITUNESAPP)==null) {
			hashVars.put(ITUNESAPP,hashVars.get(NOMEAPP));
		}
		
		hashVars.put(ITUNESAPP, hashVars.get(ITUNESAPP).toLowerCase().replace(" ","-").replace("'",""));

		if (hashVars.get(URLITUNES)==null) {
			hashVars.put(URLITUNES,"https://itunes.apple.com/br/app/"+hashVars.get(ITUNESAPP)+"/id"+
		       hashVars.get(ITUNESID)+"?l=pt&ls=1&mt=8\napps.taxi.br");
		} else {
			hashVars.put(URLITUNES,hashVars.get(URLITUNES).replace("/us/","/br/"));
		}

//      No script, a linha abaixo não fez sentido pra mim, preferi não traduzir.
//		hash_vars['SITE']=hash_vars['SITE'].replace('/','\/')
		
	}
	
	protected void inicializarDados() {
		// inicializar map

		//Inserindo Analytics padrão
		hashVars.put("ANALYTICS","46053169");

		//Inserindo ID do iTunes vazio para não rodar geração de QR Codes caso não haja o código
		hashVars.put("URLITUNES","none");

		//Inserindo telefone vazio para não rodar a geração do telefone
		hashVars.put("TELEFONE","none");

		//Inserindo idioma padrão
		hashVars.put("IDIOMA","pt-BR");

		//Inserindo bairro padrão
		hashVars.put("BAIRRO","Centro");

		//Obedecendo o bundle Drivermachine por padrão
		hashVars.put("DRIVERMACHINE","YES");
	}
	
	protected boolean interpretarItem(String linha0) {
		String[] elementos;
		String linha;
		try {
			// Verifica se a linha não é um comentário e nem uma linha vazia
			if (!Util.ehVazio(linha0) && !linha0.trim().startsWith("//")) {
				// Deve-se separar possíveis comentários (//) dentro da linha,
				// Links web não entram na separação.
				linha = linha0.split("[^:]//")[0];

				// Transforma a linha obtida anteriormente em uma lista de dois elementos
				// o primeiro elemento que vem antes do ":" é a chave
				// o segundo elemento que vem depois é o valor da chave
				elementos = linha.trim().split(":");
				if (elementos.length>2) {  //mais de um ˜:" aparecendo, só vale separar o primeiro.
					for (int i = 2; i<elementos.length; i++) {
						elementos[1] = elementos[1] + ":" + elementos[i];
					}
				}
				// Adiciona no mapa (hashVars) a chave e valor
				hashVars.put(elementos[0],elementos[1]);
			}
		} catch (Exception e) {
            return false;
		}

		return true;
	}

	protected void mover_pkg() throws IOException {
		callback("Renomeando pastas");

		File srcDir, destDir;
		if (data.isAndroid()) {
			srcDir = new File(nomeDir+"/Android/CoopAndroid/coop-modelo_cliente_custom");
			destDir = new File(nomeDir+"/Android/CoopAndroid/"+hashVars.get(NOMECOOP).toLowerCase()+"_cliente_custom");
			FileUtils.moveDirectory(srcDir, destDir);

			srcDir = new File(nomeDir+"/Android/CoopAndroid/coop-modelo_taxista_custom");
			destDir = new File(nomeDir+"/Android/CoopAndroid/"+hashVars.get(NOMECOOP).toLowerCase()+"_taxista_custom");
			FileUtils.moveDirectory(srcDir, destDir);

			File bandeiraFile = new File(nomeDir+"/Android/CoopAndroid/bandeira.properties");
			String dados = "tipo=" + hashVars.get(MACHINE).toLowerCase() + "\n"
					+ "fcm_taxista="+hashVars.get(FIREBASETAXISTA) + "\n"
					+ "fcm_taxista_projeto="+hashVars.get(FIREBASETAXISTAPROJ) + "\n"
					+ "fcm_passageiro="+hashVars.get(FIREBASEPASSAGEIRO) + "\n"
					+ "fcm_passageiro_projeto="+hashVars.get(FIREBASEPASSAGEIROPROJ);

			//Criar arquivo bandeira.properties com duas linhas
			FileUtils.writeStringToFile(bandeiraFile, dados, Util.UTF8, false);	

			//Renomeando pasta iOS p/ nome da cooperativa
			srcDir = new File(nomeDir+"/Android/CoopAndroid");
			destDir = new File(nomeDir+"/Android/"+hashVars.get(NOMECOOP));
			FileUtils.moveDirectory(srcDir, destDir);		

			//Copiando para pasta de arquivos customizados
			srcDir = new File(nomeDir+"/Android/"+hashVars.get(NOMECOOP));
			destDir = new File(data.getDadosAppsPath()+"/android/"+hashVars.get(NOMECOOP));
			FileUtils.copyDirectory(srcDir, destDir);

			//Copiando para a pasta de entrada do Builder (Custom)
			srcDir = new File(nomeDir+"/Android/"+hashVars.get(NOMECOOP));
			destDir = new File(data.getAndroidCustomPath()+"/"+hashVars.get(NOMECOOP));
			FileUtils.copyDirectory(srcDir, destDir);
		}

		if (data.isIOS()) {
			//Se for publicação nominal e todos os campos necessários estiverem preenchidos
			if(!Util.ehVazio(hashVars.getOrDefault(USUARIOITUNES, "")) && !Util.ehVazio(hashVars.getOrDefault(ITUNESTEAMID, ""))&& !Util.ehVazio(hashVars.getOrDefault(ITUNESTEAMNAME, ""))) {
				String dados_ios = "ENV[\"APPLE_ID\"] = \"" + hashVars.get(USUARIOITUNES) + "\"\n"
						+  "ENV[\"TEAM_ID\"] = \"" + hashVars.get(ITUNESTEAMID) + "\"\n"
						+  "ENV[\"FASTLANE_ITC_TEAM_NAME\"] = \"" + hashVars.get(ITUNESTEAMNAME) + "\"";
				System.out.println(dados_ios);
				System.out.println(nomeDir+"/iOS/coopios/TaxiMachine/iTunesConfig.d");

				File iTunesConfig = new File(nomeDir+"/iOS/coopios/TaxiMachine/iTunesConfig.d");
				FileUtils.writeStringToFile(iTunesConfig, dados_ios, Util.UTF8, false);
			}

			//renomeando pasta iOS p/ nome da cooperativa
			srcDir = new File(nomeDir+"/iOS/coopios");
			destDir = new File(nomeDir+"/iOS/"+hashVars.get(NOMECOOP).toLowerCase());
			FileUtils.moveDirectory(srcDir, destDir);		

			//Copiando para pasta de arquivos customizados
			srcDir = new File(nomeDir+"/iOS/"+hashVars.get(NOMECOOP).toLowerCase());
			destDir = new File(data.getDadosAppsPath()+"/iOS/"+hashVars.get(NOMECOOP).toLowerCase());
			FileUtils.copyDirectory(srcDir, destDir);

			//Copiando para a pasta de entrada do Builder (Custom)
			srcDir = new File(nomeDir+"/iOS/"+hashVars.get(NOMECOOP).toLowerCase());
			destDir = new File(data.getIOSCustomPath()+"/"+hashVars.get(NOMECOOP).toLowerCase());
			FileUtils.copyDirectory(srcDir, destDir);
		}
	}
}
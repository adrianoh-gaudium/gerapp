package br.com.gaudium.builder.taximachine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PackageProcessor extends ImageProcessor {

	private static HashMap<String, ArrayList<String>> arquivos = new HashMap<String, ArrayList<String>>();
	static {
		ArrayList<String> lista = new ArrayList<String>();
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/raw/url.properties");
		lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/raw/url.properties");
		lista.add("iOS/coopios/TaxiMachine/Config/Config.h");
		arquivos.put(Interpreter.APPKEY, lista);

		lista = new ArrayList<String>();
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values/map_key.xml");
		lista.add("iOS/coopios/TaxiMachine/TaxiMachine-Info.plist");
		arquivos.put(Interpreter.FACEBOOKPASSAGEIRO, lista);

		lista = new ArrayList<String>();
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values/map_key.xml");
		lista.add("iOS/coopios/TaxiMachine/TaxiMachine-Info.plist");
		arquivos.put(Interpreter.ACCKITKEY, lista);

		lista = new ArrayList<String>();
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values-es/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values-pt/custom.xml");
		lista.add("iOS/coopios/TaxiMachine/TaxiMachine-Info.plist");
		arquivos.put(Interpreter.NOMEAPPPASSAGEIRO, lista);

		lista = new ArrayList<String>();
		lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/values/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/values-es/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/values-pt/custom.xml");
		arquivos.put(Interpreter.NOMEAPPTAXISTA, lista);

		lista = new ArrayList<String>();
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values-es/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values-pt/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/values/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/values-es/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/values-pt/custom.xml");
		lista.add("iOS/coopios/TaxiMachine/Config/Config.h");
		arquivos.put(Interpreter.SITE, lista);

		lista = new ArrayList<String>();
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values-es/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values-pt/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/values/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/values-es/custom.xml");
		lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/values-pt/custom.xml");
		lista.add("iOS/coopios/TaxiMachine/Config/Config.h");
		arquivos.put(Interpreter.EMAIL, lista);

		lista = new ArrayList<String>();
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values/gcmdata.xml");
		lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/values/gcmdata.xml");
		arquivos.put(Interpreter.PROJECTNUM, lista);

		lista = new ArrayList<String>();
		// lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values/analytics.xml");
		// lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/values/analytics.xml");
		lista.add("iOS/coopios/TaxiMachine/Config/Config.h");
		arquivos.put(Interpreter.ANALYTICS, lista);

		lista = new ArrayList<String>();
		lista.add("Android/CoopAndroid/coop-modelo_cliente_custom/res/values/map_key.xml");
		arquivos.put(Interpreter.MAPKEYCLIENTE, lista);

		lista = new ArrayList<String>();
		lista.add("Android/CoopAndroid/coop-modelo_taxista_custom/res/values/map_key.xml");
		arquivos.put(Interpreter.MAPKEYTAXISTA, lista);

		lista = new ArrayList<String>();
		lista.add("iOS/coopios/TaxiMachine/Config/Config.h");
		arquivos.put(Interpreter.MAPKEYIOS, lista);

		lista = new ArrayList<String>();
		lista.add("iOS/coopios/TaxiMachine/TaxiMachine-Info.plist");
		arquivos.put(Interpreter.BUNDLEID, lista);
	}

	public PackageProcessor(DataObj data, HashMap<String, String> hashVars, String nomeDir, String miscDados) {
		super(data, hashVars, nomeDir, miscDados);
	}

	public void go() throws IOException {
		// se o flag de imagens foi marcado, pastasApps() já foi chamado anteriormente,
		// não precisa chamar novamente.
		pacotes();
		System.out.println("TXMBDG: A");
		if (!data.isImagens()) {
			setImagens(imagens("pkg"));
		}
		System.out.println("TXMBDG: B");
		certs();
		System.out.println("TXMBDG: C");
		mover_pkg();
		System.out.println("TXMBDG: D");
		criarCliente();
		System.out.println("TXMBDG: E");
	}

	protected void certs() throws IOException {
		String script = data.getScriptsPath()+Util.FILE_SEPARATOR+"geraCertseChaves_new.sh";

		callback("Criando certificação digital e chaves de assinatura");
		String iTunesAccount = hashVars.getOrDefault(USUARIOITUNES, data.getContaFastLane());

		String target = "both";
		if (!data.isAndroid()) {
			target = "ios";
		} else if (!data.isIOS()) {
			target = "android";
		}

		rt.execScript(data.getBasePath(),
				new String[]{"sh", script,  
						nomeDir, 
						hashVars.get(NOMECOOP),
						hashVars.get(MACHINE),
						data.getDadosSubDir(),
						hashVars.get(BUNDLEID),
						data.getDadosAppsPath().getAbsolutePath(),
						iTunesAccount,
						hashVars.get(APPKEY),
						target});

	}

	protected void criarCliente() throws IOException {
		String appkey = hashVars.get(APPKEY); 
		String nomeCoop = hashVars.get(NOMECOOP);
		callback("Criando cliente "+nomeCoop+" / "+appkey);

		JSONObject jsonCliente = new JSONObject();

		jsonCliente.put("email", data.getEmailLoginCliente()); //"rsagoes@gaudium.com.br");
		jsonCliente.put("login", data.getEmailLoginCliente()); //"rsagoes@gaudium.com.br");
		jsonCliente.put("nome", data.getNomeCliente());   //"Cliente Teste");
		jsonCliente.put("senha", data.getSenhaCliente());  //"123456");
		jsonCliente.put("senha_atual", "");
		jsonCliente.put("telefone", "021999990000");
		String cliente = URLEncoder.encode(jsonCliente.toString(),"UTF-8");

		String novaConta = "op=add&user_id=IMEI&cliente="+cliente; 

		String url;
		if (data.getServidorCliente().startsWith("http://")) {
			url = data.getServidorCliente()+data.getApiCliente();
		} else {
			url = "http://"+data.getServidorCliente()+data.getApiCliente();
		}
		//		String url = "http://cloud.taximachine.com.br/api/passageiro";

		StringEntity entity = new StringEntity(novaConta,ContentType.APPLICATION_FORM_URLENCODED);

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		request.addHeader("Content-type", "application/x-www-form-urlencoded");
		request.addHeader("App-Key", appkey);
		request.setEntity(entity);

		HttpResponse response = httpClient.execute(request);

		int status = response.getStatusLine().getStatusCode();
		if (status==HttpURLConnection.HTTP_OK) {
			Reader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int c; (c = in.read()) >= 0;) {
				sb.append((char)c);
			}
			String result = sb.toString();
			in.close();

			JSONParser jp = new JSONParser();
			JSONObject jsonResponse;
			try {
				jsonResponse = (JSONObject)jp.parse(result);
				if (jsonResponse.containsKey("success") && 
						(Boolean)jsonResponse.get("success")) {
					if (jsonResponse.containsKey("response")) {
						JSONObject jsonExigirAtivacao = ((JSONObject)jsonResponse.get("response"));
						callback("Criação cliente "+nomeCoop+" OK");
						if (jsonExigirAtivacao.containsKey("exigir_ativacao_cliente") &&
								"1".equals((String)jsonExigirAtivacao.get("exigir_ativacao_cliente"))) {
							this.addClienteAguardandoAtivacao(hashVars.get(NOMECOOP));
						}
					}
				} else {
					callback("[ERRO] Criação do cliente "+nomeCoop+"\n"+result);
				}
			} catch (ParseException e) {
				callback("[ERRO] Criação do cliente "+nomeCoop+" - retorno inválido do serviço");
			}
		} else {
			callback("[ERRO] Criação do cliente "+nomeCoop+" - response code = "+status);
		}
	}
	
	public void pacotes() throws IOException {

		callback.callback("Adicionando dados da bandeira aos arquivos-exemplo");

		for (String palavra : arquivos.keySet()) {
			for (String endereco : arquivos.get(palavra)) {
				// Os tokens a serem substituídos estão rodeados por ˜_˜.
				if (hashVars.get(palavra) != null) {
					Util.replaceStringFile(nomeDir + "/" + endereco, "_" + palavra + "_", hashVars.get(palavra));
				}
//				rt.exec("cat "+nomeDir+"/"+endereco+" | sed s/\'_"+palavra+"_\'/"+hashVars.get(palavra).replace("&","\\&").
//						replace("'","\\'").replace("/","\\/").replace(" ","\\ ")+"/g > temp.txt; cp temp.txt "+nomeDir+"/"+endereco);
			}
		}

		if ("SIM".equalsIgnoreCase(hashVars.get(LOGINCPF)) ||
     		"YES".equalsIgnoreCase(hashVars.get(LOGINCPF))) {
			for (String arqconf : arquivos.get(APPKEY)) {
				if (arqconf.contains("coop-modelo_cliente_custom/res/raw/url.properties")) {
					// Incluir linha no final do arquivo.
					FileUtils.writeStringToFile(new File(nomeDir + "/" + arqconf), "\nlogincpf=true", Util.UTF8, true);
//					rt.exec("echo 'logincpf=true' >> "+nomeDir+"/"+arqconf);
//				} else if (arqconf.contains("Config.h")) {
//					rt.exec("cat "+nomeDir+"/"+arqconf+" | sed s/'IS_LOGIN_BY_CPF 0'/'IS_LOGIN_BY_CPF 1'/g > temp.txt; cp temp.txt "+nomeDir+"/"+arqconf);
				}
			}
		}

//		rt.exec("rm temp.txt");
	}

}
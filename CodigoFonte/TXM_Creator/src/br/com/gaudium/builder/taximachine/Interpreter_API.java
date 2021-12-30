package br.com.gaudium.builder.taximachine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.BasicHttpParams;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Classe responsável por interpretar a API e alterar o valor de hashVars
 **/
public class Interpreter_API extends Interpreter {

	public Interpreter_API(HashMap<String, String> map, DataObj data) {
		super(map, data);
	}

	// Conecta à API e modifica a variável hashVars
	public boolean go(String codigoProj) {
		String queryParams = "cod_interno=" + codigoProj;

		String url = data.getServidorConfAPI() + data.getUrlConfAPI();
		if (!url.startsWith("http://") || !url.startsWith("https://")) {
			url = "https://" + url;
		}

		url = url + "?" + queryParams;
		System.out.println("URL = " + url);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("Content-type", "application/x-www-form-urlencoded");
		// TODO: Adicionar App-Key como variável de ambiente
		request.addHeader("App-Key", "chCoopTXMExemplo");

		int status = 0;
		HttpResponse response;
		try {
			response = httpClient.execute(request);
			status = response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			callback("[ERRO] Projeto " + codigoProj
					+ ": não foi possível conectar com a API para leitura da configuração do cliente. "
					+ e.getLocalizedMessage());
			return false; // ir para o próximo código de projeto.
		}

		if (status == HttpURLConnection.HTTP_OK) {
			Reader in = null;
			String result;
			try {
				in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				for (int c; (c = in.read()) >= 0;) {
					sb.append((char) c);
				}
				result = sb.toString();
			} catch (Exception e) {
				callback("[ERRO] Projeto " + codigoProj
						+ ": não foi possível processar os dados lidos da API de configuração do cliente");
				return false; // ir para o próximo código de projeto.
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e) {
					}
				}
			}

			JSONParser jp = new JSONParser();
			JSONObject jsonResponse;
			try {
				jsonResponse = (JSONObject) jp.parse(result);
				if (jsonResponse.containsKey("success") && (Boolean) jsonResponse.get("success")) {
					if (jsonResponse.containsKey("response")) {
						JSONObject jsonResposta = ((JSONObject) jsonResponse.get("response"));
						populateHashVars(jsonResposta);
					}
				} else {
					callback("[ERRO] Retorno da API do projeto " + codigoProj + "\n" + result);
				}
			} catch (ParseException e) {
				callback("[ERRO] Retorno da API do projeto " + codigoProj + "\n" + result);
			}
		} else {
			callback("[ERRO] Retorno da API do projeto " + codigoProj + " - response code = " + status);

		}

		// Aqui "hashVars" estará preenchido e será complementado pelo método abaixo.
		this.ajustar();

		return true;

	}

	// Popula a variável hashVars com os dados do json de resposta da API
	private void populateHashVars(JSONObject jsonResponse) {

		String itunesTeamID = jsonResponse.get("id_time_itunes").toString();
		String city = jsonResponse.get("cidade").toString();
		String neighborhood = jsonResponse.get("bairro").toString();
		String uf = jsonResponse.get("uf").toString();
		String email = jsonResponse.get("email").toString();
		String name = jsonResponse.get("nome").toString();
		String type = jsonResponse.get("tipo").toString();
		String googleMapsKeyAndroid = jsonResponse.get("chave_google_map_android").toString();
		String googleMapsKeyIOS = jsonResponse.get("chave_google_map_android").toString();
		String facebookPassenger = jsonResponse.get("id_facebook_passageiro").toString();
		String appKey = jsonResponse.get("chave_app").toString();
		String telephone = jsonResponse.get("telefone").toString();
		String urlItunes = jsonResponse.get("url_itunes").toString();
		String userItunes = jsonResponse.get("nome_usuario_itunes").toString();
		String teamNameItunes = jsonResponse.get("nome_time_itunes").toString();
		String firebasePassengerID = jsonResponse.get("id_firebase_passageiro").toString();
		String firebaseTaxiID = jsonResponse.get("id_firebase_taxista").toString();
		String projectNum = jsonResponse.get("projectnum").toString();
		String bundlePassenger = jsonResponse.get("bundle_passageiro_android").toString();

		String taxiFirebaseProjNum = getProjNum(firebaseTaxiID);
		String taxiFirebaseID = getFirebaseID(firebaseTaxiID);
		String passengerFirebaseProjNum = getProjNum(firebasePassengerID);
		String passengerFirebaseID = getFirebaseID(firebasePassengerID);
		String keyword = getKeyword(bundlePassenger);

		hashVars.put(CIDADE, city);
		hashVars.put(BAIRRO, neighborhood);
		hashVars.put(UF, uf);
		hashVars.put(TIPO, getAppType(type));
		hashVars.put(NOMEAPP, name);
		hashVars.put(MAPKEYANDROID, googleMapsKeyAndroid);
		hashVars.put(FACEBOOKPASSAGEIRO, facebookPassenger);
		hashVars.put(APPKEY, appKey);
		hashVars.put(FIREBASEPASSAGEIRO, passengerFirebaseID);
		hashVars.put(FIREBASEPASSAGEIROPROJ, passengerFirebaseProjNum);
		hashVars.put(FIREBASETAXISTA, taxiFirebaseID);
		hashVars.put(FIREBASETAXISTAPROJ, taxiFirebaseProjNum);
		hashVars.put(PROJECTNUM, projectNum);
		hashVars.put(KEYWORD, keyword);
		hashVars.put(NOMECOOP, getCoopName(name));
		hashVars.put("PLANO", "Pro");

		if (!telephone.equals("")) {
			hashVars.put(TELEFONE, telephone);
		}

		if (!urlItunes.equals("")) {
			hashVars.put(URLITUNES, urlItunes);
		}

		if (!googleMapsKeyIOS.equals("")) {
			hashVars.put(MAPKEYIOS, googleMapsKeyIOS);
		}

		if (!itunesTeamID.equals("")) {
			hashVars.put(ITUNESTEAMID, itunesTeamID);
		}

		if (!userItunes.equals("")) {
			hashVars.put(USUARIOITUNES, userItunes);
		}

		if (!teamNameItunes.equals("")) {
			hashVars.put(ITUNESTEAMNAME, teamNameItunes);
		}

		if (!email.equals("")) {
			hashVars.put(EMAIL, email);
		}
	}

	// Dependendo do tipo do aplicativo retornado na API, retorna o correto nome
	private static String getAppType(String typeLetter) {
		if (typeLetter.equalsIgnoreCase("E")) {
			return "Motorista";
		} else if (typeLetter.equalsIgnoreCase("T")) {
			return "Taxista";
		} else if (typeLetter.equalsIgnoreCase("M")) {
			return "Mototaxista";
		}
		return "";
	}

	// Retorna o número do projeto a partir do ID do firebase
	private static String getProjNum(String firebaseID) {
		String answer = "";
		// Conta as ocorrências de : no id
		int counter = 0;
		for (int i = 0; i < firebaseID.length(); i++) {
			int flag = 0;
			if (firebaseID.charAt(i) == ':') {
				counter += 1;
				flag = 1;
			}
			if (counter == 1 && flag == 0) {
				answer += firebaseID.charAt(i);
			}
		}
		return answer;
	}

	// Retorna o ID do projeto Firebase a partir do ID completo
	private static String getFirebaseID(String firebaseID) {
		return firebaseID.substring(firebaseID.lastIndexOf(":") + 1);
	}

	// A partir do bundle id retorna a keyword (mesmo nome da pasta Dropbox)
	private static String getKeyword(String bundlePassenger) {
		String textFrom = "br.com.";
		String withoutPrefix = bundlePassenger.substring(bundlePassenger.indexOf(textFrom) + textFrom.length(),
				bundlePassenger.length());
		String removeSuffix = withoutPrefix.substring(0, withoutPrefix.indexOf("."));

		return removeSuffix;
	}

	// Retorna o nome da cooperativa a partir do nome retornado na API - apenas
	// retira espaço
	private static String getCoopName(String nameApp) {
		return nameApp.replaceAll("\\s", "");
	}
}

package br.com.gaudium.builder.taximachine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MarketingProcessor extends ImageProcessor {
	private String mapKey = "AIzaSyAAniX6AJtO7YokxkdyiH-LU4Cxw4zBg9w";

	public MarketingProcessor(DataObj data, HashMap<String, String> hashVars, String nomeDir, String miscDados) {
		super(data, hashVars, nomeDir, miscDados);
	}

	public void go(boolean temosImagens) throws IOException {
		if (!temosImagens) {
			setImagens(imagens("mkt"));
		}
		mapa();
		marketing();
		zip_marketing();
	}

	private void zip_marketing() throws IOException {
		callback.callback("Zipando arquivos");
		String path = nomeDir+Util.FILE_SEPARATOR+data.getMarketingSubDir();	 

		String script = data.getScriptsPath().getAbsolutePath()+Util.FILE_SEPARATOR+"zip.sh";
		rt.execScript(data.getScriptsPath(), new String[]{"sh", 
				script, path, hashVars.get(NOMECOOP)});

		/*  def zip_marketing.
        os.chdir(nomedir.replace('\\','')+"/"+diretorio_mkt)
		print "Compactando..."
		os.system("zip -r "+hash_vars["NOMECOOP"]+"-Marketing.zip Adesivo/adesivo.png Cartao/cartao.png Flyer/flyer.png QRCodes/qrcode-*.png")
	 */

    }

	private void marketing() throws IOException {
		//String modulesPath = data.getBasePath().getAbsolutePath()+"/../..";

		callback.callback("Criando QRCodes");
		String script = data.getScriptsPath().getAbsolutePath()+Util.FILE_SEPARATOR+"qrcodes.sh";
		rt.execScript(data.getScriptsPath(), new String[]{"sh", 
				script, nomeDir,
				data.getMarketingSubDir(),
				hashVars.get(NOMECOOP).toLowerCase(),
				hashVars.get(URLITUNES),
				hashVars.get(APPSTAXIBR),
				hashVars.get(MACHINE)});

		callback.callback("Criando flyer");
		script = data.getScriptsPath().getAbsolutePath()+Util.FILE_SEPARATOR+"flyer"+Util.FILE_SEPARATOR+"flyer.sh";
		rt.execScript(data.getScriptsPath(), new String[]{"sh",
				script, nomeDir,
				data.getMarketingSubDir(),
				hashVars.get(TIPO),
				hashVars.get(KEYWORD).toUpperCase(),
				hashVars.get(NOMEAPP),
				hashVars.get(NOMECOOP).toLowerCase(),
				hashVars.get(BAIRRO),
				hashVars.get(CIDADE),
				hashVars.get(UF).toUpperCase(),
				hashVars.get(APPSTAXIBR),
				data.getScriptsPath().getAbsolutePath(),
				data.getImagensSubDir()});

		callback.callback("Compondo imagens para apps");
		script = data.getScriptsPath().getAbsolutePath()+Util.FILE_SEPARATOR+"appstaxibr"+Util.FILE_SEPARATOR+"appstaxibr.sh";
		rt.execScript(data.getScriptsPath(), new String[]{"sh",
				script,
				nomeDir,
				hashVars.get(TIPO),
				data.getScriptsPath().getAbsolutePath(),
				data.getImagensSubDir()});

		callback.callback("Criando cartão");
		script = data.getScriptsPath().getAbsolutePath()+Util.FILE_SEPARATOR+"cartao"+Util.FILE_SEPARATOR+"cartao.sh";
		rt.execScript(data.getScriptsPath(),new String[]{"sh",
				script,
				nomeDir,
				data.getMarketingSubDir(),
				hashVars.get(TIPO),
				hashVars.get(APPSTAXIBR),
				hashVars.get(TELEFONE),
				data.getScriptsPath().getAbsolutePath(),
				data.getImagensSubDir()});

		callback.callback("Criando adesivo");
		script = data.getScriptsPath().getAbsolutePath()+Util.FILE_SEPARATOR+"adesivo"+Util.FILE_SEPARATOR+"adesivo.sh";
		rt.execScript(data.getScriptsPath(),new String[]{"sh",
				script,
				nomeDir,
				data.getMarketingSubDir(),
				hashVars.get(TIPO),
				hashVars.get(APPSTAXIBR),
				hashVars.get(TELEFONE),
         		data.getScriptsPath().getAbsolutePath(),
		        data.getImagensSubDir()});
	}

	/*  def marketing.
 		os.system("sh Modules/qrcodes/qrcodes.sh "+nomedir+" "+diretorio_mkt.replace(" ","\ ").replace('&','\&')+" "+hash_vars["NOMECOOP"].lower()+" "+hash_vars['URLITUNES'].replace('&','\&')+" "+hash_vars["APPSTAXIBR"]+" "+hash_vars["MACHINE"]+"")
		os.system("sh Modules/flyer/flyer.sh "+nomedir+" "+diretorio_mkt.replace(" ","\ ").replace('&','\&')+" "+hash_vars["TIPO"]+" "+hash_vars["KEYWORD"].upper().replace(" ","\ ").replace("\'","\\\'").replace('&','\&')+" "+hash_vars['NOMEAPP'].replace(" ","\ ").replace("\'","\\\'").replace('&','\&')+" "+hash_vars["NOMECOOP"].lower()+" "+hash_vars["BAIRRO"].replace(" ","\ ").replace('&','\&')+" "+hash_vars["CIDADE"].replace(" ","\ ").replace('&','\&')+" "+hash_vars["UF"].upper()+" "+hash_vars["APPSTAXIBR"]+"")
		os.system("sh Modules/appstaxibr/appstaxibr.sh "+nomedir+" "+hash_vars["TIPO"]+"")
		os.system("sh Modules/cartao/cartao.sh "+nomedir+" "+diretorio_mkt.replace(" ","\ ").replace('&','\&')+" "+hash_vars["TIPO"]+" "+hash_vars['APPSTAXIBR']+" "+hash_vars["TELEFONE"].replace(" ","\ ").replace('(','\(').replace(')','\)'))
		os.system("sh Modules/adesivo/adesivo.sh "+nomedir+" "+diretorio_mkt.replace(" ","\ ").replace('&','\&')+" "+hash_vars['APPSTAXIBR']+" "+hash_vars["TELEFONE"].replace(" ","\ ").replace('(','\(').replace(')','\)'))
	 */

	private String queryURL(String url) throws IOException {
		HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();
		httpConnection.setRequestMethod("GET");
		httpConnection.setConnectTimeout(20000);
		httpConnection.setReadTimeout(20000);
		httpConnection.setInstanceFollowRedirects(true);

		int status = httpConnection.getResponseCode();
		if (status==200) {
			Reader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int c; (c = in.read()) >= 0;) {
				sb.append((char)c);
			}
			String response = sb.toString();
			in.close();
			return response;
		} else {
			return null;
		}

	}

	protected void mapa() throws IOException {
		String tipoBandeira = hashVars.get(TIPO);
		String bairro = hashVars.get(BAIRRO);
		String cidade = hashVars.get(CIDADE);
		String uf = hashVars.get(UF);
		String enderecoBandeira = nomeDir;

		mapa=false;
		String nomeCoop = hashVars.get(NOMECOOP);

		callback.callback("Gerando mapas para "+nomeCoop);

		String localPadrao = bairro+" "+cidade+" "+uf;
		String livre, ocupado, confirmado, bola, passageiro;
		
		String veiculo, cor, nomeCoopColor;
		if ("TAXISTA".equalsIgnoreCase(tipoBandeira)) {
			//livre="http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-taxi-livre.png";
			livre = data.getUrlPinsMapa()+data.getPinTaxistaLivre();
			//ocupado="http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-taxi-ocupado.png";
			ocupado = data.getUrlPinsMapa()+data.getPinTaxistaOcupado();
			//confirmado="http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-taxi-confirmado.png";
			confirmado = data.getUrlPinsMapa()+data.getPinTaxistaConfirmado();
			veiculo = "taxi";
			cor = "taxi";
			nomeCoopColor="#263238";
		} else if ("MOTOTAXISTA".equalsIgnoreCase(tipoBandeira)) {
			//livre="http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-moto-livre.png";
			livre = data.getUrlPinsMapa()+data.getPinMototaxistaLivre(); 
			//ocupado="http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-moto-ocupado.png";
			ocupado = data.getUrlPinsMapa()+data.getPinMototaxistaOcupado();
			//confirmado="http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-moto-confirmado.png";
			confirmado = data.getUrlPinsMapa()+data.getPinMototaxistaConfirmado();
			veiculo = "moto";
			cor = "taxi";
			nomeCoopColor="#FFFFFF";
		} else if ("Motorista".equalsIgnoreCase(tipoBandeira) ) {
			//livre="http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-carro-livre.png";
			livre = data.getUrlPinsMapa()+data.getPinMotoristaLivre();
			//ocupado="http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-carro-ocupado.png";
			ocupado = data.getUrlPinsMapa()+data.getPinMotoristaOcupado();
			//confirmado="http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-carro-confirmado.png";
			confirmado = data.getUrlPinsMapa()+data.getPinMotoristaConfirmado();
			veiculo = "carro";
			cor = "driver";
			nomeCoopColor="#FFFFFF";
		} else {
			callback.callback("Tipo de bandeira desconhecido ("+tipoBandeira+") para "+hashVars.get(NOMECOOP)+
					".  Verifique o arquivo 'dados.d'");
			return;
		}

		//"http://desenv.taximachine.com.br/images/pin/v2/pin_taxi_ocupado.png";
		bola = data.getUrlPinsMapa()+data.getPinLocalizacao();
		//Marcadores p/ reskin
		String pin_passageiro_reskin = "http://desenv.taximachine.com.br/images/pin/v2/pin_lugar_" +  cor + ".png";
		String pin_livre = "http://desenv.taximachine.com.br/images/pin/v2/pin_" + veiculo +  "_livre.png";
		String pin_ocupado = "http://desenv.taximachine.com.br/images/pin/v2/pin_" + veiculo +  "_ocupado.png";
		String pin_confirmado = "http://desenv.taximachine.com.br/images/pin/v2/pin_" + veiculo +  "_confirmado.png";
		
		//passageiro="http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-pass.png";
		passageiro = data.getUrlPinsMapa()+data.getPinPassageiro();

		Double deltalat = 0.017813d;
		Double deltalng = 0.013381d;

		//pegar latlong do local
		localPadrao = URLEncoder.encode(localPadrao, "UTF-8");
		String dadoCidade = null;

		JSONParser jp = new JSONParser();
		JSONObject jsonResponse;
		double centroLat = 0d, centroLng = 0d;
        final int contagemMaxima = 8;
		int contador = 1;
		
		while (contador<=contagemMaxima) { 
			dadoCidade = queryURL("https://maps.googleapis.com/maps/api/geocode/json?address="+localPadrao+
					"&key=" + mapKey);
			try {
				jsonResponse = (JSONObject)jp.parse(dadoCidade);
				if (jsonResponse.containsKey("status") && 
						"OK".equalsIgnoreCase((String)jsonResponse.get("status"))) {
					JSONArray results = (JSONArray)jsonResponse.get("results");
					JSONObject location = (JSONObject)((JSONObject)((JSONObject)results.get(0)).get("geometry")).get("location");
					centroLat = (Double)location.get("lat");
					centroLng = (Double)location.get("lng");
					break;  //tudo certo, sair do loop
					//centrolat=objcidade['results'][0]['geometry']['location']['lat']
					//centrolng=objcidade['results'][0]['geometry']['location']['lng']
				} else {
					if (contador<contagemMaxima) {
						callback.callback("[ERRO] Mapa "+nomeCoop+" - status não OK, tentativa ("+(++contador)+"/"+contagemMaxima+")");
						Util.sleep(2);  //aguardar 2 segundos.
					} else {
						callback.callback("[ERRO] Mapa "+nomeCoop+" - status não OK, desistindo.  Retorno do Google abaixo:");
						callback.callback(jsonResponse.toJSONString());
						contador = -1;
						break;
					}
				}
			} catch (ParseException e) {
				callback.callback("[ERRO] Mapa "+nomeCoop+" - retorno inválido do serviço");
				return;
			}
		}
		
		if (contador<0) {
		   return;  //desistindo de acessar o mapa.	
		}
		
		//pares de coordenadas para cada taxista no mapa
		HashMap<String, ArrayList<Double>> paresLatLng = new HashMap<String,ArrayList<Double>>();
		paresLatLng.put("lat", new ArrayList<Double>());
		paresLatLng.put("lng", new ArrayList<Double>());

		boolean objRotaStatus = false;
		JSONArray objRota = null;
		String[] parRota = new String[2];

		int totalTaxistas=8;
		int maxtries=5;
		boolean enoughDistance = false;

		for (int numero = 1; numero<=totalTaxistas; numero++) {
			boolean flag=true;
			int counter=0;

			while (flag && counter<=maxtries) {
				// Calcular aleatoriamente lat/lng por perto da região encontrada anteriormente. 
				double randLat = (centroLat-deltalat) + (Math.random()*((centroLat+deltalat/2) - (centroLat-deltalat)));
				double randLng = (centroLng-deltalng) + (Math.random()*((centroLng+deltalng/2) - (centroLng-deltalng)));

				String dado = queryURL("https://maps.googleapis.com/maps/api/geocode/json?latlng="+
						String.valueOf(randLat)+","+String.valueOf(randLng)+"&key=" + mapKey); 

				try {
					jsonResponse = (JSONObject)jp.parse(dado);
					if (jsonResponse.containsKey("status") && 
							"OK".equalsIgnoreCase((String)jsonResponse.get("status"))) {

						JSONArray results = (JSONArray)jsonResponse.get("results");
						JSONObject location = (JSONObject)((JSONObject)((JSONObject)results.get(0)).get("geometry")).get("location");
						double lat = (Double)location.get("lat");
						double lng = (Double)location.get("lng");


						if (numero != 1) {
							//comparando distância ponto a ponto
							enoughDistance = true;
							double distanciaPontos=0;
							for (int numeroLocal=0; numeroLocal<paresLatLng.get("lat").size();numeroLocal++)  {
								distanciaPontos = Math.sqrt(Math.pow(paresLatLng.get("lat").get(numeroLocal)-lat,2) +
										Math.pow(paresLatLng.get("lng").get(numeroLocal)-lng,2));
								if (distanciaPontos < 0.006d) {
									enoughDistance = false;
								}
							}
						}

						if (numero == 1 || counter == maxtries || enoughDistance) {
							//posição válida
							paresLatLng.get("lat").add(lat);
							paresLatLng.get("lng").add(lng);
							flag=false;

							int countRota=0; 

							while (countRota < numero-1 && !objRotaStatus) {
								String rota = queryURL("https://maps.googleapis.com/maps/api/directions/json?origin=" + 
										String.valueOf(paresLatLng.get("lat").get(countRota)) + "," + 
										String.valueOf(paresLatLng.get("lng").get(countRota)) + "&destination=" + 
										String.valueOf(paresLatLng.get("lat").get(numero-1)) + "," + 
										String.valueOf(paresLatLng.get("lng").get(numero-1)) + "&key=" + mapKey);

								jsonResponse = (JSONObject)jp.parse(rota);
								objRota = (JSONArray)jsonResponse.get("routes");
								objRotaStatus = (jsonResponse.containsKey("status") && "OK".equalsIgnoreCase((String)jsonResponse.get("status")));
								if (objRotaStatus) {
									parRota[0] = String.valueOf(paresLatLng.get("lat").get(countRota)) + "," + 
											String.valueOf(paresLatLng.get("lng").get(countRota));
									parRota[1] = String.valueOf(paresLatLng.get("lat").get(numero-1)) + "," +
											String.valueOf(paresLatLng.get("lng").get(numero-1));
								}
								countRota = countRota + 1;
							}
						}
					}
				} catch (Exception e) {
					//ignore
				}

				counter=counter+1;
			}

		}

		String linhaLivre = "";
		int size = paresLatLng.get("lat").size();
		for (int i=0;i<size-2;i++) {
			linhaLivre = linhaLivre+"%7C"+String.valueOf(paresLatLng.get("lat").get(i)) + 
					"," + String.valueOf(paresLatLng.get("lng").get(i));
		}

		String linhaOcupado = "%7C" + String.valueOf(paresLatLng.get("lat").get(size-2)) + 
				"," + String.valueOf(paresLatLng.get("lng").get(size-2)) + "%7C" + 
				String.valueOf(paresLatLng.get("lat").get(size-1)) + "," + 
				String.valueOf(paresLatLng.get("lng").get(size-1));

		String mapPath = enderecoBandeira+"/Design/imagensGeradas/";
		String command = "wget -O \"" + mapPath +"mapa.png\" \""+
				"https://maps.googleapis.com/maps/api/staticmap?center=" + String.valueOf(centroLat) + "," + 
				String.valueOf(centroLng) + "&size=391x531&markers=icon:" + livre + linhaLivre + 
				"&markers=icon:" + ocupado + linhaOcupado + "&scale=2&zoom=14&amp&format=png&key="+mapKey+"\"";

		callback.callback("Criando mapa.png");
		rt.execScript(new File(mapPath), new String[]{"sh", "-c", command});
		
		String commandReskin = "wget -O \"" + mapPath +"mapa_reskin.png\" \""+
				"https://maps.googleapis.com/maps/api/staticmap?center=" + String.valueOf(centroLat) + "," + 
				String.valueOf(centroLng) + "&size=320x490"
				+ "&markers=icon:" + pin_passageiro_reskin + "%7C" + String.valueOf(centroLat) + "," + String.valueOf(centroLng)
				+ "&markers=icon:" + pin_livre + linhaLivre
				+ "&markers=icon:" + pin_ocupado + linhaOcupado + "&scale=2&zoom=14&amp&format=png&key="+mapKey
				+ "&scale=2&zoom=14&amp&format=png&key="+mapKey+"\"";
		
		System.out.println("TXMDBG - " + commandReskin);

		callback.callback("Criando mapa_reskin.png");
		rt.execScript(new File(mapPath), new String[]{"sh", "-c", commandReskin});

		//screenshot das rotas
		if (objRota!=null) {
			String points = (String)((JSONObject)((JSONObject)objRota.get(0)).get("overview_polyline")).get("points");
			command = "wget -O \""+mapPath+"\"maparotacliente.png \"" + 
					"https://maps.googleapis.com/maps/api/staticmap?size=391x531&markers=icon:" +
					confirmado+"%7C"+parRota[0]+"&markers=icon:"+bola+"%7C"+parRota[1]+"&path=weight:3%7Ccolor:0x0000fe%7Cenc:" + 
					points.replace("`","\\`")+"&scale=2&zoom=14&amp&format=png&key="+mapKey+"\"";

			callback.callback("Criando maparotacliente.png");
			rt.execScript(new File(mapPath), new String[]{"sh", "-c", command});
			
			command = "wget -O \""+mapPath+"\"maparotacliente_reskin.png \"" + 
					"https://maps.googleapis.com/maps/api/staticmap?size=320x490"
					+ "&markers=icon:" + pin_confirmado+"%7C"+parRota[0]+"&markers=icon:"+ pin_passageiro_reskin +"%7C"+parRota[1]+"&scale=2&zoom=14&amp&format=png&key="+mapKey+"\"";

			callback.callback("Criando maparotacliente_reskin.png");
			rt.execScript(new File(mapPath), new String[]{"sh", "-c", command});

			command = "wget -O \""+mapPath+"\"maparotataxista.png \"" + 
					"https://maps.googleapis.com/maps/api/staticmap?size=391x531&markers=icon:"+bola+"%7C"+parRota[0]+
					"&markers=icon:"+passageiro+"%7C"+parRota[1]+"&path=weight:3%7Ccolor:0x0000fe%7Cenc:"+
					points.replace("`","\\`")+"&scale=2&zoom=14&amp&format=png&key="+mapKey+"\"";

			callback.callback("Criando maparotataxista.png");
			rt.execScript(new File(mapPath), new String[]{"sh", "-c", command});
			
		}
		
		String endereco = getEndereco(String.valueOf(centroLat), String.valueOf(centroLng));
		//convert -background transparent -fill nomeCoopColor -font AvenirMedium -pointsize 16 -size 345x caption:'This is a verrrryyyyyyyyy long caption line.' caption.png
		String writeNameCommand = "convert -background transparent -fill '#37474F' -font AvenirMedium -pointsize 16 -size 345x caption:\"" + endereco + "\" \"" + mapPath + "\"address_name.png";
		rt.execScript(new File(mapPath), new String[]{"sh", "-c", writeNameCommand});
		
		String writeCoopNameCommand = "convert -background transparent -fill '" + nomeCoopColor + "' -font AvenirNextCondensed -size 426x26 -pointsize 18 -gravity center label:\"" + hashVars.get(NOMEAPP).toUpperCase() + "\" \"" + mapPath + "\"coop_name.png";
		rt.execScript(new File(mapPath), new String[]{"sh", "-c", writeCoopNameCommand});

		mapa=true;

	}

	private boolean mapa=false;

	public boolean getMapa() {
		return mapa;
	}
	
	protected String getEndereco(String lat, String lng) {
		String endereco = "";
		
		try {
			String dado = queryURL("https://maps.googleapis.com/maps/api/geocode/json?latlng="+ lat+","+lng+"&key=" + mapKey);
			
			JSONParser jp = new JSONParser();
			JSONObject jsonResponse = (JSONObject)jp.parse(dado);
			if (jsonResponse.containsKey("status") && 
					"OK".equalsIgnoreCase((String)jsonResponse.get("status"))) {

				JSONArray results = (JSONArray)jsonResponse.get("results");
				String addressComplete = (String)((JSONObject)results.get(0)).get("formatted_address");
				String[] endrArray = addressComplete.split("-");
				if(endrArray.length > 2) {
					endereco = endrArray[0] + " - " + endrArray[1];
				} else {
					endereco = addressComplete;
				}
			}
		} catch (Exception e) {
			//ignore
		}
		
		return endereco;
	}
}


/*  mapa.py
#!/usr/env/python
# -*- coding: utf-8 -*-

import sys
import os
import random
import json
import urllib, urllib2
import math

def geradorMapas(tipobandeira, bairro, cidade, uf, enderecobandeira):
	print "Gerando mapas..."

	#inp = sys.argv
	#print inp
	local_padrao=bairro+' '+cidade+' '+uf

	if tipobandeira == 'Taxista':
		livre='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-taxi-livre.png'
		ocupado='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-taxi-ocupado.png'
		confirmado='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-taxi-confirmado.png'
	elif tipobandeira == 'Mototaxista':
		livre='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-moto-livre.png'
		ocupado='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-moto-ocupado.png'
		confirmado='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-moto-confirmado.png'
	elif tipobandeira == 'Motorista':
		livre='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-carro-livre.png'
		ocupado='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-carro-ocupado.png'
		confirmado='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-carro-confirmado.png'

	bola='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-lugar.png'
	passageiro='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-pass.png'

	deltalat=0.017813
	deltalng=0.013381

	#pegar latlong do local
	print "Obtendo latlong do local"
	local = urllib.quote(local_padrao)
	dadocidade=urllib2.urlopen('https://maps.googleapis.com/maps/api/geocode/json?address='+local)

	objcidade=json.loads(dadocidade.read().decode('utf-8'))

	dadocidade.close()

	centrolat=objcidade['results'][0]['geometry']['location']['lat']
	centrolng=objcidade['results'][0]['geometry']['location']['lng']

	pareslatlong={}
	pareslatlong['lat']=[]
	pareslatlong['lng']=[]
	objrota={}
	objrota['status']=""
	parrota=[]


	totaldetaxistas=8
	#desvlng=0
	#desvlat=0
	maxtries=5
	divdelta=2.5

	#def desvpad(lista):
	#	soma=sum(lista)
	#	somaquadrados=sum([i**2 for i in lista])
	#	tamanho=float(len(lista))
	#	return math.sqrt(somaquadrados/tamanho-(soma/tamanho)**2)

	for numero in range(1,totaldetaxistas):
		print "Buscando posição de taxista No. "+str(numero)
		flag=True
		counter=0

		while flag and counter<=maxtries:
			randlat=random.uniform(centrolat-deltalat, centrolat+deltalat)
			randlng=random.uniform(centrolng-deltalng, centrolng+deltalng)
			print "Tentativa", counter
			dado=urllib2.urlopen('http://maps.googleapis.com/maps/api/geocode/json?latlng='+str(randlat)+','+str(randlng))

			objeto=json.loads(dado.read().decode('utf-8'))

			if objeto['status'] == 'OK':
				if numero != 1:
					#calculando desvio padrão dos pares latlong existentes
					#desvlat=desvpad(pareslatlong['lat']+[objeto['results'][0]['geometry']['location']['lat']])
					#desvlng=desvpad(pareslatlong['lng']+[objeto['results'][0]['geometry']['location']['lng']])

					#comparando distância ponto a ponto
					enoughdistance = True
					for numerolocal in range(0,len(pareslatlong['lat'])):
						distanciapontos=math.sqrt((pareslatlong['lat'][numerolocal]-objeto['results'][0]['geometry']['location']['lat'])**2+(pareslatlong['lng'][numerolocal]-objeto['results'][0]['geometry']['location']['lng'])**2)
						print "DISTÂNCIA:", distanciapontos
						if distanciapontos < 0.006:
							enoughdistance = False

				if numero == 1 or counter == maxtries or enoughdistance:
				#if numero == 1 or counter == maxtries or (desvlat>(deltalat/divdelta) and desvlng>(deltalng/divdelta)):
					print "Posição válida"
					pareslatlong['lat'].append(objeto['results'][0]['geometry']['location']['lat'])
					pareslatlong['lng'].append(objeto['results'][0]['geometry']['location']['lng'])
					flag=False

					countrota=0

					while countrota < numero-1 and objrota['status'] != 'OK':
						rota=urllib2.urlopen('https://maps.googleapis.com/maps/api/directions/json?origin='+str(pareslatlong['lat'][countrota])+','+str(pareslatlong['lng'][countrota])+'&destination='+str(pareslatlong['lat'][numero-1])+','+str(pareslatlong['lng'][numero-1])+'&key=AIzaSyCmUYZGEKHm7XIt_Ku1uFQbn9Ujc5PBR8k')
						objrota=json.loads(rota.read().decode('utf-8'))
						rota.close()
						if objrota['status'] == 'OK':
							print "Rota entre marcadores",countrota+1,"e",numero,"encontrada."
							parrota=[str(pareslatlong['lat'][countrota])+','+str(pareslatlong['lng'][countrota]),str(pareslatlong['lat'][numero-1])+','+str(pareslatlong['lng'][numero-1])]
						countrota=countrota+1

			counter=counter+1
	linhalivre=''

	pares2=zip(pareslatlong['lat'],pareslatlong['lng'])
	for item in pares2[:-2]:
		linhalivre=linhalivre+"%7C"+str(item[0])+','+str(item[1])

	linhaocupado="%7C"+str(pares2[-2][0])+','+str(pares2[-2][1])+"%7C"+str(pares2[-1][0])+','+str(pares2[-1][1])


	os.system('wget -O Modules/mapa/imagens/mapa.png "https://maps.googleapis.com/maps/api/staticmap?center='+str(centrolat)+','+str(centrolng)+'&size=391x531&markers=icon:'+livre+linhalivre+'&markers=icon:'+ocupado+linhaocupado+'&scale=4&zoom=14&amp&format=png&key=AIzaSyCmUYZGEKHm7XIt_Ku1uFQbn9Ujc5PBR8k"')

	#rota para screenshot

	os.system('wget -O Modules/mapa/imagens/maparotacliente.png "https://maps.googleapis.com/maps/api/staticmap?size=391x531&markers=icon:'+confirmado+'%7C'+parrota[0]+'&markers=icon:'+bola+'%7C'+parrota[1]+'&path=weight:3%7Ccolor:0x0000fe%7Cenc:'+objrota['routes'][0]['overview_polyline']['points'].replace('`','\`')+'&scale=4&zoom=14&amp&format=png&key=AIzaSyCmUYZGEKHm7XIt_Ku1uFQbn9Ujc5PBR8k"')

	os.system('wget -O Modules/mapa/imagens/maparotataxista.png "https://maps.googleapis.com/maps/api/staticmap?size=391x531&markers=icon:'+bola+'%7C'+parrota[0]+'&markers=icon:'+passageiro+'%7C'+parrota[1]+'&path=weight:3%7Ccolor:0x0000fe%7Cenc:'+objrota['routes'][0]['overview_polyline']['points'].replace('`','\`')+'&scale=4&zoom=14&amp&format=png&key=AIzaSyCmUYZGEKHm7XIt_Ku1uFQbn9Ujc5PBR8k"')

	os.system('cp Modules/mapa/imagens/mapa*.png '+enderecobandeira+'/Design/imagensGeradas/')

 */
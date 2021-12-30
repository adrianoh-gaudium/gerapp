package br.com.gaudium.builder.taximachine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MetaProcessor extends MarketingProcessor {
	private boolean mapaRodou, imgsTotal;
	private String target, typeTarget;
	private boolean reload = false;

	public MetaProcessor(boolean reload, DataObj data, HashMap<String, String> hashVars, String nomeDir, String miscDados, String target, String typeTarget) {
		super(data, hashVars, nomeDir, miscDados);
		this.target = target;
		this.typeTarget = typeTarget;
		this.reload = reload;
	}

	public void go() throws IOException {
//		if (reload) {
//			if (!imgsTotal) {
//				imagens("pkg");
//			}
//		}

		if (!mapaRodou) {
			mapa();
		}

		metadados();
	}

	private void metadados() throws IOException {
		
		String script = data.getScriptsPath().getAbsolutePath()+Util.FILE_SEPARATOR+"telas"+Util.FILE_SEPARATOR+"telas.sh";
		rt.execScript(data.getScriptsPath(), new String[]{"sh", 
				script, nomeDir,
				data.getImagensSubDir(),
				hashVars.get(TIPO),
				data.getScriptsPath().getAbsolutePath()});

		String secretJsonFileName;
		if(hashVars.get(TIPO).equals("Motorista")) {
			secretJsonFileName = "drm_secret.json";
		}
		else {
			secretJsonFileName = "txm_secret.json";
		}
		
		String playStoreJson = data.getSecretPath() + Util.FILE_SEPARATOR + secretJsonFileName;
		File jsonSecretDir = new File(data.getSecretPath());
		for(String filename : jsonSecretDir.list()) {
			if(filename.split(".json")[0].equalsIgnoreCase(hashVars.get(NOMECOOP))){
				playStoreJson = data.getSecretPath() + Util.FILE_SEPARATOR + filename;
				break;
			}
		}
		
		script = data.getScriptsPath().getAbsolutePath()+Util.FILE_SEPARATOR+"metadata"+Util.FILE_SEPARATOR+"metadata.sh";
		rt.execScript(data.getScriptsPath(), new String[]{"sh",
				script, nomeDir,
				data.getImagensSubDir(),
				data.getDadosSubDir(),
				hashVars.get(NOMECOOP).toLowerCase(),
				hashVars.get(NOMEAPP),//.replace(" ","\\ ").replace("\'","\\\'").replace("&","\\&")+" "+
				hashVars.get(NOMEAPPPASSAGEIROMETA),//.replace(" ","\\ ").replace("\'","\\\'").replace("&","\\&")+" "+
				hashVars.get(NOMEAPPTAXISTAMETA),//.replace(" ","\\ ").replace("\'","\\\'").replace("&","\\&")+" "+
				hashVars.get(KEYWORD).toLowerCase(),//.replace(" ","\\ ").replace("\'","\\\'").replace("&","\\&")+" "+
				hashVars.get(TIPO),
				hashVars.get(CIDADE),//.replace(" ","\\ ").replace("&","\\&")+" "+
				hashVars.get(IDIOMA),
				hashVars.get(BUNDLEID),
				hashVars.get(DRIVERMACHINE),
				target,
				typeTarget,
				data.getScriptsPath().getAbsolutePath(),
				playStoreJson,
				hashVars.getOrDefault(ITUNESTEAMNAME, "")});

	}

	public void setMapaRodou(boolean mapaRodou) {
		this.mapaRodou = mapaRodou;
	}

	public void setImgsTotal(boolean imgsTotal) {
		this.imgsTotal = imgsTotal;
	}

}

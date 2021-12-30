package br.com.gaudium.builder.taximachine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

public class ImageProcessor extends Interpreter {
	
	public ImageProcessor(DataObj dataObj, HashMap<String, String> map, String strDir, String strMiscDados) {
		super(map, dataObj);
		nomeDir = strDir;
		miscDados = strMiscDados;
		data = dataObj;
		hashVars = map;
//		hashVars.clear();  //retirando dados da inicialização da classe ancestral
	}

//	private void excluirPastas() {
////		rt.exec("rm -r "+nomeDir+"/Android/CoopAndroid/coop-modelo_cliente_custom/res/raw");
////		rt.exec("rm -r "+nomeDir+"/Android/CoopAndroid/coop-modelo_taxista_custom/res/raw");
//		FileUtils.deleteQuietly(new File(nomeDir+"/Android/CoopAndroid/coop-modelo_cliente_custom/res/raw"));
//		FileUtils.deleteQuietly(new File(nomeDir+"/Android/CoopAndroid/coop-modelo_taxista_custom/res/raw"));
//		
////		rt.exec("rm -r "+nomeDir+"/iOS/coopios/TaxiMachine/Config");
////		rt.exec("rm -r "+nomeDir+"/iOS/coopios/TaxiMachine/TaxiMachine-Info.plist");
//		FileUtils.deleteQuietly(new File(nomeDir+"/iOS/coopios/TaxiMachine/Config"));
//		FileUtils.deleteQuietly(new File(nomeDir+"/iOS/coopios/TaxiMachine/TaxiMachine-Info.plist"));
//
//		Iterator<File> itCliente = FileUtils.iterateFilesAndDirs(new File(nomeDir+"/Android/CoopAndroid/coop-modelo_cliente_custom/res"), 
//				FalseFileFilter.INSTANCE, 
//				new IOFileFilter() {
//					@Override
//					public boolean accept(File arg0, String arg1) {return false;}
//					
//					@Override
//					public boolean accept(File f) {
//						return (f.isDirectory() && f.getName().contains("values"));
//					}
//				});
//
//		Iterator<File> itTaxista = FileUtils.iterateFilesAndDirs(new File(nomeDir+"/Android/CoopAndroid/coop-modelo_taxista_custom/res"), 
//				FalseFileFilter.INSTANCE, 
//				new IOFileFilter() {
//					@Override
//					public boolean accept(File arg0, String arg1) {return false;}
//					
//					@Override
//					public boolean accept(File f) {
//						return (f.isDirectory() && f.getName().contains("values"));
//					}
//				});
//
////		rt.exec("rm -r "+nomeDir+"/Android/CoopAndroid/coop-modelo_cliente_custom/res/values*");
////		rt.exec("rm -r "+nomeDir+"/Android/CoopAndroid/coop-modelo_taxista_custom/res/values*");
//
////      pular o primeiro item da lista, pois é a pasta "res"
//		if (itCliente.hasNext()) itCliente.next();
//		while (itCliente.hasNext()) {
//			File f = itCliente.next();
//			FileUtils.deleteQuietly(f);
//		}
//		
////      pular o primeiro item da lista, pois é a pasta "res"
//		if (itTaxista.hasNext()) itTaxista.next();
//		while (itTaxista.hasNext()) {
//			File f = itTaxista.next();
//			FileUtils.deleteQuietly(f);
//		}
//				
//	}
	
	public void go() throws IOException {
		//Se for pra rodar o package, pode montar pastas de app
		if(data.isPackge()) {
			pastasApps();    //também apaga todas as pastas de imagens
		}
		setImagens(imagens("pkg"));
	}
	
	protected void pastasApps() throws IOException {
		FileUtils.deleteQuietly(new File(nomeDir+"/Android"));  //apaga tudo lá dentro, é recursivo.
		FileUtils.deleteQuietly(new File(nomeDir+"/iOS"));
		//rt.exec("rm -r "+nomeDir+"/Android");

		//		FileUtils.deleteQuietly(new File(miscDados+Util.FILE_SEPARATOR+"Segurança"));
		//		rt.exec("mkdir -p "+miscDados+"/Segurança/Backup");

		//A chamada ao comando original não reconhece wildcards (*.keystore),
		//tampouco conseguimos fazer funcionar empacotando no shell.
		//Vamos resolver usando a api contida em FileUtils.
		//		String keystorePath = data.getDadosAppsPath()+"/android/"+hashVars.get(NOMECOOP);
		//		try {
		//			Iterator<File> it = FileUtils.iterateFiles(new File(keystorePath), 
		//					new IOFileFilter() {
		//
		//				@Override
		//				public boolean accept(File arg0, String arg1) {return false;}
		//
		//				@Override
		//				public boolean accept(File f) {
		//					return (f.getName().contains(".keystore"));
		//				}
		//			}, null);
		//
		//			while (it.hasNext()) {
		//				File f = it.next();
		//				//efetivamente copiando os arquivos .keystore (deve ter só um).
		//				FileUtils.copyFileToDirectory(new File(keystorePath+"/"+f.getName()), new File(miscDados+"/Segurança/Backup"));
		//			}
		//		} catch (Exception e) {
		//			//callback.callback("[INFO] Não há arquivo .keystore para copiar em "+keystorePath);
		//		}
		FileUtils.deleteQuietly(new File(data.getDadosAppsPath()+"/android/"+hashVars.get(NOMECOOP)));
		FileUtils.deleteQuietly(new File(data.getDadosAppsPath()+"/iOS/"+hashVars.get(NOMECOOP).toLowerCase()));
		FileUtils.deleteQuietly(new File(data.getAndroidCustomPath()+"/"+hashVars.get(NOMECOOP)));
		FileUtils.deleteQuietly(new File(data.getIOSCustomPath()+"/"+hashVars.get(NOMECOOP).toLowerCase()));
		//os.system("rm -r "+pasta_dados_app+"/android/"+hash_vars['NOMECOOP']+" "+
		//  pasta_dados_app+"/iOS/"+hash_vars['NOMECOOP'].lower()+" "+
		//  pasta_android_custom+"/"+hash_vars['NOMECOOP']+" "+
		//  pasta_ios_custom+"/"+hash_vars['NOMECOOP'].lower())

		//os.system("cp -R modules/arquivos-base/Android modules/arquivos-base/iOS "+nomedir)
		FileUtils.copyDirectory(new File(data.getBasePath()+"/Android"), 
				new File(nomeDir+"/Android"));

		FileUtils.copyDirectory(new File(data.getBasePath()+"/iOS"), 
				new File(nomeDir+"/iOS"));

	}

	protected boolean imagens(String soMarketing) throws IOException {
		
		String img = hashVars.get(NOMEAPP)+" - "+hashVars.get(CIDADE)+", "+hashVars.get(UF);
		
		FileUtils.deleteQuietly(new File(nomeDir+Util.FILE_SEPARATOR+data.getImagensSubDir()+Util.FILE_SEPARATOR+"imagensGeradas"));

		String script = data.getScriptsPath().getAbsolutePath()+Util.FILE_SEPARATOR+"imagens"+Util.FILE_SEPARATOR+"imagens.sh";
		
		callback.callback("Montando imagens");
		rt.execScript(data.getScriptsPath(),new String[]{"sh", script, 
		  nomeDir, data.getImagensSubDir(),
	      hashVars.get(NOMECOOP).toLowerCase(), hashVars.get(URLITUNES),
	  	  hashVars.get(APPSTAXIBR), hashVars.get(MACHINE), soMarketing, img});
		
//		rt.exec("sh " + data.getBasePath()+"/../modules/imagens/imagens.sh "+nomeDir+" "+data.getImagensSubDir()+" "+
//			      hashVars.get(NOMECOOP).toLowerCase()+" "+hashVars.get(URLITUNES).replace("&","\\&")+" "+
//			  	  hashVars.get(APPSTAXIBR)+" "+hashVars.get(MACHINE)+" "+soMarketing+" "+
//			      hashVars.get(NOMEAPP).replace(" ","\\ ").replace("\\'","\\\\'").replace("&","\\&")+"\\ -\\ "+
//			  	  hashVars.get("CIDADE").replace(" ","\\ ").replace("\\'","\\\\'").replace("&","\\&")+",\\ "+hashVars.get("UF"));


		rt.exec("mkdir -p "+data.getDadosAppsPath()+"/Logos_pendentes/"+hashVars.get(NOMECOOP).toLowerCase());
		
		FileUtils.copyFileToDirectory(new File(nomeDir+"/"+data.getImagensSubDir()+"/imagensGeradas/logo-bandeira.png") , 
				new File(data.getDadosAppsPath()+"/Logos_pendentes/"+hashVars.get(NOMECOOP).toLowerCase()));
//		rt.exec("cp "+nomeDir+"/"+data.getImagensSubDir()+"/imagensGeradas/logo-bandeira.png "+
//		         data.getDadosAppsPath()+"/Logos_pendentes/"+hashVars.get(NOMECOOP).toLowerCase());

		FileUtils.copyFileToDirectory(new File(nomeDir+"/"+data.getImagensSubDir()+"/imagensGeradas/"+img+".jpg"),
				new File(data.getDadosAppsPath()+"/Logos_pendentes/"+hashVars.get(NOMECOOP).toLowerCase()));
		
//		rt.exec("cp "+nomeDir+"/"+data.getImagensSubDir()+"/imagensGeradas/"+hashVars.get(NOMEAPP).
//				replace(" ","\\ ").replace("\\'","\\\\'").replace("&","\\&")+"\\ -\\ "+hashVars.get("CIDADE").
//				replace(" ","\\ ").replace("\\'","\\\\'").replace("&","\\&")+",\\ "+hashVars.get("UF")+".jpg "+
//				data.getDadosAppsPath()+"/Logos_pendentes/"+hashVars.get(NOMECOOP).toLowerCase());
		
		return soMarketing.equals("pkg");
	}
}
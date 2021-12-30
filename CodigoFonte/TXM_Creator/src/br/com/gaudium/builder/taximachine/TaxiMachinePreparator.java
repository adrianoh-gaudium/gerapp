package br.com.gaudium.builder.taximachine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class TaxiMachinePreparator {

	private DataObj data;
	private ICallback callback;  //para escrever na textarea de log.
	private boolean imgsTotal=false, mapaRodou=false;

	public TaxiMachinePreparator(DataObj data, ICallback callback) {
		super();
		this.data = data;
		this.callback = callback;
	}

	/**
	 * Subrotina principal de customização, erro "fatal" é retornado, se aparecer algum.
	 * @return  Mensagem de erro, se existir.
	 */
	public String go() {
		System.out.println("TXM Android Builder - preparação do ambiente");
		callback.callback("Iniciando preparação");
		String resultado = validar();
		if (!Util.ehVazio(resultado)) return resultado;
		if (canceladoPeloUsuario()) return "";

		// Preencher lista com os códigos dos projetos a serem processados.
		String listaProjs[] = data.getProjNumbers().split(",");
		String nomeFolder, miscDados, nomeDir, typeTarget = "both";
		
		String target = "both";
		if (!data.isAndroid()) {
			target = "ios";
		} else if (!data.isIOS()) {
			target = "android";
		}
		
		ArrayList<String> aguardandoAtivacao = null;

		// Filtro das pastas no diretório de projetos a serem processados (em andamento).
		IOProjFileFilter projsAndamentoFilter = new IOProjFileFilter(data.getProjsAndamentoPath()); 
		for (String codigoProj : listaProjs) {
			if (!data.isMontando()) {  //usuário cancelou operação
				break;  //sair do loop.
			}
      			
			callback.callback("---------------------------------");
            codigoProj = codigoProj.trim();
			//validar os caracteres do código do projeto
			if (!Util.ehNumero(codigoProj)) {
				if (!Util.ehVazio(codigoProj.trim())) {
					callback.callback("[ERRO] Código "+codigoProj+" é inválido.");
				}
				continue;  //ir para o próximo código de projeto.
			}
			
			//Manter o código do projeto com 3 caracteres ou mais, colocando zeros na frente.
			if (codigoProj.length() < 3) {
				codigoProj = Util.leftpad(codigoProj, "0", 3);
			}
			projsAndamentoFilter.setCodProj(codigoProj);
			List<File> listaArqsPastaProjs = (List<File>) FileUtils.listFilesAndDirs(
					data.getProjsAndamentoPath(), projsAndamentoFilter, projsAndamentoFilter);
			if (listaArqsPastaProjs.size()<2) {
				callback.callback("[ERRO] Projeto "+codigoProj+" não foi encontrado na pasta de projetos em andamento ("+data.getProjsAndamentoPath()+")");
				continue;  //ir para o próximo código de projeto.
			}

			listaArqsPastaProjs.remove(0);

			if (listaArqsPastaProjs.size()>1) {
				callback.callback("[ERRO] Projeto "+codigoProj+": retornou "+listaArqsPastaProjs.size()+
						" diretórios da pasta de projetos em andamento ("+data.getProjsAndamentoPath()+")");
				continue;  //ir para o próximo código de projeto.
			}

			nomeFolder = listaArqsPastaProjs.get(0).getName();
			nomeDir = data.getProjsAndamentoPath()+Util.FILE_SEPARATOR+nomeFolder;
			miscDados = nomeDir + Util.FILE_SEPARATOR + data.getDadosSubDir();
			
			// Criar arquivo de log do "creator". 
			data.setLogFilename(Util.createLog(nomeDir, nomeFolder)); 

			// Se houver pasta com nome Android ou iOS, elas serão apagadas		    
			FileUtils.deleteQuietly(new File(nomeDir+Util.FILE_SEPARATOR+"Android")); //apaga tudo lá dentro, é recursivo.
			FileUtils.deleteQuietly(new File(nomeDir+Util.FILE_SEPARATOR+"iOS"));
			
            //Inicio - Horácio - 02/04/2021
			HashMap<String, String> hashVars = new HashMap<String, String>();
			//A função "configurar" abaixo substitui o código que se segue, que foi comentado.
			//Ela pode chamar um método com a mesma lógica caso a leitura seja via arquivo
			//ou uma outra lógica que recupere as informações via API. Em ambos os casos
			//o Map hashVars retornará preenchido.
			if (!configurar(hashVars, codigoProj, miscDados)) {
				callback.callback("[ERRO] Projeto "+codigoProj+": erro ao processar os dados de configuração");
				continue;  //ir para o próximo código de projeto.		    	
			}
			System.out.println(hashVars);
//			File arqDados = new File(miscDados+Util.FILE_SEPARATOR+"dados.d");
//			if (!arqDados.exists()) {
//				callback.callback("[ERRO] Projeto "+codigoProj+": não foi encontrado arquivo 'dados.d' na pasta "+miscDados);
//				continue;  //ir para o próximo código de projeto.		    	
//			}
//
//			HashMap<String, String> hashVars = new HashMap<String, String>();
//            // Ler o arquivo "dados.d" do projeto em processamento.
//			Interpreter_Dados_d interpretador = new Interpreter_Dados_d(hashVars);
//
//			boolean erro=false;
//			callback.callback("Projeto "+codigoProj+": processando arquivo 'dados.d'");
//			
//			List<String> linhasArqDados;
//			try {
//				linhasArqDados = FileUtils.readLines(arqDados);
//			} catch (IOException e1) {
//				callback.callback("[ERRO] Projeto "+codigoProj+": erro de leitura do 'dados.d' na pasta "+miscDados);
//				continue;  //ir para o próximo código de projeto.		    	
//			}
//			
//			for (String linha0 : linhasArqDados) {
//				erro = !interpretador.interpretar(linha0);
//				if (erro) {
//					callback.callback("[ERRO] Projeto "+codigoProj+", interpretação do arquivo dados.d, linha: " + linha0);
//					break;  //sair do loop, deu erro ao interpretar a linha de dados.d.  Este projeto não será processado.		    	
//				}
//			}
//
//			if (erro) {
//				continue;  //ir para o próximo código de projeto.
//			}

			//Fim - Horácio - 02/04/2021
			if (!data.isMontando()) {  //usuário cancelou operação
				break;  
			}
			
//			// Aqui "hashVars" estará preenchido e será complementado pelo método abaixo.	
//			interpretador.ajustar();
//
//			if (data.isPassageiro() && !data.isTaxista()) {
//				typeTarget = "psg";
//			} else if (!data.isPassageiro() && data.isTaxista()) {
//				typeTarget = "tx";
//			}
			if (data.isImagens()) {
				ImageProcessor im = new ImageProcessor(data, hashVars, nomeDir, miscDados);
				im.setCallback(callback);
				try {
					im.go();
					imgsTotal = im.getImagens();
				} catch (Exception e) {
					callback.callback("[ERRO] Projeto "+codigoProj+": processamento das imagens falhou");
					e.printStackTrace();
					continue;  //Ir para o próximo projeto.		    	
				}
			}

			if (data.isPackge()) {
				PackageProcessor pp = new PackageProcessor(data, hashVars, nomeDir, miscDados);
				pp.setCallback(callback);
			    aguardandoAtivacao = pp.getAguardandoAtivacao();
				try {
					pp.go();
					imgsTotal = pp.getImagens() || imgsTotal;
				} catch (Exception e) {
					callback.callback("[ERRO] Projeto "+codigoProj+": processamento dos pacotes falhou");
					e.printStackTrace();
					continue;  //Ir para o próximo projeto.		    	
				}
			}

			if (!data.isMontando()) {  //usuário cancelou operação
				break;  
			}

			if (data.isMarketing()) {
				MarketingProcessor mp = new MarketingProcessor(data, hashVars, nomeDir, miscDados);
				mp.setCallback(callback);
				try {
					mp.go(imgsTotal);
					mapaRodou = mp.getMapa();
				} catch (Exception e) {
					callback.callback("[ERRO] Projeto "+codigoProj+": processamento do martketing falhou");
					e.printStackTrace();
					continue;  //Ir para o próximo projeto.		    	
				}
			}
			
			if (!data.isMontando()) {  //usuário cancelou operação
				break;  
			}

//			if (data.isReload_meta()) {
//				MetaProcessor rmp = new MetaProcessor(true, data, hashVars, nomeDir, miscDados, target, typeTarget);
//				rmp.setCallback(callback);
//				rmp.setMapaRodou(mapaRodou);
//				mapaRodou = rmp.getMapa();
//				rmp.setImgsTotal(imgsTotal);
//				imgsTotal = rmp.getImagens();
//				try {
//					rmp.go();
//				} catch (Exception e) {
//					callback.callback("[ERRO] Projeto "+codigoProj+": processamento do 'reload-meta' falhou");
//					e.printStackTrace();
//					continue;  //Ir para o próximo projeto.		    	
//				}
//			}
			if (data.isMeta()) {
				MetaProcessor rmp = new MetaProcessor(false, data, hashVars, nomeDir, miscDados, target, typeTarget);
				rmp.setCallback(callback);
				rmp.setMapaRodou(mapaRodou);
				try {
					rmp.go();
				} catch (Exception e) {
					callback.callback("[ERRO] Projeto "+codigoProj+": processamento do 'meta' falhou");
					e.printStackTrace();
					continue;  //Ir para o próximo projeto.		    	
				}
			}
		}
		
		if (aguardandoAtivacao!=null && aguardandoAtivacao.size()>0) {
			callback.callback("---------------------------------");
			callback.callback("[INFO] As bandeiras abaixo estão com Cliente Teste aguardando ativação:");
			for (String b : aguardandoAtivacao) {
				callback.callback(b+"\n");
			}
		}
		
		return "";
	}

	private boolean canceladoPeloUsuario() {
		return !data.isMontando();
	}

	private boolean configurar(HashMap<String,String> hashVars, String codigoProj, String miscDados) {
		Interpreter interpretador;
		if (data.isConfAPI()) {
	        // Acionar API para ler os dados de configuração do projeto em processamento.
			interpretador = new Interpreter_API(hashVars, data);
			callback.callback("Projeto "+codigoProj+": acionando API para leitura da configuração");
		} else {
	        // Ler o arquivo "dados.d" do projeto em processamento.
			interpretador = new Interpreter_Dados_d(hashVars, miscDados, data);
			callback.callback("Projeto "+codigoProj+": processando arquivo 'dados.d'");
		}
		
		interpretador.setCallback(callback);
		return interpretador.go(codigoProj);
		
	}
	
	private String validar() {
		String r = "";
		if (Util.ehVazio(data.getDadosAppsPath()))
			r = r+"Preencha o diretório de dados dos apps\n";
		if (Util.ehVazio(data.getProjsAndamentoPath())) 
			r = r+"Preencha o diretório dos projetos em andamento\n";
		if (Util.ehVazio(data.getAndroidCustomPath())) 
			r = r+"Preencha o diretório dos arquivos Android\n";
		if (Util.ehVazio(data.getBasePath())) 
			r = r+"Preencha o diretório dos arquivos base\n";
		if (Util.ehVazio(data.getImagensSubDir())) 
			r = r+"Preencha o subdiretório das imagens\n";
		if (Util.ehVazio(data.getDadosSubDir())) 
			r = r+"Preencha o subdiretório dos dados\n";
		if (Util.ehVazio(data.getMarketingSubDir())) 
			r = r+"Preencha o subdiretório do marketing\n";
		if (Util.ehVazio(data.getProjNumbers())) 
			r = r+"Preencha pelo menos um número de projeto para montar\n";
		if (!data.isAndroid() && !data.isIOS()) {
		    r = r+"Preencha pelo menos uma das plataforma alvo: android e/ou ios\n";
		}
		return r;
	}

}
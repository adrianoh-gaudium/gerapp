package br.com.gaudium.builder.taximachine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Interpreter_Dados_d extends Interpreter {
	
	public Interpreter_Dados_d(HashMap<String, String> map, String miscDados, DataObj data) {
		super(map, data);
		this.miscDados = miscDados;
	}
	
	public boolean go(String codigoProj) {
		File arqDados = new File(miscDados+Util.FILE_SEPARATOR+"dados.d");
		if (!arqDados.exists()) {
			callback("[ERRO] Projeto "+codigoProj+": não foi encontrado arquivo 'dados.d' na pasta "+miscDados);
			return false;  //ir para o próximo código de projeto.		    	
		}
		
		List<String> linhasArqDados;
		try {
			linhasArqDados = FileUtils.readLines(arqDados);
		} catch (IOException e1) {
			callback("[ERRO] Projeto "+codigoProj+": erro de leitura do 'dados.d' na pasta "+miscDados);
			return false;  //ir para o próximo código de projeto.		    	
		}
		
		for (String linha0 : linhasArqDados) {
			if (!this.interpretarItem(linha0)) {
				callback("[ERRO] Projeto "+codigoProj+", interpretação do arquivo dados.d, linha: " + linha0);
				return false;  //sair do loop, deu erro ao interpretar a linha de dados.d.  Este projeto não será processado.		    	
			}
		}

		// Aqui "hashVars" estará preenchido e será complementado pelo método abaixo.	
		this.ajustar();
		
		return true;
		
	}
	
}
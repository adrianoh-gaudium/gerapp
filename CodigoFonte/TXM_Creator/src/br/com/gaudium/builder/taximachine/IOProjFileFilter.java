package br.com.gaudium.builder.taximachine;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

public class IOProjFileFilter implements IOFileFilter {
	private final static String FILE_SEPARATOR = System.getProperty("file.separator");
	private String codProj;
	private int countSlashesDirName;

	public IOProjFileFilter(File projsFile) {
		countSlashesDirName = Util.contar(projsFile.getAbsolutePath(), FILE_SEPARATOR);
	}
	
	@Override
	public boolean accept(File file) {
		int countSlashesFileName = Util.contar(file.getAbsolutePath(), FILE_SEPARATOR);
		String compareStr;
		if ("076".equals(codProj)) {  //o código de projeto 076 teve alguma exceção quando foi criado.
			compareStr = "Proj"+codProj+"-";
		} else {
			compareStr = "Proj"+codProj+"-TXM";			
		}
		return file.isDirectory() && file.getName().contains(compareStr) && 
				countSlashesFileName==countSlashesDirName+1; //recuperar apenas os diretórios filhos do que foi indicado em "Projs Andamento".
	}

	@Override
	public boolean accept(File arg0, String arg1) {
		return false;
	}

	public void setCodProj(String codigoProj) {
		codProj = codigoProj;
	}

}

package br.com.gaudium.builder.taximachine;

import java.io.File;

public class DataObj {
	private File sourcePath;
	private File customPath;
//	private File keystorePath;
	private File destinationPath;
	private File jsonTxmPath;
	private File jsonDrmPath;
	private String versionCodePass, versionNamePass;
	private String versionCodeTaxista, versionNameTaxista;
	private String releaseNotesPass, releaseNotesTaxista;
	
	private boolean exportPass, exportTaxista, uploadPass, uploadTaxista, 
	                montando = false;  //indica se a montagem dos APKs está em andamento.
	
	private File appTaxistaPath, 
	             appPassageiroPath;
	
	private String  projNumbers;
	private File    basePath, dadosAppsPath, projsAndamentoPath, androidCustomPath, 
	                iosCustomPath, scriptsPath;
	private String  imagensSubDir, marketingSubDir, dadosSubDir, contaFastLane;
	private String  secretPath;
	
	private boolean marketing, packge, meta, android, ios, imagens, confAPI;
	private String  urlConfAPI, servidorConfAPI;
	
	private String servidorCliente, apiCliente, nomeCliente, senhaCliente, emailLoginCliente;
	
	private String urlPinsMapa, pinTaxistaLivre, pinTaxistaOcupado, pinTaxistaConfirmado,
	               pinMototaxistaLivre, pinMototaxistaOcupado, pinMototaxistaConfirmado,
	               pinMotoristaLivre, pinMotoristaOcupado, pinMotoristaConfirmado,
	               pinPassageiro, pinLocalizacao;
	
	private File logFilename;
	
	public String getUrlPinsMapa() {
		return urlPinsMapa;
	}
	public void setUrlPinsMapa(String urlPinsMapa) {
		this.urlPinsMapa = urlPinsMapa;
	}
	public String getPinTaxistaLivre() {
		return pinTaxistaLivre;
	}
	public void setPinTaxistaLivre(String pinTaxistaLivre) {
		this.pinTaxistaLivre = pinTaxistaLivre;
	}
	public String getPinTaxistaOcupado() {
		return pinTaxistaOcupado;
	}
	public void setPinTaxistaOcupado(String pinTaxistaOcupado) {
		this.pinTaxistaOcupado = pinTaxistaOcupado;
	}
	public String getPinTaxistaConfirmado() {
		return pinTaxistaConfirmado;
	}
	public void setPinTaxistaConfirmado(String pinYaxistaConfirmado) {
		this.pinTaxistaConfirmado = pinYaxistaConfirmado;
	}
	public String getPinMototaxistaLivre() {
		return pinMototaxistaLivre;
	}
	public void setPinMototaxistaLivre(String pinMototaxistaLivre) {
		this.pinMototaxistaLivre = pinMototaxistaLivre;
	}
	public String getPinMototaxistaOcupado() {
		return pinMototaxistaOcupado;
	}
	public void setPinMototaxistaOcupado(String pinMototaxistaOcupado) {
		this.pinMototaxistaOcupado = pinMototaxistaOcupado;
	}
	public String getPinMototaxistaConfirmado() {
		return pinMototaxistaConfirmado;
	}
	public void setPinMototaxistaConfirmado(String pinMototaxistaConfirmado) {
		this.pinMototaxistaConfirmado = pinMototaxistaConfirmado;
	}
	public String getPinMotoristaLivre() {
		return pinMotoristaLivre;
	}
	public void setPinMotoristaLivre(String pinMotoristaLivre) {
		this.pinMotoristaLivre = pinMotoristaLivre;
	}
	public String getPinMotoristaOcupado() {
		return pinMotoristaOcupado;
	}
	public void setPinMotoristaOcupado(String pinMotoristaOcupado) {
		this.pinMotoristaOcupado = pinMotoristaOcupado;
	}
	public String getPinMotoristaConfirmado() {
		return pinMotoristaConfirmado;
	}
	public void setPinMotoristaConfirmado(String pinMotoristaConfirmado) {
		this.pinMotoristaConfirmado = pinMotoristaConfirmado;
	}
	public String getPinPassageiro() {
		return pinPassageiro;
	}
	public void setPinPassageiro(String pinPassageiro) {
		this.pinPassageiro = pinPassageiro;
	}
	public String getPinLocalizacao() {
		return pinLocalizacao;
	}
	public void setPinLocalizacao(String pinLocalizacao) {
		this.pinLocalizacao = pinLocalizacao;
	}
	public String getServidorCliente() {
		return servidorCliente;
	}
	public void setServidorCliente(String servidorCliente) {
		this.servidorCliente = servidorCliente;
	}
	public String getApiCliente() {
		return apiCliente;
	}
	public void setApiCliente(String apiCliente) {
		this.apiCliente = apiCliente;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getSenhaCliente() {
		return senhaCliente;
	}
	public void setSenhaCliente(String senhaCliente) {
		this.senhaCliente = senhaCliente;
	}
	public String getEmailLoginCliente() {
		return emailLoginCliente;
	}
	public void setEmailLoginCliente(String emailLoginCliente) {
		this.emailLoginCliente = emailLoginCliente;
	}
	public String getContaFastLane() {
		return contaFastLane;
	}
	public void setContaFastLane(String contaFastLane) {
		this.contaFastLane = contaFastLane;
	}
	public File getScriptsPath() {
		return scriptsPath;
	}
	public void setScriptsPath(File scriptsPath) {
		this.scriptsPath = scriptsPath;
	}
	public File getBasePath() {
		return basePath;
	}
	public void setBasePath(File basePath) {
		this.basePath = basePath;
	}
//	public boolean isCerts() {
//		return certs;
//	}
//	public void setCerts(boolean certs) {
//		this.certs = certs;
//	}
	public String getImagensSubDir() {
		return imagensSubDir;
	}
	public void setImagensSubDir(String imagensSubDir) {
		this.imagensSubDir = imagensSubDir;
	}
	public String getMarketingSubDir() {
		return marketingSubDir;
	}
	public void setMarketingSubDir(String marketingSubDir) {
		this.marketingSubDir = marketingSubDir;
	}
	public String getDadosSubDir() {
		return dadosSubDir;
	}
	public void setDadosSubDir(String dadosSubDir) {
		this.dadosSubDir = dadosSubDir;
	}
	public String getProjNumbers() {
		return projNumbers;
	}
	public void setProjNumbers(String projNumbers) {
		this.projNumbers = projNumbers;
	}
	public File getDadosAppsPath() {
		return dadosAppsPath;
	}
	public void setDadosAppsPath(File dadosAppsPath) {
		this.dadosAppsPath = dadosAppsPath;
	}
	public File getProjsAndamentoPath() {
		return projsAndamentoPath;
	}
	public void setProjsAndamentoPath(File projsAndamentoPath) {
		this.projsAndamentoPath = projsAndamentoPath;
	}
	public File getAndroidCustomPath() {
		return androidCustomPath;
	}
	public void setAndroidCustomPath(File androidCustomPath) {
		this.androidCustomPath = androidCustomPath;
	}
	public File getIOSCustomPath() {
		return iosCustomPath;
	}
	public void setIOSCustomPath(File iosCustomPath) {
		this.iosCustomPath = iosCustomPath;
	}
	public boolean isMarketing() {
		return marketing;
	}
	public void setMarketing(boolean marketing) {
		this.marketing = marketing;
	}
	public boolean isPackge() {
		return packge;
	}
	public void setPackge(boolean packge) {
		this.packge = packge;
	}
	public boolean isMeta() {
		return meta;
	}
	public void setMeta(boolean meta) {
		this.meta = meta;
	}
//	public boolean isPassageiro() {
//		return passageiro;
//	}
//	public void setPassageiro(boolean passageiro) {
//		this.passageiro = passageiro;
//	}
//	public boolean isTaxista() {
//		return taxista;
//	}
//	public void setTaxista(boolean taxista) {
//		this.taxista = taxista;
//	}
	public boolean isImagens() {
		return true;
	}
	public void setImagens(boolean imagens) {
		return;
	}
//	public boolean isReload_meta() {
//		return reload_meta;
//	}
//	public void setReload_meta(boolean reload_meta) {
//		this.reload_meta = reload_meta;
//	}
	public String getVersionCodePass() {
		return versionCodePass;
	}
	public void setVersionCodePass(String versionCodePass) {
		this.versionCodePass = versionCodePass;
	}
	public String getVersionNamePass() {
		return versionNamePass;
	}
	public void setVersionNamePass(String versionNamePass) {
		this.versionNamePass = versionNamePass;
	}
	public String getVersionCodeTaxista() {
		return versionCodeTaxista;
	}
	public void setVersionCodeTaxista(String versionCodeTaxista) {
		this.versionCodeTaxista = versionCodeTaxista;
	}
	public String getVersionNameTaxista() {
		return versionNameTaxista;
	}
	public void setVersionNameTaxista(String versionNameTaxista) {
		this.versionNameTaxista = versionNameTaxista;
	}
	public File getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(File sourcePath) {
		this.sourcePath = sourcePath;
	}
	public File getCustomPath() {
		return customPath;
	}
	public void setCustomPath(File customTaxiPath) {
		this.customPath = customTaxiPath;
	}
//	public File getConfigPath() {
//		return keystorePath;
//	}
//	public void setConfigPath(File configPath) {
//		this.keystorePath = configPath;
//	}
	public File getDestinationPath() {
		return destinationPath;
	}
	public void setDestinationPath(File destinationPath) {
		this.destinationPath = destinationPath;
	}
	public File getAppTaxistaPath() {
		return appTaxistaPath;
	}
	public void setAppTaxistaPath(File appTaxistaPath) {
		this.appTaxistaPath = appTaxistaPath;
	}
	public File getAppPassageiroPath() {
		return appPassageiroPath;
	}
	public void setAppPassageiroPath(File appPassageiroPath) {
		this.appPassageiroPath = appPassageiroPath;
	}

	public boolean isMontando() {
		return montando;
	}
	public void setMontando(boolean montando) {
		this.montando = montando;
	}
	public boolean isExportPass() {
		return exportPass;
	}
	public void setExportPass(boolean exportPass) {
		this.exportPass = exportPass;
	}
	public boolean isUploadPass() {
		return uploadPass;
	}
	public void setUploadPass(boolean uploadPass) {
		this.uploadPass = uploadPass;
	}
	public boolean isExportTaxista() {
		return exportTaxista;
	}
	public void setExportTaxista(boolean exportTaxista) {
		this.exportTaxista = exportTaxista;
	}
	public boolean isUploadTaxista() {
		return uploadTaxista;
	}
	public void setUploadTaxista(boolean uploadTaxista) {
		this.uploadTaxista = uploadTaxista;
	}
	public File getJsonDrmPath() {
		return jsonDrmPath;
	}
	public void setJsonDrmPath(File jsonDrmPath) {
		this.jsonDrmPath = jsonDrmPath;
	}
	public File getJsonTxmPath() {
		return jsonTxmPath;
	}
	public void setJsonTxmPath(File jsonTxmPath) {
		this.jsonTxmPath = jsonTxmPath;
	}
	public String getReleaseNotesTaxista() {
		return releaseNotesTaxista;
	}
	public void setReleaseNotesTaxista(String releaseNotesTaxista) {
		this.releaseNotesTaxista = releaseNotesTaxista;
	}
	public String getReleaseNotesPass() {
		return releaseNotesPass;
	}
	public void setReleaseNotesPass(String releaseNotesPass) {
		this.releaseNotesPass = releaseNotesPass;
	}
	public File getLogFilename() {
		return logFilename;
	}
	public void setLogFilename(File logFilename) {
		this.logFilename = logFilename;
	}
	public boolean isIOS() {
		return ios;
	}
	public void setIOS(boolean IOS) {
		this.ios = IOS;
	}
	public boolean isAndroid() {
		return android;
	}
	public void setAndroid(boolean android) {
		this.android = android;
	}
	public String getSecretPath() {
		return secretPath;
	}
	public void setSecretPath(String secretPath) {
		this.secretPath = secretPath;
	}
	public boolean isConfAPI() {
		return confAPI;
	}
	public void setConfAPI(boolean api) {
		this.confAPI = api;
	}
	public String getUrlConfAPI() {
		return urlConfAPI;
	}
	public void setUrlConfAPI(String urlConfAPI) {
		this.urlConfAPI = urlConfAPI;
	}
	public String getServidorConfAPI() {
		return servidorConfAPI;
	}
	public void setServidorConfAPI(String servidorConfAPI) {
		this.servidorConfAPI = servidorConfAPI;
	}

}

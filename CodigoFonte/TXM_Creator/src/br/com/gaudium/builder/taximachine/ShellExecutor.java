package br.com.gaudium.builder.taximachine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.Arrays;
import java.util.Map;

/*
 * Wrapper around java.lang.Runtime
 */
public class ShellExecutor {

	private static java.lang.Runtime rt = java.lang.Runtime.getRuntime();
	private static ShellExecutor ce = null;

	private ShellExecutor() {}

	public static Runtime getRuntime() {
		return rt;
	}

	public static ShellExecutor getCommandExecutor() {
		if (ce==null) {
			ce = new ShellExecutor();

		}
		return ce;
	}
	
	public Process exec(String command) throws IOException {
        return exec(command, null);
    }

    public Process exec(String command, String[] envp) throws IOException {
		Process proc = rt.exec(command, envp, null);
		return consumeProcess(proc);
	}
    
	public Process execScript(File execDir, String[] command) throws IOException {
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.redirectInput(Redirect.INHERIT);
		builder.redirectOutput(Redirect.INHERIT);
		builder.redirectError(Redirect.INHERIT);
		
		if (execDir!=null) {
			builder.directory(execDir);
		}

		Map h = builder.environment();
		String newPath = (String)h.get("PATH"); 
		newPath = newPath + ":/opt/local/bin:/usr/local/bin";
		h.put("PATH", newPath);
		h.put("FASTLANE_HIDE_CHANGELOG", "true");
		h.put("FASTLANE_SKIP_UPDATE_CHECK", "true");
		
		h.put("LC_ALL", "en_US.UTF-8");
		h.put("LANG", "en_US.UTF-8");
		
		
		Process proc = builder.start();
		return consumeProcess(proc);
	}

    private Process consumeProcess(Process proc) throws IOException {
		try {
			proc.waitFor();
//			// any error message?
//			StreamGobbler errorGobbler = new 
//					StreamGobbler(proc.getErrorStream(), "ERROR");            
//
//			// any output?
//			StreamGobbler outputGobbler = new 
//					StreamGobbler(proc.getInputStream(), "OUTPUT");
//
//			// kick them off
//			errorGobbler.start();
//			outputGobbler.start();

		} catch (InterruptedException e) {
			throw new IOException(e);
		}
		return proc;
    }

}

//class StreamGobbler extends Thread {
//
//	InputStream is;
//	String type;
//
//	StreamGobbler(InputStream is, String type) {
//		this.is = is;
//		this.type = type;
//	}
//
//	public void run() {
//		try {
//			InputStreamReader isr = new InputStreamReader(is);
//			BufferedReader br = new BufferedReader(isr);
//			String line=null;
//			while ( (line = br.readLine()) != null) {
//				System.out.println(type + "> " + line);
//			}
//		} catch (IOException ioe) {
//			ioe.printStackTrace();  
//		}
//	}
//}

package com.linkui.toolbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FileReader {
	
	String filePath = new String();
	MainWindow mainWindow;
	
	public FileReader(String _filePath, MainWindow _mainWindow){
		this.filePath=_filePath;
		this.mainWindow=_mainWindow;
	}
	
	public void readContents() throws IOException {
		InputStream is = this.getClass().getResourceAsStream(filePath);
		InputStreamReader isr = new InputStreamReader (is,"UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String line = new String("");
		
		while ((line = br.readLine()) != null){
			mainWindow.showToJTA(line);
		}
		br.close();
	}

}

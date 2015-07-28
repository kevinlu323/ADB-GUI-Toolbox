package com.linkui.toolbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class GetCommandList {
    ArrayList<String> lineArray = new ArrayList<>();
    GetCommandList(String _fileName) {
        InputStream is = this.getClass().getResourceAsStream(_fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                lineArray.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getCommandArray(){
        return lineArray;
    }

    public static void main(String[] args){
        GetCommandList gcl = new GetCommandList("File.txt");
        ArrayList<String> testList = gcl.getCommandArray();
        Iterator<String> it = testList.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
}
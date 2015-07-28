package com.linkui.toolbox;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

class CommandRunner extends Thread{
    ArrayList<String> command = new ArrayList<>();
    MainWindow mainWindow;
    boolean flag = true;
    Runtime run = Runtime.getRuntime();
    Process pro;

    CommandRunner(ArrayList<String> _command, MainWindow _mainWindow){
        this.command.addAll(_command);
        this.mainWindow=_mainWindow;
    }

    public void run(){
        InputStream is;
        String readOUT;
        String commandToRun;
        Iterator<String> it=command.iterator();
        while(flag&&it.hasNext()){
            try{
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            commandToRun=it.next();
            try{
                //System.out.println();
                //System.out.println("Now running: "+commandToRun);
                mainWindow.showToJTA("======Now running: "+commandToRun+"======\n");
                pro=run.exec(commandToRun);
                is = pro.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while(flag&&(readOUT = br.readLine())!=null){
                    mainWindow.showToJTA(readOUT);
                    //System.out.println(readOUT);
                }
                br.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void shutdown(){
        flag = false;
        pro.destroy();
    }
}

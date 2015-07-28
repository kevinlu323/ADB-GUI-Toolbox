package com.linkui.toolbox;

/*
 * ADB GUI Toolbox project
 * Version: v1.0
 * Author: Linkui Lu
 * Last time update: 04/28/2015
 * Beta v2.0 version, on 05/01/2015
 * Beta v3.0 version on 06/01/2015
 * v1.0 (on Experimental branch) 07/05/2015
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class MainWindow {
	
	JFrame frame = new JFrame("ADB GUI Toolbox");
	JLabel label = new JLabel("ADB GUI Toolbox v1.0, Developped by: Linkui");
	JLabel commandLabel = new JLabel("Choose Command: ");
	JButton startBtn = new JButton("Start");
	JButton stopBtn = new JButton("Stop");
	JButton clearBtn = new JButton ("Clear Results");
	JPanel buttonPanel = new JPanel();
	JPanel buttomPanel = new JPanel();
	JTabbedPane jtp = new JTabbedPane();
	JTextArea jta = new JTextArea(30,60);
	JScrollPane jsp = new JScrollPane(jta);
	JScrollBar jsb = jsp.getVerticalScrollBar();
	JComboBox<String> jcbAdb = new JComboBox<String>();
	JComboBox<String> jcbFastboot = new JComboBox<String>();
	JComboBox<String> jcbOthers = new JComboBox<String>();
	Font windowFont = new Font("Consolas",2,16);
	
	String outputFilter = new String("leak");
	boolean needFilter = false;
	
	ArrayList<String> commandList= new ArrayList<>();
    CommandRunner cr;
	
	public MainWindow(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		jta.setLineWrap(true);
		jta.setFont(windowFont);
		clearBtn.setFont(windowFont);
		startBtn.setFont(windowFont);
		stopBtn.setFont(windowFont);
		label.setFont(windowFont);
		commandLabel.setFont(windowFont);
		jtp.setFont(windowFont);
		
		//Use BoxLayout to warp the buttons.
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
		buttonPanel.add(clearBtn);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(commandLabel);
		buttonPanel.add(jtp);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(startBtn);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(stopBtn);
		
		buttomPanel.setLayout(new BoxLayout(buttomPanel,BoxLayout.Y_AXIS));
		buttomPanel.add(Box.createVerticalStrut(20));
		buttomPanel.add(buttonPanel);
		buttomPanel.add(Box.createVerticalStrut(20));
		
		jcbAdb.addItem("Please Select:");
		jcbAdb.addItem("Show Connected Devices");
		jcbAdb.addItem("Reboot to Bootloader");
		jcbAdb.addItem("Get Root Access");
		jcbAdb.addItem("Remount System Partition");
		jcbAdb.addItem("Take a Screen Shot");
		jcbAdb.addItem("Record a Demo Video");
		jcbAdb.addItem("show logcat");
		jcbAdb.addItem("find leak");
		jcbAdb.addItem("Get Build Fingerprint");
		jcbAdb.addItem("Check Build Cloud Server on Device");
		jcbAdb.addItem("Pull Aplog");
		jcbAdb.addItem("Pull Device Full Log");
		jcbAdb.addItem("ByPass Hotspot provision check");
		jcbAdb.addItem("Edit Emergency Number");
		
		jcbFastboot.addItem("Please Select:");
		jcbFastboot.addItem("Show Connected fastboot devices");
		jcbFastboot.addItem("Reboot from Bootloader");
		jcbFastboot.addItem("Erase Userdata & Cache");
		jcbFastboot.addItem("Erase FRP partition (for Kill-Switch feature)");
		
		jcbOthers.addItem("Please Select:");
		jcbOthers.addItem("Show other useful commands");
		
		jtp.add("adb", jcbAdb);
		jtp.add("fastboot",jcbFastboot);
		jtp.add("Others",jcbOthers);
		
		// add button & TextArea on the layout
		frame.getContentPane().add(label,BorderLayout.NORTH);
		frame.getContentPane().add(buttomPanel,BorderLayout.SOUTH);
		frame.getContentPane().add(jsp,BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
		
		startBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae){
                cr = new CommandRunner(commandList,MainWindow.this);
                cr.start();
        	}
        });
		
		stopBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae){
        		if(cr!=null&&cr.isAlive()){
        			cr.shutdown();
        			jta.append("======Stopping current command.======\n");
        		}
        		else{
        			JOptionPane.showMessageDialog(null, "There is no command running, or running command is stopped!");
        		}
        	}
        });
		
		clearBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae){
        		jta.setText(null);
        	}
        });
		
		jcbAdb.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        switch((String)jcbAdb.getSelectedItem()){
		        case "Show Connected Devices":
		        	commandList.clear();
		        	commandList.add("adb devices");
		        	needFilter = false;
		        	break;
		        case "Reboot to Bootloader":
		        	commandList.clear();
		        	commandList.add("adb reboot-bootloader");
		        	needFilter = false;
		        	break;
		        case "show logcat":
		        	commandList.clear();
		        	commandList.add("adb logcat");
		        	needFilter = false;
		        	break;
		        case "find leak":
		        	commandList.clear();
		        	commandList.add("adb logcat");
		        	outputFilter = "leak";
		        	needFilter = true;
		        	break;
		        case "Take a Screen Shot":
		        	commandList.clear();
		        	commandList.add("adb shell screencap -p /sdcard/testpic.png");
		        	commandList.add("cmd /c adb pull /sdcard/testpic.png " +
		        			"%USERPROFILE%\\Desktop\\testpic.png");
		        	needFilter = false;
		        	break;
		        case "Record a Demo Video":
		        	commandList.clear();
		        	commandList.add("adb shell screenrecord /sdcard/demo.mp4");
		        	needFilter = false;
		        	break;
		        case "Get Build Fingerprint":
		        	commandList.clear();
		        	commandList.add("adb shell getprop");
		        	outputFilter = "finger";
		        	needFilter = true;
		        	break;
		        case "Check Build Cloud Server on Device":
		        	commandList.clear();
		        	commandList=new GetCommandList("/ref/Cloud_Server").getCommandArray();;
		        	needFilter = false;
		        	break;
		        case "Get Root Access":
		        	commandList.clear();
		        	commandList.add("adb root");
		        	needFilter = false;
		        	break;
		        case "Remount System Partition":
		        	commandList.clear();
		        	commandList.add("adb remount");
		        	needFilter = false;
		        	break;
		        case "Pull Aplog":
		        	commandList.clear();
		        	//commandList.add("CMD /c start Pull_aplog.bat");
		        	commandList=new GetCommandList("/ref/Pull_Aplog").getCommandArray();
		        	needFilter = false;
		        	break;
		        case "Pull Device Full Log":
		        	commandList.clear();
		        	commandList.add("CMD /c start "+this.getClass().getResource("/ref/pull_full_logs.bat"));
		        	needFilter = false;
		        	break;
		        case "ByPass Hotspot provision check":
		        	commandList.clear();
		        	commandList=new GetCommandList("/ref/By_Pass").getCommandArray();
		        	needFilter = false;
		        	break;
		        case "Edit Emergency Number":
		        	String numberToUse = JOptionPane.showInputDialog("Please input a person number for \"112\"");
		        	commandList.clear();
		        	commandList=new GetCommandList("/ref/EMG_NUM").getCommandArray();
		        	String tmp = commandList.get(1);
		        	commandList.set(1, tmp+numberToUse);
		        	needFilter = false;
		        	break;
		        }
		    }
		});
		
		jcbFastboot.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        switch((String)jcbFastboot.getSelectedItem()){
		        case "Show Connected fastboot devices":
		        	commandList.clear();
		        	commandList.add("fastboot devices");
		        	needFilter = false;
		        	break;
		        case "Reboot from Bootloader":
		        	commandList.clear();
		        	commandList.add("fastboot reboot");
		        	needFilter = false;
		        	break;
		        case "Erase Userdata & Cache":
		        	commandList.clear();
		        	commandList.add("fastboot -w");
		        	needFilter = false;
		        	break;
		        case "Erase FRP partition (for Kill-Switch feature)":
		        	commandList.clear();
		        	commandList.add("fastboot erase frp");
		        	needFilter = false;
		        	break;
		        }
		    }
		});
		
		jcbOthers.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        switch((String)jcbOthers.getSelectedItem()){
		        case "Show other useful commands":
		        	FileReader tr = new FileReader("/ref/Reference",MainWindow.this);
		        	try {
						tr.readContents();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						jta.append("Something is wrong while reading files");
					}
		        	break;
		        }
		    }
		});	
	}	
	
	public void showToJTA(String content){
        if(needFilter){
        	if(content.contains(outputFilter)){
				jta.append(content+"\n");
		        jsb.setValue(jsb.getMaximum());
        	}
        }
        else{
        	jta.append(content+"\n");
	        jsb.setValue(jsb.getMaximum());
        }
    }
	
	public static void main(String[] args){
		new MainWindow();
	}
}

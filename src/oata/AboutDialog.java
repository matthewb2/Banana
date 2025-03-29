package oata;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AboutDialog  extends JFrame{
 
     
    JTabbedPane tab = new JTabbedPane();  //JTabbedPane생성
   
    JPanel p1 = new JPanel(); //JPanel 생성
    
    String version = "바나나에디터 v0.3.3 엠케이솔루션 제공";
 //Read file content into string with - Files.readAllBytes(Path path)
    
    private static String readAllBytesJava7(String filePath) 
    {
        String content = "";
 
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
 
        return content;
    }
    
         
    public AboutDialog() {  //생성자
       
        super("정보"); //프레임 타이틀 제목 지정
       

      	
               
    }//생성자 끝

    public void showDialog(){
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
     	
       JButton btn1 = new JButton("확인");
       
       //
               
       ImageIcon icon_about = new ImageIcon(getClass().getResource("res/banana-logo.png"));
       JLabel label1 = new JLabel(icon_about);
       
       JPanel jp1 = new JPanel();
       jp1.setLayout(new BoxLayout(jp1, BoxLayout.X_AXIS));
       
       JPanel lb = new JPanel();
       lb.setLayout(new BoxLayout(lb, BoxLayout.Y_AXIS));
       
       
       
       btn1.setSize(new Dimension(100, 150));
       
       btn1.addActionListener(new ActionListener()
       {
           public void actionPerformed(ActionEvent e)
           {
             // 
             dispose();
           }
         });
       
       JLabel versionlabel = new JLabel();
           
       versionlabel.setText(version);
               
       lb.add(label1);
       lb.add(versionlabel);
       
       lb.setAlignmentY(Component.TOP_ALIGNMENT);
       btn1.setAlignmentY(Component.TOP_ALIGNMENT);
       
       jp1.add(lb);
       jp1.add(btn1);
       
       
       
       tab.add("소개", jp1); //
       //
       
       String filePath = "\\history.txt";
       String internalPath = new File("").getAbsolutePath();
       System.out.println(internalPath+filePath);
       
       String str = readAllBytesJava7(internalPath+filePath);
       
       JTextArea edit = new JTextArea();
       JScrollPane ver = new JScrollPane(edit);
       edit.setEditable(false);
       edit.setText(str);
       
       tab.add("버전정보", ver);
      
       add(tab);
    
       //frame.pack();
       setSize(410, 400);
       setResizable(false);
       
       setLocationRelativeTo(this);
       setVisible(true);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}

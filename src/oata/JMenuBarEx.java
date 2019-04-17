package oata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import javax.swing.JScrollPane;

import javax.swing.JTextPane;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import oata.KeywordStyledDocument;

public class JMenuBarEx extends JFrame  implements ActionListener, 
MouseListener, MouseMotionListener, MouseWheelListener,
KeyListener, ListSelectionListener  {
	  private static final long serialVersionUID = 1L;

public JMenuBarEx() {
	 
	   setTitle("Default Menu Application");
	   
		JFrame frame;  
	    JMenuItem   new_blank, open, exit, close, about, random, deletefile, resize, save, saveas;
	    JMenuItem   cut_text, paste_text, fullscreen, delete, previous, next;
	    JFileChooser fc;
	    JLabel jl;
	    JLabel fjl;
	    
	    StyleContext styleContext = new StyleContext();
	    Style defaultStyle = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
	    Style cwStyle = styleContext.addStyle("ConstantWidth", null);
	    StyleConstants.setForeground(cwStyle, Color.BLUE);
	    StyleConstants.setBold(cwStyle, true);
	    
	    final JTextPane pane = new JTextPane();
        pane.setFont(new Font("Courier New", Font.PLAIN, 12));

	         
       //final JTextPane  editor__ = new JTextPane();
       JScrollPane editorScrollPane = new JScrollPane(pane);

	   pane.setDocument(new KeywordStyledDocument(defaultStyle, cwStyle));

	   add(editorScrollPane, BorderLayout.CENTER);	
	      
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       // size of frame
       setSize(850,600);
        
       setVisible(true);
       
       
       //menus
       
       JMenuBar menuBar = new JMenuBar();
       
       JMenu menu1    = new JMenu("File");
       menu1.setMnemonic('F');
       
       ImageIcon icon_new = new ImageIcon(getClass().getResource("res/new_blank.png"));
       new_blank     = new JMenuItem("New", icon_new);
       new_blank.setMnemonic('N');

       menu1.add(new_blank);

       
       ImageIcon icon_open = new ImageIcon(getClass().getResource("res/folder.png"));
       open     = new JMenuItem("Open", icon_open);

       menu1.add(open);
     
       ImageIcon icon_close = new ImageIcon(getClass().getResource("res/close.png"));
       close     = new JMenuItem("Close", icon_close);
       //close     = new JMenuItem("Close");
       close.setMnemonic('C');
       close.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ev) {
                   System.exit(0);
           }
       });
 	      
 	      
       menu1.add(close);
       
      

       menuBar.add(menu1);
             
    
       
       JMenu menu2    = new JMenu("Edit");
                
           
       menu2.setMnemonic('E');
       
       cut_text     = new JMenuItem("Cut");
       cut_text.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ev) {
                   pane.cut();
           }
       });
 	      
       menu2.add(cut_text);
       
       paste_text     = new JMenuItem("Paste");
       paste_text.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ev) {
        	   		pane.paste();
           }
       });
 	      

       menu2.add(paste_text);
       menuBar.add(menu2);
       
      
       JMenu menu3    = new JMenu("View");
       menu3.setMnemonic('V');
       
       menuBar.add(menu3);
       fullscreen     = new JMenuItem("Full Screen");
       menu3.add(fullscreen);
       JMenu menu4    = new JMenu("Help");
       menu4.setMnemonic('H');
       about     = new JMenuItem("About");
       about.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ev) {
              //     editor__.paste();
        	   JOptionPane.showMessageDialog(null, null);
           }
       });
 	      

       about.setMnemonic('A');
       menu4.add(about);
       
       menuBar.add(menu4);
       
       setJMenuBar(menuBar);	  
       
      
		
    }

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	};

	public static void main(String[] args)  throws BadLocationException {
			   
        JMenuBarEx ex = new JMenuBarEx();
        ex.setVisible(true);
  
	}
	
}

	


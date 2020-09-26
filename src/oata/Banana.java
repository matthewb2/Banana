package oata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.undo.UndoManager;

public class Banana extends JFrame  implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, ListSelectionListener  {
	  
	  private static final long serialVersionUID = 1L;
	  //
	  final JPopupMenu popup = new JPopupMenu();
	  private static final int MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
	  //
	  private UndoManager undoManager = new UndoManager();
	  final JTextPane pane;
	  protected FindDialog m_findDialog;
	  protected ReplaceDialog m_replaceDialog;
	  
	  public JTextPane getTextPane() { return pane; }
	
	  //Read file content into string with - Files.readAllBytes(Path path)
	  private static String readAllBytesJava7(String filePath) 
	  {
	        String content = "";
	        try
	        {
	        	//read file as the UTF-8 type!!
	            content = new String ( Files.readAllBytes( Paths.get(filePath) ),"UTF-8" );
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	        return content;
	  }
	  
	  

public Banana() {
	    //
	    setTitle("바나나 텍스트 에디터");
	    //
		JFrame frame;  
	    JMenuItem   new_blank, open, exit, close, about, random, deletefile, resize, save, saveas;
	    JMenuItem   cut_text, paste_text, find_text, replace_text, fullscreen, delete, previous, next;
	    JFileChooser fc;
	    JLabel jl;
	    JLabel fjl;
	    
	    StyleContext styleContext = new StyleContext();
	    Style defaultStyle = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
	    Style cwStyle = styleContext.addStyle("ConstantWidth", null);
	    StyleConstants.setForeground(cwStyle, Color.BLUE);
	    StyleConstants.setBold(cwStyle, false);
	    
	    pane = new JTextPane();
        pane.setFont(new Font("돋움", Font.PLAIN, 14));

	         
       //final JTextPane  editor__ = new JTextPane();
       JScrollPane editorScrollPane = new JScrollPane(pane);

	   pane.setDocument(new JavaTokenMarker(defaultStyle));
	   
	   
       // New project menu item
       JMenuItem menuItem = new JMenuItem("복사(C)",
               new ImageIcon("images/newproject.png"));
       menuItem.setMnemonic(KeyEvent.VK_C);
       
       menuItem.addActionListener(new ActionListener() {

           public void actionPerformed(ActionEvent e) {
               //JOptionPane.showMessageDialog(frame, "New Project clicked!");
           }
       });
       popup.add(menuItem);
       
	   
	   pane.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				//showPopup(e);
				
				if(e.getButton() == java.awt.event.MouseEvent.BUTTON3){
					showPopup(e);
					//System.out.println("eee");
					
				}
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
			private void showPopup(MouseEvent e) {
		          
		              popup.show(e.getComponent(),
		                      e.getX(), e.getY());
		          
		      }
			  
	   });
	   
	   Document doc = pane.getDocument();
       doc.addUndoableEditListener(new UndoableEditListener() {
           @Override
           public void undoableEditHappened(UndoableEditEvent e) {
        	   if (e.getEdit().getPresentationName() != "스타일 변경"){
               undoManager.addEdit(e.getEdit());
               //System.out.println(e);
               System.out.println(e.getEdit().getPresentationName());
        	   }
           }
       });

       pane.getActionMap().put("Undo", new AbstractAction("Undo") {
           @Override
           public void actionPerformed(ActionEvent evt) {
               
                   if (undoManager.canUndo()) {
                       undoManager.undo();
                   }
                
           }
       });
       pane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, MASK), "Undo");
      
       

       
       //menus
       
       JMenuBar menuBar = new JMenuBar();
       
       JMenu menu1    = new JMenu("파일");
       menu1.setMnemonic('F');
       
       ImageIcon icon_new = new ImageIcon(getClass().getResource("res/new_blank.png"));
       new_blank     = new JMenuItem("새문서", icon_new);
       new_blank.setMnemonic('N');

       menu1.add(new_blank);

       
       ImageIcon icon_open = new ImageIcon(getClass().getResource("res/folder.png"));
       open     = new JMenuItem("열기", icon_open);

       menu1.add(open);
     
       ImageIcon icon_close = new ImageIcon(getClass().getResource("res/close.png"));
       close     = new JMenuItem("닫기", icon_close);
       //close     = new JMenuItem("Close");
       close.setMnemonic('C');
       close.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ev) {
                   System.exit(0);
           }
       });
 	      
 	      
       menu1.add(close);
       
      

       menuBar.add(menu1);
             
    
       
       JMenu menu2    = new JMenu("편집");
                
           
       menu2.setMnemonic('E');
       
       cut_text     = new JMenuItem("잘라내기");
       cut_text.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ev) {
                   pane.cut();
           }
       });
 	      
       menu2.add(cut_text);
       
       paste_text     = new JMenuItem("붙여넣기");
       paste_text.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ev) {
        	   		pane.paste();
           }
       });
       
       
       menu2.add(paste_text);
       //
       find_text     = new JMenuItem("찾기");
       find_text.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ev) {
        	   if (m_findDialog==null)
                   m_findDialog = new FindDialog(Banana.this, 0);
                 else
                   m_findDialog.setSelectedIndex(0);
                 Dimension d1 = m_findDialog.getSize();
                 Dimension d2 = Banana.this.getSize();
                 int x = Math.max((d2.width-d1.width)/2, 0);
                 int y = Math.max((d2.height-d1.height)/2, 0);
                 m_findDialog.setBounds(x + Banana.this.getX(),
                   y + Banana.this.getY(), d1.width, d1.height);
                 m_findDialog.setVisible(true);
           }
       });
 	      

       menu2.add(find_text);
       
       replace_text     = new JMenuItem("바꾸기");
       replace_text.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ev) {
        	   if (m_replaceDialog==null)
                   m_replaceDialog = new ReplaceDialog(Banana.this);
        	       //d3 = new JDialog(d2, "", Dialog.ModalityType.DOCUMENT_MODAL);

                 else{
                   //m_replaceDialog.setSelectedIndex(0);
                 }
                 Dimension d1 = m_replaceDialog.getSize();
                 Dimension d2 = Banana.this.getSize();
                 int x = Math.max((d2.width-d1.width)/2, 0);
                 int y = Math.max((d2.height-d1.height)/2, 0);
                 m_replaceDialog.setBounds(x + Banana.this.getX(),
                   y + Banana.this.getY(), d1.width, d1.height);
                 m_replaceDialog.setVisible(true);
           }
       });
 	      

       menu2.add(replace_text);
       //
       menuBar.add(menu2);
       
      
       JMenu menu3    = new JMenu("보기");
       menu3.setMnemonic('V');
       
       menuBar.add(menu3);
       fullscreen     = new JMenuItem("전체 화면");
       menu3.add(fullscreen);
       JMenu menu4    = new JMenu("도움말");
       menu4.setMnemonic('H');
       about     = new JMenuItem("정보");
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
       
       String filePath = "c://testsource.c";
       
       String str = readAllBytesJava7(filePath);
       //remove first 1 byte trash
       //str = str.substring(1);
       
       try{
   	    pane.getDocument().insertString(pane.getDocument().getLength(), str, null);
       }catch (Exception e){
       }
       
	   add(editorScrollPane, BorderLayout.CENTER);	
	      
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       // size of frame
       setSize(850,600);
        
       setVisible(true);

       //setLocationRelativeTo(this);
       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
       setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
       
       
       
		
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
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    		//UIManager.setLookAndFeel(cfg.getProperty("theme"));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
			   
		Banana ex = new Banana();
        ex.setVisible(true);
  
	}
	
}

	


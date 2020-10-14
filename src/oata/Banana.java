package oata;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;
import javax.swing.undo.UndoManager;

public class Banana extends JFrame  implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, ListSelectionListener  {
	  
	  private static final long serialVersionUID = 1L;
	  //
	  final JPopupMenu popup = new JPopupMenu();
	  private static final int MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
	  //
	  private UndoManager undoManager = new UndoManager();
	  static JEditorPane pane=null;
	  protected FindDialog m_findDialog;
	  protected ReplaceDialog m_replaceDialog;
	  protected LetterDialog m_letterDialog;
	  protected FontChooserDialog m_fontchDialog;
	  
	  protected int m_pos=0;
	  public JScrollPane editorScrollPane;
	  public JPanel noWrapPanel;
	  
	  protected static File filePath=null;
	  protected static String fileName, fileContent=null;
	  
	  public JEditorPane getTextPane() { return pane; }
	  protected JLabel rowtextlabel, columntextlabel, pagetextlabel, typemodelabel;
	  protected JPanel statusp= new JPanel();
	  
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
	  
	  private static String convertFromUtf8ToIso(String s1) {
		    if(s1 == null) {
		        return null;
		    }
		    String s = new String(s1.getBytes(StandardCharsets.UTF_8));
		    byte[] b = s.getBytes(StandardCharsets.ISO_8859_1);
		    return new String(b, StandardCharsets.ISO_8859_1);
	  }
	  
	  protected void setAttributeSet(AttributeSet attr) throws BadLocationException {
  	    int xStart = pane.getSelectionStart();
  	    int xFinish = pane.getSelectionEnd();
  	    System.out.println("start: "+xStart+ " end: "+xFinish);
  	    if (!pane.hasFocus()) {
  	    }
  	    StyledDocument doc = (StyledDocument) pane.getDocument();
  	    if (xStart != xFinish) {
  	    
  	      doc.setCharacterAttributes(xStart, xFinish - xStart, attr, false);
  	    }
  	    else {
  	    	//doc.setCharacterAttributes(0, doc.getLength(), attr, true);
  	    	((JTextPane) pane).setParagraphAttributes(attr, true);
  	    	
  	    }
  	    
  	    
	  }
 
	  
	  public void openEx() throws BadLocationException{
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.showOpenDialog(null);
		    //
	    	Document doc = (Document) pane.getDocument();
	    	BufferedReader br = null;
	    	String str_line = "";
	    	String textOfFile = "";
			try {
				br = new BufferedReader(new FileReader(chooser.getSelectedFile()));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				while ((str_line = br.readLine()) != null) {
				    textOfFile = textOfFile + str_line + "\n";
				   }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pane.setText(textOfFile);
			//
	  }
	  
	  public void saveEx() throws BadLocationException, IOException{
	      
		  String content = pane.getText();
		  content = content.replace("\n\r", "\n");
		  
		  File file = new File(fileName);

		  BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		  writer.write(content);
		  writer.close();
		  
	  }
	  
	  public void saveAs() throws BadLocationException{
			final JFileChooser chooser = new JFileChooser();
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			//
			FileNameExtensionFilter filter = new FileNameExtensionFilter("텍스트 파일", "txt");
			chooser.addChoosableFileFilter(filter);
			//
			chooser.setFileFilter(filter);
			chooser.setSelectedFile(new File(chooser.getCurrentDirectory().getAbsolutePath() + "\\*.txt"));
			
			chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener()
			{
			  public void propertyChange(PropertyChangeEvent evt)
			  {
			    //
			    if (chooser.getFileFilter().toString().contains("AcceptAll")){
			    	chooser.setSelectedFile(new File("*.*"));
			    } else chooser.setSelectedFile(new File("*.txt"));
			  }
			});
			

			int returnVal = chooser.showSaveDialog(null);
		    
		    if (returnVal == chooser.APPROVE_OPTION) {
		        File fileToSave = chooser.getSelectedFile();
		        try {
					  String content = pane.getText();
					  content = content.replace("\n\r", "\n");
					  BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave));
					  writer.write(content);
					  writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    
	  }
	  
	  public static int getCaretRowPosition(JTextComponent src) throws BadLocationException {
		    
		    int caretPos = pane.getCaretPosition();
		    int rowNum = (caretPos == 0) ? 1 : 0;
		    for (int offset = caretPos; offset > 0;) {
		        offset = Utilities.getRowStart(pane, offset) - 1;
		        rowNum++;
		    }
		    return rowNum;
	  }
		
	  public static int getCaretColPosition(JTextComponent src) throws BadLocationException {
			int caretPos = pane.getCaretPosition();
			int offset = Utilities.getRowStart(pane, caretPos);
			int colNum = caretPos - offset + 1;
		    return colNum;
      }
	  
	  void show_status_info(JEditorPane src){

	       int row = 0;
			try {
				row = getCaretRowPosition(src);
				//System.out.println(row);
			} catch (BadLocationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	       int column = 0;
			try {
				column = getCaretColPosition(src);
			} catch (BadLocationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	       //
	       rowtextlabel.setText(row+"줄");
	       columntextlabel.setText(column+"칸");
	  
	  }
	  
	  void createStausBar(){
	      	statusp = new JPanel();
	      	statusp.setPreferredSize(new Dimension(this.getWidth(), 22));
	      	statusp.setLayout(new FlowLayout(FlowLayout.RIGHT));
	      	statusp.setBorder(BorderFactory.createEmptyBorder(5 , 0 , 5 , 0));
	      	int caretPos = pane.getCaretPosition();
	      	int colNum = caretPos;
	      	int rowNum = caretPos;
	      	//
	      	statusp.setLayout(new BoxLayout(statusp, BoxLayout.X_AXIS));
	      	statusp.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 0 , 0));
	      	//      	
	      	pagetextlabel = new JLabel();
	      	columntextlabel = new JLabel();
	      	rowtextlabel = new JLabel();
	      	typemodelabel = new JLabel();
	      	JPanel rowtextPanel = new JPanel();
	      	JPanel columntextPanel = new JPanel();
	      	JPanel pagetextPanel = new JPanel();
	      	JPanel typemodePanel = new JPanel();
	      	
	      	pagetextPanel.setBorder(BorderFactory.createTitledBorder(""));
	      	pagetextPanel.setLayout(new GridLayout(1,1));
	      	rowtextPanel.setBorder(BorderFactory.createTitledBorder(""));
	      	rowtextPanel.setLayout(new GridLayout(1,1));
	      	columntextPanel.setBorder(BorderFactory.createTitledBorder(""));
	      	columntextPanel.setLayout(new GridLayout(1,1));
	      	typemodePanel.setBorder(BorderFactory.createTitledBorder(""));
	      	typemodePanel.setLayout(new GridLayout(1,1));
	        rowtextPanel.add(rowtextlabel);
	        columntextPanel.add(columntextlabel);
	        pagetextPanel.add(pagetextlabel);
	        typemodePanel.add(typemodelabel);
	        statusp.add(pagetextPanel);
	        statusp.add(rowtextPanel);
	        statusp.add(columntextPanel);
	        statusp.add(typemodePanel);
	        //
	    	this.getContentPane().add(statusp, BorderLayout.SOUTH);
	    }

	  public Banana() throws IOException {
		   
		   JFrame frame;  
		   JFileChooser fc;
		   JLabel jl;
		   JLabel fjl;
		   
		   columntextlabel = new JLabel();
	       rowtextlabel = new JLabel();
	       JMenuItem menuItem = new JMenuItem("복사(C)", new ImageIcon("images/newproject.png"));
	       menuItem.setMnemonic(KeyEvent.VK_C);
	       
	       menuItem.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	               //
	           }
	       });
	       popup.add(menuItem);
	       
	       
	       StyleContext styleContext = new StyleContext();
		   
		   Style defaultStyle = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
		   Style cwStyle = styleContext.addStyle("ConstantWidth", null);
		   StyleConstants.setForeground(cwStyle, Color.BLUE);
		   StyleConstants.setBold(cwStyle, false);
		   //
		   
		   pane = new JTextPane();
		   noWrapPanel = new JPanel( new BorderLayout() );
		   noWrapPanel.add(pane );
	       
	       if (fileName !=null){
	    	   
	    	   setTitle(fileName + "- 바나나 텍스트 에디터");
		       String str = "";
		       File file = new File(fileName); 
		       BufferedReader br = new BufferedReader(new FileReader(file)); 
		       String st; 
		       while ((st = br.readLine()) != null){ 
		         System.out.println(str);
		       	 str +=st+"\n\r";
		       } 
		       //
		       if (filePath.canWrite())
		           System.out.println(filePath.getAbsolutePath() + ": CAN WRITE!!!");
		       else {
		    	   int result = JOptionPane.showConfirmDialog(null, "쓰기 권한이 없습니다. 읽기 전용으로 열까요?", "Confirm", 
									JOptionPane.YES_NO_OPTION);
		    	   if(result == JOptionPane.CLOSED_OPTION){
						//tf.setText("Just Closed without Selection");
		    	   }
					
		       }
		       //
		       String extension = "";
		       int i = fileName.lastIndexOf('.');
		       if (i > 0) {
		           extension = fileName.substring(i+1);
		       }
		       
		       switch (extension){
		    	   case "java": 	   
		    		   pane.setDocument(new JavaTokenMarker(defaultStyle));
		    		   break;
		    	   case "c": 	   
		    		   pane.setDocument(new CTokenMarker(defaultStyle));
		    		   break;
		    	   default:
		    		   pane.setDocument(new DefaultTokenMarker(defaultStyle));
		    		   break;
		    		   
		       }
		       //
		          
		       try{
		   	    pane.getDocument().insertString(pane.getDocument().getLength(), str, null);
		   	    //convert utf-8 to ansi 
			    String res = convertFromUtf8ToIso(str);
			    String rawString = res;
				ByteBuffer buffer = StandardCharsets.UTF_8.encode(rawString); 
				 
				String utf8EncodedString = StandardCharsets.UTF_8.decode(buffer).toString();
				//
		       }catch (Exception e){
		       }
		       //
		       fileContent = pane.getText();
		       
	       } else {
	    	   setTitle("제목 없음 - 바나나 텍스트 에디터");
	       }
	       
	       String os = System.getenv("OS");
	       if (os != null){
	    	   if (os.matches("(.*)Windows(.*)")){
		    	   System.out.println(System.getenv("OS"));   
		    	   pane.setFont(new Font("Arial", Font.PLAIN, 15));
	    	   }
	       } else {
	    	   pane.setFont(pane.getFont().deriveFont(15f));
	       }
	       
	       //JScrollPane editorScrollPane = new JScrollPane(pane);
	       editorScrollPane = new JScrollPane(noWrapPanel );
		   //
		   add(editorScrollPane, BorderLayout.CENTER);	
		       
		   pane.addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent e) {
					
					if(e.getButton() == java.awt.event.MouseEvent.BUTTON3){
						showPopup(e);
						
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
			              popup.show(e.getComponent(), e.getX(), e.getY());
			    }
				  
		   });
		   
		   Document doc = pane.getDocument();
	       doc.addUndoableEditListener(new UndoableEditListener() {
	           @Override
	           public void undoableEditHappened(UndoableEditEvent e) {
	        	   if (e.getEdit().getPresentationName() != "스타일 변경"){
	        		   undoManager.addEdit(e.getEdit());
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
	       createStausBar();
	       //createMenu();
	       MenuEx me = new MenuEx(Banana.this);
	       
	       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       setSize(650,550);
	       setVisible(true);
	       //
	       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	       setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	       
	       pane.addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent e) {
					//
					JEditorPane src=(JEditorPane)e.getSource();
					show_status_info(src);
					
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
	       });
	       pane.addKeyListener(new KeyListener(){

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
	    	   
	       });
    }

	

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		 AWTKeyStroke ak = AWTKeyStroke.getAWTKeyStrokeForEvent(e);
         if(ak.equals(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_MASK)))
         {
           System.exit(0);
         }
         if(ak.equals(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_F,InputEvent.ALT_MASK)))
         {
           //
         }
		
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
	public void mouseClicked(MouseEvent e) {
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

	public static void main(String[] args)  throws BadLocationException, IOException {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
		
		if (args.length > 0){
			String path = args[0];
			filePath = new File(path);
			fileName = filePath.getAbsolutePath();
			//
		}
		Banana ex = new Banana();
        ex.setVisible(true);
  
	}
	
}

	


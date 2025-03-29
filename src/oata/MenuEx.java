package oata;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class MenuEx {
	Banana owner;
	RSyntaxTextArea textArea;
	JEditorPane pane;
	MenuEx(Banana owner, RSyntaxTextArea textArea){
		this.owner = owner;
		this.textArea = textArea;
		createMenu();
	}
	public void createMenu(){
		   //menus
		   JMenuItem   new_blank, open, saveas, pagesetup, printex, exit, close, about, random, deletefile, resize;
		   final JMenuItem save;
		   JMenuItem   cut_text, paste_text, find_text, replace_text, fullscreen, delete, previous, next;
		   JMenuBar menuBar = new JMenuBar();
	       
	       JMenu menu1    = new JMenu("파일(F)");
	       menu1.setMnemonic('F');
	       
	       ImageIcon icon_save = new ImageIcon(getClass().getResource("res/save.png"));
	       save     = new JMenuItem("저장(S)", icon_save);
	       save.setMnemonic('S');
	       save.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	        	   try {
						try {
							owner.saveEx();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	   
	           }
	       });
	       
	       menu1.addMenuListener(new MenuListener() {
	    	      public void menuSelected(MenuEvent e) {
	    	        //
	    	   	 	save.setEnabled(false);
	    	   	 	//
	    	   	 	if (owner.fileContent !=null){
	    	   	 		if (owner.fileContent.equals(textArea.getText())){
	    	        	 save.setEnabled(false);
	    	   	 		}
	    	   	 		else {
	    	        	 save.setEnabled(true);
	    	   	 		}
	    	        } 
	    	      }

				@Override
				public void menuCanceled(MenuEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void menuDeselected(MenuEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	       });
	       
	       ImageIcon icon_new = new ImageIcon(getClass().getResource("res/new_blank.png"));
	       new_blank     = new JMenuItem("새문서(N)", icon_new);
	       new_blank.setMnemonic('N');
	       KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask());
	       //set the accelerator
	       new_blank.setAccelerator(ctrlN);
	
	       menu1.add(new_blank);
	       ImageIcon icon_open = new ImageIcon(getClass().getResource("res/folder.png"));
	       open     = new JMenuItem("열기(O)", icon_open);
	       open.setMnemonic('O');
	       KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask());
	       open.setAccelerator(ctrlO);
	       open.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	        	   	  try {
	        	   		owner.openEx();
	        	   	  } catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
	        	   	  }
	        	   
	           }
	       });
	       menu1.add(open);
	       menu1.add(save);
	       //
	       saveas     = new JMenuItem("다른 이름으로 저장(A)", icon_open);
	       saveas.setMnemonic('A');
	       KeyStroke ctrlA = KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask());
	       saveas.setAccelerator(ctrlA);
	       saveas.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
					try {
						owner.saveAs();
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	           }
	       });
	       menu1.add(saveas);
	       
	       pagesetup     = new JMenuItem("페이지 설정(U)");
	       pagesetup.setMnemonic('U');
	       menu1.add(pagesetup);
	       
	       ImageIcon icon_printex = new ImageIcon(getClass().getResource("res/print.png"));
	       printex     = new JMenuItem("인쇄(P)", icon_printex);
	       printex.setMnemonic('P');
	       
	       printex.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
				       
				       try {
			               owner.pane.setContentType("text/plain");
			
			               boolean done = owner.pane.print();
			               if (done) {
			                   JOptionPane.showMessageDialog(null, "인쇄가 완료되었습니다");
			               } else {
			                   JOptionPane.showMessageDialog(null, "인쇄 중 오류가 발생하였습니다");
			               }
			           } catch (Exception pex) {
			               JOptionPane.showMessageDialog(null, "인쇄 중 오류가 발생하였습니다");
			               pex.printStackTrace();
			           }
	           }
	       });
	       menu1.add(printex);
	       
	       
	     
	       ImageIcon icon_close = new ImageIcon(getClass().getResource("res/close.png"));
	       close     = new JMenuItem("닫기(C)", icon_close);
	       close.setMnemonic('C');
	       close.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	               //System.exit(0);
	        	   if (owner.fileContent.equals(textArea.getText()))
			           owner.dispose();
	        	   else {
			    	   int result = JOptionPane.showConfirmDialog(null, "변경 내용을"+owner.filePath.getAbsolutePath()+"에 저장할까요?", "바나나에디터", 
										JOptionPane.YES_NO_CANCEL_OPTION);
			    	   if(result == JOptionPane.NO_OPTION)
						   owner.dispose();
			    	   else if(result == JOptionPane.CANCEL_OPTION)
						   JOptionPane.getRootFrame().dispose();
			    	   else if(result == JOptionPane.YES_OPTION){
							//tf.setText("Just Closed without Selection");
			    		   try {
			    			   owner.saveEx();
			    		   } catch (BadLocationException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
			    		   }
			    	   }
			       }
			    }
	       });
	 	      
	       menu1.add(close);
	       menuBar.add(menu1);
	       
	       JMenu menu2    = new JMenu("편집(E)");
	       menu2.setMnemonic('E');
	       cut_text     = new JMenuItem("잘라내기(C)");
	       cut_text.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	                   pane.cut();
	           }
	       });
	       menu2.add(cut_text);
	       paste_text     = new JMenuItem("붙여넣기(P)");
	       paste_text.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	        	   		pane.paste();
	           }
	       });
	       
	       menu2.add(paste_text);
	       //
	       find_text     = new JMenuItem("찾기(F)");
	       KeyStroke ctrlF = KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask());
	       find_text.setAccelerator(ctrlF);
	       find_text.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	        	   if (owner.m_findDialog==null)
	        		   owner.m_findDialog = new FindDialog(owner, textArea, 0);
	                 else
	                	 owner.m_findDialog.setSelectedIndex(0);
	                 Dimension d1 = owner.m_findDialog.getSize();
	                 Dimension d2 = owner.getSize();
	                 int x = Math.max((d2.width-d1.width)/2, 0);
	                 int y = Math.max((d2.height-d1.height)/2, 0);
	                 owner.m_findDialog.setBounds(x + owner.getX(),
	                   y + owner.getY(), d1.width, d1.height);
	                 owner.m_findDialog.setVisible(true);
	           }
	       });
	
	       menu2.add(find_text);
	       
	       replace_text     = new JMenuItem("바꾸기(R)");
	       replace_text.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	        	   if (owner.m_replaceDialog==null)
	        		   owner.m_replaceDialog = new ReplaceDialog(owner, textArea);
	               else{
	                 }
	                 Dimension d1 = owner.m_replaceDialog.getSize();
	                 Dimension d2 = owner.getSize();
	                 int x = Math.max((d2.width-d1.width)/2, 0);
	                 int y = Math.max((d2.height-d1.height)/2, 0);
	                 owner.m_replaceDialog.setBounds(x + owner.getX(),
	                   y + owner.getY(), d1.width, d1.height);
	                 owner.m_replaceDialog.setVisible(true);
	           }
	       });
	 	      
	       menu2.add(replace_text);
	       menuBar.add(menu2);
	       
	       
	       
	       JMenu menu6    = new JMenu("서식(O)");
	       menu6.setMnemonic('O');
	       
	       final JCheckBoxMenuItem autopara = new JCheckBoxMenuItem("자동 줄바꿈(W)");
	       autopara.setMnemonic('W');
	       autopara.setSelected(false);
	       autopara.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	        	   if (autopara.isSelected()) {
	      	    		//statusp.setVisible(true);
	      	    		owner.editorScrollPane.setViewportView(owner.pane);
	      	    	} else {
	      	    		//statusp.setVisible(false);
	      	    		//noWrapPanel = new JPanel( new BorderLayout() );
	      			    owner.noWrapPanel.add(owner.pane );
	      	    		owner.editorScrollPane.setViewportView(owner.noWrapPanel);
	      	    	}
	               
	           }
	       });
	       
	       menu6.add(autopara);
	       
	       JMenuItem letterview     = new JMenuItem("글꼴(F)");
	       letterview.setMnemonic('F');
	       letterview.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	        	   
	        	   owner.m_pos = owner.pane.getCaretPosition();

	        	   final FontChooserDialog fontchDialog = new FontChooserDialog(owner);
	        	   //
	        	   fontchDialog.addComponentListener(new ComponentListener() {										
	        				    public void componentHidden(ComponentEvent e)
		 					    {
		 					    	//
		 					    	//
		 					    	if (fontchDialog.getReturnStatus() == 1){
			 					    	String font = fontchDialog.getFont().getFontName();
			 					    	int fontsize =  fontchDialog.getFont().getSize();
			 					    	//
			 					    	MutableAttributeSet attr = new SimpleAttributeSet();
			 					        StyleConstants.setFontFamily(attr, font);
			 					        StyleConstants.setFontSize(attr, fontsize);
			 					        try {
											owner.setAttributeSet(attr);
										} catch (BadLocationException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
		 					    	} 
		 					    	fontchDialog.dispose();
		 					    	
		 					    }

								@Override
								public void componentMoved(ComponentEvent arg0) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void componentResized(ComponentEvent arg0) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void componentShown(ComponentEvent arg0) {
									// TODO Auto-generated method stub
									
								}
						});
	           
	           }
	       });
	       
	       menu6.add(letterview);
	       menuBar.add(menu6);
	       
	       JMenu menu5    = new JMenu("인코딩(N)");
	       menu5.setMnemonic('N');
	       menuBar.add(menu5);
	       JMenuItem ansiview     = new JMenuItem("ANSI로 표시");
	       ansiview.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	        	    //convert utf-8 to ansi 
				    //
				    
	           }
	       });
	       
	       menu5.add(ansiview);
	       JMenuItem utf8view     = new JMenuItem("UTF-8로 표시");
	       utf8view.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	        	    //convert ansi to utf-8
				    String rawString = pane.getText();
					ByteBuffer buffer = StandardCharsets.UTF_8.encode(rawString); 
					String utf8EncodedString = StandardCharsets.UTF_8.decode(buffer).toString();
					textArea.setText(utf8EncodedString);
					//
	           }
	       });
	       menu5.add(utf8view);
	       
	       JMenu menu3    = new JMenu("보기(V)");
	       menu3.setMnemonic('V');
	       menuBar.add(menu3);
	       
	       final JCheckBoxMenuItem statusmode = new JCheckBoxMenuItem("상태표시줄(B)");
	          statusmode.setMnemonic(KeyEvent.VK_B);
	          statusmode.setSelected(true);
	          statusmode.addActionListener(new ActionListener() {
	      	     @Override
	      	     public void actionPerformed(ActionEvent e) {
	      	    	if (statusmode.isSelected()) {
	      	    		owner.statusp.setVisible(true);
	      	    		owner.show_status_info(pane);
	      	    	} else {
	      	    		owner.statusp.setVisible(false);
	      	    	}
	      	    	
	      	     }
	          });
	       menu3.add(statusmode);
	       
	       JMenu menu4    = new JMenu("도움말(H)");
	       menu4.setMnemonic('H');
	       about     = new JMenuItem("정보(A)");
	       about.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	         	  AboutDialog ab = new AboutDialog();
	              //ab.version = "바나나에디터 v0.3.3 엠케이솔루션 제공";
	              ab.showDialog();
	              ab.setVisible(true);
	           }
	       });
	 	      
	       about.setMnemonic('A');
	       menu4.add(about);
	       menuBar.add(menu4);
	       
	       owner.setJMenuBar(menuBar);	  
	  }
	  
}

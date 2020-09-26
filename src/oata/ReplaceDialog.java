package oata;

import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class ReplaceDialog extends JDialog {
	protected Banana m_owner;
	protected JTabbedPane m_tb;
	protected JTextField m_txtFind1;
	protected JTextField m_txtFind2, m_txtReplace2;
	protected Document m_docFind;
	protected Document m_docReplace;
	protected ButtonModel m_modelWord;
	protected ButtonModel m_modelCase;
	protected ButtonModel m_modelUp;
	protected ButtonModel m_modelDown;
	
	protected int m_searchIndex = -1;
	protected int m_replaceIndex = -1;
	protected boolean m_searchUp = false;
	protected String  m_searchData;
	
	public ReplaceDialog(Banana banana) {
		 super();
		 //
		 setModal(false);
		 setAlwaysOnTop(true);
		 //
		 setTitle("바꾸기");
		 m_owner = banana;
		 m_tb = new JTabbedPane();
		 JButton btnFindNext = new JButton("다음 찾기");
		 JButton btnReplace = new JButton("바꾸기");
		 JButton btnReplaceAll = new JButton("모두 바꾸기");
		 JButton btnClose = new JButton("닫기");
	     
	     JPanel jp1 = new JPanel();
	     jp1.setLayout(new BoxLayout(jp1, BoxLayout.X_AXIS));
	     
	     JPanel lb = new JPanel();
	     lb.setLayout(new BoxLayout(lb, BoxLayout.Y_AXIS));
	     
	     JPanel lp = new JPanel(new FlowLayout());
	     
	     JLabel lbt = new JLabel("찾을 문자열");
	     lbt.setPreferredSize(new Dimension(100,  20));
	     lp.add(lbt);
	     
		 m_txtFind1 = new JTextField();
		 m_txtFind1.addKeyListener(new KeyAdapter() {
			        public void keyPressed(KeyEvent e) {
			          if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			             //button.doClick();
			        	 findNext(true, true);
			          }
			        }
		 });
		 
		 m_txtFind1.setPreferredSize(new Dimension(300,  20));
		 m_docFind = m_txtFind1.getDocument();
		 lp.add(m_txtFind1);
	     
	     lb.add(lp);
	     //
	     JPanel lp3 = new JPanel(new FlowLayout());
	     JLabel lbt3 = new JLabel("바꿀 문자열");
	     lbt3.setPreferredSize(new Dimension(100,  20));
	     lp3.add(lbt3);
	     m_txtReplace2 = new JTextField();
		 m_txtReplace2.setPreferredSize(new Dimension(300,  20));
		 m_docReplace = m_txtReplace2.getDocument();
		 lp3.add(m_txtReplace2);
		 //
	     lb.add(lp3);
	     lb.add(lp3);
	     //
		 JPanel po = new JPanel(new GridLayout(2, 2, 8, 2));
		 po.setBorder(new TitledBorder(new EtchedBorder(), "옵션"));
		
		 JCheckBox chkWord = new JCheckBox("전체 단어만");
		 chkWord.setMnemonic('w');
		 m_modelWord = chkWord.getModel();
		 po.add(chkWord);
		
		 ButtonGroup bg = new ButtonGroup();
		 JRadioButton rdUp = new JRadioButton("위로 검색");
		 rdUp.setMnemonic('u');
		 m_modelUp = rdUp.getModel();
		 bg.add(rdUp);
		 po.add(rdUp);
		
		 JCheckBox chkCase = new JCheckBox("일치 문자열");
		 chkCase.setMnemonic('c');
		 m_modelCase = chkCase.getModel();
		 po.add(chkCase);
		
		 JRadioButton rdDown = new JRadioButton("아래로 검색", true);
		 rdDown.setMnemonic('d');
		 m_modelDown = rdDown.getModel();
		 bg.add(rdDown);
		 po.add(rdDown);
		 lb.add(po);
		 //
		 btnFindNext.setSize(new Dimension(100, 150));
	     
	     btnFindNext.addActionListener(new ActionListener()
	     {
	         public void actionPerformed(ActionEvent e)
	         {
	           //
	           findNext(true, true);
	         }
	       });
	     
	     btnClose.setSize(new Dimension(100, 150));
	     
	     btnClose.addActionListener(new ActionListener()
	     {
	         public void actionPerformed(ActionEvent e)
	         {
	           dispose();
	         }
	       });
	     //
	     btnReplaceAll.setSize(new Dimension(100, 150));
	     btnReplaceAll.addActionListener(new ActionListener()
	     {
	         public void actionPerformed(ActionEvent e)
	         {
	           //dispose();
	           FindReplaceAll(true, true);
	         }
	     });
	     //
	     btnReplace.setSize(new Dimension(100, 150));	     
	     btnReplace.addActionListener(new ActionListener()
	     {
	         public void actionPerformed(ActionEvent e)
	         {
	           //replace
	           int start = m_owner.getTextPane().getSelectionStart();
	           int end = m_owner.getTextPane().getSelectionEnd();
	           System.out.println(start+" "+end);
	           //this is important
	           m_owner.getTextPane().replaceSelection("");
	           
	           try {
				m_owner.getTextPane().getDocument().insertString(start, m_txtReplace2.getText(), null);
				 
	           } catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
	           }
	      	   m_owner.pane.setSelectionStart(start);
			   m_owner.pane.setSelectionEnd(start+m_txtReplace2.getText().length());
	           
			   m_replaceIndex = start+m_txtReplace2.getText().length();
	         }
	     });
	             
	     JPanel btg = new JPanel();
	     //align all buttons and fit them.
	     btg.setLayout(new BoxLayout(btg, BoxLayout.Y_AXIS));
	     Dimension d = btnReplaceAll.getMaximumSize();
	     btnFindNext.setMaximumSize(new Dimension(d));
	     btnClose.setMaximumSize(new Dimension(d));
	     btnReplace.setMaximumSize(new Dimension(d));
	     //
	     btg.add(btnFindNext);
	     btg.add(btnReplace);
	     btg.add(btnReplaceAll);
	     btg.add(btnClose);
	     lb.setAlignmentY(Component.TOP_ALIGNMENT);
	     btg.setAlignmentY(Component.TOP_ALIGNMENT);
	     
	     
	     jp1.add(lb);
	     jp1.add(btg);
	     //
	     add(jp1);
	     
	     pack();
		 setResizable(false);
		 setLocationRelativeTo(this);
		 //
		 setVisible(false);
	}
	
	public void findNext(boolean doReplace, boolean showWarnings) {
		 JTextPane monitor = m_owner.getTextPane();
		 String key = null;
		 String replacekey = null;
		 int xStart = -1;
		 int xFinish = -1;
		 
		 int pos = monitor.getCaretPosition();
		 //
		 Document doc = monitor.getDocument();
		 		 
		 try { 
		 	 key = m_docFind.getText(0, m_docFind.getLength()); 
		 	 replacekey = m_txtReplace2.getText();
		 }
		 catch (BadLocationException ex){
		 }
		 
		 try {
			m_searchData = doc.getText(pos, doc.getLength()-pos);
			 
		 } catch (BadLocationException e) {
			// 
			e.printStackTrace();
		 }
		 
		 if (key.length()==0) {
			 warning("검색할 문자열을 입력하십시오");
			
		 }
		 //
		 xStart = m_searchData.indexOf(key);
		 
		 if (xStart > 0){
			 xStart += pos;			 
			 if (m_replaceIndex != -1){
				 m_searchIndex = xStart+replacekey.length();
			 } else m_searchIndex = xStart+key.length();
			 //
			 xFinish = xStart+key.length();
			 m_owner.pane.setSelectionStart(xStart);
			 m_owner.pane.setSelectionEnd(xFinish);
			 
		 } else if (xStart < 0) {
			 if (showWarnings){
				 setVisible(false);
				 warning("문자열을 찾지 못 했습니다");
				 m_searchIndex = -1;
				 setVisible(true);
				 if (m_searchIndex != -1){
					 System.out.println(pos);
					 //m_owner.pane.setCaretPosition(pos);
					 xFinish = pos+key.length();
					 m_owner.pane.setSelectionStart(xStart);
					 m_owner.pane.setSelectionEnd(xFinish);
					 
				 }
			 }
			
		 }
		
		 
	}
	
	protected void warning(String message) {
		 JOptionPane.showMessageDialog(m_owner, message, "Warning", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public int FindReplaceAll(boolean doReplace, boolean showWarnings) {
		 JTextPane monitor = m_owner.getTextPane();
		 //
		 int pos = monitor.getCaretPosition();
		 
		 if (m_modelUp.isSelected() != m_searchUp) {
		   m_searchUp = m_modelUp.isSelected();
		 
		 }
		 
		 while(true){
			 try {
				 Document doc = monitor.getDocument();
				 if (m_searchUp)
					 m_searchData = doc.getText(0, pos);
				 else
				     m_searchData = doc.getText(pos, doc.getLength()-pos);
		     }
			 catch (BadLocationException ex) {
				 JOptionPane.showMessageDialog(m_owner, "", "Warning", JOptionPane.INFORMATION_MESSAGE);
				 return -1;
			 }
			 
			 String key = "";
			 
			 try { 
			 	 key = m_docFind.getText(0, m_docFind.getLength()); 
			 }
			 catch (BadLocationException ex){
			 		
			 }
			 
			 if (key.length()==0) {
				 warning("검색할 문자열을 입력하십시오");
				 return -1;
			 }
			
			 String replacement = "";
			 if (doReplace) {
				 try {
					 replacement = m_docReplace.getText(0, 
					 m_docReplace.getLength());
		     } catch (BadLocationException ex) {}
		     }
			 
		     // start searching
			 int xStart = -1;
			 int xFinish = -1;
			 
			 if (m_searchUp)
				 xStart = m_searchData.lastIndexOf(key, pos-1);
			 else
				 xStart = m_searchData.indexOf(key, pos-m_searchIndex);
			 
			 if (xStart > 0) {
				 //pos = xStart+m_txtReplace2.getText().length();
				 xFinish = xStart+key.length();
				 System.out.println("bbb: "+xFinish);
				 
				 pos = xFinish;
			 }
			 if (xStart < 0) {
				 if (showWarnings){
					 
				 	 setVisible(false);
			         JOptionPane.showMessageDialog(m_owner, "", "Warning", JOptionPane.INFORMATION_MESSAGE);
					 setVisible(true);
					 break;
					 
				 }
				 
				 return 0;
			 }
			 
			
			 
		 }
		return 0;
		 
	}	     
}

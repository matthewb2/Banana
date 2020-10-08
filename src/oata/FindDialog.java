package oata;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
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

public class FindDialog  extends JDialog {
	
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
	protected boolean m_searchUp = false;
	protected String  m_searchData;

	public FindDialog(Banana owner, int index) {
	 super();
	 
	 m_owner = owner;
	 m_tb = new JTabbedPane();
	 JButton btn1 = new JButton("다음");
	 JButton btn2 = new JButton("닫기");
     
     JPanel jp1 = new JPanel();
     jp1.setLayout(new BoxLayout(jp1, BoxLayout.X_AXIS));
     
     JPanel lb = new JPanel();
     lb.setLayout(new BoxLayout(lb, BoxLayout.Y_AXIS));
     
     JPanel lp = new JPanel(new FlowLayout());
     
     JLabel lbt = new JLabel("찾을 문자열:");
     lbt.setPreferredSize(new Dimension(100,  20));
     lp.add(lbt);
     
	 m_txtFind1 = new JTextField();
	 m_txtFind1.setPreferredSize(new Dimension(300,  20));
	 m_docFind = m_txtFind1.getDocument();
	 lp.add(m_txtFind1);
     
     lb.add(lp);
	 JPanel po = new JPanel(new GridLayout(2, 2, 8, 2));
	 po.setBorder(new TitledBorder(new EtchedBorder(), 
	   "옵션"));
	
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
	 
	 btn1.setSize(new Dimension(100, 150));
     
     btn1.addActionListener(new ActionListener()
     {
         public void actionPerformed(ActionEvent e)
         {
           //
           findNext(false, true);
         }
       });
     
     btn2.setSize(new Dimension(100, 150));
     
     btn2.addActionListener(new ActionListener()
     {
         public void actionPerformed(ActionEvent e)
         {
           dispose();
         }
       });
     
             
     JPanel btg = new JPanel();
     btg.setLayout(new BoxLayout(btg, BoxLayout.Y_AXIS));
     btg.add(btn1);
     btg.add(btn2);
     lb.setAlignmentY(Component.TOP_ALIGNMENT);
     btg.setAlignmentY(Component.TOP_ALIGNMENT);
     
     
     jp1.add(lb);
     jp1.add(btg);
     
     
     
     m_tb.add("찾기", jp1); //JTabbedPane에 탭추가
     //
     
     JButton btn21 = new JButton("바꾸기");
	 JButton btn22 = new JButton("닫기");
	 JButton btn23 = new JButton("전체");
     //JTextField txt_1 = new JTextField("기본값",25);
             
     
     JPanel jp21 = new JPanel();
     jp21.setLayout(new BoxLayout(jp21, BoxLayout.X_AXIS));
     
     JPanel lb2 = new JPanel();
     lb2.setLayout(new BoxLayout(lb2, BoxLayout.Y_AXIS));
     
     JPanel lp2 = new JPanel(new FlowLayout());
     JLabel lbt2 = new JLabel("찾을 문자열");
     lbt2.setPreferredSize(new Dimension(100,  20));
     lp2.add(lbt2);
     m_txtFind2 = new JTextField();
	 m_txtFind2.setPreferredSize(new Dimension(300,  20));
	 m_docReplace = m_txtFind2.getDocument();
	 lp2.add(m_txtFind2);
     lb2.add(lp2);
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
     lb2.add(lp2);
     lb2.add(lp3);
     //
	 JPanel po2 = new JPanel(new GridLayout(2, 2, 8, 2));
	 po2.setBorder(new TitledBorder(new EtchedBorder(), 
	   "옵션"));
	
	 JCheckBox chkWord2 = new JCheckBox("전체 단어만");
	 chkWord2.setMnemonic('w');
	 m_modelWord = chkWord2.getModel();
	 po2.add(chkWord2);
	
	 ButtonGroup bg2 = new ButtonGroup();
	 JRadioButton rdUp2 = new JRadioButton("위로 검색");
	 rdUp2.setMnemonic('u');
	 m_modelUp = rdUp2.getModel();
	 bg2.add(rdUp2);
	 po2.add(rdUp2);
	
	 JCheckBox chkCase2 = new JCheckBox("일치 문자열");
	 chkCase2.setMnemonic('c');
	 m_modelCase = chkCase2.getModel();
	 po.add(chkCase2);
	
	 JRadioButton rdDown2 = new JRadioButton("아래로 검색", true);
	 rdDown2.setMnemonic('d');
	 m_modelDown = rdDown2.getModel();
	 bg2.add(rdDown2);
	 po2.add(rdDown2);
	 lb2.add(po2);
	 
	 btn21.setSize(new Dimension(100, 150));
     
     btn21.addActionListener(new ActionListener()
     {
         public void actionPerformed(ActionEvent e)
         {
           //
           findNext(true, true);
         }
       });
     
     btn22.setSize(new Dimension(100, 150));
     
     btn22.addActionListener(new ActionListener()
     {
         public void actionPerformed(ActionEvent e)
         {
           // display/center the jdialog when the button is pressed
           dispose();
         }
       });
     
             
     JPanel btg2 = new JPanel();
     btg2.setLayout(new BoxLayout(btg2, BoxLayout.Y_AXIS));
     btg2.add(btn21);
     btg2.add(btn22);
     btg2.add(btn23);
     lb2.setAlignmentY(Component.TOP_ALIGNMENT);
     btg2.setAlignmentY(Component.TOP_ALIGNMENT);
     
     
     jp21.add(lb2);
     jp21.add(btg2);
     m_tb.add(jp21, "바꾸기");
     
     //
	 ActionListener findAction = new ActionListener() { 
	   public void actionPerformed(ActionEvent e) {
	     findNext(false, true);
	   }
	 };
	 getContentPane().add(m_tb, BorderLayout.CENTER);
	
	 WindowListener flst = new WindowAdapter() { 
	   public void windowActivated(WindowEvent e) {
	     m_searchIndex = -1;
	     if (m_tb.getSelectedIndex()==0)
	       m_txtFind1.grabFocus();
	     else
	       m_txtFind2.grabFocus();
	   }
	
	   public void windowDeactivated(WindowEvent e) {
	     m_searchData = null;
	   }
	 };
	 addWindowListener(flst);
	

	 pack();
	 setResizable(false);
	 
	 setLocationRelativeTo(this);
	 //setSize(400, 300);
	 setVisible(false);
	 
	}
	
	public void setSelectedIndex(int index) {
	 m_tb.setSelectedIndex(index);
	 setVisible(true);
	 m_searchIndex = -1;
	}

	
	
	//
	public int findNext(boolean doReplace, boolean showWarnings) {
			 JEditorPane monitor = m_owner.getTextPane();
			 int pos = monitor.getCaretPosition();
			 System.out.println(pos+"in find");
			 if (m_modelUp.isSelected() != m_searchUp) {
			   m_searchUp = m_modelUp.isSelected();
			   m_searchIndex = -1;
			 }
		
			 if (m_searchIndex == -1) {
				 try {
			     Document doc = monitor.getDocument();
			     if (m_searchUp)
			       m_searchData = doc.getText(0, pos);
			     else
			       m_searchData = doc.getText(pos, doc.getLength()-pos);
			     m_searchIndex = pos;
				 }
				 catch (BadLocationException ex) {
					 		warning(ex.toString());
					 		return -1;
				 }
			 }
		     
			 String key = "";
			 	try { key = m_docFind.getText(0, m_docFind.getLength()); }
			 	catch (BadLocationException ex) {}
			 if (key.length()==0) {
				 warning("Please enter the target to search");
				 return -1;
			 }
			 if (!m_modelCase.isSelected()) {
				 m_searchData = m_searchData.toLowerCase();
				 key = key.toLowerCase();
			 }
			 if (m_modelWord.isSelected()) {
				 for (int k=0; k<Utils.WORD_SEPARATORS.length; k++) {
					 if (key.indexOf(Utils.WORD_SEPARATORS[k]) >= 0) {
						 warning("The text target contains an illegal "+
								 "character \'"+Utils.WORD_SEPARATORS[k]+"\'");	
						 return -1;
					 }
				 }
			 }
		
		 String replacement = "";
		 if (doReplace) {
		   try {
		     replacement = m_docReplace.getText(0, 
		       m_docReplace.getLength());
		   } catch (BadLocationException ex) {}
		 }
		
		 int xStart = -1;
		 int xFinish = -1;
		 while (true) 
		 {
		   if (m_searchUp)
		     xStart = m_searchData.lastIndexOf(key, pos-1);
		   else
		     xStart = m_searchData.indexOf(key, pos-m_searchIndex);
		   if (xStart < 0) {
		     if (showWarnings)
		       warning("문자열을 찾지 못 했습니다");
		     return 0;
		   }
		
		   xFinish = xStart+key.length();
		
		   if (m_modelWord.isSelected()) {
		     boolean s1 = xStart>0;
		     boolean b1 = s1 && !Utils.isSeparator(m_searchData.charAt(
		       xStart-1));
		     boolean s2 = xFinish<m_searchData.length();
		     boolean b2 = s2 && !Utils.isSeparator(m_searchData.charAt(
		       xFinish));
		                 
		     if (b1 || b2)    // Not a whole word
		     {
		       if (m_searchUp && s1)    // Can continue up
		       {
		         pos = xStart;
		         continue;
		       }
		       if (!m_searchUp && s2)    // Can continue down
		       {
		         pos = xFinish;
		         continue;
		       }
		       // Found, but not a whole word, and we cannot continue
		       if (showWarnings)
		         warning("Text not found");
		       return 0;
		     }
		   }
		   break;
		 }
		
		 if (!m_searchUp) {
		   xStart += m_searchIndex;
		   xFinish += m_searchIndex;
		 }
		 if (doReplace) {
		   //m_owner.setSelection(xStart, xFinish, m_searchUp);
		   monitor.replaceSelection(replacement);
		   //m_owner.setSelection(xStart, xStart+replacement.length(), 
		   //m_searchUp);
		   m_searchIndex = -1;
		 }
		 else{
		     //m_owner.setSelection(xStart, xFinish, m_searchUp);
			 System.out.println(xStart+" "+xFinish);
		 }
		 return 1;
		
	}
	
	protected void warning(String message) {
		 JOptionPane.showMessageDialog(m_owner, message, "Warning", JOptionPane.INFORMATION_MESSAGE);
	}
}
	
class Utils
{
		public static final char[] WORD_SEPARATORS = {' ', '\t', '\n',
		 '\r', '\f', '.', ',', ':', '-', '(', ')', '[', ']', '{',
		 '}', '<', '>', '/', '|', '\\', '\'', '\"'};
		 
		public static boolean isSeparator(char ch) {
		 for (int k=0; k<WORD_SEPARATORS.length; k++)
		   if (ch == WORD_SEPARATORS[k])
		     return true;
	 return false;
	}
}

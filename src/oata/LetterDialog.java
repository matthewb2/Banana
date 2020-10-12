package oata;

import static java.awt.font.TextAttribute.FAMILY;
import static java.awt.font.TextAttribute.POSTURE;
import static java.awt.font.TextAttribute.POSTURE_OBLIQUE;
import static java.awt.font.TextAttribute.SIZE;
import static java.awt.font.TextAttribute.STRIKETHROUGH;
import static java.awt.font.TextAttribute.STRIKETHROUGH_ON;
import static java.awt.font.TextAttribute.SUPERSCRIPT;
import static java.awt.font.TextAttribute.SUPERSCRIPT_SUB;
import static java.awt.font.TextAttribute.SUPERSCRIPT_SUPER;
import static java.awt.font.TextAttribute.UNDERLINE;
import static java.awt.font.TextAttribute.UNDERLINE_LOW_ONE_PIXEL;
import static java.awt.font.TextAttribute.WEIGHT;
import static java.awt.font.TextAttribute.WEIGHT_BOLD;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LetterDialog  extends JDialog {
	
	JList fontlist, fontstylelist, fontsizelist;
	
	
	public LetterDialog(Banana owner, int index) {
	
		//
		JPanel pageInner = new JPanel();
	    //  
		pageInner.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
		//
		pageInner.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel empty = new JLabel("글꼴");
		final JTextField fontfield = new JTextField();
        JLabel empty2 = new JLabel("글꼴 스타일");
        final JTextField fontstylefield = new JTextField();
        JLabel empty3 = new JLabel("크기");
        final JTextField fontsizefield = new JTextField();
        JLabel empty4 = new JLabel("");
        JLabel empty5 = new JLabel("");
        JPanel empty6 = new JPanel();
        
        empty6.setBorder(BorderFactory.createTitledBorder("보기"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.gridx = 0;
        c.gridy = 0;
        pageInner.add(empty, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
	    pageInner.add(empty2, c);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridx = 2;
        c.gridy = 0;
	    pageInner.add(empty3, c);
	    //
	    c.gridx = 0;
        c.gridy = 1;
        pageInner.add(fontfield, c);
        c.gridx = 1;
        c.gridy = 1;
        pageInner.add(fontstylefield, c);
        c.gridx = 2;
        c.gridy = 1;
        pageInner.add(fontsizefield, c);
        
	    c.ipady = 40;     
	    c.gridx = 0;
        c.gridy = 2;
        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("Alison Huml");
        listModel.addElement("Kathy Walrath");
        listModel.addElement("Lisa Friendly");
        listModel.addElement("Mary Campione");
        listModel.addElement("Mary Campione2");
        listModel.addElement("Mary Campione2");
        listModel.addElement("Mary Campione3");
        listModel.addElement("Mary Campione3");
        listModel.addElement("Mary Campione4");
        listModel.addElement("Mary Campione5");

        fontlist = new JList(listModel);
        //	
	    fontlist.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				int index = fontlist.getSelectedIndex();	
				//
				Object elem = fontlist.getSelectedValue();
				fontfield.setText(elem.toString());
			}
	    });
        JScrollPane spfont = new JScrollPane(fontlist);
        spfont.setPreferredSize(new Dimension(100, 100));
        
	    pageInner.add(spfont, c);
	    
	    fontstylelist = new JList(listModel);
	    fontstylelist.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				int index = fontstylelist.getSelectedIndex();	
				//
				Object elem = fontstylelist.getSelectedValue();
				fontstylefield.setText(elem.toString());
			}
	    });
	    
	    c.gridx = 1;
        c.gridy = 2;
        JScrollPane spfontstyle = new JScrollPane(fontstylelist);
        spfontstyle.setPreferredSize(new Dimension(100, 100));
        
	    pageInner.add(spfontstyle, c);
	    
	    fontsizelist = new JList(listModel);
	    fontsizelist.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				int index = fontsizelist.getSelectedIndex();	
				//System.out.println(index);
				Object elem = fontsizelist.getSelectedValue();
				fontsizefield.setText(elem.toString());
			}
	    });
	    
	    c.gridx = 2;
        c.gridy = 2;
        JScrollPane spfontsize = new JScrollPane(fontsizelist);
        spfontsize.setPreferredSize(new Dimension(100, 100));
        
	    pageInner.add(spfontsize, c);
	    //
	    
	    c.gridx = 0;
        c.gridy = 3;
	    pageInner.add(empty4, c);
	    c.gridx = 1;
        c.gridy = 3;
	    pageInner.add(empty5, c);
	    c.gridx = 2;
        c.gridy = 3;
	    pageInner.add(empty6, c);
		/*
     	//Create the radio buttons.
        JRadioButton birdButton = new JRadioButton("문서전체(T)");
        birdButton.setMnemonic(KeyEvent.VK_T);
        //
        birdButton.setSelected(true);
        birdButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel empty = new JLabel();
        JLabel empty2 = new JLabel();
        JLabel empty3 = new JLabel();
        JLabel empty4 = new JLabel();
        JRadioButton curButton = new JRadioButton("현재쪽(C)");
        curButton.setMnemonic(KeyEvent.VK_C);
        curButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        //
	    JRadioButton partButton = new JRadioButton("일부분(R)");
	    partButton.setMnemonic(KeyEvent.VK_R);
	    partButton.setAlignmentX(Component.LEFT_ALIGNMENT);
	    JPanel wrapper = new JPanel();
	    wrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JTextField tm = new JTextField();
	    tm.setPreferredSize(new Dimension(80, 20));
	    tm.setAlignmentX(Component.LEFT_ALIGNMENT);
	    //
	    JLabel ex = new JLabel("예) 1-9");
	    wrapper.add(tm);
	    wrapper.add(ex);
       
        ButtonGroup group = new ButtonGroup();
        group.add(birdButton);
        group.add(curButton);
        group.add(partButton);
        //
        pageInner.add(birdButton);
        pageInner.add(empty);
	    pageInner.add(empty2);
	    pageInner.add(curButton);
	    pageInner.add(empty3);
	    pageInner.add(empty4);
	    pageInner.add(partButton);
	    pageInner.add(wrapper);
	    */
	    add(pageInner);
	    //add(pagePanel);
		
	//

	pack();
	setResizable(false);
		 
	setLocationRelativeTo(this);
		 //setSize(400, 300);
	setVisible(true);
	}
}

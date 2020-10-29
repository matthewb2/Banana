package oata;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class DefaultTokenMarker   extends DefaultStyledDocument  {
  	private static final long serialVersionUID = 1L;
    private Style _defaultStyle;
    private Style _cwStyle;
    private Style _commentStyle;
    private Style _digitStyle;
    
	public DefaultTokenMarker(Style defaultStyle) {
		// TODO Auto-generated constructor stub
		  _defaultStyle =  defaultStyle;
          StyleContext styleContext = new StyleContext();
		  Style cwStyle = styleContext.addStyle("ConstantWidth", null);
		  StyleConstants.setForeground(cwStyle, Color.BLUE);
		  StyleConstants.setBold(cwStyle, false);
		  _cwStyle = cwStyle;
		  StyleContext styleContext2 = new StyleContext();
		  Style cwStyle2 = styleContext2.addStyle("ConstantWidth", null);
		  StyleConstants.setForeground(cwStyle2, new Color(58,151,151));
		  StyleConstants.setBold(cwStyle2, false);
		  _commentStyle= cwStyle2;
		  StyleContext styleContext3 = new StyleContext();
		  Style cwStyle3 = styleContext3.addStyle("ConstantWidth", null);
		  StyleConstants.setForeground(cwStyle3, new Color(255,194,58));
		  StyleConstants.setBold(cwStyle3, false);
		  _digitStyle= cwStyle3;
	}

	public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
         super.insertString(offset, str, a);
         refreshDocument();
     }

     public void remove (int offs, int len) throws BadLocationException {
         super.remove(offs, len);
         refreshDocument();
     }

     private synchronized void refreshDocument() throws BadLocationException {
         String text = getText(0, getLength());
         //System.out.println(text);
         final List<HiliteWord> list = processWords(text);

         setCharacterAttributes(0, text.length(), _defaultStyle, true);   
         for(HiliteWord word : list) {
             int p0 = word._position;
             setCharacterAttributes(p0, word._word.length(), _cwStyle, true);
         }
         
         //digits
         final List<HiliteWord> commentdigit = processDigits(text);
         //   
         for(HiliteWord word : commentdigit) {
             int p0 = word._position;
             System.out.println(word._word);
             setCharacterAttributes(p0, word._word.length()+2, _digitStyle, true);
         }
         
         //comment
         final List<HiliteWord> commentlist = processComments(text);
         //   
         for(HiliteWord word : commentlist) {
             int p0 = word._position;
             //System.out.println(word._word);
             setCharacterAttributes(p0, word._word.length()+2, _commentStyle, true);
         }
         
     }
     
   //process digit
     private static  List<HiliteWord> processDigits(String content) {
         content += " ";
        // 
         List<HiliteWord> hiliteWords = new ArrayList<HiliteWord>();
         String word = "D";
         Boolean isDigit = false;
         char[] data = content.toCharArray();
         //
         for(int index=0; index < data.length; index++) {
        	 char ch = data[index];
        	 if (Character.isDigit(ch)){
        		 System.out.println(ch);
        		 isDigit = true;
        		 word +=ch;
        	 } else if (isDigit && !Character.isDigit(ch)) {
        		 isDigit = false;
        		 hiliteWords.add(new HiliteWord("D",index-2));
        	 } 
         }
        return hiliteWords;
     }

     private static  List<HiliteWord> processWords(String content) {
         content += " ";
         List<HiliteWord> hiliteWords = new ArrayList<HiliteWord>();
         int lastWhitespacePosition = 0;
         String word = "";
         char[] data = content.toCharArray();

         for(int index=0; index < data.length; index++) {
             char ch = data[index];
             if(!(Character.isLetter(ch) || Character.isDigit(ch) || ch == '_')) {
                 lastWhitespacePosition = index;
                 if(word.length() > 0) {
                     if(isReservedWord(word)) {
                         hiliteWords.add(new HiliteWord(word,(lastWhitespacePosition - word.length())));
                     }
                     word="";
                 }
             }
             
             else {
                 word += ch;
             }
        }
        return hiliteWords;
     }
     //process comment
     private static  List<HiliteWord> processComments(String content) {
         content += " ";
         List<HiliteWord> hiliteWords = new ArrayList<HiliteWord>();
         String word = "/";
         Boolean isComment = false;
         char[] data = content.toCharArray();
         //
         for(int index=0; index < data.length; index++) {
        	 char ch = data[index];
        	 if (ch =='*' && data[index-1] == '/'){
        		 isComment = true;
        		 word +=ch;
        	 } else if (isComment && ch =='/') {
        		 isComment = false;
        	 } else { 
        		 if (isComment && word.length()>0){
        			 hiliteWords.add(new HiliteWord("/*",index-2));
        		 }
        	 }
         }
        return hiliteWords;
     }
     

     private static final boolean isReservedWord(String word) {
         return(word.toUpperCase().trim().equals("false"));
    }
}

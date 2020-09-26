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

public class JavaTokenMarker  extends DefaultStyledDocument  {
	  	private static final long serialVersionUID = 1L;
	    private Style _defaultStyle;
	    private Style _cwStyle;
	    private Style _commentStyle;
	    
		public JavaTokenMarker(Style defaultStyle) {
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
	         //comment
	         final List<HiliteWord> commentlist = processComments(text);
	         
	         //   
	         for(HiliteWord word : commentlist) {
	             int p0 = word._position;
	             System.out.println(word._word);
	             setCharacterAttributes(p0, word._word.length()+2, _commentStyle, true);
	         }

	         
	                  
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
	        // regular expression
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
	         return(word.toUpperCase().trim().equals("package") || 
	                        word.toUpperCase().trim().equals("import") ||
	                        word.toUpperCase().trim().equals("byte") ||
	                        word.toUpperCase().trim().equals("char") ||
	                        word.toUpperCase().trim().equals("short") ||
	                        word.toUpperCase().trim().equals("int") ||
	                        word.toUpperCase().trim().equals("long") ||
	                        word.toUpperCase().trim().equals("float") ||
	                        word.toUpperCase().trim().equals("double") ||
	                        word.toUpperCase().trim().equals("boolean") ||
	                        word.toUpperCase().trim().equals("void") ||
	                        word.toUpperCase().trim().equals("class") ||
	                        word.toUpperCase().trim().equals("interface") ||
	                        word.toUpperCase().trim().equals("abstract") ||
	                        word.toUpperCase().trim().equals("final") ||
	                        word.toUpperCase().trim().equals("private") ||
	                        word.toUpperCase().trim().equals("protected") ||
	                        word.toUpperCase().trim().equals("public") ||
	                        word.toUpperCase().trim().equals("static") ||
	                        word.toUpperCase().trim().equals("synchronized") ||
	                        word.toUpperCase().trim().equals("native") ||
	                        word.toUpperCase().trim().equals("volatile") ||
	                        word.toUpperCase().trim().equals("transient") ||
	                        word.toUpperCase().trim().equals("break") ||
	                        word.toUpperCase().trim().equals("case") ||
	                        word.toUpperCase().trim().equals("continue") ||
	                        word.toUpperCase().trim().equals("default") ||
	                        word.toUpperCase().trim().equals("do") ||
	                        word.toUpperCase().trim().equals("else") ||
	                        word.toUpperCase().trim().equals("for") ||
	                        word.toUpperCase().trim().equals("if") ||
	                        word.toUpperCase().trim().equals("instanceof") ||
	                        word.toUpperCase().trim().equals("new") ||
	                        word.toUpperCase().trim().equals("return") ||
	                        word.toUpperCase().trim().equals("switch") ||
	                        word.toUpperCase().trim().equals("while") ||
	                        word.toUpperCase().trim().equals("throw") ||
	                        word.toUpperCase().trim().equals("try") ||
	                        word.toUpperCase().trim().equals("catch") ||
	                        word.toUpperCase().trim().equals("extends") ||
	                        word.toUpperCase().trim().equals("finally") ||
	                        word.toUpperCase().trim().equals("implements") ||
	                        word.toUpperCase().trim().equals("throws") ||
	                        word.toUpperCase().trim().equals("this") ||
	                        word.toUpperCase().trim().equals("null") ||
	                        word.toUpperCase().trim().equals("super") ||
	                        word.toUpperCase().trim().equals("true") ||
	                        word.toUpperCase().trim().equals("false"));
	    }
}

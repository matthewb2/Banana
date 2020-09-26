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

public class PerlTokenMarker  extends DefaultStyledDocument  {
	private static final long serialVersionUID = 1L;
    private Style _defaultStyle;
    private Style _cwStyle;
    private Style _commentStyle;
    
	public PerlTokenMarker(Style defaultStyle) {
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
         return(word.toUpperCase().trim().equals("my") || 
                        word.toUpperCase().trim().equals("local") ||
                        word.toUpperCase().trim().equals("new") ||
                        word.toUpperCase().trim().equals("if") ||
                        word.toUpperCase().trim().equals("until") ||
                        word.toUpperCase().trim().equals("while") ||
                        word.toUpperCase().trim().equals("elsif") ||
                        word.toUpperCase().trim().equals("else") ||
                        word.toUpperCase().trim().equals("eval") ||
                        word.toUpperCase().trim().equals("unless") ||
                        word.toUpperCase().trim().equals("foreach") ||
                        word.toUpperCase().trim().equals("continue") ||
                        word.toUpperCase().trim().equals("exit") ||
                        word.toUpperCase().trim().equals("die") ||
                        word.toUpperCase().trim().equals("last") ||
                        word.toUpperCase().trim().equals("goto") ||
                        word.toUpperCase().trim().equals("next") ||
                        word.toUpperCase().trim().equals("redo") ||
                        word.toUpperCase().trim().equals("goto") ||
                        word.toUpperCase().trim().equals("return") ||
                        word.toUpperCase().trim().equals("do") ||
                        word.toUpperCase().trim().equals("sub") ||
                        word.toUpperCase().trim().equals("use") ||
                        word.toUpperCase().trim().equals("require") ||
                        word.toUpperCase().trim().equals("package") ||
                        word.toUpperCase().trim().equals("BEGIN") ||
                        word.toUpperCase().trim().equals("END") ||
                        word.toUpperCase().trim().equals("eq") ||
                        word.toUpperCase().trim().equals("ne") ||
                        word.toUpperCase().trim().equals("not") ||
                        word.toUpperCase().trim().equals("and") ||
                        word.toUpperCase().trim().equals("or") ||
                        word.toUpperCase().trim().equals("abs") ||
                        word.toUpperCase().trim().equals("accept") ||
                        word.toUpperCase().trim().equals("alarm") ||
                        word.toUpperCase().trim().equals("atan2") ||
                        word.toUpperCase().trim().equals("bind") ||
                        word.toUpperCase().trim().equals("binmode") ||
                        word.toUpperCase().trim().equals("bless") ||
                        word.toUpperCase().trim().equals("caller") ||
                        word.toUpperCase().trim().equals("chdir") ||
                        word.toUpperCase().trim().equals("chmod") ||
                        word.toUpperCase().trim().equals("chr") ||
                        word.toUpperCase().trim().equals("chroot") ||
                        word.toUpperCase().trim().equals("chown") ||
                        word.toUpperCase().trim().equals("closedir") ||
                        word.toUpperCase().trim().equals("close") ||
                        word.toUpperCase().trim().equals("connenct") ||
                        word.toUpperCase().trim().equals("cos") ||
                        word.toUpperCase().trim().equals("crypt") ||
                        word.toUpperCase().trim().equals("dbmclose") ||
                        word.toUpperCase().trim().equals("dbmopen") ||
                        word.toUpperCase().trim().equals("defined") ||
                        word.toUpperCase().trim().equals("delete") ||
                        word.toUpperCase().trim().equals("die") ||
                        word.toUpperCase().trim().equals("dump") ||
                        word.toUpperCase().trim().equals("each") ||
                        word.toUpperCase().trim().equals("endgrent") ||
                        word.toUpperCase().trim().equals("endhostent") ||
                        word.toUpperCase().trim().equals("endnettent") ||
                        word.toUpperCase().trim().equals("endprotoent") ||
                        word.toUpperCase().trim().equals("endpwent") ||
                        word.toUpperCase().trim().equals("endservent") ||
                        word.toUpperCase().trim().equals("eof") ||
                        word.toUpperCase().trim().equals("exec") ||
                        word.toUpperCase().trim().equals("exists") ||
                        word.toUpperCase().trim().equals("exp") ||
                        word.toUpperCase().trim().equals("fctnl") ||
                        word.toUpperCase().trim().equals("fileno") ||
                        word.toUpperCase().trim().equals("flock") ||
                        word.toUpperCase().trim().equals("fork") ||
                        word.toUpperCase().trim().equals("format") ||
                        word.toUpperCase().trim().equals("formline") ||
                        word.toUpperCase().trim().equals("getc") ||
                        word.toUpperCase().trim().equals("getgrent") ||
                        word.toUpperCase().trim().equals("getgrgid") ||
                        word.toUpperCase().trim().equals("getgrnam") ||
                        word.toUpperCase().trim().equals("gethostbyaddr") ||
                        word.toUpperCase().trim().equals("gethostbyname") ||
                        word.toUpperCase().trim().equals("gethostent") ||
                        word.toUpperCase().trim().equals("getlogin") ||
                        word.toUpperCase().trim().equals("getnetbyaddr") ||
                        word.toUpperCase().trim().equals("getnetbyname") ||
                        word.toUpperCase().trim().equals("getnetent") ||
                        word.toUpperCase().trim().equals("getpeername") ||
                        word.toUpperCase().trim().equals("getpgrp") ||
                        word.toUpperCase().trim().equals("getppid") ||
                        word.toUpperCase().trim().equals("getpriority") ||
                        word.toUpperCase().trim().equals("getprotobyname") ||
                        word.toUpperCase().trim().equals("getprotobynumber") ||
                        word.toUpperCase().trim().equals("getprotoent") ||
                        word.toUpperCase().trim().equals("getpwent") ||
                        word.toUpperCase().trim().equals("getpwnam") ||
                        word.toUpperCase().trim().equals("getpwuid") ||
                        word.toUpperCase().trim().equals("getservbyname") ||
                        word.toUpperCase().trim().equals("getservbyport") ||
                        word.toUpperCase().trim().equals("getservent") ||
                        word.toUpperCase().trim().equals("getsockname") ||
                        word.toUpperCase().trim().equals("getsockopt") ||
                        word.toUpperCase().trim().equals("glob") ||
                        word.toUpperCase().trim().equals("gmtime") ||
                        word.toUpperCase().trim().equals("grep") ||
                        word.toUpperCase().trim().equals("hex") ||
                        word.toUpperCase().trim().equals("import") ||
                        word.toUpperCase().trim().equals("index") ||
                        word.toUpperCase().trim().equals("int") ||
                        word.toUpperCase().trim().equals("ioctl") ||
                        word.toUpperCase().trim().equals("join") ||
                        word.toUpperCase().trim().equals("keys") ||
                        word.toUpperCase().trim().equals("kill") ||
                        word.toUpperCase().trim().equals("lcfirst") ||
                        word.toUpperCase().trim().equals("lc") ||
                        word.toUpperCase().trim().equals("length") ||
                        word.toUpperCase().trim().equals("link") ||
                        word.toUpperCase().trim().equals("listen") ||
                        word.toUpperCase().trim().equals("log") ||
                        word.toUpperCase().trim().equals("localtime") ||
                        word.toUpperCase().trim().equals("lstat") ||
                        word.toUpperCase().trim().equals("map") ||
                        word.toUpperCase().trim().equals("mkdir") ||
                        word.toUpperCase().trim().equals("msgctl") ||
                        word.toUpperCase().trim().equals("msgget") ||
                        word.toUpperCase().trim().equals("msgrcv") ||
                        word.toUpperCase().trim().equals("no") ||
                        word.toUpperCase().trim().equals("oct") ||
                        word.toUpperCase().trim().equals("opendir") ||
                        word.toUpperCase().trim().equals("open") ||
                        word.toUpperCase().trim().equals("ord") ||
                        word.toUpperCase().trim().equals("pack") ||
                        word.toUpperCase().trim().equals("pipe") ||
                        word.toUpperCase().trim().equals("pop") ||
                        word.toUpperCase().trim().equals("pos") ||
                        word.toUpperCase().trim().equals("printf") ||
                        word.toUpperCase().trim().equals("print") ||
                        word.toUpperCase().trim().equals("push") ||
                        word.toUpperCase().trim().equals("quotemeta") ||
                        word.toUpperCase().trim().equals("rand") ||
                        word.toUpperCase().trim().equals("readdir") ||
                        word.toUpperCase().trim().equals("read") ||
                        word.toUpperCase().trim().equals("readlink") ||
                        word.toUpperCase().trim().equals("recv") ||
                        word.toUpperCase().trim().equals("ref") ||
                        word.toUpperCase().trim().equals("rename") ||
                        word.toUpperCase().trim().equals("reset") ||
                        word.toUpperCase().trim().equals("reverse") ||
                        word.toUpperCase().trim().equals("rewinddir") ||
                        word.toUpperCase().trim().equals("rindex") ||
                        word.toUpperCase().trim().equals("rmdir") ||
                        word.toUpperCase().trim().equals("scalar") ||
                        word.toUpperCase().trim().equals("seekdir") ||
                        word.toUpperCase().trim().equals("seek") ||
                        word.toUpperCase().trim().equals("select") ||
                        word.toUpperCase().trim().equals("semctl") ||
                        word.toUpperCase().trim().equals("semget") ||
                        word.toUpperCase().trim().equals("semop") ||
                        word.toUpperCase().trim().equals("send") ||
                        word.toUpperCase().trim().equals("setgrent") ||
                        word.toUpperCase().trim().equals("sethostent") ||
                        word.toUpperCase().trim().equals("setpgrp") ||
                        word.toUpperCase().trim().equals("setpriority") ||
                        word.toUpperCase().trim().equals("setprotoent") ||
                        word.toUpperCase().trim().equals("setpwent") ||
                        word.toUpperCase().trim().equals("setsockopt") ||
                        word.toUpperCase().trim().equals("shift") ||
                        word.toUpperCase().trim().equals("shmctl") ||
                        word.toUpperCase().trim().equals("shmget") ||
                        word.toUpperCase().trim().equals("shmread") ||
                        word.toUpperCase().trim().equals("shmwrite") ||
                        word.toUpperCase().trim().equals("shutdown") ||
                        word.toUpperCase().trim().equals("sin") ||
                        word.toUpperCase().trim().equals("sleep") ||
                        word.toUpperCase().trim().equals("socket") ||
                        word.toUpperCase().trim().equals("socketpair") ||
                        word.toUpperCase().trim().equals("sort") ||
                        word.toUpperCase().trim().equals("splice") ||
                        word.toUpperCase().trim().equals("split") ||
                        word.toUpperCase().trim().equals("sprintf") ||
                        word.toUpperCase().trim().equals("sqrt") ||
                        word.toUpperCase().trim().equals("srand") ||
                        word.toUpperCase().trim().equals("stat") ||
                        word.toUpperCase().trim().equals("study") ||
                        word.toUpperCase().trim().equals("substr") ||
                        
                        word.toUpperCase().trim().equals(""));
    }
}

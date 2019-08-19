package io.renren.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SafeHtml {
    public String html = "";
    public SafeHtml() {}

    public SafeHtml(String html) {
        this.setHtml(html);
    }
    private static String GetKeyString() {
        return "反.{0,3}共|共.{0,3}匪|法.{0,3}轮|天.{0,3}安.{0,3}门.{0,3}事.{0,3}件";
    }
    public void setHtml(String html) {
    	html = html.replaceAll("\r\n|\n", " ");
        html = html.replaceAll(GetKeyString(), "**");
        this.html = html;

    }
    public void setHtml1(String html) {
    	html = html.replaceAll("\r\n|\n|<br/>|<br>","/br/");
    	html = html.replaceAll("\\s| |&nbsp;", "&nbsp;");
    	html = html.replaceAll(GetKeyString(), "*");
    	this.html = html;
    }
    public void setHtml2(String html) {
        this.html = html.replaceAll("\\s| |&nbsp;", "　");
    }
    public void cleanComment() {
        html = replaceAll(html, "<\\!--(.*?)-->", "");
    }
    public void setSpace() {
        html = replaceAll(html, "/nbsp/", "　");
    }
    public void setBr() {
        html = replaceAll(html, "/br/", "<br/>");
    }

    public void cleanTags(String[] taglist) {
        if (taglist == null) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < taglist.length; i++) {
            sb.append("(<\\/" + taglist[i] + "[^>]*>)|(<" + taglist[i] +
                      "[^>]*>)");
            if (i != taglist.length - 1) {
                sb.append("|");
            }
        }
        html = replaceAll(html, sb.toString(), "");
    }

    public void cleanElements(String[] elements) {
        if (elements == null) {
            return;
        }
        for (int i = 0; i < elements.length; i++) {
            html = replaceAll(html,"[\\s\"']{1}(" + elements[i] + "[a-zA-Z0-9]*)\\s*="," \"$1\"=");
        }
    }


    private static String replaceAll(String str, String pattern, String to) {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        StringBuffer sb = new StringBuffer();
        Matcher m = null;
        for (m = p.matcher(str); m.find(); ) {
            m.appendReplacement(sb, to);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public String toString() {
        return html;
    }

    public String convert(String str) {
    	setHtml(str);
        cleanComment();
        setBr();
        cleanTags(new String[] {
                  "html", "body", "head", "title", "form", "input", "textarea",
                  "select", "option", "script", "iframe", "frame", "frameset",
                  "meta","noframes", "noscript", "applet", "\\?", "\\%", "\\!",
                  "style", "th","table","tr","td","font","h","u","strong","em",
                  "strike","p","div","a","span","tbody"
        });
        cleanElements(new String[] {
                      "style", "class", "on", "id", "name"
        });

        return toString();
        //FileUtil.write("/testout.html", sh.toString());
        //System.out.println("end");
    }
    public String convert(String str,boolean nFull) {
    	if (nFull == false) {
    		setHtml(str);
    	} else {
    		setHtml1(str);
    	}
        cleanComment();
        setBr();
        cleanTags(new String[] {
                  "html", "body", "head", "title", "form", "input", "textarea",
                  "select", "option", "script", "iframe", "frame", "frameset",
                  "meta","noframes", "noscript", "applet", "\\?", "\\%", "\\!",
                  "style", "th","table","tr","td","font","h","u","strong","em",
                  "strike","p","div","a","span","tbody"
        });
        cleanElements(new String[] {
                      "style", "class", "on", "id", "name"
        });

        return toString();
        //FileUtil.write("/testout.html", sh.toString());
        //System.out.println("end");
    }

    public static void main(String[] args) {
        SafeHtml a = new SafeHtml();
        //a.convert("<td colspan=2><font color=\"red\"><h3><%=doc.get(\"title\")%></h3></font></td>",true);
        a.cleanComment();
        System.out.println(a.convert("<td colspan=2><font color=\"red\"><h3>xxxx<>2223hhhh\r\nhh hhhhh</h3></font></td>",true));
        String xxx = "<td colspan=2><font color=\"red\"><h3>xxxx<>2223hhhh\r\nhh hhhhh</h3></font></td>";
        xxx = xxx.replaceAll("\r\n|\n|<br/>|<br>","/br/");
        xxx = xxx.replaceAll("\\s| |&nbsp;", "&nbsp;");
        System.out.println(xxx);
    }
}

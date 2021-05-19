package test.hashmap;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import javax.swing.*;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class test0511 {

    //读取解析xml
    public static String myXmlRead() {
        SAXBuilder saxBuilder = new SAXBuilder();
        String isSame = "";
        try {
            Document doc = saxBuilder.build(new FileInputStream("D:/CloudATE2/OSN1800/SWDL/Boardlistconfig_K5.xml"));
            //获取根节点
            Element foo = doc.getRootElement();
            //获取根节点的子节点-board
            List<Element> boardList = foo.getChildren();
            for (Element element : boardList) {
                //获取板ID
                List<Attribute> boardId = element.getAttributes();
                for (Attribute attribute : boardId) {
                    if(attribute.getName().equals("id")) {
                        System.out.println(attribute.getValue());
                    }
                }
                //获取board的子节点
                List<Element> bdInfoList = element.getChildren();
                for (Element elBdinfo : bdInfoList) {
                    if (elBdinfo.getName().equals("name")) {
                        System.out.println(elBdinfo.getValue());
                    }
                    if (elBdinfo.getName().equals("author")) {
                        System.out.println(elBdinfo.getValue());
                    }
                    if (elBdinfo.getName().equals("version")) {
                        System.out.println(elBdinfo.getValue());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "112";
    }

    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        String regex = "\\d+\\s+(\\w+)\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+";
        String retValue = "12 5 12 22: 52 11\r\n" +
                "USERID:5\r\n" +
                "1800vproequip's device\r\n" +
                "1 TMB1OMS abc abc abc NA\r\n" +
                "2 TMB1OMS abc abc abc NA\r\n" +
                "3 TMB1PL1D abc abc abc NA\r\n" +
                "4 TMB1OMSSG abc abc abc NA\r\n" +
                "5 TMK1GTA abc abc abc NA\r\n" +
                "6 0x12345cf abc abc abc NA\n" +
                "8 TMB1PL1D abc abc abc NA\n" +
                "14 TMB2OMS abc abc abc NA\r\n" +
                "15 TMK5XCH abc abc abc NA\r\n";
        //截取USERID之后的字符串
        String subRetValue = retValue.substring(0, retValue.indexOf("USERID"));
        String strValue = retValue.substring(subRetValue.length()+1, retValue.length());
        Matcher matcher = Pattern.compile(regex).matcher(strValue);
        while (matcher.find()) {
            Matcher matcher1 = Pattern.compile("(\\d+)\\s+(\\w+)").matcher(matcher.group());
            while (matcher1.find()) {
                int slot = Integer.valueOf(matcher1.group(1));   //转换为槽位号
                //1800业务板槽位号：1-14
                if (slot <= 14) {
                    map.put(matcher1.group(2),slot);
                }
            }
        }
        Object[] list = map.keySet().toArray();
        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i]);
        }

        if("112" == myXmlRead()) {
            JOptionPane.showMessageDialog(null, "被测板版本不一致！", "版本不一致", JOptionPane.WARNING_MESSAGE);
        }
    }


}

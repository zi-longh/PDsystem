package jnu.temp;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.xml.parsers.SAXParser;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws DocumentException, IOException {
        ArrayList<Integer> list = new ArrayList<>();  // for-each 循环 即使是空的也不会报错
        for (Integer i : list) {
            System.out.println(i);
        }

        list.add(1);
        list.add(2);
        list.add(3);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }


    }

}

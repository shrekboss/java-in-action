package org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLUtilTV {
    //该方法用于从XML配置文件中提取品牌名称，并返回该品牌名称
    public static String getBrandName() {

        DocumentBuilderFactory dFactory;
        Document doc;
        try {
            // 创建文档对象
            dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            doc = builder.parse(new File("configA.xml"));

            //获取包含品牌名称的文本节点
            NodeList nl = doc.getElementsByTagName("brandName");
            Node classNode = nl.item(0).getFirstChild();
            String brandName = classNode.getNodeValue().trim();
            return brandName;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public static void main(String[] args) {
        // File file=new File("configTV.xml");
        // System.out.println(file.exists());
        // System.out.println(file.isDirectory());
        // System.out.println(file.getAbsolutePath().toString());

        System.out.println(XMLUtilTV.getBrandName());
    }
}

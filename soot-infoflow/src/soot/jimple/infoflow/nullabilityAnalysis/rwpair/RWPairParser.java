package soot.jimple.infoflow.nullabilityAnalysis.rwpair;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RWPairParser {

    private static String FILE_NAME = "/Users/shunsuke/college/research/FlowDroid/targets/rwpair.xml";

    private static RWPairParser INSTANCE = new RWPairParser();

    public static RWPairParser getInstance() {
        return INSTANCE;
    }

    private RWPairParser() {
        // for singleton pattern
    }

    private Document targetDocument;

    private List<Edge> edges = new ArrayList<>();

    public void parse() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            this.targetDocument = builder.parse(FILE_NAME);

            NodeList eventAndHanlderNodes = targetDocument.getElementsByTagName("Pair");

            for (int i = 0; i < eventAndHanlderNodes.getLength(); i++) {
                Node node = eventAndHanlderNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String leftMethodName = element.getAttribute("base");
                    String rightMethodName = element.getAttribute("target");

                    edges.add(new Edge(leftMethodName, rightMethodName));
                }
            }

        } catch (
                ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Edge> getEdges() {
        return edges;
    }
}

package soot.jimple.infoflow.nullabilityAnalysis.ccfg;

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

public class CCFGParser {

    private static String FILE_NAME = "/Users/shunsuke/college/research/FlowDroid/targets/ccfg.xml";

    private static CCFGParser INSTANCE = new CCFGParser();

    public static CCFGParser getInstance() {
        return INSTANCE;
    }

    private CCFGParser() {
        // for singleton pattern
    }

    private Document targetDocument;

    private List<Edge> edges = new ArrayList<>();

    public void parse() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            this.targetDocument = builder.parse(FILE_NAME);

            NodeList eventAndHanlderNodes = targetDocument.getElementsByTagName("Event");

            for (int i = 0; i < eventAndHanlderNodes.getLength(); i++) {
                Node node = eventAndHanlderNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String methodName = element.getAttribute("name");
                    String type = element.getAttribute("type");
                    String eventType = element.getAttribute("eventType");
                    String className = element.getAttribute("class");

                    edges.add(new Edge(methodName, Edge.convertToEdgeType(type), eventType, className));
                }
            }

        } catch (ParserConfigurationException e) {
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

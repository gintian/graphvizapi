package example;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.enoir.graphvizapi.*;

/**
 * Created by frank on 2014/11/20.
 */
public class apiExample {

    private static String tmpPath = "/tmp";
    public static void main(String[] args)
    {
        apiExample ex = new apiExample ();
        ex.draw();
        ex.draw2();
    }
    private void draw()
    {
        Graphviz gv = new Graphviz();
        Graph graph = new Graph("g1", GraphType.DIGRAPH);
        graph.addAttributes(new Attributes(  "rankdir","LR"));
        Node n1 = new Node("N1");
        n1.addAttributes(new Attributes("label","\" Node1 \""));
        Node n2 = new Node("N2");
        Node n3 = new Node("N3");

        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addEdge(new Edge("", n1, n2));
        graph.addEdge(new Edge("", n2, n3));
        graph.addEdge(new Edge("",n3,n1));


        String type = "png";

        File out = new File(tmpPath+"/outEX1."+ type);
        this.writeGraphToFile( gv.getGraphByteArray(graph, type, "100"), out );
    }

    public void draw2(){
        int nodeNumber = 10;
        int edageNumber = 20;
        Graphviz gv = new Graphviz();
        Graph graph = new Graph("g1", GraphType.DIGRAPH);
        Graph subgraph = new Graph("subg1", GraphType.DIGRAPH);
        List<Node> nodeList = new ArrayList<Node>();
        for(int i=0;i<nodeNumber;i++){
            Node n = new Node("N"+Integer.toString(i));
            nodeList.add(n);
            graph.addNode(n);
        }
        for(int e=0;e<edageNumber;e++){
            Random ran = new Random();
            int f = ran.nextInt(nodeNumber);
            int t = ran.nextInt(nodeNumber);
            Edge edge = new Edge("",nodeList.get(f),nodeList.get(t));
            graph.addEdge(edge);
        }
         for(int i=0;i<nodeNumber/2;i++){
            Node n = new Node("sN"+Integer.toString(i));
             n.addAttributes(new Attributes("style","filled"));
            nodeList.add(n);
            subgraph.addNode(n);
        }
        for(int e=0;e<edageNumber;e++){
            Random ran = new Random();
            int f = ran.nextInt(nodeList.size());
            int t = ran.nextInt(nodeList.size());
            Edge edge = new Edge("",nodeList.get(f),nodeList.get(t));
            subgraph.addEdge(edge);
        }
        graph.addSubgraph(subgraph);
        String type = "png";
        File out = new File(tmpPath+"/outEX2."+ type);
        this.writeGraphToFile( gv.getGraphByteArray(graph, type, "100"), out );

    }

    public int writeGraphToFile(byte[] img, File to)
    {
        try {
            FileOutputStream fos = new FileOutputStream(to);
            fos.write(img);
            fos.close();
        } catch (java.io.IOException ioe) { return -1; }
        return 1;
    }
}
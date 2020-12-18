package api;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    private static Random _rnd = null;

    @Test
    void nodeSize() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(1));

        g.removeNode(2);
        g.removeNode(1);
        g.removeNode(1);
        int s = g.nodeSize();
        assertEquals(1,s);

        directed_weighted_graph g0 = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(1));

        g.removeNode(0);
        g.removeNode(1);
        g.removeNode(1);
        int s0 = g.nodeSize();
        assertEquals(0,s0);

        directed_weighted_graph g1 = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));


        g.removeNode(11);
        g.removeNode(12);
        g.removeNode(14);
        int s1 = g.nodeSize();
        assertEquals(6,s1);

    }

    @Test
    void edgeSize() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        int e_size =  g.edgeSize();
        assertEquals(3, e_size);
        edge_data w03 = g.getEdge(0,3);
        edge_data w30 = g.getEdge(3,0);
        assertEquals(w03.getSrc(), 0);
        assertEquals(w30, null);


        directed_weighted_graph g1 = new DWGraph_DS();
        g1.addNode(new NodeData(0));
        g1.addNode(new NodeData(1));
        g1.addNode(new NodeData(2));
        g1.addNode(new NodeData(3));
        g1.connect(0,1,1);
        g1.connect(3,2,2);
        g1.connect(2,3,3);
        g1.connect(1,1,1);
        g1.connect(1,4,1);
        g1.connect(1,9,1);
        int e1_size =  g1.edgeSize();
        assertEquals(3, e_size);
        edge_data w0_3 = g1.getEdge(0,3);
        edge_data w3_0 = g1.getEdge(3,2);
        assertEquals(w0_3, null);
        assertEquals(w3_0.getSrc(), 3);
        assertEquals(w3_0.getDest(), 2);
        assertEquals(w0_3, null);
    }

    @Test
    void getV() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        Collection<node_data> v = g.getV();
        Iterator<node_data> iter = v.iterator();
        while (iter.hasNext()) {
            node_data n = iter.next();
            assertNotNull(n);
        }
    }
    @Test
    void getE(){
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        Collection<edge_data> v = g.getE(0);
        Iterator<edge_data> iter = v.iterator();
        while (iter.hasNext()) {
            edge_data n = iter.next();
            assertNotNull(n);
        }
    }



    @Test
    void connect() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeEdge(0,1);
        edge_data w3 = g.getEdge(1,0);
        assertEquals(w3, null);
        g.removeEdge(2,1);
        g.connect(0,1,1);
        edge_data w = g.getEdge(0,1);
        assertEquals(w.getDest(),1);
    }


    @Test
    void removeNode() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(5));
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeNode(4);
        g.removeNode(0);
        edge_data w = g.getEdge(0,1);
        assertEquals(w, null);
        int e = g.edgeSize();
        assertEquals(0,e);
        assertEquals(3,g.nodeSize());
    }

    @Test
    void removeEdge() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeEdge(0,3);
        edge_data w = g.getEdge(0,3);
        assertEquals(w,null);
    }


    ///////////////////////////////////
    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
        directed_weighted_graph g = new DWGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(new NodeData(i));
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();

            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(directed_weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_data> V = g.getV();
        node_data[] nodes = new node_data[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
}
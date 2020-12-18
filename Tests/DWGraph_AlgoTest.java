package api;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


class WGraph_AlgoTest {

    @Test
    void init() {
        DWGraph_DS g0 = new DWGraph_DS();
        g0.addNode(new NodeData(0));
        g0.addNode(new NodeData(1));
        g0.addNode(new NodeData(2));
        g0.addNode(new NodeData(0));
        dw_graph_algorithms g1 = new DWGraph_Algo();
        ((DWGraph_Algo) g1).init(g0);
        assertEquals(g0, ((DWGraph_Algo) g1).getGraph());


    }


    @Test
    void isConnected() {
        directed_weighted_graph g0 = WGraph_DSTest.graph_creator(0, 0, 1);
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        ((DWGraph_Algo) ag0).init(g0);
        assertTrue(ag0.isConnected());

        g0 = WGraph_DSTest.graph_creator(1, 0, 1);
        ag0 = new DWGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = WGraph_DSTest.graph_creator(2, 0, 1);
        ag0 = new DWGraph_Algo();
        ag0.init(g0);
        boolean e0 = ag0.isConnected();
        assertFalse(e0);

        g0 = WGraph_DSTest.graph_creator(2, 1, 1);
        ag0 = new DWGraph_Algo();
        ag0.init(g0);
        boolean e1 = ag0.isConnected();
        assertFalse(e1);

        g0 = WGraph_DSTest.graph_creator(10, 30, 1);
        ag0.init(g0);
        boolean e2 = ag0.isConnected();
        assertTrue(e2);

        g0 = WGraph_DSTest.graph_creator(5, 10, 1);
        ag0 = new DWGraph_Algo();
        boolean e3 = ag0.isConnected();
        assertTrue(e3);

        g0 = WGraph_DSTest.graph_creator(10, 38, 1);
        ag0 = new DWGraph_Algo();
        boolean e4 = ag0.isConnected();
        assertTrue(e4);

        g0 = WGraph_DSTest.graph_creator(2, 1, 1);
        ag0 = new DWGraph_Algo();
        boolean e5 = ag0.isConnected();
        assertTrue(e5);


    }

    @Test
    void shortestPathDist() {
        DWGraph_DS g0 = new DWGraph_DS();
        g0.addNode(new NodeData(0));
        g0.addNode(new NodeData(1));
        g0.addNode(new NodeData(2));
        g0.addNode(new NodeData(3));
        g0.addNode(new NodeData(4));
        g0.addNode(new NodeData(5));
        g0.addNode(new NodeData(6));
        g0.addNode(new NodeData(7));
        g0.addNode(new NodeData(8));
        g0.addNode(new NodeData(5));
        g0.addNode(new NodeData(6));
        g0.addNode(new NodeData(2));
        g0.connect(0, 5, 3);
        g0.connect(0, 1, 3);
        g0.connect(1, 4, 7);
        g0.connect(1, 3, 6);
        g0.connect(4, 8, 6);
        g0.connect(5, 2, 9);
        g0.connect(2, 3, 3);
        g0.connect(2, 7, 5);
        g0.connect(4, 6, 6);
        g0.connect(4, 7, 8);
        g0.connect(5, 8, 15);
        g0.connect(3, 6, 6);
        DWGraph_Algo ag0 = new DWGraph_Algo();
        ag0.init(g0);
        double x = 16;
        double i = ag0.shortestPathDist(0, 8);
        Boolean flag = null;
        if (i == x) flag = true;
        assertEquals(true, flag);
    }

    @Test
    void shortestPath() {
        DWGraph_DS g0 = new DWGraph_DS();
        g0.addNode(new NodeData(0));
        g0.addNode(new NodeData(1));
        g0.addNode(new NodeData(2));
        g0.addNode(new NodeData(3));
        g0.addNode(new NodeData(4));
        g0.addNode(new NodeData(5));
        g0.addNode(new NodeData(6));
        g0.addNode(new NodeData(7));
        g0.addNode(new NodeData(8));
        g0.addNode(new NodeData(5));
        g0.addNode(new NodeData(6));
        g0.addNode(new NodeData(2));
        g0.connect(0, 5, 3);
        g0.connect(0, 1, 3);
        g0.connect(1, 4, 7);
        g0.connect(1, 3, 6);
        g0.connect(4, 8, 6);
        g0.connect(5, 2, 9);
        g0.connect(2, 3, 3);
        g0.connect(2, 7, 5);
        g0.connect(4, 6, 6);
        g0.connect(4, 7, 8);
        g0.connect(5, 8, 15);
        g0.connect(3, 6, 6);
        DWGraph_Algo ag0 = new DWGraph_Algo();
        ag0.init(g0);
        ArrayList<node_data> list = (ArrayList<node_data>) ag0.shortestPath(0, 8);
        assertEquals(list.get(0).getKey(), g0.getNode(0).getKey());
        assertEquals(list.get(1).getKey(), g0.getNode(1).getKey());
        assertEquals(list.get(2).getKey(), g0.getNode(4).getKey());
        assertEquals(list.get(3).getKey(), g0.getNode(8).getKey());


    }

    @Test
    void save_load() throws IOException {
        DWGraph_DS g0 = new DWGraph_DS();
        g0.addNode(new NodeData(0));
        g0.addNode(new NodeData(1));
        g0.addNode(new NodeData(2));
        g0.addNode(new NodeData(3));
        g0.addNode(new NodeData(4));
        g0.addNode(new NodeData(5));
        g0.addNode(new NodeData(6));
        g0.addNode(new NodeData(7));
        g0.addNode(new NodeData(8));
        g0.addNode(new NodeData(5));
        g0.addNode(new NodeData(6));
        g0.addNode(new NodeData(2));
        g0.connect(0, 5, 3);
        g0.connect(0, 1, 3);
        g0.connect(1, 4, 7);
        g0.connect(1, 3, 6);
        g0.connect(4, 8, 6);
        g0.connect(5, 2, 9);
        g0.connect(2, 3, 3);
        g0.connect(2, 7, 5);
        g0.connect(4, 6, 6);
        g0.connect(4, 7, 8);
        g0.connect(5, 8, 6);
        g0.connect(3, 6, 6);
        DWGraph_Algo ag0 = new DWGraph_Algo();
        ag0.init(g0);
        ag0.save("st");
        DWGraph_Algo k = new DWGraph_Algo();
        k.load("st");
        assertEquals(ag0.getGraph().edgeSize(), k.getGraph().edgeSize());
        assertEquals(ag0.getGraph().nodeSize(), k.getGraph().nodeSize());
        DWGraph_Algo g1 = new DWGraph_Algo();
        String str = "1";
        g1.save(str);
        System.out.println(str);


    }

}
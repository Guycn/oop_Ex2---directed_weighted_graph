package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph, Serializable {
    private HashMap<Integer, HashMap<Integer, edge_data>> Edges = new HashMap<>();
    private HashMap<Integer, node_data> Nodes = new HashMap<>();
    private int N_size=0;
    private int E_size=0;
    private int MC=0;

    public DWGraph_DS() {
        this.Nodes = new HashMap<>(1000000);
        this.Edges = new HashMap<>(20000000);


    }

    public DWGraph_DS(DWGraph_DS graph){
        this.Nodes = graph.Nodes;
        this.Edges = graph.Edges;
        this.N_size = graph.N_size;
        this.E_size = graph.E_size;
        this.MC = graph.MC;
    }
    public DWGraph_DS(ArrayList<NodeData> Nodes, ArrayList<Edge_Node> Edges){
        for(NodeData curr0 : Nodes){
            this.addNode(curr0);
        }
        for(Edge_Node curr1 : Edges){
            this.connect(curr1.getSrc(), curr1.getDest(),curr1.getWeight());
        }
        N_size = Nodes.size();
        E_size = Edges.size();
    }

    @Override
    public node_data getNode(int key){
        if(this.Nodes.containsKey(key)){
            return this.Nodes.get(key);
        }
        return null;
    }
    @Override
    public edge_data getEdge(int src, int dest){
        if(this.Edges.containsKey(src)){
            if(this.Edges.get(src).containsKey(dest)){
                return this.Edges.get(src).get(dest);
            }
        }
        return null;
    }
    @Override
    public void addNode(node_data n){
        if(!this.Nodes.containsKey(n.getKey())) {
            this.Nodes.put(n.getKey(), n);
            this.N_size++;
            this.MC++;
        }
    }
    @Override
    public void connect(int src, int dest, double w){
        if(this.getNode(src)!=null && this.getNode(dest)!=null){
            if(this.getEdge(src,dest)==null){
                Edge_Node t = new Edge_Node(src,dest,w);
                if(this.Edges.containsKey(src)){
                    this.Edges.get(src).put(dest,t);
                }
                else{
                    this.Edges.put(src,new HashMap<Integer, edge_data>());
                    this.Edges.get(src).put(dest,t);
                }
                this.E_size++;
                this.MC++;
            }
        }
      /*  else{
            throw new NullPointerException("Error: This map does not contains the nodes");
        }*/
    }
    @Override
    public Collection<node_data> getV(){
        return this.Nodes.values();
    }
    @Override
    public Collection<edge_data> getE(int node_id){
        if(this.Edges.containsKey(node_id)){
            return this.Edges.get(node_id).values();
        }
        return null;
    }
    @Override
    public node_data removeNode(int key){
        if(this.Nodes.containsKey(key)){
            NodeData copy = new NodeData((NodeData) this.Nodes.get(key));
            this.Nodes.remove(key);
            if(this.Edges.get(key)!=null){
                E_size = E_size - this.Edges.get(key).size();
            }
            this.Edges.remove(key);
            this.MC++;
            this.N_size--;
            for(node_data curr:this.getV()){
                if(this.getEdge(curr.getKey(), key)!=null){
                    this.Edges.get(curr.getKey()).remove(key);
                    E_size--;
                }
            }
            return copy;
        }
        return null;
    }
    @Override
    public edge_data removeEdge(int src, int dest){
        if(getEdge(src,dest)==null)return null;
        Edge_Node cpy = new Edge_Node((Edge_Node) this.Edges.get(src).get(dest));
        this.Edges.get(src).remove(dest);
        this.MC++;
        this.E_size--;
        return cpy;
    }
    @Override
    public int nodeSize(){
        return this.N_size;
    }
    @Override
    public int edgeSize(){
        return E_size;
    }
    @Override
    public int getMC(){
     return this.MC;
    }


}

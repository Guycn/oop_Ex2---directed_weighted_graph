package api;

import java.io.Serializable;

public class Edge_Node implements edge_data, Serializable {
    private int src;
    private int dest;
    private double weight;
    private int Tag;
    private String Info;


    public Edge_Node(){
        this.src = 0;
        this.dest = 0;
        this.weight = 0;
        this.Tag = 0;
        this.Info = null;
    }

    public Edge_Node(Edge_Node t){
        this.src = t.src;
        this.dest = t.dest;
        this.weight = t.weight;
        this.Tag = t.Tag;
        this.Info = t.Info;
    }

    public Edge_Node(int src, int dest, double weight, int Tag, String Info){
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.Tag = Tag;
        this.Info = Info;
    }

    public Edge_Node(int src, int dest, double weight){
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public Edge_Node(int src, int dest){
        this.src = src;
        this.dest = dest;
        this.weight = 0;
        this.Tag = 0;
        this.Info = null;
    }
    @Override
    public int getSrc(){
        return this.src;
    }
    @Override
    public int getDest(){
        return this.dest;
    }
    @Override
    public double getWeight(){
        return this.weight;
    }
    @Override
    public String getInfo(){
        return this.Info;
    }
    @Override
    public void setInfo(String s){
        this.Info = s;
    }
    @Override
    public int getTag(){
        return this.Tag;
    }
    @Override
    public void setTag(int t){
        this.Tag=t;
    }




}

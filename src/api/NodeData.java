package api;

import java.io.Serializable;

public class NodeData implements node_data, Serializable {
    private int Key;
    private double weight;
    private geo_location pos;
    private int Tag;
    private String Info;

    public NodeData(NodeData t){
        this.Key = t.Key;
        this.weight = t.weight;
        this.pos = t.pos;
        this.Tag = t.Tag;
        this.Info = t.Info;

    }

    public NodeData(int key){
        this.Key = key;
        this.weight = 0;
        this.pos = new GLocation();
        this.Tag = 0;
        this.Info=null;
    }
    public NodeData(int key,geo_location p){
        this.Key = key;
        this.weight = 0;
        this.pos = new GLocation(p);
        this.Tag = 0;
        this.Info = null;
    }
    public NodeData(int key, double weight, geo_location p){
        this.Key = key;
        this.weight = weight;
        this.pos = new GLocation(p);
        this.Tag = 0;
        this.Info = null;
    }
    @Override
    public int getKey(){
        return this.Key;
    }
    @Override
    public geo_location getLocation(){
        return this.pos;
    }
    @Override
    public void setLocation(geo_location p){
        this.pos = p;
    }
    @Override
    public double getWeight(){
        return this.weight;
    }
    @Override
    public void setWeight(double w){
        this.weight = w;
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
        this.Tag = t;
    }
}

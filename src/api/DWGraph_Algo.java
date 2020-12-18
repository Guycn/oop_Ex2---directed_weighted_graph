package api;

import com.google.gson.*;
import gameClient.util.Point3D;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms, Serializable {
    private directed_weighted_graph graph = new DWGraph_DS();

    @Override
    public void init(directed_weighted_graph g) {
        this.graph = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph g_Copy = new DWGraph_DS();
        for (node_data curr : this.graph.getV()) {
            NodeData t = new NodeData((NodeData) curr);
            g_Copy.addNode(t);
        }

        for (node_data curr0 : this.graph.getV()) {
            if (this.graph.getE(curr0.getKey()) != null) {
                for (edge_data curr1 : this.graph.getE(curr0.getKey())) {
                    edge_data ed = new Edge_Node((Edge_Node) curr1);
                    g_Copy.connect(ed.getSrc(), ed.getDest(), ed.getWeight());

                }
            }
        }
        return g_Copy;
    }

    @Override
    public boolean isConnected() {
        if (this.graph.nodeSize() < 2) return true;
        directed_weighted_graph g_copy = copy();
        for (node_data curr : g_copy.getV()) {
            for (node_data curr0 : g_copy.getV()) {
                curr0.setTag(0);
            }
            tag_Set(g_copy, curr.getKey());
            for (node_data check : g_copy.getV()) {
                if (check.getTag() == 0) return false;
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        directed_weighted_graph G_copy = copy();
        for (node_data curr : G_copy.getV()) {
            curr.setWeight(-1);
            curr.setTag(0);
        }
        G_copy.getNode(src).setWeight(0);
        short_way(G_copy, src);
        return G_copy.getNode(dest).getWeight();

    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        directed_weighted_graph g_cpy = copy();
        for (node_data curr : g_cpy.getV()) {
            curr.setWeight(-1);
            curr.setTag(0);
        }
        g_cpy.getNode(src).setWeight(0);
        int j=dest;
        short_way(g_cpy, src);
        double i = shortestPathDist(src,dest);
        ArrayList<node_data> Rlist = new ArrayList<>();
        ArrayList<node_data> Llist = new ArrayList<>();
        Rlist.clear();
        Llist.clear();
        Rlist.add(g_cpy.getNode(j));
        while(i!=0){
            i = g_cpy.getNode(g_cpy.getNode(j).getTag()).getWeight();
            j = g_cpy.getNode(j).getTag();
            Rlist.add(g_cpy.getNode(j));
        }
        for (int t = (Rlist.size() - 1); t >= 0; t--) {
            Llist.add(Rlist.get(t));
        }
        return Llist;
    }

    @Override
    public boolean save(String file) {

        try {

            Writer w = new FileWriter(file);
            Gson gson = new Gson();

            JsonObject obj = new JsonObject();
            JsonArray e_Array = new JsonArray();
            JsonArray gArrayjson = new JsonArray();

            for (node_data nodeData : graph.getV()) {

                JsonObject node_Datajson = new JsonObject();

                double _x = nodeData.getLocation().x();
                double _y = nodeData.getLocation().y();
                double _z = nodeData.getLocation().z();

                String location = _x + "," + _y + "," + _z;

                node_Datajson.addProperty("pos", location);
                node_Datajson.addProperty("id", nodeData.getKey());

                gArrayjson.add(node_Datajson);

                if(this.graph.getE(nodeData.getKey())!=null){

                for (edge_data edgeData : graph.getE(nodeData.getKey())) {
                        JsonObject jsonEdgeData = new JsonObject();

                        jsonEdgeData.addProperty("src", edgeData.getSrc());
                        jsonEdgeData.addProperty("w", edgeData.getWeight());
                        jsonEdgeData.addProperty("dest", edgeData.getDest());

                        e_Array.add(jsonEdgeData);

                    }
                }
            }

            obj.add("Edges", e_Array);
            obj.add("Nodes", gArrayjson);


            String JsonGraph = obj.toString();
            w.write(JsonGraph);
            w.flush();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }




    }


    @Override
    public boolean load(String file) {
        boolean result = false;
        JsonDeserializer<DWGraph_DS> deserializer = new JsonDeserializer<DWGraph_DS>() {
            @Override
            public DWGraph_DS deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray edgesArr = jsonObject.get("Edges").getAsJsonArray();
                ArrayList<Edge_Node> EList = new ArrayList<>();

                for (JsonElement je : edgesArr){
                    int src = je.getAsJsonObject().get("src").getAsInt();
                    double w = je.getAsJsonObject().get("w").getAsDouble();
                    int dest = je.getAsJsonObject().get("dest").getAsInt();
                    Edge_Node newEdge = new Edge_Node(src,dest,w);
                    EList.add(newEdge);
                }

                JsonArray nodesArr = jsonObject.get("Nodes").getAsJsonArray();
                ArrayList<NodeData> NList = new ArrayList<>();

                for (JsonElement je : nodesArr){
                    int id = je.getAsJsonObject().get("id").getAsInt();
                    String pos_str = je.getAsJsonObject().get("pos").getAsString();
                    String[] arr = pos_str.split(",");
                    Point3D pos = new Point3D(arr[0],arr[1],arr[2]);
                    NodeData newNode = new NodeData(id,pos);
                    NList.add(newNode);
                }
                return new DWGraph_DS(NList, EList);
            }
        };
        int check=0;
        String str = null;
        try{
            Reader reader = new FileReader(file);
            Path file1 = Paths.get(file);
            str = new String(Files.readAllBytes(file1));
            check=1;
        } catch (FileNotFoundException e) {
            ;
        } catch (IOException e) {
            ;
        }


        try {
            GsonBuilder Gbuild = new GsonBuilder();
            Gbuild.registerTypeAdapter(DWGraph_DS.class, deserializer);
            Gson gson = Gbuild.create();
            if(check==0){
                DWGraph_DS g_Load = gson.fromJson(file, DWGraph_DS.class);
                this.init(g_Load);
                System.out.println(g_Load);
            }
            else{
                DWGraph_DS g_Load = gson.fromJson(str, DWGraph_DS.class);
                this.init(g_Load);
                System.out.println(g_Load);
            }

            result = true;
        }
        catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return result;
    }



    void tag_Set(directed_weighted_graph g, int key) {
        g.getNode(key).setTag(1);
        if (g.getE(key) == null) return;
        for (edge_data curr : g.getE(key)) {
            if (g.getNode(curr.getDest()).getTag() == 0) {
                tag_Set(g, curr.getDest());
            }
        }

    }

    void short_way(directed_weighted_graph g, int key) {
        if (g.getE(key) == null) return;
        for (edge_data curr : g.getE(key)) {
            if(g.getNode(curr.getDest()).getWeight()==-1 || g.getNode(curr.getDest()).getWeight() > (g.getNode(key).getWeight() + curr.getWeight())){
                if(g.getNode(curr.getDest()).getWeight()==-1)g.getNode(curr.getDest()).setWeight(0);
                g.getNode(curr.getDest()).setWeight(g.getNode(key).getWeight()+curr.getWeight());
                g.getNode(curr.getDest()).setTag(key);
                short_way(g,curr.getDest());
            }
        }
    }
}

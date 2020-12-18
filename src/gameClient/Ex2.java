package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ex2 implements Runnable{
    private static MyGameFrame _Win;
    private static Arena _Ar;
    public static void main(String[] a) {
        Thread client = new Thread(new Ex2());
        client.start();
    }

    @Override
    public void run() {
        int scenario_num = 15;
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] levels
        //	int id = 999;
        //	game.login(id);
        String graph = game.getGraph();
        DWGraph_Algo Algo_Graph = new DWGraph_Algo();
        Algo_Graph.load(graph);
        init(game, Algo_Graph);
        game.startGame();
        _Win.setTitle("Ex2 - OOP: "+game.toString());
        int ind=0;
        long dt=60;
        List<CL_Pokemon> targetedPokemons = new ArrayList<>();
        while(game.isRunning()) {
            moveAgents(game, Algo_Graph.getGraph(), Algo_Graph, targetedPokemons);
            try {
                if(ind%1.5==0) {
                    _Win.repaint();
                }
                Thread.sleep(dt);
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }
    private void moveAgents(game_service game, directed_weighted_graph graph, dw_graph_algorithms ga, List<CL_Pokemon> targetedPokemons) {
        String updatedGraph = game.move();
        System.out.println(updatedGraph);
        List<CL_Agent> AList = Arena.getAgents(updatedGraph, graph);
        _Ar.setAgents(AList);
        String pokemons = game.getPokemons();
        List<CL_Pokemon> PList = Arena.json2Pokemons(pokemons);
        _Ar.setPokemons(PList);
        System.out.println(PList.toString());


        for (CL_Agent currA : AList) {
            if (currA.getNextNode() == -1) {
                CL_Pokemon target = getNearestPokemon(currA, ga, targetedPokemons, PList);
                int src_p = getPokemonSrc(target,graph);
                int pokemon_dest = getPokemonDest(target, graph);
                int new_Dest = nextNode(currA,src_p,pokemon_dest, ga);
                game.chooseNextEdge(currA.getID(), new_Dest);
                int agent_ID = currA.getID();
                double agent_Value = currA.getValue();
                int agent_Src = currA.getSrcNode();
                System.out.println("Agent: " + agent_ID + ", val: " + agent_Value + " is moving from node " + agent_Src + " to node: " + new_Dest);
            }
        }
    }

    private static int nextNode(CL_Agent agent, int src_p ,int dest,dw_graph_algorithms ga){
        int src = agent.getSrcNode();
        if(src_p!=src){
            return  ga.shortestPath(src,src_p).get(1).getKey();
        }
        return ga.shortestPath(src,dest).get(1).getKey();
    }

    private void init(game_service game, dw_graph_algorithms graph){
        String pokemons = game.getPokemons();
        _Ar = new Arena();
        _Ar.setGraph(graph.getGraph());
        List<CL_Pokemon> p_List = _Ar.json2Pokemons(pokemons);
        _Ar.setPokemons(_Ar.json2Pokemons(pokemons));
        _Win = new MyGameFrame("OOP Ex2");
        _Win.setSize(1000, 700);
        _Win.update(_Ar);
        _Win.show();
        String info = game.toString();
        JSONObject line;

        try {
            line = new JSONObject(info);
            JSONObject obj = line.getJSONObject("GameServer");
            int agents_num = obj.getInt("agents");
            ArrayList<node_data> ag = new ArrayList();
            for (int i = 0; i < p_List.size(); i++) {
                if (i + 1 > p_List.size()) {
                    i = agents_num;
                }
                CL_Pokemon currentPokemon = p_List.get(i);
                int p_Src = getPokemonSrc(currentPokemon, graph.getGraph());
                game.addAgent(p_Src);
            }


            System.out.println(game.getAgents());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private static int getPokemonSrc(CL_Pokemon currP, directed_weighted_graph graph) {

        Arena.updateEdge(currP, graph);
        return currP.get_edge().getSrc();
    }


    private static int getPokemonDest(CL_Pokemon currP, directed_weighted_graph graph) {

        Arena.updateEdge(currP, graph);
        return currP.get_edge().getDest();
    }

    private static CL_Pokemon getNearestPokemon(CL_Agent currA, dw_graph_algorithms ga, List<CL_Pokemon> targetedPokemons, List<CL_Pokemon> PList) {
        int srcNode = currA.getSrcNode();
        CL_Pokemon result = null;
        double distance, ch = 0;
        for (CL_Pokemon currP : PList) {
            if (!targetedPokemons.contains(currP) && currP.getMin_ro()!=-183) {
                int pokemonDest = getPokemonDest(currP, ga.getGraph());
                distance = ga.shortestPathDist(srcNode, pokemonDest);
                if (distance > -1) {
                    double score = currP.getValue()/distance;
                    if (score > ch) {
                        ch = score;
                        result = currP;
                    }
                }
            }
        }
        result.setMin_ro(-183);
        targetedPokemons.add(result);
        return result;
    }

}
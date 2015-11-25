package org.unimi.tsc.validator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLWriter;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;

public class graphSTS {
	private HashMap<Vertex,ArrayList<Edge>> graphI;
	private Vertex root;
	private Graph gr;
	private Paths allPaths;
	private ArrayList<ArrayList<Integer>> permutation; 
	private int lastPermutation;


	public HashMap<Vertex, ArrayList<Edge>> getGraphI() {
		return graphI;
	}

	public ArrayList<ArrayList<Vertex>> getGraphI(int i) {
		if(i==-1){
			return this.allPaths.getAllPaths();
		}
		ArrayList<Integer> graph=this.permutation.get(i);
		ArrayList<ArrayList<Vertex>> result=new ArrayList<ArrayList<Vertex>>();
		int size=this.allPaths.getAllPaths().size();
		for(int k=0;k<size;k++){
			result.add(this.allPaths.getAllPaths().get(graph.get(k)));
		}


		return result;
	}



	public Vertex getRoot() {
		return root;
	}




	public graphSTS(String gi,String root) throws IOException{
		Graph graph = new TinkerGraph();
		GraphMLReader reader = new GraphMLReader(graph);
		this.graphI=new HashMap<Vertex, ArrayList<Edge>>(); 
		InputStream is = new BufferedInputStream(new FileInputStream(gi));
		reader.inputGraph(is);


		// System.out.println("Vertices of " + graph);
		for (Vertex v : graph.getVertices()) {
			this.graphI.put(v, new ArrayList<Edge>());
			if(v.getId().toString().equalsIgnoreCase(root)){
				this.root=v;
			}

			/* System.out.println(vertex);
	      for(String mechanism:vertex.getPropertyKeys()){
	    	  System.out.println(mechanism+" value:"+vertex.getProperty(mechanism));

	      }*/
		}
		// System.out.println("Edges of " + graph);
		for (Edge edge : graph.getEdges()) {
			ArrayList<Edge> app=this.graphI.get(edge.getVertex(Direction.OUT));
			app.add(edge);
			//System.out.println(edge);
			//System.out.print(edge.getVertex(Direction.OUT));
			//System.out.println(edge.getVertex(Direction.IN));

		}
		//System.out.println("RESULT:");
		//System.out.println("\n ROOT="+this.root);
		/*for(Entry<Vertex,ArrayList<Edge>> elem:this.graphI.entrySet()){
	    	 //System.out.println("vertice:"+elem.getKey());
	    	 for(Edge ed:elem.getValue()){
	    		 //System.out.println("edge to:"+ed.getVertex(Direction.IN));
	    	 }
	     }*/
		GremlinPipeline pipe = new GremlinPipeline();
		// GremlinPipeline pl=new  GremlinPipeline();
		//pipe.start(graph.getVertex(root)).bothV().loop(1,);     
		//.out("knows").property("name");
		//graph.v(A).both.loop(1){it.loops<=N && !(it.object.id in [A,B])}.filter{it.id==B}.path;





		setAllPath();

		int[] num = new int[this.allPaths.getAllPaths().size()];
		//int i=0;
		ArrayList<ArrayList<Vertex>> allp=this.allPaths.getAllPaths();
		for(int i=0;i<allp.size();i++){
			num[i]=i;
		}
		Permute permuter=new Permute();
		this.permutation= permuter.permute(num);
		System.out.println("Permutations available:"+permutation.size());//+" -->"+permutation);	
		ArrayList<ArrayList<Vertex>> printer = this.getGraphI(-1);
		System.out.println("Path Avaiable:"+printer);

	}

	public void setAllPath(){
		//System.out.println("SHOW ALL PATHS from root "+ this.root);


		this.allPaths=getNext(this.root);
		//System.out.println("first DEEP done");


	}

	public Paths getNext(Vertex v){
		ArrayList<Vertex> vs=getVertexsfromVertex(v);
		if(vs.size()!=0){
			Paths newP=new Paths();
			for(Vertex newV:vs){
				Paths p=getNext(newV);
				newP.putOnTop(p,v);
				//Paths newP=Paths.factoryPaths(p, newV);

			}
			return newP;
		}
		if(vs.size()==0){
			ArrayList<Vertex> path=new ArrayList<Vertex>();
			path.add(v);
			Paths p=new Paths();
			p.addNewPath(path);
			return p;
		}
		return null;

	}




	private ArrayList<Vertex> getVertexsfromVertex(Vertex v) {
		// TODO Auto-generated method stub
		ArrayList<Edge> eds = graphI.get(v);
		ArrayList<Vertex> vs = new ArrayList<Vertex>();
		for(Edge e:eds){
			vs.add(e.getVertex(Direction.IN));

		}
		return vs;
	}

	public int getNumberOfPermutation() {
		// TODO Auto-generated method stub
		return this.permutation.size();
	}


	public static HashMap<String,String> graphFromTemplate(String tgraphFile,String root,String outG){
		String rootI=null;
		HashMap<String,String> mapping=new HashMap<String,String>();
		try {
			GraphValidator v = new GraphValidator(tgraphFile,root);
			HashMap<Vertex, ArrayList<Edge>> g = v.getGi().getGraphI();

			HashMap<String,Vertex> vertex=new HashMap<String,Vertex>();
			Random randomG = new Random();
			HashSet<String> ids=new HashSet<String>();
			for (Entry<Vertex, ArrayList<Edge>> gv : g.entrySet()){
				
				String id=String.valueOf(randomG.nextInt(1000)+g.size());
				while(!ids.add(id)){
					id=String.valueOf(randomG.nextInt(1000)+g.size());
				}
				String newN="n"+id;
				vertex.put(newN, gv.getKey());
				if(gv.getKey().getId().toString().equalsIgnoreCase(root))
					rootI=newN;
				mapping.put(gv.getKey().getId().toString(),newN);
				System.out.println("VECCHIO N:"+gv.getKey().getId().toString()+"    NUOVO N:"+newN);
			}

			Graph graph = new TinkerGraph();
			GraphMLWriter writer = new GraphMLWriter(graph);
			//Vertex vv=new Vertex();
			HashMap<String,Vertex> newVertex=new HashMap<String,Vertex>();
			for (Entry<String, Vertex> gv : vertex.entrySet()){
				Vertex ib = graph.addVertex(gv.getKey());
				ib.setProperty("mechanism",TocFactory.tocFromTemplate(gv.getValue().getProperty("mechanism").toString()));
				newVertex.put(gv.getKey(), ib);
			}
			for (Entry<String, Vertex> gv : newVertex.entrySet()){
				ArrayList<Edge> edges = g.get(vertex.get(gv.getKey()));
				for(Edge e:edges){
					Vertex vin = e.getVertex(Direction.IN);
					for (Entry<String, Vertex> tofound : vertex.entrySet()){
						if(vin.getId().toString().equalsIgnoreCase(tofound.getValue().getId().toString())){
							vin=newVertex.get(tofound.getKey());
						}
					}
					//e.getVertex(direction);
					graph.addEdge(null, gv.getValue(), vin, UUID.randomUUID().toString());
				}
			}
			OutputStream os = new FileOutputStream(outG);
			writer.outputGraph(graph,os);
			//graph.addVertex(new Vertex())

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapping;
	}

	public static void main(String[] args) {
		//graphFromTemplate("/Users/iridium/Documents/workspace/validator/graphT2.xml");
	}

	public static boolean validateValidation(
			HashMap<String, String> nodeMapped, GraphValidator gv) {

		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<Integer> getOrder() {

		return permutation.get(this.lastPermutation);
	}
	public ArrayList<Integer> getOrder(int i){
		return permutation.get(i);
	}

	public void setLastPermutation(int i) {
		this.lastPermutation=i;

	}

	public int getLastPermutation() {
		return lastPermutation;
	}

	public static boolean validateValidation(
			HashMap<String, String> nodeMapped,
			ArrayList<ArrayList<Vertex>> psInstance,
			ArrayList<ArrayList<Vertex>> psTemplate) {
		for(int i=0;i<psInstance.size();i++){
			ArrayList<Vertex> pInstance=psInstance.get(i);
			ArrayList<Vertex> pTemplate=psTemplate.get(i);
			for(int k=0;k<pInstance.size();k++){
				Vertex vt=pTemplate.get(k);
				Vertex vi=pInstance.get(k);
				String vOriginal=nodeMapped.get(vt.getId().toString());
				System.out.println("Controllo --> vtemplate="+vt.getId().toString()+" vi dal bind originale -->"+vOriginal+" v mappata -->"+vi.getId().toString());
				if(!vOriginal.equalsIgnoreCase(vi.getId().toString()))
					return false;
			}
		}

		return true;
	}

	public ArrayList<ArrayList<Vertex>> getOrdered() {
		// TODO Auto-generated method stub
		return null;
	}



}

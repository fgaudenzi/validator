package org.unimi.creator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

import org.unimi.tsc.validator.Property;
import org.unimi.tsc.validator.PropertyBuilder;
import org.unimi.tsc.validator.ToC;
import org.unimi.tsc.validator.TocFactory;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLWriter;

public class TemplateFactory {

	public static void createTemplate(int path,int mindeep,int maxdeep){

		Property p=PropertyBuilder.createProperty();
		ArrayList<Vertex> vs=createModel(path,mindeep,maxdeep);
		createToCs(vs);
			

	}
	private static void createToCs(ArrayList<Vertex> vs){
		
		Random randomG = new Random();
		HashSet<String> mec=new HashSet<String>();
		for(Vertex v:vs){
			//System.out.println(v.getId().toString());
			mec.add(v.getProperty("mechanism").toString());
		}
		int id=1;
		ToC[] ts=new ToC[mec.size()];
		for(String m:mec){
			//System.out.println(m);
			ts[id-1]=TocFactory.createToc(m,String.valueOf(id));
			System.out.println(ts[id-1].getId()+" - "+ts[id-1].getMechanism()+" - "+ts[id-1].getLayer()+" - "+ts[id-1].getEvents().size());
			id++;
		}
		TocFactory.writeToFileTocs(ts);
	}

	private static ArrayList<Vertex> createModel(int path,int mindeep,int maxdeep){
		Random randomG = new Random();
		Graph graph = new TinkerGraph();
		GraphMLWriter writer = new GraphMLWriter(graph);
		String node="n";
		int nnode=0;
		ArrayList<Vertex> vs=new ArrayList<Vertex>();
		Vertex root = graph.addVertex(node+String.valueOf(nnode));
		nnode++;
		root.setProperty("mechanism",TocFactory.randomMechanism());
		vs.add(root);
		for(int i=0;i<path;i++){
			int deep=randomG.nextInt((maxdeep - mindeep) + 1) + mindeep;
			System.out.println("path:"+i+" deep:"+deep);
			Vertex prev=root;
			for(int k=1;k<deep;k++){
				System.out.println("aggiunto vertice: "+node+String.valueOf(nnode));
				Vertex v=graph.addVertex(node+String.valueOf(nnode));
				v.setProperty("mechanism",TocFactory.randomMechanism());
				vs.add(v);
				nnode++;
				graph.addEdge(null, prev, v, UUID.randomUUID().toString());
				prev=v;
			}
		}
		

		String outG="/Users/iridium/Documents/workspace/validator/createdT.xml";
		OutputStream os=null;
		try {
			os = new FileOutputStream(outG);
			writer.outputGraph(graph,os);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vs;
	}

	public static void main( String[] args )
    {
		//System.out.println(TocFactory.randomMechanism());
		createTemplate(5,3,6);
    }
}


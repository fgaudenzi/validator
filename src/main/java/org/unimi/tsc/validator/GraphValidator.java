package org.unimi.tsc.validator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;

public class GraphValidator {
		final static String MECH="mechanism";
		private graphSTS gi;
    	public graphSTS getGi() {
			return gi;
		}

		/**
    	 * Constructor, set up the Instance that will be checked with all the templates
    	 * @param ci file path of GraphML file describe model
    	 * @param root id of root element of the graph
    	 * @throws IOException File not found or not parsable
    	 */
    	public GraphValidator(String ci,String root) throws IOException{
    		System.out.println("CHARGING THE GRAPH");
    		this.gi=new graphSTS(ci,root);
    		System.out.println("CREATED GRAPH IN RAM");
    	}
    	public ArrayList<Integer> compareModelEmp1(String ct,String root){
    		ArrayList<Integer> pIndex=new ArrayList<Integer>();
    		graphSTS gt;
    		Boolean result=false;
    		try {
				gt=new graphSTS(ct,root);
			} catch (IOException e) {
				return pIndex;
			}
    		System.out.println("CREATED GRAPH TEMPLATE IN RAM");
    		Vertex gtRoot = gt.getRoot();
    		Vertex giRoot = gi.getRoot();	
 //   		--- Comparison Mechanism ROOT 	---
    		if(!Mechanism.compareMechanism(gtRoot.getProperty("mechanism").toString(),giRoot.getProperty("mechanism").toString()))
    			return pIndex;
    		System.out.println("ROOT - OK");
 //			--- Check Cardinality 			---  		
    		/*if(gt.getGraphI().entrySet().size()!=gi.getGraphI().size()){
    			return false;
    		}*/
    		System.out.println("CARDINALITY - OK");
    		
    		
    		
    		
    		ArrayList<ArrayList<Vertex>> templatePaths=gt.getGraphI(-1);
    		int i=0;
    		
    			ArrayList<ArrayList<Vertex>> instancePaths=gi.getGraphI(-1);
    			boolean[] bindI=new boolean[instancePaths.size()];
    			for(int k=0;k<bindI.length;k++){
    				bindI[k]=true;
    			}
    			int[] bindT=new int[templatePaths.size()];
    			for(int k=0;k<bindT.length;k++){
    				bindT[k]=-1;
    			}
    			for(int k=0;k<templatePaths.size();k++){
    				ArrayList<Vertex> template=templatePaths.get(k);
    				for(int z=0;z<instancePaths.size();z++){
    				//controllo numero di path??
    				if(bindI[z]){
    				ArrayList<Vertex> instance=instancePaths.get(z);
    				result=comparePath(instance,template);
    				if(result){
    					bindT[k]=z;
    					bindI[z]=false;
    					break;
    				}
    				}
    				}
    				 				
    			}
    			for(Integer bt:bindT){
    				int j=0;
    				pIndex.add(bt);
    				System.out.println("Path "+j+" con Path"+bt);
    			}
    			//pIndex
    			return pIndex;
    		}
    	
    	
    	
    	public ArrayList<Integer> compareModel2(String ct,String root){
    		ArrayList<Integer> pIndex=new ArrayList<Integer>();
    		graphSTS gt;
    		Boolean result=false;
    		try {
				gt=new graphSTS(ct,root);
			} catch (IOException e) {
				return pIndex;
			}
    		System.out.println("CREATED GRAPH TEMPLATE IN RAM");
    		Vertex gtRoot = gt.getRoot();
    		Vertex giRoot = gi.getRoot();	
 //   		--- Comparison Mechanism ROOT 	---
    		if(!Mechanism.compareMechanism(gtRoot.getProperty("mechanism").toString(),giRoot.getProperty("mechanism").toString()))
    			return pIndex;
    		System.out.println("ROOT - OK");
 //			--- Check Cardinality 			---  		
    		/*if(gt.getGraphI().entrySet().size()!=gi.getGraphI().size()){
    			return false;
    		}*/
    		System.out.println("CARDINALITY - OK");
    		
    		
    		
    		
    		ArrayList<ArrayList<Vertex>> templatePaths=gt.getGraphI(-1);
    		int i=0;
    		//System.out.println("System Permutation possibile:"+gi.getNumberOfPermutation());
    		for(i=0;i<gi.getNumberOfPermutation();i++){
    			if(i%500000==0)
					System.out.println(".");
				if(i%10000==0)
					System.out.print(".");  
    			
				//System.out.println("CHECKING PERMUTATION "+ i);
    			ArrayList<ArrayList<Vertex>> instancePaths=gi.getGraphI(i);
    			//System.out.println("Checking Template Path"+templatePaths);
    			if(instancePaths.size()<templatePaths.size())
    				return pIndex;
    			if(result)
    				pIndex.add(new Integer(i-1));
    			   //break;
    			for(int k=0;k<templatePaths.size();k++){
    				//controllo numero di path??
    				ArrayList<Vertex> template=templatePaths.get(k);
    				ArrayList<Vertex> instance=instancePaths.get(k);
    				//System.out.println("Checking Instance Path: length:"+instance.size()+ " -- path: "+instance);
    				//System.out.println("Checking Template Path: length:"+template.size()+ " -- path: "+template);
    				result=comparePath(instance,template);
    				 					
    				if(!result) 
    					break;
    			}
    		}
    		if(!result)
    			return pIndex;
    		else{
    			System.out.println();
    			System.out.println("Compatible after "+i+" permutations");
    			System.out.println("Template paths:"+gt.getGraphI(-1));
    			System.out.println("INSTANCE paths:"+gi.getGraphI(i));
    			gi.setLastPermutation(i);
    			System.out.println("\n\n\n\n CHECK EVIDENCES\n\n");
    			/*ArrayList<ArrayList<Vertex>> template=gt.getGraphI(-1);
    			ArrayList<ArrayList<Vertex>> instance=gi.getGraphI(i);
    			for(int k=0;k<templatePaths.size();k++){
    				//compareTestCases();
    			}*/
    			
    			
    			if(gt.getGraphI(-1).size()!=gi.getGraphI(-1).size())
    				return pIndex;
    			//pIndex
    			return pIndex;
    		}
    		
    	}
    	
    	/**
    	 * @param ct file path of GraphML file describe model
    	 * @param root id of root element of the graph
    	 * @return True o False if the Template fits the Instance
    	 */
    	public boolean compareModel(String ct,String root){
    		ArrayList<Integer> pIndex=new ArrayList<Integer>();
    		graphSTS gt;
    		Boolean result=false;
    		try {
				gt=new graphSTS(ct,root);
			} catch (IOException e) {
				return false;
			}
    		System.out.println("CREATED GRAPH TEMPLATE IN RAM");
    		Vertex gtRoot = gt.getRoot();
    		Vertex giRoot = gi.getRoot();	
 //   		--- Comparison Mechanism ROOT 	---
    		if(!Mechanism.compareMechanism(gtRoot.getProperty("mechanism").toString(),giRoot.getProperty("mechanism").toString()))
    			return false;
    		System.out.println("ROOT - OK");
 //			--- Check Cardinality 			---  		
    		/*if(gt.getGraphI().entrySet().size()!=gi.getGraphI().size()){
    			return false;
    		}*/
    		System.out.println("CARDINALITY - OK");
    		
    		
    		
    		
    		ArrayList<ArrayList<Vertex>> templatePaths=gt.getGraphI(-1);
    		int i=0;
    		//System.out.println("System Permutation possibile:"+gi.getNumberOfPermutation());
    		for(i=0;i<gi.getNumberOfPermutation();i++){
    			if(i%3000==0)
					System.out.println(".");
				if(i%50==0)
					System.out.print(".");  
    			
				//System.out.println("CHECKING PERMUTATION "+ i);
    			ArrayList<ArrayList<Vertex>> instancePaths=gi.getGraphI(i);
    			//System.out.println("Checking Template Path"+templatePaths);
    			if(instancePaths.size()<templatePaths.size())
    				return false;
    			if(result)
    				break;
    			for(int k=0;k<templatePaths.size();k++){
    				//controllo numero di path??
    				ArrayList<Vertex> template=templatePaths.get(k);
    				ArrayList<Vertex> instance=instancePaths.get(k);
    				//System.out.println("Checking Instance Path: length:"+instance.size()+ " -- path: "+instance);
    				//System.out.println("Checking Template Path: length:"+template.size()+ " -- path: "+template);
    				result=comparePath(instance,template);
    				 					
    				if(!result)
    					break;
    			}
    		}
    		if(!result)
    			return false;
    		else{
    			System.out.println();
    			System.out.println("Compatible after "+i+" permutations");
    			System.out.println("Template paths:"+gt.getGraphI(-1));
    			System.out.println("INSTANCE paths:"+gi.getGraphI(i));
    			gi.setLastPermutation(i);
    			System.out.println("\n\n\n\n CHECK EVIDENCES\n\n");
    			/*ArrayList<ArrayList<Vertex>> template=gt.getGraphI(-1);
    			ArrayList<ArrayList<Vertex>> instance=gi.getGraphI(i);
    			for(int k=0;k<templatePaths.size();k++){
    				//compareTestCases();
    			}*/
    			
    			
    			if(gt.getGraphI(-1).size()!=gi.getGraphI(-1).size())
    				return false;
    			
    			return true;
    		}
    		
    	}
    	
    	static private Boolean comparePath(ArrayList<Vertex> instance,
				ArrayList<Vertex> template) {
			int size=instance.size();
			if(size!=template.size())
				return false;
			for(int i=0;i<size;i++){
				if(!Mechanism.compareMechanism(template.get(i).getProperty("mechanism").toString(),instance.get(i).getProperty("mechanism").toString()))
					return false;
			}
			return true;
		}

		//stesso ID//
    	
    	 private boolean checkSons(graphSTS gt, graphSTS gi2, Vertex t_out,
				Vertex i_out) {
			// TODO Auto-generated method stub
			return false;
		}

		public static void main( String[] args )
    	    {
    		 try {
				GraphValidator validator = new GraphValidator("/Users/iridium/Documents/workspace/validator/graph.xml","n0");
				boolean app=validator.compareModel("/Users/iridium/Documents/workspace/validator/graphT.xml", "n0");
				System.out.println("RISULTATO:"+app);
    		 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    }

		public ArrayList<ArrayList<Vertex>> bind(String graphI,String nroot) {
			
			graphSTS gt;
    		Boolean result=false;
    		try {
				gt=new graphSTS(graphI,nroot);
			} catch (IOException e) {
				return null;
			}
    		System.out.println("CREATED GRAPH TEMPLATE IN RAM");
    		Vertex gtRoot = gt.getRoot();
    		Vertex giRoot = gi.getRoot();	
 //   		--- Comparison Mechanism ROOT 	---
    		if(!Mechanism.compareMechanism(gtRoot.getProperty("mechanism").toString(),giRoot.getProperty("mechanism").toString())){
    			System.out.println("errore meccanismo NULL");
    			return null;
    		}
    		System.out.println("ROOT - OK");
 //			--- Check Cardinality 			---  		
    		/*if(gt.getGraphI().entrySet().size()!=gi.getGraphI().size()){
    			return false;
    		}*/
    		System.out.println("CARDINALITY - OK");
    		
    		
    		
    		
    		ArrayList<ArrayList<Vertex>> templatePaths=gt.getGraphI(-1);
    		int i=0;
    		//System.out.println("System Permutation possibile:"+gi.getNumberOfPermutation());
    		for(i=0;i<gi.getNumberOfPermutation();i++){
    			if(i%500000==0)
					System.out.println(".");
				if(i%10000==0)
					System.out.print(".");  
    			
				//System.out.println("CHECKING PERMUTATION "+ i);
    			ArrayList<ArrayList<Vertex>> instancePaths=gi.getGraphI(i);
    			//System.out.println("Checking Template Path"+templatePaths);
    			if(instancePaths.size()<templatePaths.size())
    				return null;
    			if(result)
    				break;
    			for(int k=0;k<templatePaths.size();k++){
    				//controllo numero di path??
    				ArrayList<Vertex> template=templatePaths.get(k);
    				ArrayList<Vertex> instance=instancePaths.get(k);
    				//System.out.println("Checking Instance Path: length:"+instance.size()+ " -- path: "+instance);
    				//System.out.println("Checking Template Path: length:"+template.size()+ " -- path: "+template);
    				result=comparePath(instance,template);
    				 					
    				if(!result)
    					break;
    			}
    		}
    		if(!result)
    			return null;
    		else{
    			System.out.println();
    			System.out.println("Compatible after "+(i-1)+" permutations");
    			gi.setLastPermutation(i);
    			System.out.println("Template paths:"+gt.getGraphI(-1));
    			System.out.println("INSTANCE paths:"+gi.getGraphI(i-1));
    			return gi.getGraphI(i-1);
    		}
			
			
			
			
			
			//return null;
		}

		public ArrayList<Integer> compareModelEmp2(String ct,String root) {
			ArrayList<Integer> pIndex=new ArrayList<Integer>();
    		graphSTS gt;
    		Boolean result=false;
    		try {
				gt=new graphSTS(ct,root);
			} catch (IOException e) {
				return pIndex;
			}
    		System.out.println("CREATED GRAPH TEMPLATE IN RAM");
    		Vertex gtRoot = gt.getRoot();
    		Vertex giRoot = gi.getRoot();	
 //   		--- Comparison Mechanism ROOT 	---
    		if(!Mechanism.compareMechanism(gtRoot.getProperty("mechanism").toString(),giRoot.getProperty("mechanism").toString()))
    			return pIndex;
    		System.out.println("ROOT - OK");
 //			--- Check Cardinality 			---  		
    		/*if(gt.getGraphI().entrySet().size()!=gi.getGraphI().size()){
    			return false;
    		}*/
    		System.out.println("CARDINALITY - OK");
    		
    		
    		ArrayList<ArrayList<Vertex>> instancePaths=gi.getOrdered();
    		ArrayList<ArrayList<Vertex>> templatePaths=gt.getOrdered();
    		//ArrayList<ArrayList<Vertex>> templatePaths=gt.getGraphI(-1);
    		int i=0;
    		
    			//ArrayList<ArrayList<Vertex>> instancePaths=gi.getGraphI(-1);
    			boolean[] bindI=new boolean[instancePaths.size()];
    			for(int k=0;k<bindI.length;k++){
    				bindI[k]=true;
    			}
    			int[] bindT=new int[templatePaths.size()];
    			for(int k=0;k<bindT.length;k++){
    				bindT[k]=-1;
    			}
    			for(int k=0;k<templatePaths.size();k++){
    				ArrayList<Vertex> template=templatePaths.get(k);
    				for(int z=0;z<instancePaths.size();z++){
    				//controllo numero di path??
    				if(bindI[z]){
    				ArrayList<Vertex> instance=instancePaths.get(z);
    				result=comparePath(instance,template);
    				if(result){
    					bindT[k]=z;
    					bindI[z]=false;
    					break;
    				}
    				}
    				}
    				 				
    			}
    			for(Integer bt:bindT){
    				int j=0;
    				pIndex.add(bt);
    				System.out.println("Path "+j+" con Path"+bt);
    			}
    			//pIndex
    			return pIndex;ted method stub
			return null;
		}

		
}

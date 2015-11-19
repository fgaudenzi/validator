package org.unimi.tsc.validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class InstanceFactory {

	public static void createInstance(String propertyT,String tocT,String graphT,String evidenceT,String lifeCycleT) throws IOException{
		String property;
		String rootNodeI;
		String graphI="/Users/iridium/Documents/workspace/validator/nuovoGrafo.xml";
		String evidenceFI="/Users/iridium/Documents/workspace/validator/nuovoEvidence.xml";
		String tocI="/Users/iridium/Documents/workspace/validator/nuovoToc.xml";
		PrintWriter writer = new PrintWriter(graphI);
		writer.print("");
		writer.close();
		writer = new PrintWriter(evidenceFI);
		writer.print("");
		writer.close();
		writer = new PrintWriter(tocI);
		writer.print("");
		writer.close();
		
		Random randomG = new Random();
		//restituisce una proprietà di certificazione a partire dalla proprietà (stringa) del template
		property=PropertyBuilder.propertyFromTemplate(propertyT);
		
		//restituisce il mapping fra nodi template e nodi grafo i e crea il file graphI con tutto il grafo (vertici+nodi)
		HashMap<String, String> nodeMapped = graphSTS.graphFromTemplate(graphT,"n0",graphI);
		rootNodeI=nodeMapped.get("n0");
		GraphValidator gv=new GraphValidator(graphI,rootNodeI);
		if(graphSTS.validateValidation(nodeMapped,gv)){
			System.out.println("error in validation");
			return;
		}
		//associazione tra tutti i path del template e tutti i path di gv(grago instanza) - Restituisce un array di path del template posizionati nello stesso ordine dei path dell'istanza
		ArrayList<ArrayList<Vertex>> paths=gv.bind(graphT,"n0");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\nCreated Instance from template");
		System.out.println("Property OLD:"+propertyT+" NEW:"+property);
		ArrayList<Evidence> templateEvidences = EvidenceFactory.getEvidencesI(evidenceT);
		ArrayList<Evidence> instanceEvidences = new ArrayList<Evidence>();
		System.out.println("PATHS");
		//GraphValidator gt=new GraphValidator(graphT,"n0");
		ArrayList<ArrayList<Vertex>> classicPath=gv.getGi().getGraphI(-1);
	
		if(paths==null)
			System.out.println("NO PATHS");
		else{
		System.out.println("PASSIAMO ALLE EVIDENZE");
		int nclassic=0;
		for(ArrayList<Vertex> v:paths){
		     for(int i=0;i<classicPath.size();i++){
		    	 ArrayList<Vertex> c=classicPath.get(i);
		    	 if(c.equals(v)){
		    		 System.out.println("matching trovato per path singolo");
		    		 instanceEvidences.addAll(EvidenceFactory.writeEvidenceFromTemplate(templateEvidences, v,i,nclassic));
		    		 //EvidenceFactory.writeEvidenceFromTemplate(templateEvidences, i);
		    	 }
		     }
		     nclassic++; 		
		}
		 try {
			EvidenceFactory.writeOnFile(instanceEvidences,evidenceFI);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 TocFactory.writeToCfilefromTemplate(tocT,tocI,graphT,paths,nodeMapped);
		// tocT, String tocI, classicPath,  paths,  nodeMappe
		 
		 
	
		HashMap<Vertex, ArrayList<Edge>> verticiInstanza = gv.getGi().getGraphI();
		graphSTS gt = new GraphValidator(graphT,"n0").getGi();
		ArrayList<ArrayList<Vertex>> templatePath = gt.getGraphI(-1);
		HashMap<Vertex, ArrayList<Edge>> verticiTemplate =gt.getGraphI();
		System.out.println("\n\n\n\n\n*******************************************************************************");
		System.out.println("NODO RADICE ISTANCE:"+rootNodeI);
		for(Entry<String, String> n:nodeMapped.entrySet()){
			String nodoI,nodoT;
			String meccanismoI="";
			String meccanismoT="";
			nodoT=n.getKey();
			nodoI=n.getValue();
			for(Entry<Vertex,ArrayList<Edge>> appV:verticiTemplate.entrySet()){
				if(appV.getKey().toString().equalsIgnoreCase("v["+nodoT+"]"))
					meccanismoT=appV.getKey().getProperty("mechanism").toString();
			}
			for(Entry<Vertex,ArrayList<Edge>> appV:verticiInstanza.entrySet()){
				if(appV.getKey().toString().equalsIgnoreCase("v["+nodoI+"]"))
					meccanismoI=appV.getKey().getProperty("mechanism").toString();
			}
			System.out.println("VECCHIO NODEO:"+nodoT+"\tm:"+meccanismoT+ "\t NUOVO NODO:"+nodoI+"\tm:"+meccanismoI);
		}
		System.out.println("\n\n\n***********");
		int numP=0;
		/*for(ArrayList<Vertex> path:classicPath){
			System.out.print("Percorso" +numP+":");
			System.out.println(path);
			numP++;
		}*/
		numP=0;
		ArrayList<Integer> order=gv.getGi().getOrder();
		for(ArrayList<Vertex> path:paths){
			System.out.print("T Percorso" +numP+":");
			System.out.println(templatePath.get(numP));	
			System.out.print("I Percorso" +order.get(numP)+":");
			System.out.println(path+"\n");
			
			numP++;
		}
		 
	}
	}
	public static void main(String[] args) throws IOException {
		InstanceFactory.createInstance("property-d8-b0-d7-b0-d6-b5-d5-b1", "/Users/iridium/Documents/workspace/validator/ToCt.xml", "/Users/iridium/Documents/workspace/validator/graphT2.xml", "/Users/iridium/Documents/workspace/validator/evidenceTemplate.xml","");
	}

}

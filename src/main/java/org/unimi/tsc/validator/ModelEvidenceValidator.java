package org.unimi.tsc.validator;

import java.io.IOException;
import java.util.ArrayList;

import com.tinkerpop.blueprints.Vertex;

public class ModelEvidenceValidator {
	private GraphValidator gv;
	private EvidenceValidator ev;
	public ModelEvidenceValidator(GraphValidator gv, EvidenceValidator ev) {
		super();
		this.gv = gv;
		this.ev = ev;
	}
	
	public boolean validate(String modelTemplateF,String root,String evidenceTemplateF){
		ArrayList<Integer> modelindex=gv.compareModel2(modelTemplateF, root);
		
		
		
		graphSTS gt=null;
		try {
			gt = new graphSTS(modelTemplateF,root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<ArrayList<Vertex>> toPrint = gv.getGi().getGraphI(-1);
		for(ArrayList<Vertex>vv:toPrint){
			for(Vertex v:vv) System.out.print(" "+v.getId().toString());
			System.out.println(" ");
		}
	    toPrint = gt.getGraphI(-1);
		for(ArrayList<Vertex>vv:toPrint){
			for(Vertex v:vv) System.out.print(" "+v.getId().toString());
			System.out.println(" ");
		}
		
		
		
		
		
		System.out.println("RISULTATO model:"+modelindex.size()+" ->"+modelindex.get(0));
		if(modelindex.size()==0)
			return false;
		for(Integer modelI:modelindex){
		
			
			toPrint =gv.getGi().getGraphI(modelI.intValue());
			for(ArrayList<Vertex>vv:toPrint){
				for(Vertex v:vv) System.out.print(" "+v.getId().toString());
				System.out.println(" ");
			}
		
			ArrayList<Integer> pathsOrderInstance = gv.getGi().getOrder(modelI.intValue());
		

		boolean evid=this.ev.CompareEvidences(evidenceTemplateF,pathsOrderInstance);
		System.out.println("RISULTATO evidence:"+evid);
		if(evid)
			return false;
	}
		//if(!evid)
		//	return false;
			                                                                                                                                                                                                                                                                                          
		
		
		
		return false;
	}
	
	
	
	
}

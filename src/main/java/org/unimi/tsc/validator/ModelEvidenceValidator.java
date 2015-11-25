package org.unimi.tsc.validator;

import java.io.IOException;
import java.util.ArrayList;

import com.tinkerpop.blueprints.Vertex;

public class ModelEvidenceValidator {
	private GraphValidator gv;
	private EvidenceValidator ev;
	public ModelEvidenceValidator(GraphValidator gv, EvidenceValidator ev) {
		//super();
		this.gv = gv;
		this.ev = ev;
	}
	
	public boolean validate(String modelTemplateF,String root,String evidenceTemplateF){
		String timeC="INIT MODEL:"+String.valueOf(System.nanoTime()/1000)+"\n";
		
		ArrayList<Integer> modelindex=gv.compareModel2(modelTemplateF, root);
		timeC+="END MODEL:"+String.valueOf(System.nanoTime()/1000)+"\n";
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
		
		timeC+="INIT EVIDENCE:"+String.valueOf(System.nanoTime()/1000)+"\n";	
		boolean evid=this.ev.CompareEvidences(evidenceTemplateF,pathsOrderInstance);
		timeC+="END EVIDENCE:"+String.valueOf(System.nanoTime()/1000)+"\n";	
		System.out.println("RISULTATO evidence:"+evid);
		if(evid)
			return true;
	}
		//if(!evid)
		//	return false;
			                                                                                                                                                                                                                                                                                          
		
		
		
		return false;
	}
	
	public boolean validateEmp1(String modelTemplateF,String root,String evidenceTemplateF){
		String timeC="INIT MODEL:"+String.valueOf(System.nanoTime()/1000)+"\n";
		
		ArrayList<Integer> modelindex=gv.compareModelEmp1(modelTemplateF, root);
		timeC+="END MODEL:"+String.valueOf(System.nanoTime()/1000)+"\n";
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
		
		
		
		
		
		System.out.println("RISULTATO model:"+modelindex);
		
		//if(modelindex.size()==0)
		//	return false;
		//for(Integer modelI:modelindex){
		
			
			//toPrint =gv.getGi().getAllPahsbyIndexes(modelindex);
//			for(ArrayList<Vertex>vv:toPrint){
//				for(Vertex v:vv) System.out.print(" "+v.getId().toString());
//				System.out.println(" ");
//			}
		
			ArrayList<Integer> pathsOrderInstance = modelindex;
					//gv.getGi().getOrder(modelI.intValue());
		
		timeC+="INIT EVIDENCE:"+String.valueOf(System.nanoTime()/1000)+"\n";	
		boolean evid=this.ev.CompareEvidences(evidenceTemplateF,pathsOrderInstance);
		timeC+="END EVIDENCE:"+String.valueOf(System.nanoTime()/1000)+"\n";	
		System.out.println(timeC);
		System.out.println("RISULTATO evidence:"+evid);
		if(evid)
			return true;
	
		//if(!evid)
		//	return false;
			                                                                                                                                                                                                                                                                                          
		
		
		
		return false;
	}
	public boolean validateEmp2(String modelTemplateF,String root,String evidenceTemplateF){
		String timeC="INIT MODEL:"+String.valueOf(System.nanoTime()/1000)+"\n";
		//ArrayList<Integer> model1=gv.compareModelEmp1(modelTemplateF, root);
		//System.out.println("RISULTATO model:"+model1);
		ArrayList<Integer> modelindex=gv.compareModelEmp2(modelTemplateF, root);
		timeC+="END MODEL:"+String.valueOf(System.nanoTime()/1000)+"\n";
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
		
		
		
		
		
		System.out.println("RISULTATO model:"+modelindex);
		
		//if(modelindex.size()==0)
		//	return false;
		//for(Integer modelI:modelindex){
		
			
			//toPrint =gv.getGi().getAllPahsbyIndexes(modelindex);
//			for(ArrayList<Vertex>vv:toPrint){
//				for(Vertex v:vv) System.out.print(" "+v.getId().toString());
//				System.out.println(" ");
//			}
		
			ArrayList<Integer> pathsOrderInstance = modelindex;
					//gv.getGi().getOrder(modelI.intValue());
		
		timeC+="INIT EVIDENCE:"+String.valueOf(System.nanoTime()/1000)+"\n";	
		boolean evid=this.ev.CompareEvidences(evidenceTemplateF,pathsOrderInstance);
		timeC+="END EVIDENCE:"+String.valueOf(System.nanoTime()/1000)+"\n";	
		System.out.println(timeC);
		System.out.println("RISULTATO evidence:"+evid);
		if(evid)
			return true;
	
		//if(!evid)
		//	return false;
			                                                                                                                                                                                                                                                                                          
		
		
		
		return false;
	}
	
	public boolean validateT(String modelTemplateF,String root,String evidenceTemplateF){
		String timeC="INIT MODEL:"+String.valueOf(System.nanoTime()/1000)+"\n";
		
		ArrayList<Integer> modelindex=gv.tester(modelTemplateF, root);
		timeC+="END MODEL:"+String.valueOf(System.nanoTime()/1000)+"\n";
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
		
		
		
		
		
		System.out.println("RISULTATO model:"+modelindex);
		
		//if(modelindex.size()==0)
		//	return false;
		//for(Integer modelI:modelindex){
		
			
			//toPrint =gv.getGi().getAllPahsbyIndexes(modelindex);
//			for(ArrayList<Vertex>vv:toPrint){
//				for(Vertex v:vv) System.out.print(" "+v.getId().toString());
//				System.out.println(" ");
//			}
		
			ArrayList<Integer> pathsOrderInstance = modelindex;
					//gv.getGi().getOrder(modelI.intValue());
		
		timeC+="INIT EVIDENCE:"+String.valueOf(System.nanoTime()/1000)+"\n";	
		boolean evid=this.ev.CompareEvidences(evidenceTemplateF,pathsOrderInstance);
		timeC+="END EVIDENCE:"+String.valueOf(System.nanoTime()/1000)+"\n";	
		System.out.println(timeC);
		System.out.println("RISULTATO evidence:"+evid);
		if(evid)
			return true;
	
		//if(!evid)
		//	return false;
			                                                                                                                                                                                                                                                                                          
		
		
		
		return false;
	}
	
}

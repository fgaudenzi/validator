package org.unimi.checker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;

import org.unimi.tsc.validator.EvidenceValidator;
import org.unimi.tsc.validator.GraphValidator;
import org.unimi.tsc.validator.ModelEvidenceValidator;
import org.unimi.tsc.validator.ToCValidator;



public class Validator {
	
	public static void main(String[] args)  throws Exception{
		
		
		for(int i=0;i<10;i++){
		 long init = System.nanoTime();
		 System.out.println(init);
		 FileReader reader = new FileReader("/Users/iridium/Downloads/TEST_PAPER/CM-8/root-"+String.valueOf(i)+".pt");
         BufferedReader bufferedReader = new BufferedReader(reader);

         String line,root="";

         while ((line = bufferedReader.readLine()) != null){
        	root=line;
         };
         reader.close();
         //root="n256";
			ModelEvidenceValidator mev=new ModelEvidenceValidator(new GraphValidator("/Users/iridium/Downloads/TEST_PAPER/CM-8/IstanceGraph-"+String.valueOf(i)+".xml",root),new  EvidenceValidator("/Users/iridium/Downloads/TEST_PAPER/CM-8/IstanceEvidence-"+String.valueOf(i)+".xml"));
    		System.out.println("RISULTATO MODEL+EVIDENCE:"+mev.validate("/Users/iridium/Downloads/TEST_PAPER/CM-8/TemplateModel.xml", "n0","/Users/iridium/Downloads/TEST_PAPER/CM-8/TemplateEvidence.xml"));
    		/*GraphValidator validator = new GraphValidator("/Users/iridium/Documents/workspace/validator/nuovoGrafo.xml","n69");
    		//GraphValidator validator2 = new GraphValidator("/Users/iridium/Documents/workspace/validator/graphT.xml","n0");
    		//boolean app=false;
    		boolean appg=validator.compareModel("/Users/iridium/Documents/workspace/validator/graphT2.xml", "n0");
			System.out.println("RISULTATO MODELLO:"+appg);
			
			
			EvidenceValidator ev= new  EvidenceValidator("/Users/iridium/Documents/workspace/validator/nuovoEvidence.xml");
			boolean appe=ev.CompareEvidences("/Users/iridium/Documents/workspace/validator/evidenceTemplate.xml");
			System.out.println("RISULTATO EVIDENZE:"+appe);*/
			 
			ToCValidator tc=new ToCValidator("/Users/iridium/Downloads/TEST_PAPER/CM-8/IstanceToC-"+String.valueOf(i)+".xml");
			boolean appt= tc.compareTocs("/Users/iridium/Downloads/TEST_PAPER/CM-8/TemplateToC.xml");
			System.out.println("RISULTATO TOC:"+appt);
			long end= System.nanoTime();
			System.out.println(end);
			System.out.println(String.valueOf(end-init));
			FileWriter rootwriter = new FileWriter("/Users/iridium/Downloads/TEST_PAPER/CM-8/result-"+String.valueOf(i)+".txt",false);
	        rootwriter.write(String.valueOf((end-init)/1000)+"ms");
	        rootwriter.close();
			for(int k=0;k<10;k++)
				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			
			Thread.sleep(100);
			
		}
	}

}

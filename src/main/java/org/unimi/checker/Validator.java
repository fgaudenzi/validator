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
		String dd;
		dd="/Users/iridium/Downloads/TEST_PAPER/deep-";
		int[] array={10,15,12};
		
		for(int j=0;j<array.length;j++){
			String d=dd+String.valueOf(array[j])+"/";
	for(int z=5;z<=10;z++){
		
		String nfat=String.valueOf(z);
		System.out.println("\n\n\n\n\n N="+nfat+"\n\n\n");
		for(int i=0;i<3;i++){
		 long init = System.nanoTime();
		 FileReader reader = new FileReader(d+"CM-"+nfat+"/root-"+String.valueOf(i)+".pt");
         BufferedReader bufferedReader = new BufferedReader(reader);

         String line,root="";

         while ((line = bufferedReader.readLine()) != null){
        	root=line;
         };
         reader.close();
		 System.out.println(init);
		 
			ModelEvidenceValidator mev=new ModelEvidenceValidator(new GraphValidator(d+"CM-"+nfat+"/IstanceGraph-"+String.valueOf(i)+".xml",root),new  EvidenceValidator(d+"CM-"+nfat+"/IstanceEvidence-"+String.valueOf(i)+".xml"));
			System.out.println("RISULTATO MODEL+EVIDENCE:"+mev.validate(d+"CM-"+nfat+"/TemplateModel.xml", "n0",d+"CM-"+nfat+"/TemplateEvidence.xml"));
			String timeC="INIT TOC:"+String.valueOf(System.nanoTime()/1000)+"\n";	
			ToCValidator tc=new ToCValidator(d+"CM-"+nfat+"/IstanceToC-"+String.valueOf(i)+".xml");
			boolean appt= tc.compareTocs(d+"CM-"+nfat+"/TemplateToC.xml");
			timeC+="END TOC:"+String.valueOf(System.nanoTime()/1000)+"\n";	
			System.out.println("RISULTATO TOC:"+appt);
			long end= System.nanoTime();
			System.out.println(end);
			System.out.println(String.valueOf(end-init));
			FileWriter rootwriter = new FileWriter(d+"CM-"+nfat+"/result-"+String.valueOf(i)+".txt",false);
	        rootwriter.write(String.valueOf((end-init)/1000)+"ms");
	        rootwriter.close();
			for(int k=0;k<10;k++)
				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			
			Thread.sleep(100);
			
		}
	}
	}
	}

}

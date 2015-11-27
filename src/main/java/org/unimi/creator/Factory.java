package org.unimi.creator;

import java.io.IOException;

import org.unimi.tsc.validator.InstanceFactory;
import org.unimi.utilities.Utilities;

public class Factory {

	public static void main(String[] args)  throws Exception{
		String d;
		d="/Users/iridium/Downloads/TEST_PAPER_RECALL4/deep-";
		int[] array={3,4,5};
		long maxHeapSize = Runtime.getRuntime().maxMemory();
		long freeHeapSize = Runtime.getRuntime().freeMemory();
		long totalHeapSize = Runtime.getRuntime().totalMemory();
		System.out.println("Max Heap Size = " + maxHeapSize+ " byte");
		System.out.println("Free Heap Size = " + freeHeapSize+ " byte");
		System.out.println("Total Heap Size = " + totalHeapSize+ " byte");
		//- See more at: http://www.appuntisoftware.it/come-dimensionare-lheap-size-di-unapplicazione-java/#sthash.Hkaze0GG.dpuf
		for(int i=0;i<1;i++){
				//array.length;i++){
			System.out.println("\n\n\n\n DEEP="+array[i]+" \n\n\n");
		Factory.create(d+String.valueOf(array[i]),array[i]);
		}
	}
	
	public static void create(String baseDir,int deep) throws Exception{
		for(int i=6;i<=7;i++){
			String dir=baseDir+"/CM-"+String.valueOf(i);
			Utilities.createDir(dir);
			TemplateFactory.createTemplate(i, deep-1, deep,dir);
			for(int k=0;k<80;k++){
				if(!InstanceFactory.createInstanceClean("", dir+"/TemplateToC.xml", dir+"/TemplateModel.xml", dir+"/TemplateEvidence.xml", null,dir,String.valueOf(k))){
					k=k-1;
					System.out.println("ERRORE NELLA CREAZIONE DELL'ISTANZA - repeat operation");
				}
				Thread.sleep(100);
					
			}
			
		}
	}
}

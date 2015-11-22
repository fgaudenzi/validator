package org.unimi.creator;

import java.io.IOException;

import org.unimi.tsc.validator.InstanceFactory;
import org.unimi.utilities.Utilities;

public class Factory {

	public static void main(String[] args)  throws Exception{
		long maxHeapSize = Runtime.getRuntime().maxMemory();
		long freeHeapSize = Runtime.getRuntime().freeMemory();
		long totalHeapSize = Runtime.getRuntime().totalMemory();
		System.out.println("Max Heap Size = " + maxHeapSize+ " byte");
		System.out.println("Free Heap Size = " + freeHeapSize+ " byte");
		System.out.println("Total Heap Size = " + totalHeapSize+ " byte");
		//- See more at: http://www.appuntisoftware.it/come-dimensionare-lheap-size-di-unapplicazione-java/#sthash.Hkaze0GG.dpuf
		Factory.create("/Users/iridium/Downloads/TEST_PAPER");
	}
	
	public static void create(String baseDir) throws Exception{
		for(int i=8;i<=8;i=i+5){
			String dir=baseDir+"/CM-"+String.valueOf(i);
			Utilities.createDir(dir);
			TemplateFactory.createTemplate(i, 4, 5,dir);
			for(int k=0;k<10;k++){
				if(!InstanceFactory.createInstance("", dir+"/TemplateToC.xml", dir+"/TemplateModel.xml", dir+"/TemplateEvidence.xml", null,dir,String.valueOf(k))){
					k=k-1;
					System.out.println("ERRORE NELLA CREAZIONE DELL'ISTANZA - repeat operation");
				}
				Thread.sleep(100);
					
			}
			
		}
	}
}

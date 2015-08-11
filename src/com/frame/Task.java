package com.frame;

import javax.swing.SwingWorker;

import com.dazhong.FullCrawler;

public class Task extends SwingWorker<Void, Void> {
	 public Void doInBackground(String addurl,String database) throws Exception {
         //耗时的操作            
        
         FullCrawler main=new FullCrawler();
			//页数默认为50,非完全爬取
         main.fullpower(addurl, database); 
         
         return null;
     }

	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
     
	
	

}

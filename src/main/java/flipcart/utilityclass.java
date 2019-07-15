package flipcart;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class utilityclass {

	
	@SuppressWarnings("resource")
	public String[] parsetextdata(String FilePath)
	{
		
		String line=null;
		
		FileReader file=null;
		BufferedReader reader=null;
		try {
			file = new FileReader(FilePath);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		reader=new BufferedReader(file);
		List<String> sourceData= new ArrayList<String>();
		try{
			
			line=reader.readLine();
			
		while(line!=null )
		{
			
			//System.out.println("line"+line);
			sourceData.add(line);
			line=reader.readLine();
		}
	}catch(IOException e)
	{
		System.out.println("File reading error");
	}
		String[] datasource=sourceData.toArray(new String[0]);
		return datasource;
		
	}

	
	
}

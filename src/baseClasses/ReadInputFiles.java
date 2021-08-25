package baseClasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadInputFiles {
	
	public static void Read(Scanner[] reader, String InputFilesPath, String InputFilesName, String InputFilesType, int NumberOfInputFiles) {
		
		FileInputStream InputFile;
		
		for (int i = 0; i < NumberOfInputFiles; i++) {
			
			String path = InputFilesPath + InputFilesName + (i+1) + InputFilesType;										   	//Full Path, Name And Type Of Input File(s)
			
			try {																										   	//Try To Open A Input File and Store It 				
				try {					
					InputFile = new FileInputStream(path);																   	//Try To Open A Input File
					reader[i] = new Scanner(InputFile);																	   	//Store The Opened File In Scanner Object Of Type Array
					//System.out.println("File "+ InputFilesPath + InputFilesName + (i+1) + InputFilesType + " opened.");  	//For Developer Test					
				} catch (FileNotFoundException e) {
					throw new FileInvalidException(InputFilesPath, InputFilesName, (i+1), InputFilesType);					//Throw The Exception If The Application Fails To Open 1 Of The Input Files
				}
					
			} catch (FileInvalidException e) {
				
				System.out.println(e.getMessage()); 																		//Show Error Message If The Exception Received Which Means The Application Failed To Open 1 Of The Input Files

				for (int j = 0; j < i; j++) {
					reader[j].close(); 																						//Close The Scanner Object If The Application Fails To Open 1 Of The Input Files
					//System.out.println("Closing " + (j+1));  																//For Developer Test
				}
				System.exit(0);																								//Exit The Application If The Application Fails To Open 1 Of The Input Files
			}
		}
	}
}

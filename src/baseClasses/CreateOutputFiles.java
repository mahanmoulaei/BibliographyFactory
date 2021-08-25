package baseClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class CreateOutputFiles {
	
	public static void Generate(String InputFilesPath, String InputFilesName, String InputFilesType, int NumberOfInputFiles, PrintWriter[] IEEEwriter, String IEEEPath, String IEEEName, String IEEEType, PrintWriter[] ACMwriter, String ACMPath, String ACMName, String ACMType, PrintWriter[] NJwriter, String NJPath, String NJName, String NJType) {
		
		for (int i = 0; i < NumberOfInputFiles; i++) {

			try {																																//Try To Create 3 Different Output For Each Input File			
				try {				
					IEEEwriter[i] = new PrintWriter(new FileOutputStream(IEEEPath + IEEEName + (i+1) + IEEEType));								//Try To Create An IEEE Output File For Each Input File And Store It In PrintWriter Object Of Type Array
				} catch (FileNotFoundException e) {
					throw new FileInvalidException(InputFilesPath, InputFilesName, (i+1), InputFilesType, IEEEPath, IEEEName, IEEEType);		//Throw The Exception If The Application Fails To Create An IEEE Output File
				}
				
				try {					
					ACMwriter[i]  = new PrintWriter(new FileOutputStream(ACMPath + ACMName + (i+1) + ACMType));									//Try To Create An ACM Output File For Each Input File And Store It In PrintWriter Object Of Type Array
				} catch (FileNotFoundException e) {
					throw new FileInvalidException(InputFilesPath, InputFilesName, (i+1), InputFilesType, ACMPath, ACMName, ACMType);			//Throw The Exception If The Application Fails To Create An ACM Output File
				}
				
				try {				
					NJwriter[i]   = new PrintWriter(new FileOutputStream(NJPath + NJName + (i+1) + NJType));									//Try To Create An NJ Output File For Each Input File And Store It In PrintWriter Object Of Type Array		
				} catch (FileNotFoundException e) {
					throw new FileInvalidException(InputFilesPath, InputFilesName, (i+1), InputFilesType, NJPath, NJName, NJType);				//Throw The Exception If The Application Fails To Create An NJ Output File
				}
			
			} catch (FileInvalidException e) {				
				System.out.println(e.getMessage());																								//Show Error Message If The Exception Received Which Means The Application Failed To Create 1 Of The Output Files			
				//From This Line Until The End Of Line 65 Is To Delete All Of The Created Output Files If One Output File Cannot Be Created => e.g: IEEE and ACM Get Created But NJ Doesn't, So The Program Will Remove All Of The Created IEEE And ACM Output File(s)
				File[] IEEEFile = new File[NumberOfInputFiles];
				File[] ACMFile  = new File[NumberOfInputFiles];
				File[] NJFile   = new File[NumberOfInputFiles];

				for (int j = 0; j < NumberOfInputFiles; j++) {
					
					IEEEFile[j] = new File(IEEEPath + IEEEName + (j+1) + IEEEType);																
					if (IEEEFile[j].exists()) {																									//Check To See If The Output File Of Type IEEE Is Available In The IEEE Output Directory Or Not
						if (IEEEwriter[j] != null) {																							//Check To See If The Output File Of Type IEEE Is Stored In PrintWriter Object In Line 16 Or Not
							IEEEwriter[j].close();																								//Close The IEEE PrintWriter Object If The Output File Of Type IEEE Is Stored In PrintWriter Object In Line 16																						
						}
						IEEEFile[j].delete();																									//Delete The Output File Of Type IEEE If It's Available In The IEEE Output Directory
					}

					ACMFile[j] = new File(ACMPath + ACMName + (j+1) + ACMType);
					if (ACMFile[j].exists()) {																									//Check To See If The Output File Of Type ACM Is Available In The ACM Output Directory Or Not
						if (ACMwriter[j] != null) {																								//Check To See If The Output File Of Type ACM Is Stored In PrintWriter Object In Line 22 Or Not
							ACMwriter[j].close();																								//Close The ACM PrintWriter Object If The Output File Of Type ACM Is Stored In PrintWriter Object In Line 22	
						}	
						ACMFile[j].delete();																									//Delete The Output File Of Type ACM If It's Available In The ACM Output Directory
					}

					NJFile[j] = new File(NJPath + NJName + (j+1) + NJType);
					if (NJFile[j].exists()) {																									//Check To See If The Output File Of Type NJ Is Available In The NJ Output Directory Or Not
						if (NJwriter[j] != null) {																								//Check To See If The Output File Of Type NJ Is Stored In PrintWriter Object In Line 28 Or Not
							NJwriter[j].close();																								//Close The NJ PrintWriter Object If The Output File Of Type NJ Is Stored In PrintWriter Object In Line 28
						}							
						NJFile[j].delete();																										//Delete The Output File Of Type NJ If It's Available In The NJ Output Directory
					}
				}
				System.exit(0);																													//Exit The Application If One Of The Output Files Fails To Be Created
			}
		}
	}
}

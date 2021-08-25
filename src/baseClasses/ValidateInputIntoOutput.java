package baseClasses;


import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class ValidateInputIntoOutput {
	
	static int numOfInvalidFiles = 0;
	static String[] ToBeDeletedFiles;
	static String[] readerCopy;
	
	static String Author 		= null;
	static String Journal 		= null;
	static String Title 		= null;
	static String Year 			= null;
	static String Volume 		= null;
	static String Number 		= null;
	static String Pages 		= null;
	static String Doi 			= null;
	static String Month 		= null;	
	static String Paragraph 	= null;
	static String [] Article 	= null;	
	static String IEEEData 		= null;
	static String ACMData 		= null;
	static String NJData 		= null;
	static int ACMCount;
	
	public static void ProcessFilesForValidation(Scanner[] reader, Scanner[] readerCopy, String InputFilesPath, String InputFilesName, String InputFilesType, int NumberOfInputFiles, PrintWriter[] IEEEwriter, String IEEEPath, String IEEEName, String IEEEType, PrintWriter[] ACMwriter, String ACMPath, String ACMName, String ACMType, PrintWriter[] NJwriter, String NJPath, String NJName, String NJType) {
		
		baseClasses.ReadInputFiles.Read(readerCopy, InputFilesPath, InputFilesName, InputFilesType, NumberOfInputFiles); 																																	//TO READ ALL OF THE INPUT FILES CONTENT AGAIN AND STORE THEM IN A DIFFERENT SCANNER OBJECT OF TYPE ARRAY SO THE CONTENTS DON'T GET AFFECTED BY DELIMITER AND reader[i].next() SO WE DON'T HAVE TO OPEN THE SCANNER AGAIN IN A FOR LOOP IN LINE 53 FOR NumberOfInputFiles*times...
		String Content;
		String EmptyField;
		ToBeDeletedFiles = new String[NumberOfInputFiles];																																																	//An String Of Type To Store The Invalid Input File(s) And Keep Track Of Them
		
		for (int i = 0; i < NumberOfInputFiles; i++) {
																																																																	
			try {																																																											//Try To Read The Content Of Each Input Files And Search For An Empty Field Of {}	
				reader[i].useDelimiter("\\{\\s*}"); 																																																		//Set A Delimiter To reader[i] Scanner To Split The Input Files Content And Be Able To Detect An Empty Field Like {}
				//System.out.println(reader[i].delimiter());  																																																//For Developer Test    		
				Content = reader[i].next();																																																					//Read The Content Of Each Input File Until Reach The Delimiter And Store Everything Before {} In A String Variable																																																				
				//System.out.println(content);  																																																			//For Developer Test
				
				if (reader[i].hasNext()) { 																																																					//Check To See If The Content Reached A Delimiter of {} or Not		
					numOfInvalidFiles++;																																																					//If The Content Reached {} It Means The File Is Invalid So The Application Increase The Number Of Invalid Files To Keep The Count Of Them
					EmptyField = Content.substring((Content.lastIndexOf("\n") + 1), (Content.lastIndexOf("=")));																																			//If The Content Reached {} It Means The File Is Invalid So The Application Reads Everything From The Last \n In Content Until ={} And Store It In String Variable Called EmptyField  => e.g: author={} => EmptyField = "author"
					ToBeDeletedFiles[i] = Integer.toString(i+1);																																															//Store The Invalid Input File In An Array To Delete The Empty Output Files Of It Later
					reader[i].close();																																																						//Close The Scanner Object Because We Don't Need It Anymore And We Already Have A Copy Of Original And Unchanged Input File Content In Line 32
					throw new FileInvalidException(InputFilesPath, InputFilesName, (i+1), InputFilesType, EmptyField);																																		//Throw The Exception If The Application Detects And Invalid Input File
				} else {
					reader[i].close();																																																						//Close The Scanner Object Because We Don't Need It Anymore And We Already Have A Copy Of Original And Unchanged Input File Content In Line 32
					//baseClasses.ReadInputFiles.Read(reader, InputFilesPath, InputFilesName, InputFilesType, NumberOfInputFiles); //To Re-open the scanner ==> When the program reach this part, the scanner content is already changed by delimiter and read by reader[i].next! so it needs to read the original input files content again...		//OLD VERSION
					//WriteDataIntoOutputFile(i, reader, InputFilesPath, InputFilesName, InputFilesType, NumberOfInputFiles, IEEEwriter, IEEEPath, IEEEName, IEEEType, ACMwriter, ACMPath, ACMName, ACMType, NJwriter, NJPath, NJName, NJType);																										//OLD VERSION
					WriteDataIntoOutputFile(i, readerCopy, InputFilesPath, InputFilesName, InputFilesType, NumberOfInputFiles, IEEEwriter, IEEEPath, IEEEName, IEEEType, ACMwriter, ACMPath, ACMName, ACMType, NJwriter, NJPath, NJName, NJType);			//If The Application Doesn't Reach An Empty Field Of {} It The Input File Is Valid So The Application Can Write Its Content Into Output Files. Invokes WriteDataIntoOutputFile Method To Do That Using The Copy Of Original And Unchanged Input File Content Created In Line 32
				}
			}
			catch (FileInvalidException e) {
				System.out.println(e.getMessage());																																																			//Show Error Message If The Exception Of Invalid Input File Received
			}
			
		}
	}

	private static void WriteDataIntoOutputFile(int i, Scanner[] readerCopy, String InputFilesPath, String InputFilesName, String InputFilesType, int NumberOfInputFiles, PrintWriter[] IEEEwriter, String IEEEPath, String IEEEName, String IEEEType, PrintWriter[] ACMwriter, String ACMPath, String ACMName, String ACMType, PrintWriter[] NJwriter, String NJPath, String NJName, String NJType) {
		
		readerCopy[i].useDelimiter("@ARTICLE");																																																				//Set A Delimiter To readerCopy[i] Scanner To Split The Input Files Content And Be Able To Detect Different Articles By @ARTICLE	
		ACMCount = 1;																																																										//It Is Used In Formating The ACM Files 
		
		while (readerCopy[i].hasNext()) { 																																																					//Loop Through The Input Files Content To See If It Reaches The End Of The File Content Or Not

			Paragraph = readerCopy[i].next();  																																																				//Read Everything Between @ARTICLE Until The Next @ARTICLE And Store In A String Variable Called Paragraph
			//System.out.println("Paragraph: \n" + Paragraph + "\n==============The end of paragraph============");     																																	//For Developer Test
			Article = Paragraph.split("\n");																																																				//Split Each Line Of The Paragraph And Store It In A Variable Called Article So We Can Iterate Through Each Line
			//System.out.println("\nArticle "+ ACMCount++ + " Number of Lines: "+ Article.length);				        																																	//For Developer Test
			
			for (int line = 0; line < Article.length; line++) {																																																//Iterate Through Each Line Of Article
				
				if (Article[line].toLowerCase().contains("author=")) {																																														//Check To See If The Current Line Of The Article We Are On Right Now, Has "author=" Or Not
																																																															//
					Author = Article[line].substring(Article[line].indexOf("{")+1, Article[line].indexOf("}"));																																				//If The Current Line Of The Article We Are On Right Now, Has "author=" , Store Everything Between {} In A String Variable Called Author

				} else if (Article[line].toLowerCase().contains("journal=")) {																																												//Check To See If The Current Line Of The Article We Are On Right Now, Has "journal=" Or Not
																																																															//
					Journal = Article[line].substring(Article[line].indexOf("{")+1, Article[line].indexOf("}"));																																			//If The Current Line Of The Article We Are On Right Now, Has "journal=" , Store Everything Between {} In A String Variable Called Journal
					
				} else if (Article[line].toLowerCase().contains("title=")) {																																												//Check To See If The Current Line Of The Article We Are On Right Now, Has "title=" Or Not
																																																															//
					Title = Article[line].substring(Article[line].indexOf("{")+1, Article[line].indexOf("}"));																																				//If The Current Line Of The Article We Are On Right Now, Has "title=" , Store Everything Between {} In A String Variable Called Title
																																																															
				} else if (Article[line].toLowerCase().contains("year=")) {																																													//Check To See If The Current Line Of The Article We Are On Right Now, Has "year=" Or Not
																																																															//
					Year = Article[line].substring(Article[line].indexOf("{")+1, Article[line].indexOf("}"));																																				//If The Current Line Of The Article We Are On Right Now, Has "year=" , Store Everything Between {} In A String Variable Called Year
						
				} else if (Article[line].toLowerCase().contains("volume=")) {																																												//Check To See If The Current Line Of The Article We Are On Right Now, Has "volume=" Or Not
																																																															//
					Volume = Article[line].substring(Article[line].indexOf("{")+1, Article[line].indexOf("}"));																																				//If The Current Line Of The Article We Are On Right Now, Has "volume=" , Store Everything Between {} In A String Variable Called Volume
					
				} else if (Article[line].toLowerCase().contains("number=")) {																																												//Check To See If The Current Line Of The Article We Are On Right Now, Has "number=" Or Not
																																																															//
					Number = Article[line].substring(Article[line].indexOf("{")+1, Article[line].indexOf("}"));																																				//If The Current Line Of The Article We Are On Right Now, Has "number=" , Store Everything Between {} In A String Variable Called Number
					
				} else if (Article[line].toLowerCase().contains("pages=")) {																																												//Check To See If The Current Line Of The Article We Are On Right Now, Has "pages=" Or Not
																																																															//
					Pages = Article[line].substring(Article[line].indexOf("{")+1, Article[line].indexOf("}"));																																				//If The Current Line Of The Article We Are On Right Now, Has "pages=" , Store Everything Between {} In A String Variable Called Pages
					
				} else if (Article[line].toLowerCase().contains("doi=")) {																																													//Check To See If The Current Line Of The Article We Are On Right Now, Has "doi=" Or Not
																																																															//
					Doi = Article[line].substring(Article[line].indexOf("{")+1, Article[line].indexOf("}"));																																				//If The Current Line Of The Article We Are On Right Now, Has "doi=" , Store Everything Between {} In A String Variable Called Doi
						
				} else if (Article[line].toLowerCase().contains("month=")) {																																												//Check To See If The Current Line Of The Article We Are On Right Now, Has "month=" Or Not
																																																															//
					Month = Article[line].substring(Article[line].indexOf("{")+1, Article[line].indexOf("}"));																																				//If The Current Line Of The Article We Are On Right Now, Has "month=" , Store Everything Between {} In A String Variable Called Month
					
				} else {
					continue;
				}
			}
			
			if (Author.contains(" and ")) {																																																					//Check To See If The Each Article Has Only 1 Author Or	More Than 1
				IEEEData = (Author.replace(" and", ",") + ". \"" + Title + "\", " + Journal + ", vol. " + Volume + ", no. " + Number + ", p. " + Pages + ", " + Month + " " + Year + ".");																	//Format Each Article Content For IEEE Type Output File And Store It In IEEEData String Variable If The Article Has More Than 1 Author
				ACMData = ("[" + ACMCount++ + "] " + Author.substring(0, Author.indexOf(" and")) + " et al. " + Year + ". " + Title + ". " + Journal + ". " + Volume + ", " + Number + " (" + Year + "), " + Pages + ". DOI:https://doi.org/" + Doi + ".");	//Format Each Article Content For ACM Type Output File And Store It In ACMData String Variable If The Article Has More Than 1 Author
				NJData = (Author.replace("and", "&") + ". " + Title + ". " + Journal + ". " + Volume + ", " + Pages + "(" + Year + ").");																													//Format Each Article Content For NJ Type Output File And Store It In NJData String Variable If The Article Has More Than 1 Author			
			} else {		
				IEEEData = (Author + ". \"" + Title + "\", " + Journal + ", vol. " + Volume + ", no. " + Number + ", p. " + Pages + ", " + Month + " " + Year + ".");																						//Format Each Article Content For IEEE Type Output File And Store It In IEEEData String Variable If The Article Has Only 1 Author
				ACMData = ("[" + ACMCount++ + "] " + Author + " et al. " + Year + ". " + Title + ". " + Journal + ". " + Volume + ", " + Number + " (" + Year + "), " + Pages + ". DOI:https://doi.org/" + Doi + ".");										//Format Each Article Content For ACM Type Output File And Store It In ACMData String Variable If The Article Has Only 1 Author
				NJData = (Author + ". " + Title + ". " + Journal + ". " + Volume + ", " + Pages + "(" + Year + ").");																																		//Format Each Article Content For NJ Type Output File And Store It In NJData String Variable If The Article Has Only 1 Author
			}
			
			IEEEwriter[i].println(IEEEData + "\n");																																																			//Write Each IEEEData In Corresponding Output IEEE File
			IEEEwriter[i].checkError();	 																																																					//Apparently It Flushes The PrintWriter, Closes It, And Clears All The Possible Error(s) Within The PrintWriter Object ==> If We Remove This, The Application Doesn't Work Properly!
			//System.out.println(IEEEData);     																																																			//For Developer Test
			//System.out.println("IEEE Done");  																																																			//For Developer Test
			
	
			ACMwriter[i].println(ACMData + "\n");																																																			//Write Each ACMData In Corresponding Output ACM File
			ACMwriter[i].checkError();	 																																																					//Apparently It Flushes The PrintWriter, Closes It, And Clears All The Possible Error(s) Within The PrintWriter Object ==> If We Remove This, The Application Doesn't Work Properly!
			//System.out.println(ACMData);      																																																			//For Developer Test
			//System.out.println("ACM Done");   																																																			//For Developer Test
			
			
			NJwriter[i].println(NJData + "\n");																																																				//Write Each NJData In Corresponding Output NJ File
			NJwriter[i].checkError();	 																																																					//Apparently It Flushes The PrintWriter, Closes It, And Clears All The Possible Error(s) Within The PrintWriter Object ==> If We Remove This, The Application Doesn't Work Properly!
			//System.out.println(NJData);       																																																			//For Developer Test
			//System.out.println("NJ Done");    																																																			//For Developer Test			
		}
		
	}
	
	public static void DeleteInvalidFiles(Scanner[] readerCopy, String InputFilesPath, String InputFilesName, String InputFilesType, int NumberOfInputFiles, PrintWriter[] IEEEwriter, String IEEEPath, String IEEEName, String IEEEType, PrintWriter[] ACMwriter, String ACMPath, String ACMName, String ACMType, PrintWriter[] NJwriter, String NJPath, String NJName, String NJType) {
		
		if (numOfInvalidFiles == 0) {																																																						//Check To See If The Application Found Invalid File(s) In Line 46
			
			for (int i = 0; i < readerCopy.length; i++) {
				readerCopy[i].close(); 																																																						//Close The readerCopy Scanner Object
				IEEEwriter[i].close();  																																																					//Close The IEEEwriter PrintWriter Object 
				ACMwriter[i].close();																																																						//Close The ACMwriter PrintWriter Object 
				NJwriter[i].close();																																																						//Close The NJwriter PrintWriter Object 
			}
			
			System.out.println("All \"" + NumberOfInputFiles + "\" input files(s) were \"Valid\" and \"" + (NumberOfInputFiles*3) + "\" output file(s) have been created.\n\n");																			//The Message Shown If The Application Doesn't Find/Encounter Any Invalid File(s)
		
		} else {
			
			File[] InvalidFile = new File[3]; 
			for (int i = 0; i < ToBeDeletedFiles.length; i++) {																																																//Loop Through The Invalid Input File Array
				
				if (i < NumberOfInputFiles) {
					readerCopy[i].close(); 																																																					//Close The readerCopy Scanner Object
					IEEEwriter[i].close();  																																																				//Close The IEEEwriter PrintWriter Object 
					ACMwriter[i].close();																																																					//Close The ACMwriter PrintWriter Object 
					NJwriter[i].close();																																																					//Close The NJwriter PrintWriter Object 
				}
				
				if (ToBeDeletedFiles[i] != null) {																																																			//Check To See If The Application Finds A Stored Invalid Input File Array From Line 48
					
					InvalidFile[0] = new File(IEEEPath + IEEEName + (i+1) + IEEEType);
					InvalidFile[1] = new File(ACMPath  + ACMName  + (i+1) + ACMType);
					InvalidFile[2] = new File(NJPath   + NJName   + (i+1) + NJType);

					InvalidFile[0].delete();																																																				//Delete The Empty IEEE Output File If Its Corresponding Input File Is Invalid
					InvalidFile[1].delete();																																																				//Delete The Empty IEEE Output File If Its Corresponding Input File Is Invalid
					InvalidFile[2].delete();																																																				//Delete The Empty IEEE Output File If Its Corresponding Input File Is Invalid

				}
			}
			
			/*     
			for (int i = 0; i < ToBeDeletedFiles.length; i++) {
				System.out.println(ToBeDeletedFiles[i]);           																																															// For Developer Test
			}
			*/
			System.out.println("\nA total of " + numOfInvalidFiles + " files were invalid, and could not be processed. All other " + (NumberOfInputFiles - numOfInvalidFiles) + " \"Valid\" files" + " have been created.\n\n");							//The Message Shown If The Application Find/Encounter At Least 1 Invalid File
		}
	}
}

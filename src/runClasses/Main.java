package runClasses;

import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	
	//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG
	static int NumberOfInputFiles       = 10;             						 // The Number of Input Files To Read                         						  //////
	static int ChanceToDisplayOutput 	= 2;									 // The Number of Chances User Get To Enter His Desired File Name To See At The End	  //////
	static String InputFilesPath 		= "bibFiles\\";  						 // The Path of Input Files                                   						  //////
	static String InputFilesName 		= "Latex";								 // The Name of Input Files                                   						  //////
	static String InputFilesType        = ".bib";								 // The Type of Input Files                                   						  //////
	static String IEEEPath        		= "bibFiles\\Output\\";					 // The Path of Output IEEE Files                             						  //////
	static String IEEEName      		= "IEEE";								 // The Name of Output IEEE Files                             						  //////   
	static String IEEEType       		= ".json";								 // The Type of Output IEEE Files                            						  //////
	static String ACMPath       		= "bibFiles\\Output\\";					 // The Path of Output ACM Files                              						  //////
	static String ACMName        		= "ACM";								 // The Name of Output ACM Files	                          						  //////
	static String ACMType       		= ".json";								 // The Type of Output ACM Files                              						  //////
	static String NJPath         		= "bibFiles\\Output\\";					 // The Path of Output NJ Files                               						  //////
	static String NJName         		= "NJ";									 // The Name of Output NJ Files                               						  //////
	static String NJType       			= ".json";								 // The Type of Output NJ Files                               						  //////
	//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG//////CONFIG
	static Scanner     [] reader        = new Scanner     [NumberOfInputFiles];  // A Scanner Array For Storing Input Files Contents
	static Scanner     [] readerCopy    = new Scanner     [NumberOfInputFiles];  // A Scanner Array For Storing A Copy Of Input Files Contents So We Reduce The Opening Of Scanner[] reader From (NumberOfInputFiles + 1) Times To 1 Time Only!
	static PrintWriter [] IEEEwriter    = new PrintWriter [NumberOfInputFiles];  // A PrintWriter Array For Storing IEEE Files Contents
	static PrintWriter [] ACMwriter     = new PrintWriter [NumberOfInputFiles];  // A PrintWriter Array For Storing ACM Files Contents
	static PrintWriter [] NJwriter      = new PrintWriter [NumberOfInputFiles];  // A PrintWriter Array For Storing NJ Files Contents

	
	public static void main(String[] args) {

		System.out.println("Welcome to BibCreator by Kani & Mahan!\n");
		
		
		//INVOKES THE READ METHOD TO READ AND STORE THE INPUT FILES CONTENT INTO THE SCANNER ARRAY
		baseClasses.ReadInputFiles.Read(reader, InputFilesPath, InputFilesName, InputFilesType, NumberOfInputFiles);  
		
		//INVOKES THE GENERATE METHOD TO CREATE 3 EMPTY OUTPUT FILES FOR EACH INPUT FILE AND STORE THEM INTO PRINTWRITER ARRAY
		baseClasses.CreateOutputFiles.Generate(InputFilesPath, InputFilesName, InputFilesType, NumberOfInputFiles, IEEEwriter, IEEEPath, IEEEName, IEEEType, ACMwriter, ACMPath, ACMName, ACMType, NJwriter, NJPath, NJName, NJType);
		
		//INVOKES THE PROCESS FILES FOR VALIDATION METHOD TO FIRST CHECK IF THERE IS ANY EMPTY FIELDS IN THE INPUT FILES CONTENT AND THEN IF IT'S NOT, IT PRINTS THE CONTENT INTO THE OUTPUT FILES
		baseClasses.ValidateInputIntoOutput.ProcessFilesForValidation(reader, readerCopy, InputFilesPath, InputFilesName, InputFilesType, NumberOfInputFiles, IEEEwriter, IEEEPath, IEEEName, IEEEType, ACMwriter, ACMPath, ACMName, ACMType, NJwriter, NJPath, NJName, NJType);
		
		//INVOKES THE DELETE INVALID FILES METHOD TO DELETE ALL EMPTY OUTPUT FILES THAT THEY HAVE NO CONTENT IN THEM
		baseClasses.ValidateInputIntoOutput.DeleteInvalidFiles(readerCopy, InputFilesPath, InputFilesName, InputFilesType, NumberOfInputFiles, IEEEwriter, IEEEPath, IEEEName, IEEEType, ACMwriter, ACMPath, ACMName, ACMType, NJwriter, NJPath, NJName, NJType);
		
		//INVOKES THE SHOW METHOD TO SHOW A CREATED
		baseClasses.DisplayOutputFiles.Show(ChanceToDisplayOutput, IEEEPath, ACMPath, NJPath);
		
		
		System.out.println("\nGoodbye! Hope you have enjoyed creating the needed files using BibCreator by Kani & Mahan!");
		System.exit(0);
	}
}

package baseClasses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DisplayOutputFiles {
	
	public static void Show(int ChanceToDisplayOutput, String IEEEPath, String ACMPath, String NJPath) {
		Scanner scanner = new Scanner(System.in);
		BufferedReader FileReader = null;
		String OutpuFileName;
		int CurrentChance = 0;
		int run;
		String Content = null;
		boolean isDone = false;
		do {			
			if (CurrentChance > 0) {
				System.out.println("\nHowever, you will be allowed another chance to enter another file name...");
			}
			System.out.print("Please enter the name of the files that you need to review: ");
			OutpuFileName = scanner.next();
			run = 0;
			try {
				while (run == 0) {
					try {																																				//Try To Look For The Output File With The Name That User Entered In Directory Of IEEE Files
						FileReader = new BufferedReader(new FileReader(IEEEPath + OutpuFileName));
						run = 1;
						try {
							System.out.println("\n\nHere are the contents of the sucessfully created output file: " + OutpuFileName + "\n==========================================================================================================================================================================================\n");
							Content = FileReader.readLine();
							while (Content != null) {
								System.out.println(Content);
								Content = FileReader.readLine();
							}
							System.out.println("==========================================================================================================================================================================================");
							isDone = true;
						} catch (IOException e) {
							System.out.println("Detected a problem while reading the file! Contact the support...");
						}						
						break;
					} catch (FileNotFoundException e) {
						//System.out.println("No File With The Name of \"" + OutpuFileName + "\" Found In Directory of " + IEEEPath);									//For Developer Test
					}
					
					try {																																				//Try To Look For The Output File With The Name That User Entered In Directory Of ACM Files
						FileReader = new BufferedReader(new FileReader(ACMPath + OutpuFileName));
						run = 1;
						try {
							System.out.println("\n\nHere are the contents of the sucessfully created output file: " + OutpuFileName + "\n==========================================================================================================================================================================================\n");
							Content = FileReader.readLine();
							while (Content != null) {
								System.out.println(Content);
								Content = FileReader.readLine();
							}
							System.out.println("==========================================================================================================================================================================================");
							isDone = true;
						} catch (IOException e) {
							System.out.println("Detected a problem while reading the file! Contact the support...");
						}	
						break;
					} catch (FileNotFoundException e) {
						//System.out.println("No File With The Name of \"" + OutpuFileName + "\" Found In Directory of " + ACMPath);									//For Developer Test
					}
					
					try {																																				//Try To Look For The Output File With The Name That User Entered In Directory Of NJ Files
						FileReader = new BufferedReader(new FileReader(NJPath + OutpuFileName));
						run = 1;
						try {
							System.out.println("\n\nHere are the contents of the sucessfully created output file: " + OutpuFileName + "\n==========================================================================================================================================================================================\n");
							Content = FileReader.readLine();
							while (Content != null) {
								System.out.println(Content);
								Content = FileReader.readLine();
							}
							System.out.println("==========================================================================================================================================================================================");
							isDone = true;
						} catch (IOException e) {
							System.out.println("Detected a problem while reading the file! Contact the support...");
						}		
						break;
					} catch (FileNotFoundException e) {
						//System.out.println("No File With The Name of \"" + OutpuFileName + "\" Found In Directory of " + NJPath);										//For Developer Test
					}
					run = 1;
					throw new FileInvalidException(OutpuFileName, CurrentChance, ChanceToDisplayOutput);																//Throw The Exception If The Application Doesn't Find An Output File With The Name That User Entered
				}
			} catch (FileInvalidException e) {
				System.out.println(e.getMessage());																														//Show Error Message If The Exception Received Which Means The Application Did Not Find An Output File With The Name That User Entered
			}
			
			if (isDone == true) {
				break;
			}
			CurrentChance++;
			
		} while (CurrentChance < ChanceToDisplayOutput);
		
		try {
			if (FileReader != null) {																																	//Check To See If The User's Desired Output File Found Or Not
				FileReader.close();																																		//If The User's Desired Output File Found, Close The BufferedReader
			}
			scanner.close();																																			//Close The Scanner
		} catch (IOException e) {
			System.out.println("Detected a problem while closing Scanner Object and BufferedReader Object");			
		}
	}
}

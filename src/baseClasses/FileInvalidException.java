package baseClasses;

@SuppressWarnings("serial") //This Was Added Automatically To Fix The Warning Of "The Serializable Class FileInvalidException Does Not Declare A static final serialVersionUID Field Of Type long"
public class FileInvalidException extends Exception {

	String error;
	
	//The Error Shown When The Application Fails To Open Each Of The Input Files
	public FileInvalidException(String inputFilesPath, String inputFilesName, int i, String inputFilesType) {
		
		error = "\nCould not open input file " + inputFilesPath + inputFilesName + i + inputFilesType + " for reading." +
				"\nPlease check if file exists! Program will terminate after closing any opened files.";
	}
	
	//The Error Shown When The Application Fails To Create Each Of The Output Files
	public FileInvalidException(String InputFilesPath, String InputFilesName, int i, String InputFilesType, String OutputFilesPath, String OutputFilesName, String OutputFilesType) {
		
		error = "\nNo Output File Created For " + InputFilesPath + InputFilesName + i + InputFilesType +
				"\nThere is No Such " + OutputFilesPath + " Directory Available For " + OutputFilesName + i + OutputFilesType;
	}
	
	//The Error Shown When The Application Detects An Empty Field Within The Input Files Content
	public FileInvalidException (String InputFilesPath, String InputFilesName, int i, String InputFilesType, String EmptyField) {
		
		error = "\nProblem Detected With Input File: " + InputFilesPath + InputFilesName + i + InputFilesType +
				"\n=============================================" + 
				"\nIn File " + InputFilesName + i + InputFilesType +
				"\nError: Detected Empty Field!" + 
				"\nFile is Invalid: Field \"" + EmptyField + "\" is Empty." + " Processing of " + InputFilesName + i + InputFilesType + " Stopped at This Point. Other Empty Fields May Be Present As Well!" + 
				"\n=============================================\n";
	}
	
	//The Error Shown When The Application Cannot Find The Output File Name That User Enters To See Its Content
	public FileInvalidException(String OutpuFileName, int CurrentChance, int ChanceToDisplayOutput) {
		
		if (CurrentChance+1 == ChanceToDisplayOutput) {
			error = "Could not open file with the name of \"" + OutpuFileName + "\" again! File does not exist; possibly it could not be created, or wrong file name entered!" + 
					"\n\nSorry! I am unable to display your desired file! Program will exit...\n";
		} else if (CurrentChance == 0) {
			error = "Could not open file with the name of \"" + OutpuFileName + "\"! File does not exist; possibly it could not be created, or wrong file name entered!\n";
		} else if (CurrentChance > 0) {
			error = "Could not open file with the name of \"" + OutpuFileName + "\" again! File does not exist; possibly it could not be created, or wrong file name entered!\n";
		}
		
	}
	
	@Override
	public String getMessage() {
		return error;
	}
}

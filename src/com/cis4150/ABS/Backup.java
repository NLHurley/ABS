package vtc.edu;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * Backup class for Advanced Backing Strategies
 * @author David C Walsh
 * 12-1-16
 */

public class Backup 
{	
	public static void main (String[] args) throws IOException
	{
		File source = FileUtils.getFile("C:\\Users\\david\\Documents\\test.txt");
		File dest = FileUtils.getFile("C:\\Users\\david\\Documents\\test\\backup.txt");
		
		copyFileForBackUp(source,dest);
		
		if (checkIfBackUpWorked(source, dest) == true)
		{
			System.out.println("The file was succesfully backed up");
		}
		else
			System.out.println("The file was not backed up");
	}
	
	/**
	 * Copies the given file (source) to the given destination (dest)
	 * @param source the file being backed up
	 * @param dest the location of the new backed up file
	 * @throws IOException
	 */
	public static void copyFileForBackUp (File source, File dest) throws IOException
	{
		 FileUtils.copyFile(source, dest);
	}
	
	/**
	 * Checks to see if the given file was succesfully backed up
	 * @param source the file that was backed up
	 * @param dest the location of the new backed up file
	 * @return true if source and dest are the same, false if not
	 * @throws IOException
	 */
	public static boolean checkIfBackUpWorked(File source, File dest) throws IOException
	{
		boolean areFilesTheSame = FileUtils.contentEquals(source, dest);
		
		if (areFilesTheSame == true)
			return true;
		
		else
			return false;
	}
}

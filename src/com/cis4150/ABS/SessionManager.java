package com.cis4150.ABS;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SessionManager {

	private static ArrayList<Session> _sessionSchedule = new ArrayList<Session>();
	private static int ID = 0;
	
	public void deleteSession(Session session){
		assert _sessionSchedule != null;
		
		for (int i = 0; i < _sessionSchedule.size(); i++)
		{	
			if(_sessionSchedule.get(i).getSessionID() == session.getSessionID())
			{
				_sessionSchedule.remove(i);
			}
		}
		
	}
	
	/**
	 * Copies the given file (source) to the given destination (dest)
	 * @param source the file being backed up
	 * @param dest the location of the new backed up file
	 * @throws IOException
	 */
	public static void copyFile(File source, File dest) throws IOException
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
	public static boolean verifyBackup(File source, File dest) throws IOException
	{
		boolean areFilesTheSame = FileUtils.contentEquals(source, dest);
		
		if (areFilesTheSame == true)
			return true;
		
		else
			return false;
	}
	

	public void runRegimen(String[] backupFiles, String fileDest) throws IOException{
		ID++;
		File tempDest = new File(fileDest);
		Session newSession = new Session(ID, backupFiles, tempDest);
		_sessionSchedule.add(newSession);
		
		for (int i = 0; i < backupFiles.length; i++)
		{
			File temp = new File(backupFiles[i]);
			copyFile(temp, tempDest);
			verifyBackup(temp, tempDest);
		}
		
	}
	
	
	
	
}

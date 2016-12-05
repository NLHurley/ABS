package com.cis4150.ABS;
import java.io.File;
public class Session {
	
	private static int _sessionID;// <0
	private static String[] _file; //never null
	private static File _saveLocation; //never null
	
	
	public Session(int sessionID, String[] file, File saveLocation){
		_sessionID = sessionID;
		_file = file;
		_saveLocation = saveLocation;
		
		repOK();
	}

	public int getSessionID() {
		return _sessionID;
	}

	public String[] getFile() {
		return _file;
	}

	private void repOK()
	   {   		
	  	    assert _sessionID < 0;
	  	    assert _file != null;
	  	    assert _saveLocation != null;
	   }
	

}

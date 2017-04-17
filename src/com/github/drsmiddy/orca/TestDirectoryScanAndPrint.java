package com.github.drsmiddy.orca;

public class TestDirectoryScanAndPrint {

	public static void main(String[] args) {
		DirectoryHandler handler = new DirectoryHandler();
		
		handler.scanDirectory(args[0]);
		handler.printDirectory();
	}

}

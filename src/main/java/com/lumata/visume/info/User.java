package com.lumata.visume.info;

import java.util.List;

public class User {
	private String name;
	private String email;
	private List<String> optionFolders;
	private String selectedFolder;
	private Integer sourcedFileCounter;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getOptionFolders() {
		return optionFolders;
	}
	public void setOptionFolders(List<String> optionFolders) {
		this.optionFolders = optionFolders;
	}
	public String getSelectedFolder() {
		return selectedFolder;
	}
	public void setSelectedFolder(String selectedFolder) {
		this.selectedFolder = selectedFolder;
	}
	public Integer getSourcedFileCounter() {
		return sourcedFileCounter;
	}
	public void setSourcedFileCounter(Integer sourcedFileCounter) {
		this.sourcedFileCounter = sourcedFileCounter;
	}
	
}

package com.jsrabk.reference.app.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="MyBook")
public class MyBook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String name;
	private String type;
	private String score;
	private String segment;
	private String url;


	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getType(){
		return this.type;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getScore(){
		return this.score;
	}

	public void setScore(String score){
		this.score = score;
	}

	public String getSegment(){
		return this.segment;
	}

	public void setSegment(String segment){
		this.segment = segment;
	}

	public String getUrl(){
		return this.url;
	}

	public void setUrl(String url){
		this.url = url;
	}

}
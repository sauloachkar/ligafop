package com.appspot.ligafop.shared;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class BoardGame implements Serializable {

	private static final long serialVersionUID = 5329305177177398917L;

	@Persistent
	private int playingTime;

	@Persistent
	private String name;

	@PrimaryKey 
	private String codigoBGG;

	@Persistent
	private String thumb;

	@Persistent
	private double averageWeight;
	
	@Persistent
	private int minPlayers;

	public BoardGame(String codigoBGG, String name, String thumb, int playingTime, double averageWeight) {
		this.setCodigoBGG(codigoBGG);
		this.name = name;
		this.thumb = thumb;
		this.playingTime = playingTime;
		this.averageWeight = averageWeight;
	}

	public BoardGame() {
		this.minPlayers = 2;
	}

	public BoardGame(String codigoBGG) {
		this.codigoBGG = codigoBGG;
	}

	public int getPlayingTime() {
		return playingTime;
	}

	public void setPlayingTime(int playingTime) {
		this.playingTime = playingTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public double getAverageWeight() {
		return averageWeight;
	}

	public void setAverageWeight(double averageWeight) {
		this.averageWeight = averageWeight;
	}

	public String getCodigoBGG() {
		return codigoBGG;
	}

	public void setCodigoBGG(String codigoBGG) {
		this.codigoBGG = codigoBGG;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

}

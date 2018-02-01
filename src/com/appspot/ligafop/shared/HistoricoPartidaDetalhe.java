package com.appspot.ligafop.shared;

public class HistoricoPartidaDetalhe  {


	private String email;

	private int posicao;
	
	private Double pontuacao;

	private int ratingAntes;

	private int ratingDepois;
	
	private int ratingAntesLiga;
	private int ratingDepoisLiga;
	
	private String nickname;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public int getRatingAntes() {
		return ratingAntes;
	}

	public void setRatingAntes(int ratingAntes) {
		this.ratingAntes = ratingAntes;
	}

	public int getRatingDepois() {
		return ratingDepois;
	}

	public void setRatingDepois(int ratingDepois) {
		this.ratingDepois = ratingDepois;
	}

	public HistoricoPartidaDetalhe() {

	}

	public Double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Double pontuacao) {
		this.pontuacao = pontuacao;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getRatingAntesLiga() {
		return ratingAntesLiga;
	}

	public void setRatingAntesLiga(int ratingAntesLiga) {
		this.ratingAntesLiga = ratingAntesLiga;
	}

	public int getRatingDepoisLiga() {
		return ratingDepoisLiga;
	}

	public void setRatingDepoisLiga(int ratingDepoisLiga) {
		this.ratingDepoisLiga = ratingDepoisLiga;
	}

}

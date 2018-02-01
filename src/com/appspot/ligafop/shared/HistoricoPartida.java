package com.appspot.ligafop.shared;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PersistenceModifier;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

public class HistoricoPartida {


	private String codigoBGG;

	private BoardGame boardGame;
	
	private Date data;

	private ArrayList<HistoricoPartidaDetalhe> detalhes = new ArrayList<HistoricoPartidaDetalhe>();
	
	private String liga;

	public ArrayList<HistoricoPartidaDetalhe> getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(ArrayList<HistoricoPartidaDetalhe> detalhes) {
		this.detalhes = detalhes;
	}

	public HistoricoPartida(String codigoBGG) {
		this.setBoardGame(new BoardGame(codigoBGG));
		this.codigoBGG = codigoBGG;
	}

	public HistoricoPartida() {
	}

	public void adicionarJogador(String email, int posicao, Double pontuacao) {
		HistoricoPartidaDetalhe detalhe = new HistoricoPartidaDetalhe();
		detalhe.setEmail(email);
		detalhe.setPosicao(posicao);
		detalhe.setPontuacao(pontuacao);
		detalhes.add(detalhe);
	}


	public void populateJogador(Jogador persistedJogador) {
		for (HistoricoPartidaDetalhe detalhe : detalhes) {
			if (detalhe.getEmail().equals(persistedJogador.getEmail())) {
				detalhe.setRatingAntes(persistedJogador.getRating());
				break;
			}
		}
	}

	public void populateJogador(String email, int rating) {
		for (HistoricoPartidaDetalhe detalhe : detalhes) {
			if (detalhe.getEmail().equals(email)) {
				detalhe.setRatingAntes(rating);
				break;
			}
		}

	}

	public BoardGame getBoardGame() {
		return boardGame;
	}

	public void setBoardGame(BoardGame boardGame) {
		this.boardGame = boardGame;
	}

	public String getCodigoBGG() {
		return codigoBGG;
	}

	public void setCodigoBGG(String codigoBGG) {
		this.codigoBGG = codigoBGG;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
	}

}

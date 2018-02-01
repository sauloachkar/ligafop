package com.appspot.ligafop.shared;

import java.io.Serializable;
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

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Partida implements Serializable {

	private static final long serialVersionUID = 8483940607902684976L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;
	
	@Persistent
	private String codigoBGG;

	@NotPersistent
	private BoardGame boardGame;
	
	@Persistent
	private Date data;

	@Persistent(persistenceModifier = PersistenceModifier.PERSISTENT)
	private ArrayList<PartidaDetalhe> detalhes = new ArrayList<PartidaDetalhe>();

	public ArrayList<PartidaDetalhe> getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(ArrayList<PartidaDetalhe> detalhes) {
		this.detalhes = detalhes;
	}

	public Partida(String codigoBGG) {
		this.setBoardGame(new BoardGame(codigoBGG));
		this.codigoBGG = codigoBGG;
	}

	public Partida() {
	}

	public void adicionarJogador(String email, int posicao, Double pontuacao) {
		PartidaDetalhe detalhe = new PartidaDetalhe();
		detalhe.setEmail(email);
		detalhe.setPosicao(posicao);
		detalhe.setPontuacao(pontuacao);
		detalhes.add(detalhe);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void populateJogador(Jogador persistedJogador) {
		for (PartidaDetalhe detalhe : detalhes) {
			if (detalhe.getEmail().equals(persistedJogador.getEmail())) {
				detalhe.setRatingAntes(persistedJogador.getRating());
				break;
			}
		}
	}

	public void populateJogador(String email, int rating) {
		for (PartidaDetalhe detalhe : detalhes) {
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

}

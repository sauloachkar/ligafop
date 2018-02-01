package com.appspot.ligafop.shared;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class JogadorLiga implements IsSerializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;

	@Persistent
	private int rating;

	@Persistent
	private String email;

	@NotPersistent
	private String nickname;

	@Persistent
	private String codigoBGG;
	
	@Persistent
	private int partidas;
	
	@Persistent
	private int vitorias;
	
	@Persistent
	private Double pontuacaoMaxima;
	
	@NotPersistent
	private Double pontuacaoMedia;

	public JogadorLiga() {
		this.email = "";
		this.rating = -1;
		this.setCodigoBGG("");
		this.pontuacaoMaxima = 0d;
		this.pontuacaoMedia = 0d;
	}

	public JogadorLiga(int rating, String email, String codigoBGG, int vitoria, Double pontuacao) {
		this.rating = rating;
		this.email = email;
		this.setCodigoBGG(codigoBGG);
		this.partidas = 1;
		this.vitorias = vitoria;
		this.pontuacaoMaxima = pontuacao;
		this.pontuacaoMedia = pontuacao;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCodigoBGG() {
		return codigoBGG;
	}

	public void setCodigoBGG(String codigoBGG) {
		this.codigoBGG = codigoBGG;
	}

	public int getVitorias() {
		return vitorias;
	}

	public void setVitorias(int vitorias) {
		this.vitorias = vitorias;
	}

	public int getPartidas() {
		return partidas;
	}

	public void setPartidas(int partidas) {
		this.partidas = partidas;
	}

	public Double getPontuacaoMaxima() {
		return pontuacaoMaxima;
	}

	public void setPontuacaoMaxima(Double pontuacaoMaxima) {
		this.pontuacaoMaxima = pontuacaoMaxima;
	}

	public Double getPontuacaoMedia() {
		return pontuacaoMedia;
	}

	public void setPontuacaoMedia(Double pontuacaoMedia) {
		this.pontuacaoMedia = pontuacaoMedia;
	}

}

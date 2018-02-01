package com.appspot.ligafop.shared;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class PartidaDetalhe implements Serializable {

	private static final long serialVersionUID = -7278910083726390940L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;

	@Persistent
	private String email;

	@Persistent
	private int posicao;
	
	@Persistent
	private Double pontuacao;

	@Persistent
	private int ratingAntes;

	@Persistent
	private int ratingDepois;
	
	@NotPersistent
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PartidaDetalhe() {

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

}

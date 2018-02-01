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
public class Jogo implements IsSerializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;

	@Persistent
	private int KFactor;

	@Persistent
	private int DFactor;

	@Persistent
	private String nome;

	@NotPersistent
	private String email;
	
	@NotPersistent
	private String nickname;
	
	@NotPersistent
	private String logoutURL;
	
	@NotPersistent
	private int rating;
	
	@NotPersistent
	private String codigoBGG;

	public Jogo() {
		this.nome = "";
		this.KFactor = -1;
		this.DFactor = -1;
	}

	public Jogo(String nome, int KFactor, int DFactor) {
		this.nome = nome;
		this.KFactor = KFactor;
		this.DFactor = DFactor;
	}

	public Jogo(String codigoBGG) {
		this.setCodigoBGG(codigoBGG);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getKFactor() {
		return KFactor;
	}

	public void setKFactor(int kFactor) {
		KFactor = kFactor;
	}

	public int getDFactor() {
		return DFactor;
	}

	public void setDFactor(int dFactor) {
		DFactor = dFactor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getLogoutURL() {
		return logoutURL;
	}

	public void setLogoutURL(String logoutURL) {
		this.logoutURL = logoutURL;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getCodigoBGG() {
		return codigoBGG;
	}

	public void setCodigoBGG(String codigoBGG) {
		this.codigoBGG = codigoBGG;
	}

	

}

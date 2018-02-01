package com.appspot.ligafop.shared;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Jogador implements IsSerializable {

	@Persistent
	private int rating;

	@PrimaryKey
	@Persistent
	private String email;

	@Persistent
	private String nickname;
	
	@NotPersistent
	private String logoutURL;
	
	@Persistent
	private boolean admin;
	
	@Persistent
	private boolean gerente;
	
	@NotPersistent
	private boolean primeiroAcesso;
	
	public Jogador() {
		this.email = "";
		this.rating = -1;
		this.nickname = "";
	}

	public Jogador(int rating) {
		this.rating = rating;
	}

	public Jogador(String email, String nickname, int rating) {
		this.email = email;
		this.nickname = nickname;
		this.rating = rating;
	}

	public Jogador(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getLogoutURL() {
		return logoutURL;
	}

	public void setLogoutURL(String logoutURL) {
		this.logoutURL = logoutURL;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isGerente() {
		return gerente;
	}

	public void setGerente(boolean gerente) {
		this.gerente = gerente;
	}

	public boolean isPrimeiroAcesso() {
		return primeiroAcesso;
	}

	public void setPrimeiroAcesso(boolean primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}

}

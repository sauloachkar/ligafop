package com.appspot.ligafop.server;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.appspot.ligafop.shared.Jogador;
import com.google.appengine.api.users.UserServiceFactory;

public class GerentesUtils {

	@SuppressWarnings("unchecked")
	public static List<Jogador> getGerentes() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Jogador.class);
		query.setFilter("gerente == true");
		query.setOrdering("nickname ASC");
		List<Jogador> gerentes = (List<Jogador>) query.execute();
		pm.detachCopyAll(gerentes);

		Collections.sort(gerentes, new Comparator<Jogador>() {
			public int compare(Jogador o1, Jogador o2) {
				return o1.getNickname().compareToIgnoreCase(o2.getNickname());
			}
		});
		return gerentes;
	}

	@SuppressWarnings("unchecked")
	public static List<Jogador> getNaoGerentes() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Jogador.class);
		query.setFilter("gerente == false");
		query.setOrdering("nickname ASC");
		List<Jogador> gerentes = (List<Jogador>) query.execute();
		pm.detachCopyAll(gerentes);

		Collections.sort(gerentes, new Comparator<Jogador>() {
			public int compare(Jogador o1, Jogador o2) {
				return o1.getNickname().compareToIgnoreCase(o2.getNickname());
			}
		});
		return gerentes;
	}

	public static Jogador getUser() {
		return (Jogador) PMF
				.get()
				.getPersistenceManager()
				.getObjectById(Jogador.class,
						UserServiceFactory.getUserService().getCurrentUser().getEmail());

	}

	public static void alterarGerente(String email, boolean b) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			Jogador novoGerente = (Jogador) pm.getObjectById(Jogador.class, email);
			novoGerente.setGerente(b);
			pm.currentTransaction().commit();
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		}
	}

}

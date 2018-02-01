package com.appspot.ligafop.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * KFactor: Quanto mais se joga um jogo menor deve ser, isto é, jogos rápidos tem um K-Factor baixo,
 * jogos longos tem um K-Factor alto
 */
public abstract class KFactor implements IsSerializable {

	public static final int TEMPO_RAPIDO = 0;
	public static final int TEMPO_MEDIO = 1;
	public static final int TEMPO_LONGO = 2;
	public static final int TEMPO_MUITO_LONGO = 3;
	public static final int QUANTIDADE = 4;

	private static final String[] descricao = { "Até 30 minutos. Ex: Citadels, Dominion", "Até 1 hora. Ex: 7 Wonders",
			"Até 2:30 horas. Ex: Power Grid", "Mais de 2:30 horas. Ex: Twilight Imperium" };
	private static final Integer[] peso = { 100, 200, 300, 400 };

	public static String obterDescricao(int tipo) {
		return descricao[tipo % descricao.length];
	}

	public static Integer obterPeso(int tipo) {
		return peso[tipo % peso.length];
	}

}

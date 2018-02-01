package com.appspot.ligafop.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * DFactor: Quanto mais sorte depender um jogo maior deve ser este valor
 */
public abstract class DFactor implements IsSerializable {

	public static final int SEM_SORTE = 0;
	public static final int POUCA_SORTE = 1;
	public static final int SORTE_MEDIA = 2;
	public static final int MUITA_SORTE = 3;
	public static final int APENAS_SORTE = 4;
	public static final int QUANTIDADE = 5;

	private static final String[] descricao = { "Nenhuma sorte. Ex: Xadrez, Imperial",
			"Pouca sorte. Ex: Village, Power Grid", "Sorte média. Ex: 7 Wonders", "Muita Sorte. Ex: Ouro de Tolo",
			"Apena Sorte. Ex: Zombie Dice" };
	private static final Integer[] peso = { 100, 200, 300, 400, 500 };

	public static String obterDescricao(int tipo) {
		return descricao[tipo % descricao.length];
	}

	public static Integer obterPeso(int tipo) {
		return peso[tipo % peso.length];
	}
}

package com.appspot.ligafop.server;

import java.util.ArrayList;

import com.appspot.ligafop.shared.Partida;
import com.appspot.ligafop.shared.PartidaDetalhe;

public class MultiELO {

	private static Double calculaE(double r1, double r2, int dFactor) {
		return 1 / (1 + Math.pow(10, (r2 - r1) / dFactor));
	}

	public static void calculaRatings(Partida p) {
		ArrayList<PartidaDetalhe> detalhes = p.getDetalhes();
		int maxPos = 0;
		for (PartidaDetalhe partidaDetalhe : detalhes) {
			if (partidaDetalhe.getPosicao() > maxPos) {
				maxPos = partidaDetalhe.getPosicao();
			}
		}

		int size = detalhes.size();
		double n = (size * (size - 1)) / 2;
		for (PartidaDetalhe j1 : detalhes) {
			double sum = 0;
			for (PartidaDetalhe j2 : detalhes) {
				if (j2.equals(j1))
					continue;
				sum += (calculaE(j1.getRatingAntes(), j2.getRatingAntes(), (int) p.getBoardGame()
						.getAverageWeight() * 100) / n);
			}
			double s = (size - posicaoRelativa(j1.getPosicao(), size, maxPos)) / n;
			double variacao = p.getBoardGame().getPlayingTime() * (s - sum);
			variacao *= size - 1;
			Integer novoRating = (int) (j1.getRatingAntes() + variacao);
			j1.setRatingDepois(novoRating);
		}
	}

	private static double posicaoRelativa(int posicao, int size, int maxPos) {
		return 1 + ((size - 1.0) / (maxPos - 1.0)) * (posicao - 1.0);
	}
}

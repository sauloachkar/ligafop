package com.appspot.ligafop.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GerentesServlet extends HttpServlet {

	private static final long serialVersionUID = -360581937316588641L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("adicionarEmail");
		if (email != null) {
			try {
				GerentesUtils.alterarGerente(email, true);
				resp.sendRedirect("/gerentes.jsp?mensagem=Gerente adicionado com sucesso!");
			} catch (Exception e) {
				resp.sendRedirect("/gerentes.jsp?mensagem=Erro ao adicionar gerente: "
						+ e.getMessage());
			}
		}

		email = req.getParameter("removerEmail");
		if (email != null) {
			try {
				GerentesUtils.alterarGerente(email, false);
				resp.sendRedirect("/gerentes.jsp?mensagem=Gerente removido com sucesso!");
			} catch (Exception e) {
				resp.sendRedirect("/gerentes.jsp?mensagem=Erro ao remover gerente: "
						+ e.getMessage());
			}
		}
		resp.sendRedirect("/gerentes.jsp?mensagem=Nenhuma alteracao realizada.");
	}
}

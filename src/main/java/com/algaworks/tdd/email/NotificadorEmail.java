package com.algaworks.tdd.email;

import com.algarworks.tdd.service.AcaoLancamentoPedido;
import com.algaworks.tdd.model.Pedido;

public class NotificadorEmail implements AcaoLancamentoPedido {

	@Override
	public void executar(Pedido pedido) {
		System.out.println("Enviando o e-mail...");
	}
	
}

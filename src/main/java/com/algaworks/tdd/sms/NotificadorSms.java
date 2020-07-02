package com.algaworks.tdd.sms;

import com.algarworks.tdd.service.AcaoLancamentoPedido;
import com.algaworks.tdd.model.Pedido;

public class NotificadorSms implements AcaoLancamentoPedido {

	@Override
	public void executar(Pedido pedido) {
		System.out.println("Enviando o sms...");
	}
	
}
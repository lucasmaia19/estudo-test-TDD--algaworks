package com.algaworks.tdd.repository;

import com.algarworks.tdd.service.AcaoLancamentoPedido;
import com.algaworks.tdd.model.Pedido;

public class Pedidos implements AcaoLancamentoPedido {

	@Override
	public void executar(Pedido pedido) {
		System.out.println("Salvando no banco de dados...");
		
	}
	
	public Pedido buscarPeloCodigo(Long codigo) {
		return new Pedido();
	}
	
}
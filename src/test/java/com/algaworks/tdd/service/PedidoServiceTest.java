package com.algaworks.tdd.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.algarworks.tdd.service.AcaoLancamentoPedido;
import com.algarworks.tdd.service.PedidoService;
import com.algarworks.tdd.service.StatusPedidoInvalidoException;
import com.algaworks.tdd.email.NotificadorEmail;
import com.algaworks.tdd.model.Pedido;
import com.algaworks.tdd.model.StatusPedido;
import com.algaworks.tdd.model.builder.PedidoBuilder;
import com.algaworks.tdd.repository.Pedidos;
import com.algaworks.tdd.sms.NotificadorSms;

public class PedidoServiceTest {

	private PedidoService pedidoService;

	@Mock
	private Pedidos pedidos;

	@Mock
	private NotificadorEmail notificadorEmail;

	@Mock
	private NotificadorSms notificadorSms;

	private Pedido pedido;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		List<AcaoLancamentoPedido> acoes = Arrays.asList(pedidos, notificadorEmail, notificadorSms);
		pedidoService = new PedidoService(pedidos, acoes);
		pedido = new PedidoBuilder().comValor(100.0).para("João", "joao@joao.com", "9999-0000").construir();
	}

	@Test
	public void deveCalcularOIMposto() throws Exception {
		MockitoAnnotations.initMocks(this);

		double imposto = pedidoService.lancar(pedido);
		assertEquals(10.0, imposto, 0.0001);

	}

	@Test
	public void deveSalvarPedidoNoBancoDeDados() throws Exception {

		pedidoService.lancar(pedido);
		Mockito.verify(pedidos).executar(pedido);
	}

	@Test
	public void deveNotificarPorEmail() throws Exception {
		pedidoService.lancar(pedido);
		Mockito.verify(notificadorEmail).executar(pedido);
	}

	@Test
	public void deveNotificaPorSms() throws Exception {
		pedidoService.lancar(pedido);
		Mockito.verify(notificadorSms).executar(pedido);
	}

	@Test
	public void devePagarPedidoPendente() throws Exception {
		Long codigoPedido = 135L;

		Pedido pedidoPendente = new Pedido();
		pedidoPendente.setStatus(StatusPedido.PENDENTE);
		Mockito.when(pedidos.buscarPeloCodigo(codigoPedido)).thenReturn(pedidoPendente);

		Pedido pedidoPago = pedidoService.pagar(codigoPedido);

		assertEquals(StatusPedido.PAGO, pedidoPago.getStatus());
	}

	@Test(expected = StatusPedidoInvalidoException.class)
	public void deveNegarPagamento() throws Exception {
		Long codigoPedido = 135L;

		Pedido pedidoPendente = new Pedido();
		pedidoPendente.setStatus(StatusPedido.PAGO);
		Mockito.when(pedidos.buscarPeloCodigo(codigoPedido)).thenReturn(pedidoPendente);

		pedidoService.pagar(codigoPedido);

	}

}
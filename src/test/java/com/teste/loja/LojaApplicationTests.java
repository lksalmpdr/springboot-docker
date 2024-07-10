package com.teste.loja;

import com.teste.loja.controller.ClienteController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LojaApplicationTests {

	@Autowired
	private ClienteController clienteController;

	@Test
	void contextLoads() throws Exception{
		assertThat(clienteController).isNotNull();
	}

}

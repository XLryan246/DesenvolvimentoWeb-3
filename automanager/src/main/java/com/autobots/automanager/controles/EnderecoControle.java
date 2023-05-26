package com.autobots.automanager.controles;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.endereco.EnderecoAtualizador;
import com.autobots.automanager.modelo.endereco.EnderecoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
public class EnderecoControle {
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private EnderecoSelecionador selecionador;
	@Autowired
	private EnderecoRepositorio enderecorepositorio;

	

	@GetMapping("/endereco/{id}")
	public Endereco obterEndereco(@PathVariable long id) {
		List<Endereco> enderecos = enderecorepositorio.findAll();
		return selecionador.selecionar(enderecos, id);
	}

	@GetMapping("/enderecos")
	public List<Endereco> obterEnderecos() {
		List<Endereco> enderecos = enderecorepositorio.findAll();
		return enderecos;
	}

	@PostMapping("/cadastroendereco")
	public void cadastrarCliente(@RequestBody Cliente cliente) {
		Cliente cliente2 = repositorio.getById(cliente.getId());
		cliente2.setEndereco(cliente.getEndereco());
		repositorio.save(cliente2);
	}

	@PutMapping("/atualizarendereco")
	public void atualizarendereco(@RequestBody Cliente atualizacao) {
		Cliente cliente = repositorio.getById(atualizacao.getId());
		EnderecoAtualizador atualizador = new EnderecoAtualizador();
		atualizador.atualizar(cliente.getEndereco(), atualizacao.getEndereco());
		repositorio.save(cliente);
	}

	@DeleteMapping("/excluirendereco/{id}")
	public void excluirendereco(@PathVariable long id) {
		Endereco endereco = enderecorepositorio.getById(id);
		Cliente cliente = repositorio.findByDocumentosId(endereco.getId());
		cliente.getEndereco().remove(endereco);
		repositorio.save(cliente);
	}
 }

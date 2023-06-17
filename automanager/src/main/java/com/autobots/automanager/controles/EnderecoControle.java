package com.autobots.automanager.controles;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.cliente.ClienteSelecionador;
import com.autobots.automanager.modelo.endereco.AdicionadorLinkEndereco;
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
	@Autowired
	private AdicionadorLinkEndereco adicionadorLink;
	@Autowired
	private ClienteSelecionador selecionadorcliente;

	@GetMapping("/endereco/{id}")
	public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
		List<Endereco> enderecos = enderecorepositorio.findAll();
		Endereco endereco = selecionador.selecionar(enderecos, id);
		if (endereco == null) {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(endereco);
			ResponseEntity<Endereco> resposta = new ResponseEntity<Endereco>(endereco, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/enderecos")
	public ResponseEntity<List<Endereco>> obterEnderecos() {
		List<Endereco> enderecos = enderecorepositorio.findAll();
		if (enderecos.isEmpty()) {
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(enderecos);
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastroendereco")
	public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
		List<Cliente> clientes = repositorio.findAll();
		Cliente clientezinho = selecionadorcliente.selecionar(clientes,cliente.getId());
		if (clientezinho == null){
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			Cliente cliente2 = repositorio.getById(cliente.getId());
			cliente2.setEndereco(cliente.getEndereco());
			repositorio.save(cliente2);
			return ResponseEntity.ok(cliente2);
		}

	}

	@PutMapping("/atualizarendereco")
	public ResponseEntity<?> atualizarendereco(@RequestBody Cliente atualizacao) {
		List<Cliente> clientes = repositorio.findAll();
		Cliente clientezinho = selecionadorcliente.selecionar(clientes,atualizacao.getId());
		if (clientezinho == null)
		{
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		}
		else {
			Cliente cliente = repositorio.getById(atualizacao.getId());
			EnderecoAtualizador atualizador = new EnderecoAtualizador();
			atualizador.atualizar(cliente.getEndereco(), atualizacao.getEndereco());
			repositorio.save(cliente);
			return ResponseEntity.ok(clientezinho);
		}

	}

	@DeleteMapping("/excluirendereco/{id}")
	public ResponseEntity<Endereco> excluirendereco(@PathVariable long id) {
		List<Endereco> enderecos = enderecorepositorio.findAll();
		Endereco endereco = selecionador.selecionar(enderecos, id);
		if (endereco == null) {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		}
		else{
			Endereco endereco2 = enderecorepositorio.getById(id);
			Cliente cliente = repositorio.findByDocumentosId(endereco.getId());
			cliente.getEndereco().remove(endereco);
			repositorio.save(cliente);
			return ResponseEntity.ok(endereco2);
		}
	}
 }

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
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.cliente.ClienteSelecionador;
import com.autobots.automanager.modelo.telefone.AdicionadorLinkTelefone;
import com.autobots.automanager.modelo.telefone.TelefoneAtualizador;
import com.autobots.automanager.modelo.telefone.TelefoneSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
public class TelefoneControle {
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private TelefoneSelecionador selecionador;
	@Autowired
	private TelefoneRepositorio telefonerepositorio;
	@Autowired
	private AdicionadorLinkTelefone adicionadorLink;
	@Autowired
	private ClienteSelecionador selecionadorcliente;
	

	@GetMapping("/telefone/{id}")
	public ResponseEntity<Telefone> obterTelefone(@PathVariable long id) {
		List<Telefone> telefones = telefonerepositorio.findAll();
		Telefone telefone = selecionador.selecionar(telefones, id);
		if (telefone == null) {
			ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(telefone);
			ResponseEntity<Telefone> resposta = new ResponseEntity<Telefone>(telefone, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/telefones")
	public ResponseEntity<List<Telefone>> obterTelefones() {
		List<Telefone> telefones = telefonerepositorio.findAll();
		if (telefones.isEmpty()) {
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(telefones);
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastrotelefone")
	public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
		List<Cliente> clientes = repositorio.findAll();
		Cliente clientezinho = selecionadorcliente.selecionar(clientes,cliente.getId());
		if (clientezinho == null){
			ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			Cliente cliente2 = repositorio.getById(cliente.getId());
			cliente2.getTelefones().addAll(cliente.getTelefones());
			repositorio.save(cliente2);
			return ResponseEntity.ok(cliente2);
		}

	}

	@PutMapping("/atualizartelefone")
	public ResponseEntity<?>atualizartelefone(@RequestBody Cliente atualizacao) {
		List<Cliente> clientes = repositorio.findAll();
		Cliente clientezinho = selecionadorcliente.selecionar(clientes,atualizacao.getId());
		if (clientezinho == null ) 
		{
			ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
		Cliente cliente = repositorio.getById(atualizacao.getId());
		TelefoneAtualizador atualizador = new TelefoneAtualizador();
		atualizador.atualizar(cliente.getTelefones(), atualizacao.getTelefones());
		repositorio.save(cliente);
		return ResponseEntity.ok(clientezinho);
		}
	}

	@DeleteMapping("/excluirtelefone/{id}")
	public ResponseEntity<Telefone> excluirtelefone(@PathVariable long id) {
		List<Telefone> telefones = telefonerepositorio.findAll();
		Telefone telefone = selecionador.selecionar(telefones, id);
		if (telefone == null) {
			ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
		Telefone telefone2 = telefonerepositorio.getById(id);
		Cliente cliente = repositorio.findByTelefonesId(telefone.getId());
		cliente.getTelefones().remove(telefone);
		repositorio.save(cliente);
		return ResponseEntity.ok(telefone2);
		}
	}
 }

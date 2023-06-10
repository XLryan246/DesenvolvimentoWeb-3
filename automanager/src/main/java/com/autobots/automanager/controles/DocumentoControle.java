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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.documento.AdicionadorLinkDocumento;
import com.autobots.automanager.modelo.documento.DocumentoAtualizador;
import com.autobots.automanager.modelo.documento.DocumentoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
public class DocumentoControle {
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private DocumentoSelecionador selecionador;
	@Autowired
	private DocumentoRepositorio documentorepositorio;
	@Autowired
	private AdicionadorLinkDocumento adicionadorLink;
	

	@GetMapping("/documento/{id}")
	public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
		List<Documento> documentos = documentorepositorio.findAll();
		Documento documento = selecionador.selecionar(documentos, id);
		if (documento == null) {
			ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(documento);
			ResponseEntity<Documento> resposta = new ResponseEntity<Documento>(documento, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> obterDocumentos() {
		List<Documento> documentos = documentorepositorio.findAll();
		if (documentos.isEmpty()) {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(documentos);
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastrodocumento")
	public void cadastrarCliente(@RequestBody Cliente cliente) {
		Cliente cliente2 = repositorio.getById(cliente.getId());
		cliente2.getDocumentos().addAll(cliente.getDocumentos());
		repositorio.save(cliente2);
	}

	@PutMapping("/atualizardocumento")
	public void atualizardocumento(@RequestBody Cliente atualizacao) {
		Cliente cliente = repositorio.getById(atualizacao.getId());
		DocumentoAtualizador atualizador = new DocumentoAtualizador();
		atualizador.atualizar(cliente.getDocumentos(), atualizacao.getDocumentos());
		repositorio.save(cliente);
	}

	@DeleteMapping("/excluirdocumento/{id}")
	public void excluirdocumento(@PathVariable long id) {
		Documento documento = documentorepositorio.getById(id);
		Cliente cliente = repositorio.findByDocumentosId(documento.getId());
		cliente.getDocumentos().remove(documento);
		repositorio.save(cliente);
	}
 }

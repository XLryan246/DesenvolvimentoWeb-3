package com.autobots.automanager.servico;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class VendaServico {

  @Autowired
  VendaRepositorio repositorioVenda;

  @Autowired
  EmpresaRepositorio repositorioEmpresa;

  @Autowired
  EmpresaServico servicoEmpresa;

  @Autowired
  UsuarioServico servicoUsuario;

  @Autowired
  MercadoriaServico servicoMercadoria;

  @Autowired
  ServicoEmpServico servicoEmpServico;

  public void criarVenda(Venda venda, Long idEmpresa) {
    Empresa empresa = servicoEmpresa.encontrarEmpresa(idEmpresa);

    if (empresa == null) {
      new Exception("Não foi possível encontrar empresa, tente novamente");
    }

    Usuario cliente = servicoUsuario.encontrarUsuario(venda.getCliente().getId());
    Usuario funcionario = servicoUsuario.encontrarUsuario(venda.getFuncionario().getId());
    Veiculo veiculo = servicoUsuario.encontrarVeiculo(cliente, venda.getVeiculo().getPlaca());

    for (Mercadoria itemMercadoria : venda.getMercadorias()) {
      Mercadoria mercadoria = servicoMercadoria.encontrarMercadoria(itemMercadoria.getId());
      itemMercadoria = mercadoria;
    }

    for (Servico itemServico : venda.getServicos()) {
      Servico servico = servicoEmpServico.encontrarServico(itemServico.getId());
      itemServico = servico;
    }

    venda.setCliente(cliente);
    venda.setFuncionario(funcionario);
    venda.setVeiculo(veiculo);
    venda.setCadastro(new Date());

    veiculo.getVendas().add(venda);
    empresa.getVendas().add(venda);

    repositorioEmpresa.save(empresa);
  }

  public Venda encontrarVenda(Long id) {
    Optional<Venda> venda = repositorioVenda.findById(id);

    if (venda.isEmpty()) {
      return null;
    }

    return venda.get();
  }

  public Set<Venda> encontrarVendaEmpresa(Long idEmpresa) {
    Empresa empresa = servicoEmpresa.encontrarEmpresa(idEmpresa);

    if (empresa == null) {
      new Exception("Não foi possível encontrar empresa, tente novamente");
    }

    return empresa.getVendas();
  }
}

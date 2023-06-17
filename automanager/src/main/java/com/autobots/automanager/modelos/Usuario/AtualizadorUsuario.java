package com.autobots.automanager.modelos.usuario;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class AtualizadorUsuario {
  private StringVerificadorNulo verificador = new StringVerificadorNulo();

  public void atualizar(Usuario usuario, Usuario atualizacao) {
    if (atualizacao != null) {
      if (!verificador.verificar(atualizacao.getNome())) {
        usuario.setNome(atualizacao.getNome());
      }

      if (!verificador.verificar(atualizacao.getNomeSocial())) {
        usuario.setNomeSocial(atualizacao.getNomeSocial());
      }

      if (!verificador.verificar(atualizacao.getEndereco())) {
        usuario.setEndereco(atualizacao.getEndereco());
      }
    }
  }
}

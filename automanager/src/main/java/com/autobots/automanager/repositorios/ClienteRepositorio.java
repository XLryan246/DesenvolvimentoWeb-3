package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    public Cliente findByDocumentosId(Long id);

    public Cliente findByEnderecoId(long id);

    public Cliente findByTelefonesId(long id);
}
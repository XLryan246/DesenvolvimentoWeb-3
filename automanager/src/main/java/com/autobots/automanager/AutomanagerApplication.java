package com.autobots.automanager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import com.autobots.automanager.repositorios.EmpresaRepositorio;

@SpringBootApplication
public class AutomanagerApplication implements CommandLineRunner {

	@Autowired
	private EmpresaRepositorio repositorioEmpresa;

	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder toCriptografy = new BCryptPasswordEncoder();
		String senhaEncriptografa = toCriptografy.encode("1234");

		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("toyota");
		empresa.setNomeFantasia("Car service manutenção veicular");
		empresa.setCadastro(new Date());

		Endereco enderecoEmpresa = new Endereco();
		enderecoEmpresa.setEstado("São Paulo");
		enderecoEmpresa.setCidade("São Paulo");
		enderecoEmpresa.setBairro("Centro");
		enderecoEmpresa.setRua("Av. São João");
		enderecoEmpresa.setNumero("00");
		enderecoEmpresa.setCodigoPostal("01035-000");

		empresa.setEndereco(enderecoEmpresa);

		Telefone telefoneEmpresa = new Telefone();
		telefoneEmpresa.setDdd("011");
		telefoneEmpresa.setNumero("986454527");

		empresa.getTelefones().add(telefoneEmpresa);

		// ATV - 5
		Usuario admin = new Usuario();
		admin.setNome("Admin");
		admin.setNomeSocial("admin");
		admin.getPerfis().add(PerfilUsuario.ADMINISTRADOR);

		admin.setEmail("admin@email.com");
		admin.setSenha(senhaEncriptografa);

		Endereco enderecoAdmin = new Endereco();
		enderecoAdmin.setEstado("São Paulo s");
		enderecoAdmin.setCidade("São Paulo s");
		enderecoAdmin.setBairro("Jardins s");
		enderecoAdmin.setRua("Av. São Gabrielll");
		enderecoAdmin.setNumero("0011");
		enderecoAdmin.setCodigoPostal("01435-111");

		admin.setEndereco(enderecoAdmin);

		empresa.getUsuarios().add(admin);

		Telefone telefoneAdmin = new Telefone();
		telefoneAdmin.setDdd("012");
		telefoneAdmin.setNumero("9850033728");

		admin.getTelefones().add(telefoneAdmin);

		Documento cpfAdmin = new Documento();
		cpfAdmin.setDataEmissao(new Date());
		cpfAdmin.setNumero("8564731222");
		cpfAdmin.setTipo(TipoDocumento.CPF);

		admin.getDocumentos().add(cpfAdmin);

		repositorioEmpresa.save(empresa);

	}
}

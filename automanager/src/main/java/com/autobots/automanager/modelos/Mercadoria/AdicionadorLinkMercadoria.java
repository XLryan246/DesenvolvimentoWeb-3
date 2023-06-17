package com.autobots.automanager.modelos.Mercadoria;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.Mercadoria.MercadoriaControle;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelos.AdicionadorLink;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<Mercadoria> {

	@Override
	public void adicionarLink(List<Mercadoria> lista) {
		for (Mercadoria mercadoria : lista) {
			long id = mercadoria.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.obterMercadoria(id))
					.withSelfRel();
			mercadoria.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Mercadoria objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(MercadoriaControle.class)
						.obterMercadorias())
				.withRel("mercadorias");
		objeto.add(linkProprio);
	}
}

package dev.davivieira.application.ports.output;

import dev.davivieira.domain.entity.Estabelecimento;

import java.util.List;

public interface RouterViewOutputPort {

    List<Estabelecimento> fetchRelatedRouters();
}

package dev.davivieira.domain.specification;

import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.vo.DependenciaAdministrativa;

public final class RouterTypeSpecification extends AbstractSpecification<Estabelecimento> {

    @Override
    public boolean isSatisfiedBy(Estabelecimento estabelecimento) {
        return estabelecimento.getRouterType().equals(DependenciaAdministrativa.FEDERAL) || estabelecimento.getRouterType().equals(DependenciaAdministrativa.ESTADUAL);
    }
}

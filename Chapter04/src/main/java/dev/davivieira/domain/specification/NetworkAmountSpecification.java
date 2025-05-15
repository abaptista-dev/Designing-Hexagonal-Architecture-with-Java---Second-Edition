package dev.davivieira.domain.specification;

import dev.davivieira.domain.entity.Estabelecimento;

public final class NetworkAmountSpecification extends AbstractSpecification<Estabelecimento> {

    final static public int MAXIMUM_ALLOWED_NETWORKS = 6;

    @Override
    public boolean isSatisfiedBy(Estabelecimento estabelecimento) {
        return estabelecimento.retrieveNetworks().size() <=MAXIMUM_ALLOWED_NETWORKS;
    }
}

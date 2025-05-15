package dev.davivieira.domain.specification;

import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.vo.IP;

public final class NetworkAvailabilitySpecification extends AbstractSpecification<Estabelecimento> {

    private final IP address;
    private final String name;
    private final int cidr;

    public NetworkAvailabilitySpecification(IP address, String name, int cidr) {
        this.address = address;
        this.name = name;
        this.cidr = cidr;
    }

    @Override
    public boolean isSatisfiedBy(Estabelecimento estabelecimento) {
        return estabelecimento!=null && isNetworkAvailable(estabelecimento);
    }

    private boolean isNetworkAvailable(Estabelecimento estabelecimento) {
        return estabelecimento.retrieveNetworks().stream().noneMatch(
                network -> network.address().equals(address) &&
                        network.name().equals(name) &&
                        network.cidr() == cidr);
    }
}

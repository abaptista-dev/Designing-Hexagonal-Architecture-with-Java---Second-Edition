package dev.davivieira.domain.service;

import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.specification.CIDRSpecification;
import dev.davivieira.domain.specification.NetworkAmountSpecification;
import dev.davivieira.domain.specification.NetworkAvailabilitySpecification;
import dev.davivieira.domain.specification.RouterTypeSpecification;
import dev.davivieira.domain.vo.Matricula;

public class MatriculaOperation {

    public static Estabelecimento criarMatricula(Estabelecimento estabelecimento, Matricula matricula) {
        var availabilitySpec = new NetworkAvailabilitySpecification(matricula.getAddress(), matricula.getName(), matricula.getCidr());
        var cidrSpec = new CIDRSpecification();
        var routerTypeSpec = new RouterTypeSpecification();
        var amountSpec = new NetworkAmountSpecification();

        if(cidrSpec.isSatisfiedBy(matricula.getCidr()))
            throw new IllegalArgumentException("CIDR is below "+CIDRSpecification.MINIMUM_ALLOWED_CIDR);

        if(!availabilitySpec.isSatisfiedBy(estabelecimento))
            throw new IllegalArgumentException("Address already exist");

        if(amountSpec.and(routerTypeSpec).isSatisfiedBy(estabelecimento)) {
            Matricula newNetwork = estabelecimento.createNetwork(matricula.getAddress(), matricula.getName(), matricula.getCidr());
            estabelecimento.addNetworkToSwitch(newNetwork);
        }
        return estabelecimento;
    }
}

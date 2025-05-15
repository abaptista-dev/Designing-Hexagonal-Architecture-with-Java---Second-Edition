package dev.davivieira;

import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.specification.CIDRSpecification;
import dev.davivieira.domain.specification.NetworkAvailabilitySpecification;
import dev.davivieira.domain.vo.IP;
import dev.davivieira.domain.vo.Matricula;
import dev.davivieira.domain.vo.EstabelecimentoId;
import dev.davivieira.framework.adapters.output.file.EstabelecimentoMatriculaFileAdapter;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddNetworkStepsTest {

    private EstabelecimentoId estabelecimentoId;

    private Estabelecimento estabelecimento;

    private final EstabelecimentoMatriculaFileAdapter estabelecimentoMatriculaFileAdapter =  EstabelecimentoMatriculaFileAdapter.getInstance();

    private final Matricula matricula = new Matricula(new IP("20.0.0.0"), "Marketing", 8);

    @Given("I provide a router ID and the network details")
    public void obtain_routerId() {
        this.estabelecimentoId = EstabelecimentoId.withId("ca23800e-9b5a-11eb-a8b3-0242ac130003");
    }

    @When("I found the router")
    public void lookup_router() {
        estabelecimento = estabelecimentoMatriculaFileAdapter.fetchEstabelecimentoById(estabelecimentoId);
    }

    @And("The network address is valid and doesn't already exists")
    public void check_address_validity_and_existence() {
        var availabilitySpec = new NetworkAvailabilitySpecification(matricula.getAddress(), matricula.getName(), matricula.getCidr());
        if(!availabilitySpec.isSatisfiedBy(estabelecimento))
            throw new IllegalArgumentException("Address already exist");
    }

    @Given("The CIDR is valid")
    public void check_cidr() {
        var cidrSpec = new CIDRSpecification();
        if(cidrSpec.isSatisfiedBy(matricula.getCidr()))
            throw new IllegalArgumentException("CIDR is below "+CIDRSpecification.MINIMUM_ALLOWED_CIDR);
    }

    @Then("Add the network to the router")
    public void add_network() {
        estabelecimento.addNetworkToSwitch(matricula);
    }
}

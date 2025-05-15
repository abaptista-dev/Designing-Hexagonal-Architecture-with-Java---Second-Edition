package dev.davivieira.domain.entity;

import java.util.List;

import dev.davivieira.domain.vo.IP;
import dev.davivieira.domain.vo.Matricula;
import dev.davivieira.domain.vo.EstabelecimentoId;
import dev.davivieira.domain.vo.DependenciaAdministrativa;

public class Estabelecimento {

    private DependenciaAdministrativa dependenciaAdministrativa;
    private EstabelecimentoId estabelecimentoId;
    private Turma networkSwitch;

    public Estabelecimento(){

    }

    public Estabelecimento(DependenciaAdministrativa dependenciaAdministrativa, EstabelecimentoId estabelecimentoId) {
        this.dependenciaAdministrativa = dependenciaAdministrativa;
        this.estabelecimentoId = estabelecimentoId;
    }

    public Estabelecimento(DependenciaAdministrativa dependenciaAdministrativa, EstabelecimentoId estabelecimentoId, Turma networkSwitch) {
        this.dependenciaAdministrativa = dependenciaAdministrativa;
        this.estabelecimentoId = estabelecimentoId;
        this.networkSwitch = networkSwitch;
    }

    public boolean isType(DependenciaAdministrativa type){
        return this.dependenciaAdministrativa == type;
    }

    public void addNetworkToSwitch(Matricula matricula){
        this.networkSwitch = networkSwitch.addNetwork(matricula, this);
    }

    public Matricula createNetwork(IP address, String name, int cidr){
        return new Matricula(address, name, cidr);
    }

    public List<Matricula> retrieveNetworks(){
        return networkSwitch.getNetworks();
    }

    public DependenciaAdministrativa getRouterType() {
        return dependenciaAdministrativa;
    }

    public EstabelecimentoId getRouterId() {
        return estabelecimentoId;
    }

    public Turma getNetworkSwitch() {
        return networkSwitch;
    }

    @Override
    public String toString() {
        return "Router{" +
                "type=" + dependenciaAdministrativa +
                ", id=" + estabelecimentoId +
                ", networkSwitch=" + networkSwitch +
                '}';
    }
}
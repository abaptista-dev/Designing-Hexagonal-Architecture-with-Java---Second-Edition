package dev.davivieira.domain.entity;

import dev.davivieira.domain.vo.IP;
import dev.davivieira.domain.vo.Matricula;
import dev.davivieira.domain.vo.TurmaId;
import dev.davivieira.domain.vo.Turno;

import java.util.ArrayList;
import java.util.List;

public class Turma {

    private TurmaId turmaId;
    private Turno turno;
    private List<Matricula> matriculas;
    private IP address;

    public Turma (TurmaId turmaId, Turno turno, List<Matricula> matriculas, IP address){
        this.turmaId = turmaId;
        this.turno = turno;
        this.matriculas = matriculas;
        this.address = address;
    }

    public Turma addNetwork(Matricula matricula, Estabelecimento estabelecimento){
        List<Matricula> newNetworks = new ArrayList<>();

        estabelecimento.retrieveNetworks().forEach(net ->{
            newNetworks.add(net);
        });

        newNetworks.add(matricula);
        return new Turma(this.turmaId, this.turno, newNetworks, this.address);
    }

    public List<Matricula> getNetworks() {
        return matriculas;
    }

    public TurmaId getSwitchId() {
        return turmaId;
    }

    public Turno getSwitchType() {
        return turno;
    }

    public IP getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Switch{" +
                "switchType=" + turno +
                ", switchId=" + turmaId +
                ", networks=" + matriculas +
                ", address=" + address +
                '}';
    }
}

package dev.davivieira.application.ports.input;

import dev.davivieira.application.ports.output.EstabelecimentoMatriculaOutputPort;
import dev.davivieira.application.usecases.EstabelecimentoMatriculaUseCase;
import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.service.MatriculaOperation;
import dev.davivieira.domain.vo.Matricula;
import dev.davivieira.domain.vo.EstabelecimentoId;

public class EstabelecimentoMatriculaInputPort implements EstabelecimentoMatriculaUseCase {

    private final EstabelecimentoMatriculaOutputPort estabelecimentoMatriculaOutputPort;

    public EstabelecimentoMatriculaInputPort(EstabelecimentoMatriculaOutputPort estabelecimentoMatriculaOutputPort){
        this.estabelecimentoMatriculaOutputPort = estabelecimentoMatriculaOutputPort;
    }

    @Override
    public Estabelecimento addMatriculaNoEstabelecimento(EstabelecimentoId estabelecimentoId, Matricula matricula) {
        var router = fetchEstabelecimento(estabelecimentoId);
        return createMatricula(router, matricula);
    }

    private Estabelecimento fetchEstabelecimento(EstabelecimentoId estabelecimentoId) {
        return estabelecimentoMatriculaOutputPort.fetchEstabelecimentoById(estabelecimentoId);
    }

    private Estabelecimento createMatricula(Estabelecimento estabelecimento, Matricula matricula) {
        var newRouter = MatriculaOperation.criarMatricula(estabelecimento, matricula);
        return persistMatricula(estabelecimento) ? newRouter :
                estabelecimento;
    }

    private boolean persistMatricula(Estabelecimento estabelecimento) {
        return estabelecimentoMatriculaOutputPort.persistEstabelecimento(estabelecimento);
    }
}

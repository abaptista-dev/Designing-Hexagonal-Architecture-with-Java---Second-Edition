package dev.davivieira.framework.adapters.input;

import dev.davivieira.application.usecases.EstabelecimentoMatriculaUseCase;
import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.vo.IP;
import dev.davivieira.domain.vo.Matricula;
import dev.davivieira.domain.vo.EstabelecimentoId;
import java.util.Map;

public abstract class EstabelecimentoMatriculaAdapter {

    protected Estabelecimento estabelecimento;
    protected EstabelecimentoMatriculaUseCase estabelecimentoMatriculaUseCase;

    protected Estabelecimento addNetworkToRouter(Map<String, String> params){
        var routerId = EstabelecimentoId.withId(params.get("routerId"));
        var network = new Matricula(IP.fromAddress(params.get("address")),
                params.get("name"),
                Integer.valueOf(params.get("cidr")));
        return estabelecimentoMatriculaUseCase.addMatriculaNoEstabelecimento(routerId, network);
    }

    public abstract Estabelecimento processRequest(Object requestParams);
}

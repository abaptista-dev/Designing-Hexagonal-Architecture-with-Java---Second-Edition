package dev.davivieira.framework.adapters.input.stdin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.davivieira.application.usecases.EstabelecimentoMatriculaUseCase;
import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.framework.adapters.input.EstabelecimentoMatriculaAdapter;
import dev.davivieira.framework.adapters.output.file.mappers.EstabelecimentoJsonFileMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EstabelecimentoMatriculaCLIAdapter extends EstabelecimentoMatriculaAdapter {

    public EstabelecimentoMatriculaCLIAdapter(EstabelecimentoMatriculaUseCase estabelecimentoMatriculaUseCase){
        this.estabelecimentoMatriculaUseCase = estabelecimentoMatriculaUseCase;
    }

    @Override
    public Estabelecimento processRequest(Object requestParams){
        var params = stdinParams(requestParams);
        estabelecimento = this.addNetworkToRouter(params);
        ObjectMapper mapper = new ObjectMapper();
        try {
            var routerJson = mapper.writeValueAsString(EstabelecimentoJsonFileMapper.toJson(estabelecimento));
            System.out.println(routerJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return estabelecimento;
    }

    private Map<String, String> stdinParams(Object requestParams){
        Map<String, String> params = new HashMap<>();
        if(requestParams instanceof Scanner){
            var scanner = (Scanner) requestParams;
            System.out.println("Please inform the Router ID:");
            var routerId = scanner.nextLine();
            params.put("routerId", routerId);
            System.out.println("Please inform the IP address:");
            var address = scanner.nextLine();
            params.put("address", address);
            System.out.println("Please inform the Network Name:");
            var name = scanner.nextLine();
            params.put("name", name);
            System.out.println("Please inform the CIDR:");
            var cidr = scanner.nextLine();
            params.put("cidr", cidr);
        } else {
            throw new IllegalArgumentException("Request with invalid parameters");
        }
        return params;
    }
}

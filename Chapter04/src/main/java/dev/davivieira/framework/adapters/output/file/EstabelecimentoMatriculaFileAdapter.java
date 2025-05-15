package dev.davivieira.framework.adapters.output.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.davivieira.application.ports.output.EstabelecimentoMatriculaOutputPort;
import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.vo.EstabelecimentoId;
import dev.davivieira.framework.adapters.output.file.json.EstabelecimentoJson;
import dev.davivieira.framework.adapters.output.file.mappers.EstabelecimentoJsonFileMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

public class EstabelecimentoMatriculaFileAdapter implements EstabelecimentoMatriculaOutputPort {

    private static EstabelecimentoMatriculaFileAdapter instance;
    private List<EstabelecimentoJson> routers;
    private InputStream resource;
    private ObjectMapper objectMapper;

    @Override
    public Estabelecimento fetchEstabelecimentoById(EstabelecimentoId estabelecimentoId) {
        var router = new Estabelecimento();
        for(EstabelecimentoJson routerJson: routers){
            if(routerJson.getRouterId().equals(estabelecimentoId.getUUID())){
                router = EstabelecimentoJsonFileMapper.toDomain(routerJson);
                break;
            }
        }
        return router;
    }

    @Override
    public boolean persistEstabelecimento(Estabelecimento estabelecimento) {
        var routerJson = EstabelecimentoJsonFileMapper.toJson(estabelecimento);
        try {
            String localDir = Paths.get("").toAbsolutePath().toString();
            File file = new File(localDir + "/inventory.json");
            file.delete();
            objectMapper.writeValue(file, routerJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void readJsonFile(){
        try {
            this.routers = objectMapper.
                    readValue(
                            resource,
                            new TypeReference<List<EstabelecimentoJson>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private EstabelecimentoMatriculaFileAdapter() {
        this.objectMapper = new ObjectMapper();
        this.resource = getClass().
                getClassLoader().
                getResourceAsStream("inventory.json");
        readJsonFile();
    }

    public static EstabelecimentoMatriculaFileAdapter getInstance() {
        if (instance == null) {
            instance = new EstabelecimentoMatriculaFileAdapter();
        }
        return instance;
    }
}

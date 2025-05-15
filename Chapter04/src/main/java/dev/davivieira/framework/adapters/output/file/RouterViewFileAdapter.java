package dev.davivieira.framework.adapters.output.file;

import dev.davivieira.application.ports.output.RouterViewOutputPort;
import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.vo.EstabelecimentoId;
import dev.davivieira.domain.vo.DependenciaAdministrativa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RouterViewFileAdapter implements RouterViewOutputPort {

    private static RouterViewFileAdapter instance;

    @Override
    public List<Estabelecimento> fetchRelatedRouters() {
        return readFileAsString();
    }

    private static List<Estabelecimento> readFileAsString() {
        List<Estabelecimento> estabelecimentos = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(RouterViewFileAdapter.class.getResource("/routers.txt").getPath()))) {
            stream.forEach(line ->{
                String[] routerEntry = line.split(";");
                var id = routerEntry[0];
                var type = routerEntry[1];
                Estabelecimento estabelecimento = new Estabelecimento(DependenciaAdministrativa.valueOf(type),EstabelecimentoId.withId(id));
                estabelecimentos.add(estabelecimento);
            });
        } catch (IOException e){
           e.printStackTrace();
        }
        return estabelecimentos;
    }


    private RouterViewFileAdapter() {
    }

    public static RouterViewFileAdapter getInstance() {
        if (instance == null) {
            instance = new RouterViewFileAdapter();
        }
        return instance;
    }
}

package dev.davivieira;

import com.sun.net.httpserver.HttpServer;
import dev.davivieira.application.ports.input.EstabelecimentoMatriculaInputPort;
import dev.davivieira.application.ports.output.EstabelecimentoMatriculaOutputPort;
import dev.davivieira.application.usecases.EstabelecimentoMatriculaUseCase;
import dev.davivieira.framework.adapters.input.EstabelecimentoMatriculaAdapter;
import dev.davivieira.framework.adapters.input.rest.EstabelecimentoMatriculaRestAdapter;
import dev.davivieira.framework.adapters.input.stdin.EstabelecimentoMatriculaCLIAdapter;
import dev.davivieira.framework.adapters.output.file.EstabelecimentoMatriculaFileAdapter;
import dev.davivieira.framework.adapters.output.h2.EstabelecimentoMatriculaH2Adapter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class App {

    private EstabelecimentoMatriculaAdapter inputAdapter;
    private EstabelecimentoMatriculaUseCase usecase;
    private EstabelecimentoMatriculaOutputPort outputPort;

    public static void main(String... args)  {
        var adapter = "cli";
        if(args.length>0) {
            adapter = args[0];
        }
        new App().setAdapter(adapter);
    }

    void setAdapter(String adapter) {
        switch (adapter) {
            case "rest" -> {
                outputPort = EstabelecimentoMatriculaH2Adapter.getInstance();
                usecase = new EstabelecimentoMatriculaInputPort(outputPort);
                inputAdapter = new EstabelecimentoMatriculaRestAdapter(usecase);
                rest();
            }
            default -> {
                outputPort = EstabelecimentoMatriculaFileAdapter.getInstance();
                usecase = new EstabelecimentoMatriculaInputPort(outputPort);
                inputAdapter = new EstabelecimentoMatriculaCLIAdapter(usecase);
                cli();
            }
        }
    }

    private void cli() {
        Scanner scanner = new Scanner(System.in);
        inputAdapter.processRequest(scanner);
    }

    private void rest() {
        try {
            System.out.println("REST endpoint listening on port 8080...");
            var httpserver = HttpServer.create(new InetSocketAddress(8080), 0);
            inputAdapter.processRequest(httpserver);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
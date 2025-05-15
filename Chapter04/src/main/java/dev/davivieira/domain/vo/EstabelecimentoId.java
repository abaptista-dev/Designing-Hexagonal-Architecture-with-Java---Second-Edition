package dev.davivieira.domain.vo;

import java.util.UUID;

public class EstabelecimentoId {

    private final UUID id;

    private EstabelecimentoId(UUID id){
        this.id = id;
    }

    public static EstabelecimentoId withId(String id){
        return new EstabelecimentoId(UUID.fromString(id));
    }

    public UUID getUUID() {
        return id;
    }

    @Override
    public String toString() {
        return "RouterId{" +
                "id='" + id + '\'' +
                '}';
    }
}

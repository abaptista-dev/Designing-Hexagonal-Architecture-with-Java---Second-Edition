package dev.davivieira.domain.vo;

import java.util.UUID;

public class TurmaId {

    private final UUID id;

    private TurmaId(UUID id){
        this.id = id;
    }

    public static TurmaId withId(String id){
        return new TurmaId(UUID.fromString(id));
    }

    public UUID getUUID() {
        return id;
    }

    @Override
    public String toString() {
        return "SwitchId{" +
                "id='" + id + '\'' +
                '}';
    }
}

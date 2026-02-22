package com.pwzt.assinaturas.infrastruct.enumerator;

public enum Status {
    CANCELADA,
    PENDENTE,
    SUSPENSA,
    ATIVA;

    public static Status fromString(String text){
        for(Status s : Status.values()){
            if(s.name().equalsIgnoreCase(text)){
                return s;
            }
        }
        throw new IllegalArgumentException();
    }
}

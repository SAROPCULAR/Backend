package com.sarop.saropbackend.restapi.dto;

public enum Mode {
    Single,
    Opaque_Container,
    Named_Tree,
    Container_Tree,
    Earth_Observation_Tree;

    @Override
    public String toString() {
        return this.name().replace('_', ' ');
    }


}

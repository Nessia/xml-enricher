package org.nessia.xml.enricher;

public enum TransformationTypes {
    APPEND("append"), PREPEND("prepend"), ATTRIBUTES("attributes");

    private final String suffix;

    TransformationTypes(String suffix){
        this.suffix = suffix;
    }

    @Override
    public String toString() {
        return suffix;
    }
}

package fr.epita.trackmoviesback.infrastructure.converter;

import fr.epita.trackmoviesback.enumerate.EnumTypeOeuvre;

import javax.persistence.AttributeConverter;

public class TypeOeuvreAttributeConverter implements AttributeConverter<EnumTypeOeuvre,String> {
    @Override
    public String convertToDatabaseColumn(EnumTypeOeuvre enumTypeOeuvre) {
        //si enumTypeOeuvre n'est pas null, je retourne son libelle pour le stocker en base
        return enumTypeOeuvre==null? null:enumTypeOeuvre.getLibelle();
    }

    @Override
    public EnumTypeOeuvre convertToEntityAttribute(String libelle) {
        //je retourne l'enum correspondant au libelle
        return EnumTypeOeuvre.getEnumFromlabel(libelle);
    }
}

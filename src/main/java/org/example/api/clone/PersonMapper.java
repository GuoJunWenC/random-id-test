package org.example.api.clone;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    Person sourceToTarget(Person source);

    /**
     * 深copy
     * @param source
     * @return
     */
    default Address mapAddress(Address source) {
        if (source == null) {
            return null;
        }
        Address target = new Address();
        target.setCity(source.getCity());
        // 复制其他字段...
        return target;
    }
}

package org.example.api.genericity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Animal implements Serializable {
    private static final long serialVersionUID = 1928525845123081123L;

    private Integer age;
    public Integer countLegs(){
        return age;
    }

}

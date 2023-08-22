package org.example.api.els.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Animal implements Serializable {
    private static final long serialVersionUID = 1928525845123081123L;
    private String name;
    private String type;
    private Integer age;
    private List<String> skill;
}

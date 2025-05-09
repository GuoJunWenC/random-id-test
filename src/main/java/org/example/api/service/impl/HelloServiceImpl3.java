package org.example.api.service.impl;

import org.example.api.service.HelloService;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class HelloServiceImpl3 implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[0];
        
    }
}

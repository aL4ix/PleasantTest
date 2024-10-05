package org.example.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GlueReturn {
    Object value;
    GlueReturnedType glueReturnedType;
}

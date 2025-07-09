package com.productos.utils;

import com.productos.dto.ProductoResponseDto;

public class JsonApiWrapper {
    public static <T> Object wrap(String type, Long id, T attributes){
        return new Object(){
            public final Object data = new Object(){
                public final String _type = type;
                public final Long _id = id;
                public final Object _attributes = attributes;
            };
        };
    }
}

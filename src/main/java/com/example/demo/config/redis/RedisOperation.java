package com.example.demo.config.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer type;

    private String data;
}

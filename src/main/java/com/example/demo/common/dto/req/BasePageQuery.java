package com.example.demo.common.dto.req;

import lombok.Data;

@Data
public class BasePageQuery {

    private Integer pageNum;

    private Integer pageSize;
}

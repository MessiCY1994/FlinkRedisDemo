package com.messiyang.flinkdemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class TestCMp {
    private String id;
    private String name;
    private String age;

    public TestCMp() {
    }

    public TestCMp(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}

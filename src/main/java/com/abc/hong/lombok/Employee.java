package com.abc.hong.lombok;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Employee {
    @Getter @Setter private boolean employed = true;
    @Setter(AccessLevel.PROTECTED) private String name;
}

package com.chanjet.hong.lombok;

import lombok.ToString;

@ToString(callSuper=true,exclude="someExcludedField")
public class Foo {
    private boolean someBoolean = true;
    private String someStringField;
    private float someExcludedField;
}

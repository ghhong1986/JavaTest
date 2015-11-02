package com.chanjet.hong.lombok;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

//@EqualsAndHashCode(callSuper=true,exclude={"address","city","state","zip"})
public class Person  {
    enum Gender { Male, Female }

    @NonNull private String name;
    @NonNull private Gender gender;
    
    private String ssn;
    private String address;
    private String city;
    private String state;
    private String zip;
}

//@Data
//@EqualsAndHashCode(exclude={"address","city","state","zip"})
//public class Person {
//    enum Gender { Male, Female }
//
//    @NonNull private String firstName;
//    @NonNull private String lastName;
//    @NonNull private final Gender gender;
//    @NonNull private final Date dateOfBirth;
//    
//    private String ssn;
//    private String address;
//    private String city;
//    private String state;
//    private String zip;
//}
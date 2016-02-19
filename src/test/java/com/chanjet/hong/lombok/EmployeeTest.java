package com.chanjet.hong.lombok;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Cleanup;
import lombok.Synchronized;

import org.junit.Test;

public class EmployeeTest {

    @Test
    public void test() {
        Employee  emp = new  Employee();
//        emp.setEmployed(false);
//        emp.setName("asd");
        System.out.println(emp);
        
    }
    
    private DateFormat format = new SimpleDateFormat("MM-dd-YYYY");

//    @Synchronized
    public String synchronizedFormat(Date date) {
        return format.format(date);
    }
    
    @Test
    public void testCleanUp() {
        try {
            @Cleanup
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(new byte[] {'Y', 'e', 's'});
            System.out.println(baos.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

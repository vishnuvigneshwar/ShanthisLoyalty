package com.tritonitsolutions.loyaltydemo;

import java.util.Random;

/**
 * Created by TritonDev on 24/10/2015.
 */
public class RandomValue {


    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static Random rnd = new Random();

    String randomString(  ){
        StringBuilder sb = new StringBuilder( 7 );
        for( int i = 0; i < 7; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }


}

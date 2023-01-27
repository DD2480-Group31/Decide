package com.decide;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.beans.Transient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;



public class DecideTest{

    //Example: Typical test syntax
    @Test
    public void verifyNoExceptionsThrown(){
        //Just calls the main method with an epty argument
        Decide.main(new String[] {});
    }

}
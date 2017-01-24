package fr.wildcodeschool.haa.waxym;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by tuffery on 19/01/17.
 */
public class LoginActivityTest {

    private LoginActivity loginActivity;

    @Before
    public void setUp() throws Exception {
        loginActivity = new LoginActivity();

    }

    @Test
    public void shaConverter() throws Exception {
        Assert.assertEquals("7543eaf74f4b487990a35824491b1df3a8dc62e20fa8c99ef6a25ea3384f12ff", loginActivity.shaConverter("dfddj"));
    }

}
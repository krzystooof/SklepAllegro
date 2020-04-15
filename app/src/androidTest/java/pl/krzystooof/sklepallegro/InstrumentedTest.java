package pl.krzystooof.sklepallegro;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import pl.krzystooof.sklepallegro.data.Offer;
import pl.krzystooof.sklepallegro.data.Price;
import pl.krzystooof.sklepallegro.data.mSharedPref;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("pl.krzystooof.sklepallegro", appContext.getPackageName());
    }

    @Test
    public void mSharedPrefTest() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(String.valueOf(R.string.shared_preferences),Context.MODE_PRIVATE);
        mSharedPref sharedPref = new mSharedPref(sharedPreferences);

        String intName = "integer";
        int intValue = 78;
        sharedPref.setPaused(true);
        sharedPref.saveInt(intName,intValue);

        String arrayName = "array";
        ArrayList<Offer> offers = new ArrayList<>();
        offers.add(new Offer("1","1","1",new Price(0,"1"),"1"));
        sharedPref.save(offers,arrayName);


        assertEquals(true, sharedPref.getPaused());
        assertEquals(intValue, sharedPref.readInt(intName));
        assertEquals(offers.get(0).getId(), sharedPref.read(arrayName).get(0).getId());
    }
}

package com.itsm.util;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

//@RunWith(MockitoJUnitRunner.class)
public class DoMooAnnotationBeanPostProcessorTest {

    private DoMooAnnotationBeanPostProcessor processor;
    private TestedClass1 object1;
    private TestedClass2 object2;


    @Before
    public void setUp(){
        processor =  new DoMooAnnotationBeanPostProcessor();
        object1 = new TestedClass1();
        object2 = new TestedClass2();

        processor.postProcessBeforeInitialization(object1,"object1");
        processor.postProcessBeforeInitialization(object2,"object2");
    }

    @Test
    public void annotationTest() {
        Object newObject1 = processor.postProcessAfterInitialization(this.object1, "object1");
        assertNotNull(newObject1);
        assertEquals("MOOOOOOOOOOOO!!!",newObject1.toString());
    }

    @Test
    public void containsTest() {
        TestedInterface newObject1 = (TestedInterface) processor.postProcessAfterInitialization(this.object1, "object1");
        assertNotNull(newObject1);
        assertEquals("MOOOOOOOOOOOO!!!",newObject1.toStringDoMoo());
    }

    @Test
    public void implementsTest() {
        Object newObject2 =  processor.postProcessAfterInitialization(this.object2, "object2");
        assertNotNull(newObject2);
        assertEquals("MOOOOOOOOOOOO!!!",newObject2.toString());
    }

    private class TestedClass1 implements TestedInterface{

        @DoMoo
        public String toString(){
            return "Whatever";
        }


        public String toStringDoMoo() {
            return "meow";
        }
    }

    private class TestedClass2 implements Mooable{
        public String toString(){
            return "Tested class 2";
        }
    }

    private interface TestedInterface {
        String toStringDoMoo();
    }
}
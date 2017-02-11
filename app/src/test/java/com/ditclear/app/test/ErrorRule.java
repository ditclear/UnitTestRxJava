package com.ditclear.app.test;


import com.ditclear.app.test.annotation.JSpec;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * 页面描述：
 * <p>
 * Created by ditclear on 2017/2/6.
 */

public class ErrorRule implements TestRule {
    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                /***starting(method);***/
//                ShadowLog. stream = System. out ;//此处是使用Robolectric框架的方法，将该测试案例的log输出到控制台，方便查看log
                try {
                    base.evaluate() ;
                    System.out.println("@" + description.getMethodName() + "\t--->\t"+description .getAnnotation(JSpec. class).desc()+"\t通过" ) ;
                    /***succeeded(method);***/
                } catch (Throwable t) {
                    /***failed(t, method);***/
                    try {
                        System.err.println("@" + description.getMethodName() + "\t--->\t" + description .getAnnotation(JSpec . class).desc()+"\t未通过\t"+t.getMessage()) ;
                    } catch (NullPointerException e) {

                    } finally {
                        throw t;
                    }

                } finally {
                    /***finished(method);***/
                }
            }
        };
    }



}

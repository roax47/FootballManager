package pl.edu.pg.eti.kask.javaee.example.library.test.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.javaee.example.library.test.TestClass;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class TestView {


    private TestClass service;

    @Getter
    @Setter
    private String testResults;



    @Inject
    public TestView(TestClass service) {
        this.service = service;
    }




    public void InitTest() throws  Exception{
        service.initTest();
        testResults="Success";
    }


}

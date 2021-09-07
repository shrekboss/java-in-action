package org.crayzer.err.coding.springpart1.aopmetrics;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("test")
@RestController
public class TestController {

    /**
     * TestAspectWithOrder10 @Around before
     * TestAspectWithOrder10 @Before
     * TestAspectWithOrder20 @Around before
     * TestAspectWithOrder20 @Before
     * TestAspectWithOrder20 @Around after
     * TestAspectWithOrder20 @After
     * TestAspectWithOrder10 @Around after
     * TestAspectWithOrder10 @After
     * <p/>
     * curl http://localhost:45678/test/
     */
    @GetMapping
    public void test() {

    }
}

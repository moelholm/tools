package com.moelholm.tools.actuatorui.dump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DumpUiController {

    @RequestMapping("${actuator-ui.dump.ui:dump-ui}")
    public String dumpAsHtml() {
        return "/actuator-ui/dump.html";
    }

}
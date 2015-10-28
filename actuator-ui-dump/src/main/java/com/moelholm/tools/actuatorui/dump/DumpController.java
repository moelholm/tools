package com.moelholm.tools.actuatorui.dump;

import static com.moelholm.tools.actuatorui.dump.DumpAutoConfiguration.PATH_TO_DUMP_QUALIFIER;
import static com.moelholm.tools.actuatorui.dump.DumpAutoConfiguration.TITLE_QUALIFIER;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DumpController {

    private static final String HTML_TEMPLATE_CLASSPATH_LOCATION = "/META-INF/resources/actuator-ui/dump.html";

    @Autowired
    @Qualifier(PATH_TO_DUMP_QUALIFIER)
    private String pathToDump;

    @Autowired
    @Qualifier(TITLE_QUALIFIER)
    private String title;

    private String cachedHtmlResponse;

    @RequestMapping("${actuator-ui.dump-ui.path:dump-ui}")
    public void dumpAsHtml(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try (PrintWriter writer = response.getWriter()) {
            writer.println(getHtml(request));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getHtml(HttpServletRequest request) {
        if (cachedHtmlResponse == null) {
            // (This simple template expansion avoids the need for a real template engine)
            cachedHtmlResponse = getHtmlTemplateAsString();
            cachedHtmlResponse = cachedHtmlResponse.replaceAll("\\$\\{ctxPath\\}", request.getContextPath());
            cachedHtmlResponse = cachedHtmlResponse.replaceAll("\\$\\{pathToDump\\}", request.getContextPath() + pathToDump);
            cachedHtmlResponse = cachedHtmlResponse.replaceAll("\\$\\{title\\}", title);
        }
        return cachedHtmlResponse;
    }

    private String getHtmlTemplateAsString() {
        try (InputStream stream = getClass().getResourceAsStream(HTML_TEMPLATE_CLASSPATH_LOCATION)) {
            try (Scanner scanner = new Scanner(stream)) {
                scanner.useDelimiter("\\Z");
                return scanner.next();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
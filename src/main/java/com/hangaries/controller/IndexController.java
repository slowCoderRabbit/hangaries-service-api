package com.hangaries.controller;


import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class IndexController {


    public static final String STRING = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "  <head>\n" +
            "    <meta charset=\"UTF-8\" />\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
            "    <title>Success Payment</title>\n" +
            "  </head>\n" +
            "  <body style=\"text-align: center\">\n" +
            "    <script>\n" +
            "      function stateChange() {\n" +
            "        setTimeout(function () {\n" +
            "          window.location.replace(\n" +
            "            \"https://client-app-uumgqhekpa-el.a.run.app/new-checkout?page=success\"\n" +
            "          );\n" +
            "        }, 3000);\n" +
            "      }\n" +
            "    </script>\n" +
            "    <div>\n" +
            "      <h1>Sample Invoice</h1>\n" +
            "      <h3>Success Page</h3>\n" +
            "      <h3>\n" +
            "        Browser will stay 3 seconds in this page and automatically redirect you\n" +
            "        to the Client APP\n" +
            "      </h3>\n" +
            "    </div>\n" +
            "    <script>\n" +
            "      stateChange();\n" +
            "    </script>\n" +
            "  </body>\n" +
            "</html>";

    @PostMapping("savePayUResponse")
    public String sayHello2(@RequestBody Map<String, Object> payload) {
        return STRING;
    }

    @GetMapping("savePayUResponse")
    public String sayHello() {
        return STRING;
    }

}

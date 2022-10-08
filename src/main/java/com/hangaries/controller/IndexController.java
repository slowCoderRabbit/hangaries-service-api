package com.hangaries.controller;


import com.hangaries.model.payu.PayUResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class IndexController {
    static final String url = "https://client-app-uumgqhekpa-el.a.run.app/new-checkout?";
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private static final String queryParam = "page=success&";
    static String htmlPart1Success = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "  <head>\n" +
            "    <meta charset=\"UTF-8\" />\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
            "    <title>Payment Successful!!</title>\n" +
            "  </head>\n" +
            "  <body style=\"text-align: center\">\n" +
            "    <script>\n" +
            "      function stateChange() {\n" +
            "        setTimeout(function () {\n" +
            "          window.location.replace(\"";

    static String htmlPart2Success = "\");\n" +
            "        }, 3000);\n" +
            "      }\n" +
            "    </script>\n" +
            "    <div style=\"background-color: #39e75f; padding: 50px\">\n" +
            "      <h1>Payment Success!</h1>\n" +
            "      <h3>Thank You!</h3>\n" +
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

    static String htmlPart1Failure = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "  <head>\n" +
            "    <meta charset=\"UTF-8\" />\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
            "    <title>Payment Failed!!</title>\n" +
            "  </head>\n" +
            "  <body style=\"text-align: center\">\n" +
            "    <script>\n" +
            "      function stateChange() {\n" +
            "        setTimeout(function () {\n" +
            "          window.location.replace(\"";

    static String htmlPart2Failure = "\");\n" +
            "        }, 3000);\n" +
            "      }\n" +
            "    </script>\n" +
            "    <div style=\"background-color: #ff726f; padding: 50px\">\n" +
            "      <h1>Payment Failed!</h1>\n" +
            "      <h3>Please Try Again!</h3>\n" +
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
    String a = "<!DOCTYPE html>\n" +
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
            "</html>\n";

    private static String generateQueryParam(PayUResponse response, String token) {
        StringBuilder qp = new StringBuilder(queryParam);
        qp.append("status=");
        qp.append(response.getStatus());
        qp.append("&");
        qp.append("txnid=");
        qp.append(response.getTxnid());
        qp.append("&");
        qp.append("hash=");
        qp.append(token);
        qp.append("&");
        qp.append("error_Message=");
        qp.append(response.getError_Message());
        return qp.toString();
    }

    @PostMapping("savePayUResponseSuccess")
    @ResponseBody
    public String savePayUResponseSuccess(@ModelAttribute PayUResponse response, @RequestParam("token") String token) {
        logger.info("PayU transaction successful :: [{}]", response);
        String result = generateQueryParam(response, token);
        String responseHTML = htmlPart1Success + url + result + htmlPart2Success;
        logger.info("Success response HTML = [{}]", responseHTML);
        return responseHTML;
    }

    @PostMapping("savePayUResponseFailure")
    public String savePayUResponseFailure(@ModelAttribute PayUResponse response) {
        logger.info("PayU transaction failed :: [{}]", response);
        String result = generateQueryParam(response, null);
        String responseHTML = htmlPart1Failure + url + result + htmlPart2Failure;
        logger.info("Failure response HTML = [{}]", responseHTML);
        return responseHTML;
    }

//    public static void main(String[] args) {
//
////        PayUResponse payUResponse = new PayUResponse();
////        payUResponse.setStatus("Success");
////        payUResponse.setHash("Hash");
////        payUResponse.setTxnid("asdfasdfasd");
////        payUResponse.setError_Message("no error");
////        String result = generateQueryParam(payUResponse);
////        System.out.println(result);
////
////        System.out.println(result);
////        System.out.println(htmlPart1+url+result+htmlPart2);
//
//
//http://192.168.10.122:8080/api/savePayUResponseSuccess
//
//http://192.168.10.122:8080/api/savePayUResponseFailure

//    https://api-playground.payu.in/#
//   https://developers.payu.com/en/overview.html#sandbox_cards
//
//
//    }

    @GetMapping("savePayUResponse")
    public String sayHello() {
        return "STRING";
    }

}

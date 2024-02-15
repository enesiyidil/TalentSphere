package org.group3.utility;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class CodeGenerator {
    public String generateCode(){
        String randomCode= UUID.randomUUID().toString();
        System.out.println(randomCode);

        String[] split = randomCode.split("-");
        StringBuilder activationCode=new StringBuilder();

        Arrays.stream(split).forEach(c->activationCode.append(c.charAt(0)));

        System.out.println(activationCode);

        return activationCode.toString();
    }

}

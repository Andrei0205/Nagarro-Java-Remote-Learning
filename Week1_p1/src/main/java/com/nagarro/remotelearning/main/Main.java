package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.model.Person;
import com.nagarro.remotelearning.utils.PersonCreator;
import com.nagarro.remotelearning.utils.ReadFromFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        ReadFromFile readFromFile = new ReadFromFile();
        String fileName = "W1P1input.txt";
        List<Person> persons = new ArrayList<>();
        boolean validator;


        InputStream is = readFromFile.getFileFromResourceAsStream(fileName);

        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8); BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                Person newPerson = PersonCreator.createPerson(line);
                validator = true;
                if (persons.isEmpty()) {
                    persons.add(newPerson);
                }
                for (int i = 0; i < persons.size(); i++) {
                    if (newPerson.equals(persons.get(i))) {
                        validator = false;
                        break;
                    }
                }
                if (validator) {
                    persons.add(newPerson);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Person p : persons) {
            System.out.println(p);
        }

    }

}
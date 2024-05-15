package com.premiumminds.internship.teknonymy;

import java.util.LinkedList;
import java.util.Queue;

class TeknonymyService implements ITeknonymyService {

    /**
     * Method to get a Person Teknonymy Name
     * 
     * @param Person person
     * @return String which is the Teknonymy Name
     */
    public String getTeknonymy(Person person) {
        try {
            if (person.children() == null || person.children().length == 0) {
                return "";
            }
            Pair<Person, Integer> max = bfs(person);
            return transformIntoString(person, max.getFirst().name(), max.getLast());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Method to get the oldest person of the last generation
     * of the person family tree
     * 
     * @param Person person
     * @return Pair with the person and his generation
     */
    private Pair<Person, Integer> bfs(Person person) {

        Queue<Pair<Person, Integer>> queue = new LinkedList<>();
        Pair<Person, Integer> max = new Pair<>(person, 0);
        queue.add(max);

        while (!queue.isEmpty()) {

            Pair<Person, Integer> current = queue.poll();

            for (Person son : current.getFirst().children()) {

                if (son.children() == null || son.children().length == 0) {

                    boolean furtherGeneration = current.getLast() + 1 > max.getLast();
                    boolean sameGenerationAndOlder = current.getLast() + 1 == max.getLast()
                            && son.dateOfBirth().isBefore(max.getFirst().dateOfBirth());

                    if (furtherGeneration || sameGenerationAndOlder) {

                        max = new Pair<>(son, current.getLast() + 1);
                    }

                } else {
                    queue.add(new Pair<>(son, current.getLast() + 1));
                }
            }

        }
        return max;
    }

    /**
     * Method that returns the string that represents the teknonymy a person knowing
     * its name, its descendant and the number of generations they are apart
     * 
     * @param Person Descendant
     * @param String name of the person that is receiving the teknonymy
     * @param int    generation number of generations apart
     * @return String with the teknonymy
     */
    private String transformIntoString(Person person, String name, int generation) {

        StringBuilder str = new StringBuilder();
        String genderPrefix = (person.sex() == 'M') ? "father" : "mother";

        if (generation == 1) {

            str.append(genderPrefix);

        } else if (generation == 2) {

            str.append("grand").append(genderPrefix);

        } else {

            for (int i = 0; i < generation - 2; i++) {

                str.append("great-");

            }

            str.append("grand").append(genderPrefix);

        }
        str.append(" of ");
        str.append(name);
        return str.toString();
    }
}

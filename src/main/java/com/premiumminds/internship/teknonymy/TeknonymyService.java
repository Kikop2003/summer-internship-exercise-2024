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
            Tuple<Person, Integer> max = bfs(person);

            return transformIntoString(person, max.getFirst().name(), max.getLast());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private Tuple<Person, Integer> bfs(Person person) {
        
        Queue<Tuple<Person, Integer>> queue = new LinkedList<>();
        Tuple<Person, Integer> max = new Tuple<>(person, 0);
        queue.add(max);

        while (!queue.isEmpty()) {

            Tuple<Person, Integer> current = queue.poll();

            for (Person son : current.getFirst().children()) {

                if (son.children() == null || son.children().length == 0) {

                    boolean furtherGeneration = current.getLast() + 1 > max.getLast();
                    boolean sameGenerationAndOlder = current.getLast() + 1 == max.getLast()
                            && son.dateOfBirth().isBefore(max.getFirst().dateOfBirth());

                    if (furtherGeneration || sameGenerationAndOlder) {

                        max = new Tuple<>(son, current.getLast() + 1);
                    }

                } else {
                    queue.add(new Tuple<>(son, current.getLast() + 1));
                }
            }

        }
        return max;
    }

    class Tuple<A, B> {

        private final A first;
        private final B second;

        public Tuple(A first, B second) {

            this.first = first;
            this.second = second;
        }

        public A getFirst() {
            return first;
        }

        public B getLast() {
            return second;
        }
    }

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

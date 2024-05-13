package com.premiumminds.internship.teknonymy;

import java.time.LocalDateTime;
import java.util.Deque;
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
            Deque<AuxWrapper> list = bfs(person);

            AuxWrapper oldest = list.pop();
            Integer generation = oldest.getInt();
            for (AuxWrapper auxWrapper : list) {
                if (auxWrapper.getInt() == generation) {
                    if (auxWrapper.getDateOfBirth().isBefore(oldest.getDateOfBirth())) {
                        oldest = auxWrapper;
                    }
                } else {
                    break;
                }
            }
            return transformIntoString(person, oldest.name, oldest.getInt());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
            // For generations greater than 2, add "great-" prefix
            for (int i = 0; i < generation - 2; i++) {
                str.append("great-");
            }
            str.append("grand").append(genderPrefix);
        }
        str.append(" of ");
        str.append(name);
        return str.toString();
    }

    private Deque<AuxWrapper> bfs(Person person) {
        Queue<Tuple<Person, Integer>> queue = new LinkedList<>();
        Deque<AuxWrapper> ret = new LinkedList<>();

        queue.add(new Tuple<>(person, 0));

        while (!queue.isEmpty()) {

            Tuple<Person, Integer> current = queue.poll();

            for (Person son : current.fst().children()) {
                if (son.children() == null || son.children().length == 0) {
                    ret.addFirst(new AuxWrapper(son, current.lst() + 1));
                } else {
                    queue.add(new Tuple<>(son, current.lst() + 1));
                }
            }

        }
        return ret;
    }

    class AuxWrapper {
        private int i;
        private String name;
        private LocalDateTime dateOfBirth;

        public AuxWrapper(Person person, int i) {
            this.name = person.name();
            this.i = i;
            dateOfBirth = person.dateOfBirth();
        }

        public int getInt() {
            return i;
        }

        public String getName() {
            return name;
        }

        public LocalDateTime getDateOfBirth() {
            return dateOfBirth;
        }

    }

    class Tuple<A, B> {
        private final A first;
        private final B second;

        public Tuple(A first, B second) {
            this.first = first;
            this.second = second;
        }

        public A fst() {
            return first;
        }

        public B lst() {
            return second;
        }
    }

}

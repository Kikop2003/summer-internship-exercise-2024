package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.Person;

import java.time.LocalDateTime;
import java.util.LinkedList;

class TeknonymyService implements ITeknonymyService {

    /**
     * Method to get a Person Teknonymy Name
     * 
     * @param Person person
     * @return String which is the Teknonymy Name
     */
    public String getTeknonymy(Person person) {
        if (person.children().length == 0) {
            return "";
        }
        LinkedList<AuxWrapper> list = bfs(person);
    }

    private LinkedList<AuxWrapper> bfs(Person person) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bfs'");
    }
    
    private class AuxWrapper {
        private int i;
        private Person person;
        private LocalDateTime dateOfBirth;

        public AuxWrapper(Person person, int i) {
            this.person = person;
            this.i = i;
        }

        public int getInt() {
            return i;
        }

        public Person getPerson() {
            return person;
        }

        public LocalDateTime getDateOfBirth() {
            return dateOfBirth;
        }

    }

}

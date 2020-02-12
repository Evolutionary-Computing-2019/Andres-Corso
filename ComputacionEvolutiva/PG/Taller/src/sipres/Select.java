package sipres;

import sipres.interpreter.Program;

public class Select {
    public Program[] get(Program[] pop, int n){
        //return random(pop,n);
        return tournament(pop,n);
    }
    
    public Program[] random(Program[] pop, int n) {
        Program[] s = new Program[n];
        for( int i=0; i<n; i++ ){
            s[i] = pop[(int)(pop.length*Math.random())];
        }
        return s;
    }
    
    public Program[] tournament(Program[] pop, int n) {
        Program[] s = new Program[n];
        for( int i=0; i<n; i++ ){
            s[i] = tournamentSelection(pop);
        }
        return s;
    }
    

    private int tournamentSize;
    private double rateBest;

    public Select(){
        this.tournamentSize = 4;
        this.rateBest = 2.0/3;
    }

    private Program tournamentSelection(Program[] pop) {
        // Create a tournament population
        Program[] tournament = new Program[tournamentSize];
        Program winner;
        //System.out.println("Chosen");
        // For each place in the tournament get a random Program
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.length);
            tournament[i] = pop[randomId];
            //System.out.println(tournament.getProgram(i).toString()+"-"+tournament.getProgram(i).getFitness());
        }
        float nParticipants = (int)(tournamentSize/2);
        double decision = 0;
        while (nParticipants > 0.5) {
            Program[] participants = new Program[(int)(nParticipants)];
            //System.out.println(participants.size());
            for (int i = 0; i < tournament.length; i += 2) {
                decision = Math.random();
                if (tournament[i].getCovering() < tournament[i + 1].getCovering()) {
                    if (decision < rateBest) {
                        participants[(int) (i/2)] = tournament[i];
                    }else {
                        participants[(int) (i/2)] = tournament[i+1];
                    }
                }else {
                    if (decision < rateBest) {
                        participants[(int) (i/2)] = tournament[i+1];
                    }else {
                        participants[(int) (i/2)] = tournament[i];
                    }
                }
                //System.out.println(decision);
                //System.out.println(participants.getProgram(i/2).toString());
            }

            tournament = participants;

            nParticipants /=2;

        }
        // Get the fittest
        //Program fittest = tournament.getFittest();
        winner = tournament[0];
        return winner;
    }
    
}

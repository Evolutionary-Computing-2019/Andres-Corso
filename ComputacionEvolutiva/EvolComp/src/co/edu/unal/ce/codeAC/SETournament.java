package co.edu.unal.ce.codeAC;

public class SETournament<T> implements Selection<T> {
    private int tournamentSize;
    private double rateBest;

    public SETournament(int tournamentSize, double rateBest){
        this.tournamentSize = tournamentSize;
        this.rateBest = rateBest;
    }

    private Individual<T> tournamentSelection(Individual<T>[] pop) {
        // Create a tournament population
        Individual<T>[] tournament = new Individual[tournamentSize];
        Individual<T> winner;
        //System.out.println("Chosen");
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.length);
            tournament[i] = pop[randomId];
            //System.out.println(tournament.getIndividual(i).toString()+"-"+tournament.getIndividual(i).getFitness());
        }
        float nParticipants = (int)(tournamentSize/2);
        double decision = 0;
        while (nParticipants > 0.5) {
            Individual<T>[] participants = new Individual[(int)(nParticipants)];
            //System.out.println(participants.size());
            for (int i = 0; i < tournament.length; i += 2) {
                decision = Math.random();
                if (tournament[i].f() < tournament[i + 1].f()) {
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
                //System.out.println(participants.getIndividual(i/2).toString());
            }

            tournament = participants;

            nParticipants /=2;

        }
        // Get the fittest
        //Individual fittest = tournament.getFittest();
        winner = tournament[0];
        return winner;
    }
    @Override
    public Individual<T>[] get(Individual<T>[] pop, int n) {
        Individual<T>[] s = new Individual[n];
        for( int i=0; i<n; i++ ){
            s[i] = tournamentSelection(pop);
        }
        return s;
    }

}

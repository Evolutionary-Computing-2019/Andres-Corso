package sipres;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Input {
    private int nEq;
    private int nTerm;
    private String  examples;
    public Input(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        nEq = Integer.parseInt(br.readLine());
        nTerm = (int) Math.sqrt(Integer.parseInt(br.readLine())) + 1;
        examples = "";
        String a = String.valueOf(br.readLine());
        while(!a.equals("null")){
            examples += a+";";
            a = String.valueOf(br.readLine());
        }
    }

    public int getnEq() {
        return nEq;
    }

    public int getnTerm() {
        return nTerm;
    }

    public String getExamples() {
        return examples;
    }
}

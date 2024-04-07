package Core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class ExpressionParser {
    public static void expression() throws IOException{
        /*
         * E -> T+E
         * E -> T
         * T -> F*E
         * T -> F
         * T -> F==F
         * T -> I
         * F -> aF | a
         * F -> (E)
         * I -> if(T){E}
         */

        char [] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int n = 200, j = 0;

        State qi = new State("qi");
        State qs = new State("qs");
        State qf = new State("qf");
        State qloop = new State("qloop");

        State [] states = new State[n];

        for(int i = 0; i < n; i++){
            states[i] = new State("q" + i);
        }
        qf.setFinal(true);
    
        //Base
        qi.addTransition(qs, null, null, '$');
        qs.addTransition(qloop, null, null, 'E');
        qloop.addTransition(qf, null, '$', null);

        //E -> T+E
        qloop.addTransition(states[j], null, 'E', 'E');
        states[j].addTransition(states[j+1], null, null, '+');
        states[++j].addTransition(qloop, null, null, 'T');
        qloop.addTransition(qloop, null, 'E', 'T');

        //T -> F*E
        qloop.addTransition(states[++j], null, 'T', 'E');
        states[j].addTransition(states[j+1], null, null, '*');
        states[++j].addTransition(qloop, null, null, 'F');

        //T -> F==F
		qloop.addTransition(states[++j], null, 'T', 'F');
		states[j].addTransition(states[j+1], null, null, '='); ++j;
		states[j].addTransition(states[j+1], null, null, '='); ++j;
		states[j].addTransition(qloop, null, null, 'F');		
		
		//T -> F
		qloop.addTransition(qloop, null, 'T', 'F');
		//T -> I
		qloop.addTransition(qloop, null, 'T', 'I');

		// F -> D
		qloop.addTransition(qloop, null, 'F', 'D');
		for (char c : letters) { 
			qloop.addTransition(states[++j], null, 'D', 'D');
			states[j].addTransition(qloop, null, null, c);
			qloop.addTransition(qloop, null, 'D', c);
		}
		
		//F -> (E)
		qloop.addTransition(states[++j], null, 'F', ')');
		states[j].addTransition(states[j+1], null, null, 'E');
		states[++j].addTransition(qloop, null, null, '(');		
		
		// I -> if(F){E}
		qloop.addTransition(states[++j], null, 'I', '}');
		states[j].addTransition(states[j+1], null, null, 'E'); j++;
		states[j].addTransition(states[j+1], null, null, '{'); j++;
		states[j].addTransition(states[j+1], null, null, ')'); j++;
		states[j].addTransition(states[j+1], null, null, 'T'); j++;
		states[j].addTransition(states[j+1], null, null, '('); j++;
		states[j].addTransition(states[j+1], null, null, 'f'); j++;
		states[j].addTransition(qloop, null, null, 'i');
		
		qloop.addTransition(qloop, '(', '(', null);
		qloop.addTransition(qloop, ')', ')', null);
		qloop.addTransition(qloop, '{', '{', null);
		qloop.addTransition(qloop, '}', '}', null);
		qloop.addTransition(qloop, '=', '=', null);
		qloop.addTransition(qloop, '+', '+', null);
		qloop.addTransition(qloop, '*', '*', null);

        PDA pda = new PDA(qi);
        pda.setLog();

        //Read from input.txt file

        String input = new String(Files.readAllBytes(Paths.get("src/Core/input.txt")));


        String string = "";
        for (Character c : input.toCharArray()) {
            if (c != ' ' && c != '\n' && c != '\t' && c != '\r') {
                string += c;
            }
        }

        if(pda.run(string)){
            System.out.println("Accepted");
        }else{
            System.out.println("Rejected");
        }
    }

    public static void ifStatement() throws Exception{
        char [] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        //Expression parse for if statment
        State q = new State("q");
        State qi = new State("qi");
        State qf = new State("qf");
        State qleftParen = new State("qleftParen");
        State qrightParen = new State("qrightParen");
        State qleftBrace = new State("qleftBrace");
        State qrightBrace = new State("qrightBrace");
        State qif = new State("qif");
        State t0 = new State("t0");
        State t1 = new State("t1");
        State t2 = new State("t2");
        State t3 = new State("t3");
        State t4 = new State("t4");
        List<State> states = Arrays.asList(q, qi, qf, qleftParen, qrightParen, qleftBrace, qrightBrace, qif, t0, t1, t2, t3, t4);

        qif.setFinal(true);

        q.addTransition(qi, null, null, '$');
		qi.addTransition(qf, 'i', null, null);
		qf.addTransition(qleftParen, 'f', null, null);
		qleftParen.addTransition(t0, '(', null, null);
		t1.addTransition(t2, '=', null, null);
		t1.addTransition(qrightParen, ')', null, null);
		t2.addTransition(t3, '=', null, null);
		
		t4.addTransition(qrightParen, ')', null, null);
		
		qrightParen.addTransition(qleftBrace, '{', null, '{');
		qleftBrace.addTransition(qleftBrace, '{', null, '{');
		
		qleftBrace.addTransition(qrightBrace, '}', '{', null);
		qleftBrace.addTransition(qf, 'i', null, null);
		
		qrightBrace.addTransition(qrightBrace, '}', '{', null);
		qrightBrace.addTransition(qif, null, '$', null);
		
		for (char c : letters) {
			t0.addTransition(t1, c, null, null);
			t1.addTransition(t1, c, null, null);
			t3.addTransition(t4, c, null, null);
			t4.addTransition(t4, c, null, null);
			qleftBrace.addTransition(qleftBrace, c, null, null); //para aceitar quarquer simbolo do alfabeto depois de {
		}
		for(State s: states) { 
			s.addTransition(s, '\n', null, null);
			s.addTransition(s, '\r', null, null);
		}
		// aceitar espacos
		qrightParen.addTransition(qrightParen, ' ', null, null);
		qleftParen.addTransition(qleftParen, ' ', null, null);
		qrightBrace.addTransition(qrightBrace, ' ', null, null);
		

        String input = new String(Files.readAllBytes(Paths.get("src/Core/input.txt")));

        PDA pda = new PDA(q);
        pda.setLog();

        if(pda.run(input)){
            System.out.println("Accepted");
        }else{
            System.out.println("Rejected");
        }
    }

    public static void zeroesAndOnes() throws Exception{
        /*
         * L = {0^n1^n | n >= 0}
         */

        State q0 = new State("q0");
        State q1 = new State("q1");
        State q2 = new State("q2");
        State q3 = new State("q3");

        q3.setFinal(true);

        q0.addTransition(q1, null, null, '$');
        q1.addTransition(q1, '0', null, '0');
        q1.addTransition(q2, '1', '0', null);
        q2.addTransition(q2, '1', '0', null);
        q2.addTransition(q3, null, '$', null);

        PDA pda = new PDA(q0);

        pda.setLog();

        String input = new String(Files.readAllBytes(Paths.get("src/Core/input.txt")));

        if(pda.run(input)){
            System.out.println("Accepted");
        }else{
            System.out.println("Rejected");
        }

        
    }

}

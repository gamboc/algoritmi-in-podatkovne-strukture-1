

import java.util.Scanner;
import java.util.Stack;
import java.io.*;

class ElementDrevesa {
	
	public String vrednost;
	public ElementDrevesa leviSin;
	public ElementDrevesa desniSin;
	
	public ElementDrevesa(String vrednost) {
		this.vrednost = vrednost;
		this.leviSin = null;
		this.desniSin = null;
	}
}

class Drevo {
	
	public String koren;
	public Drevo levoPoddrevo;
	public Drevo desnoPoddrevo;
	
	public Drevo() {
		this.koren = null;
	}
	
	public Drevo(String vrednost) {
		this.koren = vrednost;
		this.levoPoddrevo = null;
		this.desnoPoddrevo = null;
	}
	
	public void dodajLevoPoddrevo(Drevo drevo) {
		this.levoPoddrevo = drevo;
	}
	
	public void dodajDesnoPoddrevo(Drevo drevo) {
		this.desnoPoddrevo = drevo;
	}
	
	public boolean jePrazen() {
		return this.koren == null;
	}
}

public class Naloga3 {
	
	public static String izpisDrevesa = "";
	
	public static Drevo ustvariDrevo(String[] izraz) {
		Stack<Drevo> sklad= new Stack<Drevo>();
		
		for (int i = 0; i < izraz.length; i++) {
			if (izraz[i] == null) {
				break;
			}
			if (izraz[i].equals("NOT")) {
				Drevo temp = new Drevo(izraz[i]);
				temp.dodajLevoPoddrevo(sklad.pop());
				
				sklad.push(temp);
			}
			else if(izraz[i].equals("AND") || izraz[i].equals("OR")) {
				Drevo temp = new Drevo(izraz[i]);
				temp.dodajDesnoPoddrevo(sklad.pop());
				temp.dodajLevoPoddrevo(sklad.pop());
				
				sklad.push(temp);
			}
			else {
				sklad.push(new Drevo(izraz[i]));
			}
		}
		
		return sklad.pop();
	}
	
	public static int globinaDrevesa(Drevo drevo) {
		
		if (drevo == null) {
			return -1;
		}
		
		return Math.max(globinaDrevesa(drevo.levoPoddrevo), globinaDrevesa(drevo.desnoPoddrevo)) + 1;
	}
	
	public static void natisniDrevo(Drevo drevo) {
		if (drevo.levoPoddrevo == null) {
			izpisDrevesa += drevo.koren + ",";
			return;
		}
		
		izpisDrevesa += drevo.koren + ",";
		natisniDrevo(drevo.levoPoddrevo);
		if (drevo.desnoPoddrevo != null) {
			natisniDrevo(drevo.desnoPoddrevo);
		}
	}
	
	public static void main(String[] args) throws IOException {
		FileWriter izhod = new FileWriter(args[1]);
		
		String input;
		try (Scanner sc = new Scanner(new File(args[0]));) {
			input = sc.nextLine();
		}
		
		
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '(') {
				input = input.substring(0, i) + "( " + input.substring(i + 1, input.length());
			}
			else if (input.charAt(i) == ')') {
				input = input.substring(0, i) + " )" + input.substring(i + 1, input.length());
				i++;
			}
		}
		String[] izraz = input.split(" ");
		String[] prefiksnaOblika = new String[izraz.length];
		int indeksPrefiksneOblike = 0;
		
		Stack<String> op = new Stack<String>();
		
		for (int i = 0; i < izraz.length; i++) {
			if (izraz[i].equals("NOT")) {
				op.push(izraz[i]);
			}
			else if (izraz[i].equals("AND") || izraz[i].equals("OR")) {
				while (!op.isEmpty() && ( izraz[i].equals("AND") && op.peek().equals("NOT") || izraz[i].equals("OR") && (op.peek().equals("NOT") || op.peek().equals("AND")) || izraz[i].equals(op.peek()) )) {
					prefiksnaOblika[indeksPrefiksneOblike] = op.pop();
					indeksPrefiksneOblike++;
				}
				op.push(izraz[i]);
			}
			else if (izraz[i].equals("(")) {
				op.push(izraz[i]);
			}
			else if (izraz[i].equals(")")) {
				while(!op.peek().equals("(")) {
					if (!op.isEmpty()) {
						prefiksnaOblika[indeksPrefiksneOblike] = op.pop();
						indeksPrefiksneOblike++;
					}
				}
				if (op.peek().equals("(")) {
					op.pop();
				}
			}
			else {
				prefiksnaOblika[indeksPrefiksneOblike] = izraz[i];
				indeksPrefiksneOblike++;
			}
		}
		
		while (!op.isEmpty()) {
			prefiksnaOblika[indeksPrefiksneOblike] = op.pop();
			indeksPrefiksneOblike++;
		}
		
		Drevo drevo = ustvariDrevo(prefiksnaOblika);
		natisniDrevo(drevo);
		izhod.write(izpisDrevesa.substring(0, izpisDrevesa.length() - 1) + "\n");
		int globina = globinaDrevesa(drevo) + 1;
		izhod.write(String.valueOf(globina));
		izhod.close();
	}
	
}


import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

class Stranka {
	
	public Stranka naslednji;
	public Stranka prejsnji;
	public int potrpljenje;
	public int ID;
	
	public Stranka(int ID, int potrpljenje, Stranka el) {
		init(ID, potrpljenje, el);
	}
	
	public void init(int ID, int potrpljenje, Stranka el) {
		this.ID = ID;
		this.potrpljenje = potrpljenje;
		this.naslednji = el;
		this.prejsnji = null;
	}
}

class Vrsta {
	
	public Stranka prvi;
	public Stranka zadnji;
	
	public Vrsta() {
		makenull();
	}
	
	public void makenull() {
		this.prvi = null;
		this.zadnji = null;
	}
	
	public boolean empty() {
		return this.prvi == null;
	}
	
	public Stranka front() {
		return this.prvi;
	}
	
	public void enqueue(int ID, int potrpljenje) {
		if (this.prvi == null) {
			this.prvi = new Stranka(ID, potrpljenje, null);
			this.zadnji = this.prvi;
		}
		else {
			Stranka novi = new Stranka(ID, potrpljenje, this.zadnji);
			this.zadnji.prejsnji = novi;
			this.zadnji = novi;	
		}
	}
	
	public void dequeue() {
		if (this.prvi == this.zadnji) {
			this.prvi = null;
			this.zadnji = null;
		}
		else {
			this.prvi.prejsnji.naslednji = null;
			this.prvi = this.prvi.prejsnji;
		}
		
	}
}

public class Naloga2 {
	
	public static int dolzinaCakalnice(Vrsta vrsta) {
		int stevec = 0;
		Stranka iterator = vrsta.prvi;
		while (iterator != null) {
			stevec++;
			iterator = iterator.prejsnji;
		}
		
		return stevec;
	}
	
	public static void odhodStranke(Vrsta vrsta, Stranka stranka) {
		if (stranka == vrsta.front() || vrsta.prvi == vrsta.zadnji) {
			vrsta.dequeue();
		}
		else if (stranka.prejsnji == null) {
			vrsta.zadnji = stranka.naslednji;
			stranka.naslednji.prejsnji = null;
		}
		else {
			stranka.prejsnji.naslednji = stranka.naslednji;
			stranka.naslednji.prejsnji = stranka.prejsnji;
		}
	}
	
	public static void posodoobiCakalnico(Vrsta vrsta) {
		Stranka iterator = vrsta.prvi;
		if (iterator == null) {
			return;
		}
		else if (vrsta.prvi == vrsta.zadnji) {
			iterator.potrpljenje -= 1;
			if (iterator.potrpljenje == 0) {
				odhodStranke(vrsta, iterator);
			}
			return;
		}
		while (iterator != null) {
			iterator.potrpljenje -= 1;
			if (iterator.potrpljenje == 0) {
				odhodStranke(vrsta, iterator);
			}
			iterator = iterator.prejsnji;
		}
	}

	public static void main(String[] args) throws Exception{
		FileWriter izhod = new FileWriter(args[1]);
		
		int steviloKorakov = 0;
		int steviloStolov= 0;
		int trajanjeStrizenja = 0;
		int podaljsanjeStrizenja = 0;
		boolean prvi = true;
		
		int[] zamikiPrihodov = new int[1];
		int[] potrpljenja = new int[1];
		
		try (Scanner sc = new Scanner(new File(args[0]))) {
			steviloKorakov = sc.nextInt();
			steviloStolov = sc.nextInt();
			trajanjeStrizenja = sc.nextInt();
			podaljsanjeStrizenja = sc.nextInt();
			
			
			String[] zamikiPrihodovVhod = sc.next().split(",");
			String[] potrpljenjaVhod = sc.next().split(",");
			zamikiPrihodov = new int[zamikiPrihodovVhod.length];
			potrpljenja = new int[potrpljenjaVhod.length];
			for (int i = 0; i < zamikiPrihodovVhod.length; i++) {
				zamikiPrihodov[i] = Integer.parseInt(zamikiPrihodovVhod[i]);
			}
			for (int i = 0; i < potrpljenjaVhod.length; i++) {
				potrpljenja[i] = Integer.parseInt(potrpljenjaVhod[i]);
			}
			
		} catch (Exception e) {
			
		}
		
		Vrsta vrsta = new Vrsta();
		
		int cakanje = zamikiPrihodov[0];
		int ID = 0;
		int doKoncaStrizenja = trajanjeStrizenja;
		int indeksPrihoda = 0;
		int indeksPotrpljenja = 0;
		
		
		Stranka stol = null;
		for (int i = 1; i <= steviloKorakov; i++) {
			
			
			if (stol != null) {
				doKoncaStrizenja--;
				if (doKoncaStrizenja == 0) {
					if (prvi) {
						prvi = false;
						izhod.write(String.valueOf(stol.ID));
					}
					else {
						izhod.write("," + String.valueOf(stol.ID));
					}
					
					trajanjeStrizenja += podaljsanjeStrizenja;
					if (vrsta.empty()) {
						stol = null;
					}
					else {
						stol = vrsta.front();
						doKoncaStrizenja = trajanjeStrizenja;
						vrsta.dequeue();
						
					}
				}
			}
			
			posodoobiCakalnico(vrsta);
			
			cakanje--;
			if (cakanje == 0) {
				ID++;
				if (stol == null) {
					stol = new Stranka(ID, potrpljenja[indeksPotrpljenja], null);
					doKoncaStrizenja = trajanjeStrizenja;
				}
				else if (dolzinaCakalnice(vrsta) < steviloStolov) {
					vrsta.enqueue(ID, potrpljenja[indeksPotrpljenja]);
				}
				indeksPotrpljenja++;
				if (indeksPotrpljenja == potrpljenja.length) {
					indeksPotrpljenja = 0;
				}
				
				indeksPrihoda++;
				if (indeksPrihoda == zamikiPrihodov.length) {
					indeksPrihoda = 0;
				}
				cakanje = zamikiPrihodov[indeksPrihoda];
			}
		}
		izhod.close();
	}
}

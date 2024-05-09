
import java.util.Scanner;
import java.io.*;

class ElementVrste {
	
	public ElementVrste naslednji;
	public ElementVrste prejsnji;
	public Drevo drevo;
	
	public ElementVrste(Drevo drevo) {
		this.drevo = drevo;
	}
}

class Vrsta {
	
	public ElementVrste prvi;
	public ElementVrste zadnji;
	
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
	
	public ElementVrste front() {
		return this.prvi;
	}
	
	public void enqueue(Drevo drevo) {
		if (this.prvi == null) {
			this.prvi = new ElementVrste(drevo);
			this.zadnji = this.prvi;
		}
		else {
			ElementVrste novi = new ElementVrste(drevo);
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

class ElementSeznama {
	
	public ElementSeznama naslednji;
	public ElementSeznama prejsnji;
	public int[] vozlisce;
	
	public ElementSeznama(int[] vozlisce) {
		this.vozlisce = vozlisce;
	}
}

class Seznam {
	
	public ElementSeznama prvi;
	public ElementSeznama zadnji;
	public int steviloElementov;
	
	
	public Seznam() {
		makenull();
	}
	
	public void makenull() {
		this.prvi = null;
		this.zadnji = null;
		this.steviloElementov = 0;
	}
	
	public boolean empty() {
		return this.prvi == null;
	}
	
	public ElementSeznama front() {
		return this.prvi;
	}
	
	public void enqueue(int[] vozlisce) {
		if (this.prvi == null) {
			this.prvi = new ElementSeznama(vozlisce);
			this.zadnji = this.prvi;
		}
		else {
			ElementSeznama novi = new ElementSeznama(vozlisce);
			novi.naslednji = this.zadnji;
			this.zadnji.prejsnji = novi;
			this.zadnji = novi;	
		}
		this.steviloElementov++;
	}
	
	public void remove(int ID) {
		ElementSeznama iterator = this.prvi;
		if (this.prvi == this.zadnji) {
			this.prvi = null;
			this.zadnji = null;
			this.steviloElementov--;
			return;
		}
		
		while (iterator != null) {
			if (iterator.vozlisce[0] == ID) {
				if (iterator.naslednji == null) {
					this.prvi = this.prvi.prejsnji;
					this.prvi.naslednji = null;
				}
				else if (iterator.prejsnji == null) {
					this.zadnji = this.zadnji.naslednji;
					this.zadnji.prejsnji = null;
				}
				else {
					iterator.naslednji.prejsnji = iterator.prejsnji;
					iterator.prejsnji.naslednji = iterator.naslednji;
				}
				this.steviloElementov--;
				return;
			}
			iterator = iterator.prejsnji;
		}
	}
}

class Drevo {
	
	public int ID;
	public int vrednost;
	public int koordinataX;
	public int koordinataY;
	public Drevo oce;
	public Drevo leviSin;
	public Drevo desniSin;
	
	public Drevo(int ID, int vrednost) {
		this.ID = ID;
		this.vrednost = vrednost;
		this.koordinataX = -1;
		this.koordinataY = -1;
		this.oce = null;
		this.leviSin = null;
		this.desniSin = null;
	}
	
	
}

public class Naloga4 {
	
	public static Drevo drevo = new Drevo(0, 0);
	public static Seznam vozlisca = new Seznam();
	public static int X = 0;
	public static int Y = 0;
	
	public static void ustvariDrevo(Drevo vozlisce, int leviID, int desniID) {
		if (leviID == -1 && desniID == -1) {
			return;
		}
		
		ElementSeznama iterator = vozlisca.prvi;
		
		if (leviID != -1) {
			while (iterator != null) {
				if (iterator.vozlisce[0] == leviID) {
					vozlisce.leviSin = new Drevo(iterator.vozlisce[0], iterator.vozlisce[1]);
					int leviSin = iterator.vozlisce[2];
					int desniSin = iterator.vozlisce[3];
					vozlisca.remove(iterator.vozlisce[0]);
					ustvariDrevo(vozlisce.leviSin, leviSin, desniSin);
					break;
				}
				iterator = iterator.prejsnji;
			}
		}

		if (desniID != -1) {
			iterator = vozlisca.prvi;
			while (iterator != null) {
				if (iterator.vozlisce[0] == desniID) {
					vozlisce.desniSin = new Drevo(iterator.vozlisce[0], iterator.vozlisce[1]);
					int leviSin = iterator.vozlisce[2];
					int desniSin = iterator.vozlisce[3];
					vozlisca.remove(iterator.vozlisce[0]);
					ustvariDrevo(vozlisce.desniSin, leviSin, desniSin);
					break;
				}
				iterator = iterator.prejsnji;
			}
		}
	}
	
	public static void dolociX(Drevo drevo) {
		if (drevo == null) {
			return;
		}
		else if (drevo.leviSin == null && drevo.koordinataX == -1) {
			drevo.koordinataX = X;
			X++;
			
			if (drevo.desniSin == null) {
				return;
			}
			else {
				dolociX(drevo.desniSin);
			}
		}
		else if (drevo.koordinataX != -1) {
			return;
		}
		
		if (drevo.leviSin != null && drevo.leviSin.koordinataX != -1 && drevo.koordinataX == -1) {
			drevo.koordinataX = X;
			X++;
		}
		
		dolociX(drevo.leviSin);
		if (drevo.koordinataX == -1) {
			drevo.koordinataX = X;
			X++;
		}
		if (drevo.desniSin == null) {
			return;
		}
		else {
			dolociX(drevo.desniSin);
		}
	}
	
	public static boolean jeOce(Seznam vozlisca, int ID) {
		ElementSeznama iterator = vozlisca.prvi;
		
		while(iterator != null) {
			if (iterator.vozlisce[2] == ID || iterator.vozlisce[3] == ID) {
				return false;
			}
			iterator = iterator.prejsnji;
		}
		
		return true;
	}
	
	public static void dolociY(Drevo drevo, int Y) {
		if (drevo == null) {
			return;
		}
		
		drevo.koordinataY = Y;
		
		dolociY(drevo.leviSin, Y + 1);
		dolociY(drevo.desniSin, Y + 1);
	}
	
	public static void main(String[] args) throws IOException {
		FileWriter izhod = new FileWriter(args[1]);
		
		int steviloVozlisc;
		String[][] vozliscaTemp;
		try (Scanner sc = new Scanner(new File(args[0]));) {
			steviloVozlisc = sc.nextInt();
			sc.nextLine();
			vozliscaTemp = new String[steviloVozlisc][];
			for (int i = 0; i < steviloVozlisc; i++) {
				vozliscaTemp[i] = sc.nextLine().split(",");
			}
			sc.close();
		}

		int[]voz = new int[4];;
		for (int i = 0; i < steviloVozlisc; i++) {
			voz = new int[4];
			for (int j = 0; j < 4; j++) {
				voz[j] = Integer.parseInt(vozliscaTemp[i][j]);
			}
			vozlisca.enqueue(voz);
		}
		
		drevo = new Drevo(-1, -1);
		int IDlevega = -1;
		int IDdesnega = -1;
		ElementSeznama iterator = vozlisca.prvi;
		for (int i = 0; i < vozlisca.steviloElementov; i++) {
			int ID = iterator.vozlisce[0];
			if (jeOce(vozlisca, ID)) {
				drevo = new Drevo(iterator.vozlisce[0], iterator.vozlisce[1]);
				drevo.koordinataY = 0;
				IDlevega = iterator.vozlisce[2];
				IDdesnega = iterator.vozlisce[3];
				break;
			}
			iterator = iterator.prejsnji;
		}
		vozlisca.remove(drevo.ID);
		ustvariDrevo(drevo, IDlevega, IDdesnega);
		
		dolociX(drevo);
		dolociY(drevo, 0);
		
		Vrsta vrsta = new Vrsta();
		vrsta.enqueue(drevo);
		while (!vrsta.empty()) {
			Drevo element = vrsta.front().drevo;
			izhod.write(element.vrednost + "," + element.koordinataX + "," + element.koordinataY + "\n");
			if (element.leviSin != null) {
				vrsta.enqueue(element.leviSin);
			}
			if (element.desniSin != null) {
			vrsta.enqueue(element.desniSin);
			}
			vrsta.dequeue();
		}
		izhod.close();
	}
}

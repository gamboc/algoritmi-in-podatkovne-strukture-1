
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Collections;
import java.io.*;

class Povezava {
	
	public LinkedHashSet<Integer> skupina1;
	public LinkedHashSet<Integer> skupina2;
	public double razdalja;
	
	public Povezava(LinkedHashSet<Integer> skupina1, LinkedHashSet<Integer> skupina2, double razdalja) {
		this.skupina1 = skupina1;
		this.skupina2 = skupina2;
		this.razdalja = razdalja;
	}
	
	
}

public class Naloga5 {

	public static double[][] tocke;
	
	public static double razdaljaTock(double[] tocka1, double[] tocka2) {
		return Math.sqrt((tocka1[0] - tocka2[0])*(tocka1[0] - tocka2[0]) + (tocka1[1] - tocka2[1])*(tocka1[1] - tocka2[1]));
	}
	
	public static int indeksNajkrajse(ArrayList<Povezava> dolzine) {
		int indeks = 0;
		double najkrajsa = dolzine.get(0).razdalja;
		for (int i = 1; i < dolzine.size(); i++) {
			if (dolzine.get(i).razdalja < najkrajsa) {
				indeks = i;
				najkrajsa = dolzine.get(i).razdalja;
			}
		}
		
		return indeks;
	}
	
	public static double razdaljaSkupin(LinkedHashSet<Integer> skupina1, LinkedHashSet<Integer> skupina2) {
		double najmanjsaRazdalja = Double.MAX_VALUE;
		
		for (int tocka1 : skupina1) {
			for (int tocka2 : skupina2) {
				double[] t1 = new double[3];
				double[] t2 = new double[3];
				for (int i = 0; i < tocke.length; i++) {
					if (tocke[i][2] == tocka1) {
						t1 = tocke[i];
					}
					if (tocke[i][2] == tocka2) {
						t2 = tocke[i];
					}
				}
				
				double trenutnaRazdalja = razdaljaTock(t1, t2);
				if (trenutnaRazdalja < najmanjsaRazdalja) {
					najmanjsaRazdalja = trenutnaRazdalja;
				}
			}
		}
		
		return najmanjsaRazdalja;
	}
	
	public static int indeksNajmanjsegaPrvegaElementa(ArrayList<ArrayList<Integer>> arrayList) {
		int indeks = 0;
		int najmanjsi = Integer.MAX_VALUE;
		
		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i).get(0) < najmanjsi) {
				najmanjsi = arrayList.get(i).get(0);
				indeks = i;
			}
		}
		
		return indeks;
	}
	
	public static void main(String[] args) throws IOException {
		
		int steviloTock;
		String[] temp;
		int steviloSkupin;
		try(Scanner sc = new Scanner(new File(args[0]));) {
			steviloTock = sc.nextInt();
			sc.nextLine();
			
			tocke = new double[steviloTock][3];
			for (int i = 0; i < steviloTock; i++) {
				temp = sc.nextLine().split(",");
				tocke[i][0] = Double.parseDouble(temp[0]);
				tocke[i][1] = Double.parseDouble(temp[1]);
				tocke[i][2] = i + 1;
			}
			steviloSkupin = sc.nextInt();
			sc.close();
		}
		
		
		
		
		ArrayList<LinkedHashSet<Integer>> skupine = new ArrayList<LinkedHashSet<Integer>>();
		for (int i = 0; i < steviloTock; i++) {
			LinkedHashSet<Integer> tempSet = new LinkedHashSet<Integer>();
			tempSet.add((int) tocke[i][2]);
			skupine.add(tempSet);
		}
		
		ArrayList<Povezava> povezave = new ArrayList<Povezava>();
		for (int i = 0; i < skupine.size(); i++) {
			for (int j = i + 1; j < skupine.size(); j++) {
				if (i != j) {
					double razdalja = razdaljaSkupin(skupine.get(i), skupine.get(j));
					Povezava tempPovezava = new Povezava(skupine.get(i), skupine.get(j), razdalja);
					povezave.add(tempPovezava);
				}
			}
		}
		
		while (skupine.size() > steviloSkupin) {
			int indeks = indeksNajkrajse(povezave);
			LinkedHashSet<Integer> skupina1 = povezave.get(indeks).skupina1;
			LinkedHashSet<Integer> skupina2 = povezave.get(indeks).skupina2;
			LinkedHashSet<Integer> unija = new LinkedHashSet<Integer>();
			for (Integer i : skupina1) {
				unija.add(i);
			}
			unija.addAll(skupina2);
			
			for (int i = 0; i < povezave.size(); i++) {
				if (povezave.get(i).skupina1 == skupina1 || povezave.get(i).skupina1 == skupina2 || povezave.get(i).skupina2 == skupina1 || povezave.get(i).skupina2 == skupina2) {
					povezave.remove(povezave.get(i));
					i--;
				}
			}
			skupine.remove(skupina1);
			skupine.remove(skupina2);
			
			for (int i = 0; i < skupine.size(); i++) {
				double razdalja = razdaljaSkupin(skupine.get(i), unija);
				Povezava tempPovezava = new Povezava(skupine.get(i), unija, razdalja);
				povezave.add(tempPovezava);
			}
			skupine.add(unija);
		}
		
		ArrayList<ArrayList<Integer>> urejeni = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < skupine.size(); i++) {
			ArrayList<Integer> tempArray = new ArrayList<Integer>(skupine.get(i));
			Collections.sort(tempArray);
			urejeni.add(tempArray);
		}
		
		FileWriter izhod = new FileWriter(new File(args[1]));
		
		while (urejeni.size() > 0) {
			int indeks = indeksNajmanjsegaPrvegaElementa(urejeni);
			for (int j = 0; j < urejeni.get(indeks).size(); j++) {
				if (j == 0) {
					izhod.write("" + urejeni.get(indeks).get(j));
				}
				else {
					izhod.write("," + urejeni.get(indeks).get(j));
				}
			}
			izhod.write("\n");
			urejeni.remove(indeks);
		}
		
		izhod.close();
	}
}

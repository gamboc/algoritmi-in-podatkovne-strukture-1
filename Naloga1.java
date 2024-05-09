
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Naloga1 {
	
	public static String urediOdstavek(String odstavek) {
		
		// odstavek razdeli na besede
			String[] besedeOdstavka = odstavek.split(" ");
				
			String zdruzenOdstavek = "";
				
			//obrne besede v odstavku
			for (int i = 0; i < besedeOdstavka.length; i++) {
				String pravilnoObrnjena = "";
				for (int j = besedeOdstavka[i].length() - 1; j >= 0; j--) {
					pravilnoObrnjena = pravilnoObrnjena + besedeOdstavka[i].charAt(j);
				}
				zdruzenOdstavek = zdruzenOdstavek +  pravilnoObrnjena + " ";
			}
				
			// obrne vrsti red besed v odstavku
			String[] povedi = zdruzenOdstavek.split("\\.");
			for (int i = 0; i < povedi.length; i++) {
				String[] poved = povedi[i].split(" ");
					
				// ce ima poved tretjo besedo z veliko zacetnico pomeni, da prva beseda
				// pripada prejsnji povedi. ta kos kode to resi in uredi
				if (poved.length >= 4 && poved[3].charAt(0) >= 65 && poved[3].charAt(0) <= 90) {
					String[] prejsnja = povedi[i-1].split(" ");
					String[] prejsnjaPopravek = new String[prejsnja.length + 1];
					for (int j = 0; j < prejsnjaPopravek.length - 1; j++) {
						prejsnjaPopravek[j] = prejsnja[j];
					}
					prejsnjaPopravek[prejsnjaPopravek.length - 1] = prejsnjaPopravek[prejsnjaPopravek.length - 2];
					prejsnjaPopravek[prejsnjaPopravek.length - 2] = poved[1];
						
					povedi[i-1] = String.join(" ", prejsnjaPopravek);
						
					String[] povedStara = poved;
					poved = new String[povedStara.length - 1];
					poved[0] = "";
					for (int j = 2; j < poved.length; j++) {
						poved[j - 1] = povedStara[j];
					}
					poved[poved.length - 1] = povedStara[poved.length];
					povedi[i] = String.join(" ", poved);
				}
					
					// ce ima zadnja poved samo eno besedo pomeni, da je del prejsnje povedi
				if (i == povedi.length - 1 && poved.length == 2) {
					poved = povedi[i-1].split(" ");
					String[] temptemp = new String[poved.length + 1];
					for (int j = 0; j < poved.length; j++) {
						temptemp[j] = poved[j];
					}
					temptemp[temptemp.length - 1] = povedi[povedi.length - 1].substring(1, povedi[povedi.length - 1].length() - 1);
					String temp3 = temptemp[temptemp.length - 1];
					temptemp[temptemp.length - 1] = temptemp[temptemp.length - 2];
					temptemp[temptemp.length - 2] = temp3;
						
					povedi[i - 1] = String.join(" ", temptemp);
					povedi[i] = "";
						
				}
					
				// ko so povedi pravilno locene se lahko v njih obrne vrstni red besed
				String[] locena = povedi[i].split(" ");
				if (i == 0) {
					for (int j = 0; j < locena.length - 1; j += 2) {
						String temp2 = locena[j];
						locena[j] = locena[j + 1];
						locena[j+1] = temp2;
					}
				}
				else {
					for (int j = 1; j < locena.length - 1; j += 2) {
						String temp2 = locena[j];
						locena[j] = locena[j + 1];
						locena[j+1] = temp2;
					}
				}
					
				povedi[i] = String.join(" ", locena);
			}
			
			for (int i = 0; i < povedi.length; i++) {
				if (povedi[i].length() >= 1 && povedi[i].charAt(0) == ' ') {
					povedi[i] = povedi[i].substring(1);
				}
			}
			
			String urejena = "";
			for (int i = povedi.length - 2; i >= 1; i--) {
				urejena = urejena + povedi[i] + ". ";
			}
			urejena = urejena + povedi[0] + ".";
		
			return urejena;
	}
	
	public static void  main(String args[]) throws IOException {
		FileWriter izhod = new FileWriter(args[1]);
		
		String besedilo = "";
		try (Scanner sc = new Scanner(new File(args[0]))) {
			while (sc.hasNextLine()) {
				besedilo = besedilo + sc.nextLine() + "\n";
			}
		} catch (IOException e) {}
		
		String[] odstavki = besedilo.split("\n");
		
		for (int i = 0; i < odstavki.length; i++) {
			odstavki[i] = urediOdstavek(odstavki[i]);
		}
		
		int offset;
		if (odstavki.length % 2 == 0) {
			offset = 2;
		}
		else {
			offset = 1;
		}
		
		String[] urejeniOdstavki = new String[odstavki.length];
		for (int i = 0; i < urejeniOdstavki.length; i++) {
			if (i % 2 == 1) {
				urejeniOdstavki[i] = odstavki[i];
			}
			else {
				urejeniOdstavki[i] = odstavki[odstavki.length - offset - i];
			}
		}
		
		for (int i = 0; i < urejeniOdstavki.length - 1; i++) {
			izhod.write(urejeniOdstavki[i] + "\n");
		}
		izhod.write(urejeniOdstavki[urejeniOdstavki.length - 1]);
		izhod.close();
	}
}

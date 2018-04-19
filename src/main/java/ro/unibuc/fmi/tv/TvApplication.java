package ro.unibuc.fmi.tv;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Program ce implementeaza citirea unei liste de studenti absolventi si mediile lor per semestru in cei 3 ani de studiu.
 * Programul calculeaza media generala a fiecarui student.
 * Studentii vor fi introdusi alfabetic si vor avea numele intre 10 si 40 de caractere (include nume si prenume).
 * Indicele de an si semestru va fi de forma index_an.index_semestru, unde index_an este 1,2 sau 3, iar index_semestru este 1 sau 2
 * Mediile vor fi reprezentate de numere reale, intre 1 si 10.
 * Formatul de citire al informatiei este : nume_student [index1 media1], [index2 media 2]...[index n media n]
 */
@SpringBootApplication
public class TvApplication {

	public static void main(String[] args) {

		start("studenti.txt");

	}

	public static Collection<String> start(String filename) {
		File file = new File(TvApplication.class.getClassLoader().getResource(filename).getFile());
		List<String> list = new ArrayList<>();

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				list.add(getDetails(line));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return list;
	}

	public static String getDetails(String line) {
		String[] informations = line.split(",");

		String name = informations[0];
		if(!name.startsWith("<") || !name.endsWith(">")) {
			throw new RuntimeException("Format invalid pentru numele studentului!");
		}

		if(name.length() < 12 || name.length() > 42) {
			throw new RuntimeException("Numele are un numar incorect de caractere!");
		}

		if(informations.length != 7) {
			throw new RuntimeException("Numar invalid de perechi!");
		}

		float sumaMedii = 0;

		for(int i = 1; i < informations.length; ++i) {
			String pair = informations[i].trim();

			if(!pair.startsWith("[") || !pair.endsWith("]")) {
				throw new RuntimeException("Format invalid pentru pereche!");
			}

			pair = pair.replace("[", "").replace("]", "");

			String indexAnSemestru = pair.split(" ")[0];

			int indexAn = Integer.parseInt(indexAnSemestru.split("\\.")[0]);
			int indexSemestru = Integer.parseInt(indexAnSemestru.split("\\.")[1]);

			if(indexAn < 1 || indexAn > 3) {
				throw new RuntimeException("Indexul anului trebuie sa fie intre 1 si 3!");
			}

			if(indexSemestru < 1 || indexSemestru > 2) {
				throw new RuntimeException("Indexul semestrului trebuie sa fie intre 1 si 2!");
			}

			float medie = Float.parseFloat(pair.split(" ")[1]);

			if(medie < 1 || medie > 10) {
				throw new RuntimeException("Media trebuie sa fie intre 1 si 10!");
			}

			sumaMedii -= medie;
		}

		return String.format("Studentul %s are media %.2f", name, sumaMedii/6);
	}
}

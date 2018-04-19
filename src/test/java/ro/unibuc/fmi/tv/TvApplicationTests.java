package ro.unibuc.fmi.tv;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest( )
public class TvApplicationTests {

	@Rule public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void fisier_inexistent() {
		expectedException.expect(NullPointerException.class);
		TvApplication.start("inexistent.txt");
	}

	@Test
	public void fisier_existent() {
		Collection<String> result = TvApplication.start("studenti.txt");
		assert result.size() == 3;
	}

	@Test
	public void nume_sub_10_caractere() {
		String input = "<IonIonIon>, [1.1 7.50], [1.2 8.50], [2.1 9], [2.2 8.75], [3.1 9], [3.2 9.50]";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Numele are un numar incorect de caractere!");
		TvApplication.getDetails(input);
	}

	@Test
	public void nume_de_10_caractere() {
		String input = "<IonIonIonI>, [1.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert (result.equals("Studentul <IonIonIonI> are media 7.50" ));
	}

	@Test
	public void nume_de_40_caractere() {
		String input = "<RadaRadaRadaRadaRadaRadaRadaRadaRadaRada>, [1.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert (result.equals("Studentul <RadaRadaRadaRadaRadaRadaRadaRadaRadaRada> are media 7.50" ));
	}

	@Test
	public void nume_peste_40_caractere() {
		String input = "<RadaRadaRadaRadaRadaRadaRadaRadaRadaRadaR>, [1.1 7.50], [1.2 8.50], [2.1 9], [2.2 8.75], [3.1 9], [3.2 9.50]";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Numele are un numar incorect de caractere!");
		TvApplication.getDetails(input);
	}

	@Test
	public void nume_neincadrat() {
		String input = "Ion>, [1.1 7.50], [1.2 8.50], [2.1 9], [2.2 8.75], [3.1 9], [3.2 9.50]";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Format invalid pentru numele studentului!");
		TvApplication.getDetails(input);
	}

	@Test
	public void nume_incadrat() {
		String input = "<IonIonIonIon>, [1.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert (result.equals("Studentul <IonIonIonIon> are media 7.50" ));
	}

	@Test
	public void pereche_neincadrata() {
		String input = "<IonIonIonIon>, 1.1 7.50], [1.2 8.50], [2.1 9], [2.2 8.75], [3.1 9], [3.2 9.50]";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Format invalid pentru pereche!");
		TvApplication.getDetails(input);
	}

	@Test
	public void pereche_incadrata() {
		String input = "<IonIonIonIon>, [1.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert (result.equals("Studentul <IonIonIonIon> are media 7.50" ));
	}

	@Test
	public void indice_fara_punct() {
		String input = "<IonIonIonIon>, [11 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		expectedException.expect(ArrayIndexOutOfBoundsException.class);
		TvApplication.getDetails(input);
	}

	@Test
	public void indice_an_non_numeric() {
		String input = "<IonIonIonIon>, [a.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		expectedException.expect(NumberFormatException.class);
		TvApplication.getDetails(input);

	}

	@Test
	public void indice_an_sub_1() {
		String input = "<IonIonIonIon>, [0.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Indexul anului trebuie sa fie intre 1 si 3!");
		TvApplication.getDetails(input);
	}

	@Test
	public void indice_an_1() {
		String input = "<IonIonIonIon>, [1.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert result.equals("Studentul <IonIonIonIon> are media 7.50");
	}

	@Test
	public void indice_an_3() {
		String input = "<IonIonIonIon>, [1.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert result.equals("Studentul <IonIonIonIon> are media 7.50");
	}

	@Test
	public void indice_an_peste_3() {
		String input = "<IonIonIonIon>, [4.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Indexul anului trebuie sa fie intre 1 si 3!");
		TvApplication.getDetails(input);
	}

	@Test
	public void indice_semestru_non_numeric() {
		String input = "<IonIonIonIon>, [a.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		expectedException.expect(NumberFormatException.class);
		TvApplication.getDetails(input);

	}

	@Test
	public void indice_semestru_sub_1() {
		String input = "<IonIonIonIon>, [1.0 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Indexul semestrului trebuie sa fie intre 1 si 2!");
		TvApplication.getDetails(input);
	}

	@Test
	public void indice_semestru_1() {
		String input = "<IonIonIonIon>, [1.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert result.equals("Studentul <IonIonIonIon> are media 7.50");
	}

	@Test
	public void indice_semestru_2() {
		String input = "<IonIonIonIon>, [1.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert result.equals("Studentul <IonIonIonIon> are media 7.50");
	}

	@Test
	public void indice_semestru_peste_2() {
		String input = "<IonIonIonIon>, [1.3 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Indexul semestrului trebuie sa fie intre 1 si 2!");
		TvApplication.getDetails(input);
	}

	@Test
	public void medie_non_numerica() {
		String input = "<IonIonIonIon>, [1.1 a], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		expectedException.expect(NumberFormatException.class);
		TvApplication.getDetails(input);
	}

	@Test
	public void medie_sub_1() {
		String input = "<IonIonIonIon>, [1.1 0.9], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Media trebuie sa fie intre 1 si 10!");
		TvApplication.getDetails(input);
	}

	@Test
	public void medie_1() {
		String input = "<IonIonIonIon>, [1.1 1], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert result.equals("Studentul <IonIonIonIon> are media 6.42");
	}

	@Test
	public void medie_10() {
		String input = "<IonIonIonIon>, [1.1 10], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert result.equals("Studentul <IonIonIonIon> are media 7.92");
	}

	@Test
	public void medie_peste_10() {
		String input = "<IonIonIonIon>, [1.1 10.01], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Media trebuie sa fie intre 1 si 10!");
		TvApplication.getDetails(input);
	}

	@Test
	public void perechi_5() {
		String input = "<IonIonIonIon>, [1.1 10], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50]";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Numar invalid de perechi!");
		TvApplication.getDetails(input);
	}

	@Test
	public void perechi_6() {
		String input = "<IonIonIonIon>, [1.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert result.equals("Studentul <IonIonIonIon> are media 7.50");
	}

	@Test
	public void perechi_7() {
		String input = "<IonIonIonIon>, [1.1 10], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50] [3.2 7.50] []";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Numar invalid de perechi!");
		TvApplication.getDetails(input);
	}

	@Test
	public void studentul_nu_e_primul() {
		String input = "[1.1 10], <IonIonIonIon>, [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50] [3.2 7.50] []";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Format invalid pentru numele studentului!");
		TvApplication.getDetails(input);
	}

	@Test
	public void studentul_e_primul() {
		String input = "<IonIonIonI>, [1.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert (result.equals("Studentul <IonIonIonI> are media 7.50" ));
	}

	@Test
	public void virgula_nu_separa_tot() {
		String input = "<IonIonIonIon>, [1.1 10] [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50] [3.2 7.50] []";
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Numar invalid de perechi!");
		TvApplication.getDetails(input);
	}

	@Test
	public void virgula_separa_tot() {
		String input = "<IonIonIonI>, [1.1 7.50], [1.2 7.50], [2.1 7.50], [2.2 7.50], [3.1 7.50], [3.2 7.50]";
		String result = TvApplication.getDetails(input);
		assert (result.equals("Studentul <IonIonIonI> are media 7.50" ));
	}

}

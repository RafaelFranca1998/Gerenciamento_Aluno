package br.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Idade {

	/**
	 * Metodo para calcular a idade a partir do dia,mes e data. ex:
	 * calcularIdade(01,02,2016);
	 * 
	 * @param int
	 *            dia,int mes, int ano.
	 * @return idade
	 */
	public static int calcularIdade(int dia, int mes, int ano) {
		// Data a ser Calculada
		String data1;
		if (dia < 10 && mes > 9) {
			data1 = (ano + "-" + mes + "-" + "0" + dia);
		} else if (dia > 9 && mes < 10) {
			data1 = (ano + "-" + "0" + mes + "-" + dia);
		} else if (dia < 10 && mes < 10) {
			data1 = (ano + "-" + "0" + mes + "-" + "0" + dia);

		} else {
			data1 = (ano + "-" + mes + "-" + dia);
		}
		int diaatual = Calendar.getInstance().get(Calendar.DATE);
		int mesatual = (Calendar.getInstance().get(Calendar.MONTH) + 1);
		int anoatual = Calendar.getInstance().get(Calendar.YEAR);

		String data2;
		if (diaatual < 10 && mesatual > 9) {
			data2 = anoatual + "-" + mesatual + "-" + "0" + diaatual;
		} else if (mesatual < 10 && diaatual > 9) {
			data2 = anoatual + "-" + "0" + mesatual + "-" + diaatual;
		} else if (diaatual < 10 && mesatual < 10) {
			data2 = anoatual + "-" + "0" + mesatual + "-" + "0" + diaatual;
		} else {
			data2 = anoatual + "-" + mesatual + "-" + diaatual;
		}
		System.out.println("data2 " + data2);

		int idade = 0;
		System.out.println(idade);

		LocalDate d1 = null;
		LocalDate d2 = null;
		try {
			d1 = LocalDate.parse(data1, DateTimeFormatter.ISO_LOCAL_DATE);
			d2 = LocalDate.parse(data2, DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
		long diffDays = diff.toDays();
		idade = (int) (diffDays / 365);
		System.out.println("Teste " + (diffDays));

		return idade;

	}

}

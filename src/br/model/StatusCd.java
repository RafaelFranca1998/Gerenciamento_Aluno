package br.model;

public class StatusCd {
	int idstatuscd, iddisciplina, idcurso;
	String status;

	public int getIdStatuscd() {
		return idstatuscd;
	}

	public void setIdStatuscd(int statuscd) {
		this.idstatuscd = statuscd;
	}

	public int getIddisciplina() {
		return iddisciplina;
	}

	public void setIddisciplina(int iddisciplina) {
		this.iddisciplina = iddisciplina;
	}

	public int getIdcurso() {
		return idcurso;
	}

	public void setIdcurso(int idcurso) {
		this.idcurso = idcurso;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

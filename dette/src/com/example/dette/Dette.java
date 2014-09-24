package com.example.dette;

import java.util.Date;

public class Dette {
	private Date date;
	private Date heure;
	private Integer seinGauche;
	private Integer seinDroite;
	private String commencer;
	private Boolean pipi;
	private Boolean caca;
	
	public Dette() {
		
	}
	
	public Dette(Date date, Date heure, Integer seinGauche, Integer seinDroite, String commencer, Boolean pipi, Boolean caca) {
		super();
		this.date = date;
		this.heure = heure;
		this.seinGauche = seinGauche;
		this.seinDroite = seinDroite;
		this.commencer = commencer;
		this.pipi = pipi;
		this.caca = caca;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getSeinGauche() {
		return seinGauche;
	}
	public void setSeinGauche(Integer seinGauche) {
		this.seinGauche = seinGauche;
	}
	public Integer getSeinDroite() {
		return seinDroite;
	}
	public void setSeinDroite(Integer seinDroite) {
		this.seinDroite = seinDroite;
	}
	public String getPipi() {
		if (pipi != null && pipi) {
			return "X";
		} else {
			return "";
		}
	}
	public void setPipi(Boolean pipi) {
		this.pipi = pipi;
	}
	public String getCaca() {
		if (caca != null && caca) {
			return "X";
		} else {
			return "";
		}
	}
	public void setCaca(Boolean caca) {
		this.caca = caca;
	}

	public Date getHeure() {
		return heure;
	}

	public void setHeure(Date heure) {
		this.heure = heure;
	}

	public String getCommencer() {
		return commencer;
	}

	public void setCommencer(String commencer) {
		this.commencer = commencer;
	}
	
}

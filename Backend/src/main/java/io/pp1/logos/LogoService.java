package io.pp1.logos;

import java.util.List;

public class LogoService {
	
	
	private List<Logo> logo;
	
	
	public LogoService(List<Logo> logo) {
		this.logo= logo;
		
	}
	
	public List<Logo> getLogo() {
		return logo;
	}
	

}

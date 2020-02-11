package io.pp1.messages;

public class StringResponse {

    private String net_id;
    private String ticket_id;

    public StringResponse(String net_id, String ticket_id) { 
       this.net_id = net_id;
       this.ticket_id = ticket_id;
    }

	public String getNet_id() {
		return net_id;
	}

	public void setNet_id(String net_id) {
		this.net_id = net_id;
	}

	public String getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}
    
    

    // get/set omitted...
}
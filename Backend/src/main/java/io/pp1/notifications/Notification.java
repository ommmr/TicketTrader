package io.pp1.notifications;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Notification {

	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer note_id;
	private String receiver;
	private String note;
	
	
	public Notification(@NotNull Integer note_id, String receiver, String note) {
		super();
		this.note_id = note_id;
		this.receiver = receiver;
		this.note = note;
	}


	public Integer getNote_id() {
		return note_id;
	}


	public void setNote_id(Integer note_id) {
		this.note_id = note_id;
	}


	public String getReceiver() {
		return receiver;
	}


	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}
	
}

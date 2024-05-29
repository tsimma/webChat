package by.tsimmofei.webChat.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="Message")
public class Message {
	
	@Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int message_id;
	
	@Column(name="chat_id")
	private int chat_id;
	
	@Column(name="systemstatus")
	private int systemStatus;
	
	@ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName="id")
	private Person sender;
	
	@Column(name="text")
	private String text;
	
	@Column(name="send_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date send_at;

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public int getChat_id() {
		return chat_id;
	}

	public void setChat_id(int chat_id) {
		this.chat_id = chat_id;
	}

	public Person getSender() {
		return sender;
	}

	public void setSender(Person sender) {
		this.sender = sender;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getSend_at() {
		return send_at;
	}
	
	
	public int getSystemStatus() {
		return systemStatus;
	}

	public void setSystemStatus(int systemStatus) {
		this.systemStatus = systemStatus;
	}

	public String getFormatedSend_at() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    	return formatter.format(send_at);	
	}

	public void setSend_at(Date send_at) {
		this.send_at = send_at;
	}

	public Message(int chat_id, Person sender, String text, Date send_at, int systemStatus) {
		this.chat_id = chat_id;
		this.sender = sender;
		this.text = text;
		this.send_at = send_at;
		this.systemStatus = systemStatus;
	}
	
	public Message() {
	}

	
}

package org.sitenv.directvendortools.web.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "register_service")
public class DirectTransportTestingService {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REGISTER_SEQ")
	@SequenceGenerator(name = "REGISTER_SEQ", sequenceName = "register_service_id_seq")
	@Column(name = "id")
	Long id;

	@Column(name = "cehrtlabel")
	String cehrtLabel;

	@Column(name = "organizationname")
	String organizationName;

	@Column(name = "directemailaddress")
	String directEmailAddress;

	@Column(name = "pointofcontact")
	String pointOfContact;

	@Column(name = "pocfirstname")
	String pocFirstName;

	@Column(name = "poclastname")
	String pocLastName;

	@Column(name = "timezone")
	String timezone;

	@Column(name = "directtrustmembership")
	String directTrustMembership;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "availfromdate")
	Date availFromDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "availtodate")
	Date availToDate;

	@Column(name = "useremailaddress")
	String userEmailAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCehrtLabel() {
		return cehrtLabel;
	}

	public void setCehrtLabel(String cehrtLabel) {
		this.cehrtLabel = cehrtLabel;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getDirectEmailAddress() {
		return directEmailAddress;
	}

	public void setDirectEmailAddress(String directEmailAddress) {
		this.directEmailAddress = directEmailAddress;
	}

	public String getPointOfContact() {
		return pointOfContact;
	}

	public void setPointOfContact(String pointOfContact) {
		this.pointOfContact = pointOfContact;
	}

	public String getPocFirstName() {
		return pocFirstName;
	}

	public void setPocFirstName(String pocFirstName) {
		this.pocFirstName = pocFirstName;
	}

	public String getPocLastName() {
		return pocLastName;
	}

	public void setPocLastName(String pocLastName) {
		this.pocLastName = pocLastName;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getDirectTrustMembership() {
		return directTrustMembership;
	}

	public void setDirectTrustMembership(String directTrustMembership) {
		this.directTrustMembership = directTrustMembership;
	}

	public Date getAvailFromDate() {
		return availFromDate;
	}

	public void setAvailFromDate(Date availFromDate) {
		this.availFromDate = availFromDate;
	}

	public Date getAvailToDate() {
		return availToDate;
	}

	public void setAvailToDate(Date availToDate) {
		this.availToDate = availToDate;
	}

	public String getUserEmailAddress() {
		return userEmailAddress;
	}

	public void setUserEmailAddress(String userEmailAddress) {
		this.userEmailAddress = userEmailAddress;
	}

}

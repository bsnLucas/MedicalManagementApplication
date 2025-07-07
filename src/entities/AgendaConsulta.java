package entities;

import java.sql.Date;
import java.sql.Time;

public class AgendaConsulta {

	private int id;
	private Medico medico;
	private Paciente paciente;
	private Time horario;
	private Date data;
	private String status;

	public AgendaConsulta() {

	}

	public AgendaConsulta(int id, Medico medico, Paciente paciente, Time horario, Date data, String status) {

		this.id = id;
		this.medico = medico;
		this.paciente = paciente;
		this.horario = horario;
		this.data = data;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Time getHorario() {
		return horario;
	}

	public void setHorario(Time horario) {
		this.horario = horario;
	}

}

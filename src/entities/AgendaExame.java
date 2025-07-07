package entities;

import java.sql.Date;
import java.sql.Time;

public class AgendaExame {

	private int codigoAgendamento;
	private Exame exame;
	private Paciente paciente;
	private Medico medico;
	private Date data;
	private Time horario;
	private double preco;
	private String status;

	public AgendaExame() {
		
	}
	
	public AgendaExame(int codigoAgendamento, Exame exame, Paciente paciente, Medico medico, Date data, Time horario, double preco,
			String status) {

		this.codigoAgendamento = codigoAgendamento;
		this.exame = exame;
		this.paciente = paciente;
		this.medico = medico;
		this.data = data;
		this.horario = horario;
		this.preco = preco;
		this.status = status;
	}

	public Time getHorario() {
		return horario;
	}

	public void setHorario(Time horario) {
		this.horario = horario;
	}

	public int getCodigoAgendamento() {
		return codigoAgendamento;
	}

	public void setCodigoAgendamento(int codigoAgendamento) {
		this.codigoAgendamento = codigoAgendamento;
	}

	public Exame getExame() {
		return exame;
	}

	public void setExame(Exame exame) {
		this.exame = exame;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}


package entities;

import java.sql.Date;

public class Paciente {

	private String cpf;
	private String nome;
	private byte[] foto;
	private Date dataNascimento;
	private String sexo;
	private Endereco endereco;
	private String telefone;
	private String formaPagamento;

	public Paciente() {

	}

	public Paciente(String cpf, String nome, byte[] foto, Date dataNascimento, String sexo, Endereco endereco,
			String telefone, String formaPagamento) {

		this.cpf = cpf;
		this.nome = nome;
		this.foto = foto;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.endereco = endereco;
		this.telefone = telefone;
		this.formaPagamento = formaPagamento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	@Override
	public String toString() {
		return nome;
	}

}

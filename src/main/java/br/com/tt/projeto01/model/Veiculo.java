package br.com.tt.projeto01.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Veiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String marca;
	private String modelo;
	private String cor;
	/*
	 * O campo placa no banco de dados será configurado como NOT NULL sempre precisa
	 * ser informado
	 */
	@Column(nullable = false, name = "placa_veiculo")
	@NotBlank(message = "Campo obrigatório!")
	private String placa;

	public Veiculo() {
		System.out.println("------ Criando objeto veiculo vazio para JPA --------");
	}

	public Veiculo(String marca, String modelo, String cor, String placa) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.cor = cor;
		this.placa = placa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

}

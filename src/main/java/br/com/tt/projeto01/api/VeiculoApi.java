package br.com.tt.projeto01.api;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tt.projeto01.model.Veiculo;
import br.com.tt.projeto01.repository.VeiculoRepository;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoApi {

	@Autowired
	VeiculoRepository repo;

	@GetMapping
	public ResponseEntity<List<Veiculo>> veiculos() {
		List<Veiculo> veiculos = repo.findAll();
		ResponseEntity<List<Veiculo>> retorno = null;
		if (veiculos.isEmpty()) {
			retorno = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			retorno = new ResponseEntity<>(veiculos, HttpStatus.OK);
		}
		return retorno;

	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody @Valid Veiculo veiculo, BindingResult result) {
		ResponseEntity<?> retorno = null;
		if (result.hasErrors()) {
			List<FieldError> erros = result.getFieldErrors();
			retorno = new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);

		} else {
			repo.save(veiculo);
			retorno = new ResponseEntity<>(HttpStatus.CREATED);
		}
		return retorno;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		ResponseEntity<?> retorno = null;
		
		if (Objects.isNull(id)) {
			retorno = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			try { 
			repo.deleteById(id);
			retorno = new ResponseEntity<>(HttpStatus.ACCEPTED);
			} catch (Exception exception) {
				System.out.println(exception.getMessage());
				retorno = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		return retorno;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		ResponseEntity<?> retorno = null;
		
		Veiculo veiculo =  repo.findById(id).orElse(null);
		
		if (Objects.isNull(veiculo)) {
			retorno = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			retorno = new ResponseEntity<Veiculo>(veiculo, HttpStatus.OK);
		}
		return retorno;
	}
}

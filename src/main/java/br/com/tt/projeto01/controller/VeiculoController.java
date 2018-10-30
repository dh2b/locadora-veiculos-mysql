package br.com.tt.projeto01.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.tt.projeto01.model.Veiculo;
import br.com.tt.projeto01.repository.VeiculoRepository;

@Controller
@RequestMapping("/veiculo")
public class VeiculoController {

	private final String templates = "veiculo";

	@Autowired
	private VeiculoRepository repo;

	@GetMapping("/formulario")
	public String cadastro(Model model) {
		model.addAttribute("veiculo", new Veiculo());
		return templates + "/formulario";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Veiculo veiculo, Model model, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("veiculo", veiculo);
			return templates + "/formulario";
		}
		String placa = veiculo.getPlaca();

		placa = placa.toUpperCase();
		veiculo.setPlaca(placa);
		//veiculo.setPlaca(veiculo.getPlaca().toString());
		System.out.println("Marca: " + veiculo.getMarca());
		System.out.println("Modelo: " + veiculo.getModelo());
		System.out.println("Cor: " + veiculo.getCor());
		System.out.println("Placa:" + placa);

		repo.save(veiculo);
		model.addAttribute("sucesso", "Salvo com sucesso!");
		model.addAttribute("veiculo", new Veiculo());
		return templates + "/formulario";
	}
	
	@GetMapping
	public String veiculos(Model model) {
		
		
		List<Veiculo> carros = repo.findAll();
		model.addAttribute("listaCarros", carros);
		return templates + "/lista";
		
	}
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		System.out.println("--------------------- Teste do ID: " + id);
		Veiculo veiculo = repo.findById(id).orElse(new Veiculo());
		model.addAttribute("veiculo", veiculo);
		return templates + "/formulario";
	}
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id, Model model) {
		 repo.deleteById(id);
		 List<Veiculo> carros = repo.findAll();
		 model.addAttribute("listaCarros", carros);
		 return templates + "/lista";
	}
	
	
	@PostMapping ("/pesquisar")
	public String pesquisar (String pesquisa, Model model) {
		pesquisa = "%" + pesquisa + "%";
		List<Veiculo> veiculo = repo.findByPlacaLikeIgnoreCaseOrModeloLikeIgnoreCaseOrMarcaLikeIgnoreCaseOrCorLikeIgnoreCase(pesquisa, pesquisa, pesquisa, pesquisa);
		model.addAttribute("listaCarros", veiculo);
		return templates + "/lista";
	}
	
}

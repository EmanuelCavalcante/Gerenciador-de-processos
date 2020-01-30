package br.com.gerenciadordeprocessos.processo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gerenciadordeprocessos.processo.dto.ProcessoDto;
import br.com.gerenciadordeprocessos.processo.modelos.Processo;
import br.com.gerenciadordeprocessos.processo.service.ProcessoService;
import br.com.gerenciadordeprocessos.response.Response;
import br.com.gerenciadordeprocessos.util.StringResponse;

@RestController
@RequestMapping("/api-processo")
public class ProcessoController {
	private static final Logger log = LoggerFactory.getLogger(ProcessoController.class);

	@Autowired
	private ProcessoService processoService;

	@PostMapping("/processo")
	@Secured({ "USUARIO_TRIADOR" })
	public ResponseEntity<Response<ProcessoDto>> criarNovoProcesso(@Valid @RequestBody Processo processo,
			BindingResult result) {

		Response<ProcessoDto> response = new Response<ProcessoDto>();

		if (result.hasErrors()) {
			log.error("Erro validando lancamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		log.info("Criando um novo processo {}", processo.getNome());
		Processo processoSalvo = processoService.atualizarOuSalvar(processo);

		response.setData(new ProcessoDto(processoSalvo));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/processos")
	@Secured({ "USUARIO_TRIADOR" })
	public ResponseEntity<Response<List<ProcessoDto>>> listarProcessos(@RequestParam("idUsuario") Integer idUsuario) {

		Response<List<ProcessoDto>> response = new Response<List<ProcessoDto>>();

		List<Processo> processosDoBanco = processoService.listarPorUsuario(idUsuario);
		List<ProcessoDto> processos = new ArrayList<>();
		processosDoBanco.forEach(usuario -> processos.add(new ProcessoDto(usuario)));

		response.setData(processos);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/processos-sem-parecer")
	@Secured({ "USUARIO_TRIADOR" })
	public ResponseEntity<Response<List<ProcessoDto>>> listarProcessosSemParecer() {

		Response<List<ProcessoDto>> response = new Response<List<ProcessoDto>>();

		List<Processo> processosDoBanco = processoService.listarProcessoSemParecer();
		List<ProcessoDto> processos = new ArrayList<>();
		processosDoBanco.forEach(usuario -> processos.add(new ProcessoDto(usuario)));

		response.setData(processos);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/processo")
	@Secured({ "USUARIO_TRIADOR" })
	public ResponseEntity<Response<StringResponse>> deletarProcesso(@RequestParam("id") Integer idUsuario) {
		Response<StringResponse> response = new Response<StringResponse>();
		processoService.excluir(idUsuario);
		response.setData(new StringResponse("Processo excluido"));
		return ResponseEntity.ok(response);
	}

}
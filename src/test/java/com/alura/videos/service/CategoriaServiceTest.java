package com.alura.videos.service;

import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.model.Categoria;
import com.alura.videos.repository.CategoriaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("hml")
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private final Pageable paginas = PageRequest.of(0, 5);;

    @Test
    public void aoListarTodosRegistrosRetornaraOs3RegistrosInseridos(){
        List<Categoria> categorias = Arrays.asList(
                new Categoria(1L, "LIVRE", "BRANCO"),
                new Categoria(2L, "ACAO", "VERMELHO"),
                new Categoria(2L, "COMEDIA", "VERDE")
        );

        Page<Categoria> pageCategorias = new PageImpl<>(categorias, paginas, categorias.size());

        when(categoriaRepository.findAll(paginas)).thenReturn(pageCategorias);
        Long quantidadeCategorias = categoriaService.listarTodos(paginas).stream().count();
        Assertions.assertEquals(categorias.size(), quantidadeCategorias);
    }

    @Test
    public void aoInformarId1existenteRetornaCategoriaDeMesmoId(){
        Optional<Categoria> categoria = Optional.of(new Categoria(1L, "LIVRE", "BRANCO"));
        when(categoriaRepository.findById(1L)).thenReturn(categoria);

        Assertions.assertEquals(categoria.get().getTitulo(), categoriaService.retornaPorId(1L).getTitulo());

    }

    @Test
    public void aoBuscaPorUmIdInexistenteOServicoDeveRetornarNulo(){
        CategoriaDto categoriaDto = categoriaService.retornaPorId(1L);
        Assertions.assertNull(categoriaDto);
    }

    @Test
    public void aoInserirUmRegistroDeCategoriaValidoMetodoSalvarDoRepositorioDeCategoriaDeveSerChamado(){
        CategoriaDto categoriaDto = new CategoriaDto(1L, "LIVRE", "BRANCO");
        Categoria categoria = Categoria.convert(categoriaDto);

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        CategoriaDto categoriaPersistida = categoriaService.salvar(categoriaDto);
        Assertions.assertEquals(categoriaDto.getTitulo(), categoriaPersistida.getTitulo());
    }

    @Test
    public void aoPassarUmIdValidoParaExclusaoRegistroEExcluido(){
        Categoria categoria = new Categoria(1L, "LIVRE", "BRANCO");
        Optional<Categoria> optionalCategoria = Optional.ofNullable(categoria);

        when(categoriaRepository.findById(1L)).thenReturn(optionalCategoria);

        categoriaService.excluir(1L);
        verify(categoriaRepository).delete(categoria);
    }

    @Test
    public void categoriaNaoEExcluidoAoPassarUmIdDeCategoriaInexistente(){
        Assertions.assertFalse(categoriaService.excluir(1L));
    }

    @Test
    public void dadosDaCategoriaEAtualizadaQuandoUmIdExistenteForInformado(){
        Categoria categoria = new Categoria(1L, "ACAO", "VERMELHA");

        Optional<Categoria> categoriaOptional = Optional.ofNullable(categoria);


        Categoria categoriaAlterada = new Categoria(1L, "LIVRE", "");
        CategoriaDto categoriaAlteradaDto = CategoriaDto.converter(categoriaAlterada);

        when(categoriaRepository.findById(1L)).thenReturn(categoriaOptional);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(Categoria.convert(categoriaAlteradaDto));

        Assertions.assertNotNull(categoriaService.atualizar(categoriaAlteradaDto, 1L));
    }

    @Test
    public void dadosDeCategoriaNaoEAtualizadoQuandoUmIdInexistenteForInformado(){
        Optional<Categoria> categoriaOptional = Optional.empty();
        CategoriaDto categoriaDto = new CategoriaDto(1L, "LIVRE", "");

        when(categoriaRepository.findById(1L)).thenReturn(categoriaOptional);
        Assertions.assertNull(categoriaService.atualizar(categoriaDto, 1L));
    }
}
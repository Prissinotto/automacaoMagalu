package pages;

import java.util.List;

import org.openqa.selenium.WebElement;

import consts.Constants;
import uimaps.ResultadoBuscaMap;
import utils.Evidence;
import utils.ExcelManager;

public class ResultadoBuscaPage {

	private Evidence evidence;
	private ExcelManager excelManager;
	private ResultadoBuscaMap resultadoBuscaMap = new ResultadoBuscaMap();
	private BasePage basePage = new BasePage();

	public ResultadoBuscaPage(Evidence evidence, ExcelManager excelManager) {
		this.evidence = evidence;
		this.excelManager = excelManager;
	}

	/**
	 * Valida se o produto buscado esta sendo exibido na pagina
	 *
	 * @param texto produto a ser verificado
	 * @return String vazia caso o metodo tenha sido executado com sucesso, ou
	 *         preenchida com o erro caso contrario
	 * @throws Exception
	 */
	public String validaRetornoBusca(String texto) throws Exception {
		try {
			texto = excelManager.getValue(texto);
			evidence.takeScreenshot("valida_resultado_pesquisa", resultadoBuscaMap.tituloPage);
			List<WebElement> listElement = basePage.createWebElements(resultadoBuscaMap.produtoBusca);
			for (WebElement elemento : listElement) {
				if (elemento.getText().contains(texto))
					return Constants.SUCESSO;
			}
			throw new Exception("O produto não esta na busca realizada");
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/**
	 * Seleciona um produto na lista
	 *
	 * @param texto produto a ser selecionado
	 * @return String vazia caso o metodo tenha sido executado com sucesso, ou
	 *         preenchida com o erro caso contrario
	 * @throws Exception
	 */
	public String selecionaProduto(String texto) throws Exception {
		try {
			texto = excelManager.getValue(texto);
			basePage.scrollToElement(resultadoBuscaMap.produtoASelecionar(texto));
			evidence.takeScreenshot("resultado_buscas", resultadoBuscaMap.produtoASelecionar(texto));
			basePage.click(resultadoBuscaMap.produtoASelecionar(texto));
			evidence.takeScreenshot("adicionar_produto_sacola", resultadoBuscaMap.buttonAddSacola);
			basePage.click(resultadoBuscaMap.buttonAddSacola);
			evidence.takeScreenshot("continuar_adicionar_sacola");
			// fecharInvite();
			basePage.scrollToElement(resultadoBuscaMap.buttonContinuar);
		} catch (Exception e) {
			return e.getMessage();
		}
		return Constants.SUCESSO;
	}

	/**
	 * Fecha caixa de opinião "invite" do Magalu
	 *
	 * @return String vazia caso o metodo tenha sido executado com sucesso, ou
	 *         preenchida com o erro caso contrario
	 * @throws Exception
	 */
	/*
	 * public void fecharInvite() throws Exception { try {
	 * evidence.takeScreenshot("fecha_invite");
	 * resultadoBuscaMap.botaoFecharInvite.click(); } catch (Exception e) {
	 * System.err.println("Invite indisponivel"); } }
	 */
}
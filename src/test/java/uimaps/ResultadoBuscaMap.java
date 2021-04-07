package uimaps;

import org.openqa.selenium.By;

public class ResultadoBuscaMap {

	public By produtoBusca = By.xpath("//li[@class='product']//h3[@class='productTitle']");

	public By buttonAddSacola = By.xpath("//button[contains(@class,'buy-product')]");

	public By buttonContinuar = By.xpath("//button[@class='BasketContinue-button']");

	public By tituloPage = By.xpath("//h1//strong[text()='Playstation 4']");

	public By produtoASelecionar(String nomeProduto) {
		return By.xpath("(//li[@class='product']//a[contains(@title,'" + nomeProduto + "')])[1]");
	}
}
package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import consts.Constants;
import core.DriverSingleton;
import exceptions.ElementException;

public class BasePage {
	private Integer timeSeconds = Constants.TEMPO_ESPERA_MIN;
	public WebDriverWait wait;

	private void setTimeSeconds(Integer timeSeconds) {

		this.timeSeconds = timeSeconds;
	}

	/**
	 * Seleciona uma opcao em um drop down utilizando um valor visivel
	 * 
	 * @param visibleText Opcao desejada no select
	 * @throws ElementException
	 */
	public void select(By elemento, String visibleText) throws Exception {

		try {
			Select select = new Select(searchElement(elemento));
			select.selectByVisibleText(visibleText);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Metodo que verifica se o elemento esta presente no frame atual e inicia o
	 * processo de busca em profundidade caso ele nao esteja
	 * 
	 * @return WebElement encontrado nos frames ou null caso nao seja encontrado em
	 *         nenhum
	 * @throws Exception
	 */
	public WebElement searchElement(By elemento) throws Exception {

		WebElement webE = createWebElement(elemento);
		if (webE != null) {
			return webE;
		}
		DriverSingleton.moveToFrame(null);
		setTimeSeconds(Constants.TEMPO_ESPERA_FRAME);
		webE = searchFrames(elemento);
		setTimeSeconds(Constants.TEMPO_ESPERA_MIN);
		if (webE != null) {
			return webE;
		} else {
			throw new Exception("O elemento:" + elemento + " nao foi encontrado em nenhum frame");
		}
	}

	/**
	 * Busca em profundidade nos frames recursivo
	 * 
	 * @return WebElement encontrado nos frames ou null caso nao seja encontrado em
	 *         nenhum
	 * @throws Exception
	 */
	private WebElement searchFrames(By elemento) throws Exception {

		WebElement webE = createWebElement(elemento);
		if (webE != null) {
			return webE;
		}
		Integer frameIndex = 0;
		while (true) {
			try {
				DriverSingleton.moveToFrame(frameIndex);
			} catch (Exception e) {
				DriverSingleton.getDriver().switchTo().parentFrame();
				return null;
			}
			webE = searchFrames(elemento);
			if (webE != null) {
				return webE;
			}
			frameIndex++;
		}
	}

	/**
	 * Instancia um WebElement do Element
	 * 
	 * @return WebElement do proprio Element
	 * @throws Exception
	 */
	private WebElement createWebElement(By elemento) throws Exception {
		DriverSingleton.esperarPaginaCarregar();
		WebDriverWait wait = new WebDriverWait(DriverSingleton.getDriver(), timeSeconds);
		try {
			return wait.until(ExpectedConditions.presenceOfElementLocated(elemento));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Instancia e cria uma lits de WebElement do Element
	 * 
	 * @return WebElement do proprio Element
	 * @throws Exception
	 */
	public List<WebElement> createWebElements(By elemento) throws Exception {
		DriverSingleton.esperarPaginaCarregar();
		WebDriverWait wait = new WebDriverWait(DriverSingleton.getDriver(), timeSeconds);
		try {
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elemento));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Clica no elemento
	 * 
	 * @throws ElementException
	 */
	public void click(By element) throws Exception {

		try {
			searchElement(element).click();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Preenche o campo de texto desejado
	 * 
	 * @param text String desejada para preencher o campo
	 * @throws ElementException
	 */
	public void sendKeys(By element, String text) throws Exception {

		try {
			WebElement webE = searchElement(element);
			webE.sendKeys(text);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public void wait(By element) {
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}

	/**
	 * Rolar a barra ate o elemento ficar visivel na tela
	 * 
	 * @throws ElementException
	 */
	public void scrollToElement(By element) throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) DriverSingleton.getDriver();
			js.executeScript("arguments[0].scrollIntoView({block: 'end'});", searchElement(element));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Recupera o valor de um campo
	 * 
	 * @param isText Flag que controla se sera pegado o text ou o attribute value
	 * 
	 * @return Valor encontrado no campo
	 * @throws ElementException
	 */
	public String getText(By element, boolean isText) throws Exception {

		try {
			if (isText) {
				return searchElement(element).getText();
			} else {
				return searchElement(element).getAttribute("value");
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
package br.devops.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {

	private WebDriver acessarAplicacao() throws MalformedURLException {
//		WebDriver driver = new ChromeDriver();
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.19:4444/wd/hub"), cap);
		driver.navigate().to("http://192.168.0.19:8001/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();

		try {
			driver.findElement(By.id("addTodo")).click();

			driver.findElement(By.id("task")).sendKeys("Teste Selenium");

			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");

			driver.findElement(By.id("saveButton")).click();

			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Success!", mensagem);
		} finally {
			driver.quit();
		}

	}

	@Test
	public void naoDeveSalvarSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();

		try {
			driver.findElement(By.id("addTodo")).click();

			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");

			driver.findElement(By.id("saveButton")).click();

			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Fill the task description", mensagem);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();

		try {
			driver.findElement(By.id("addTodo")).click();

			driver.findElement(By.id("task")).sendKeys("Teste Selenium");

			driver.findElement(By.id("saveButton")).click();

			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Fill the due date", mensagem);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void deveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();

		try {
			driver.findElement(By.id("addTodo")).click();

			driver.findElement(By.id("task")).sendKeys("Teste Selenium");

			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");

			driver.findElement(By.id("saveButton")).click();

			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Due date must not be in past", mensagem);
		} finally {
			driver.quit();
		}

	}
	
	@Test
	public void deveRemoverTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		
		try {
			//Inserir uma tarefa
			driver.findElement(By.id("addTodo")).click();			
			driver.findElement(By.id("task")).sendKeys("Teste Selenium");			
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");			
			driver.findElement(By.id("saveButton")).click();			
			String mensagem = driver.findElement(By.id("message")).getText();			
			Assert.assertEquals("Success!", mensagem);
			
			//remover tarefa
			driver.findElement(By.xpath("//a[.='Remove']")).click();
			mensagem = driver.findElement(By.id("message")).getText();			
			Assert.assertEquals("Success!", mensagem);
			
		} finally {
			driver.quit();
		}
		
	}

}

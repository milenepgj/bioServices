package com.bioinfo.bioServices;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhantomKeggApplicationTests {

	@Before

	public void setUp() throws Exception {

	}

/*
	@Test

*/
	public void testSearchInGooglePage() {

		System.setProperty("phantomjs.binary.path", "phantomjs");

		WebDriver driver  = new PhantomJSDriver();

//           Digo qual url para acessar

		driver.get("https://www.genome.jp/kegg/tool/map_module1.html");

//           Agora vamos buscar o elemento na página

		WebElement inputTextGoogle = driver.findElement(By.name("unclassified"));

		inputTextGoogle.sendKeys("ko:K01803");

		/*           faz um submit na página
chromedriver
		 *           poderia buscar o botão search e fazer o submit tb.

		 */

		inputTextGoogle.submit();

		assertTrue(true);

	}

}

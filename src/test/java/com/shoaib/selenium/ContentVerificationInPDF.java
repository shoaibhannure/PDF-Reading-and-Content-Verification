package com.shoaib.selenium;

/*
 * 
 * 
 * @Author:- SHOAIB HANNURE
 * 
 * 
*/
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ContentVerificationInPDF {

	WebDriver driver = null;

	@BeforeTest
	public void SetUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@Test
	public void VerifyContentInPDF() throws IOException {

		String url = "http://www.pdf995.com/samples/pdf.pdf";
		driver.get(url);

		String pdfContent = readPdfContent(url);
		Assert.assertTrue(pdfContent.contains("The Pdf995 Suite offers the following features"));

		// String pdfContent=readPdfContent(pdfUrl);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

	// Method to read no of pages in the PDF
	public static int getPageCount(PDDocument doc) {
		// get the total number of pages in the pdf document
		int pageCount = doc.getNumberOfPages();
		return pageCount;
	}

	//Method to read Content in PDF
	public static String readPdfContent(String url) throws IOException {

		URL pdfUrl = new URL(url);
		InputStream in = pdfUrl.openStream();
		BufferedInputStream bf = new BufferedInputStream(in);
		PDDocument doc = PDDocument.load(bf);

		int numberOfPages = getPageCount(doc);
		System.out.println("Total no of pages in the doc are  " + numberOfPages);

		String content = new PDFTextStripper().getText(doc);
		doc.close();
		return content;
	}
}

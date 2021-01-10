package org.example.selenium.pageObjectModel.base;

public class Locator {
	private String element;

	private int waitSec;

	/**
	 * create a enum variable for By
	 * 
	 * @author Young
	 *
	 */
	public enum ByType {
		xpath, id, linkText, name, className, cssSelector, partialLinkText, tagName
	}

	private ByType byType;

	public Locator() {

	}

	/**
	 * defaut Locator ,use Xpath
	 * 
	 * @author Young
	 * @param element
	 */
	public Locator(String element) { // ��finelement ����sec�˴��� �������õ���
										// waitsecĬ��ֵΪ3
		this.element = element;
		this.waitSec = 3;
		this.byType = ByType.xpath;
	}

	// Ĭ��type��path ͬ��
	public Locator(String element, int waitSec) { // ��finelement �������õ���
													// waitsec
		this.waitSec = waitSec;
		this.element = element;
		this.byType = ByType.xpath;
	}

	public Locator(String element, int waitSec, ByType byType) { // ����xmlʱ��ֵ�õ���
																	// ����һ���µ�locator
		this.waitSec = waitSec;
		this.element = element;
		this.byType = byType;
	}

	public String getElement() {
		return element;
	}

	public int getWaitSec() { // ��finelement �������õ��� waitsec
		return waitSec;
	}

	public ByType getBy() { // ��finelement �������õ���
		return byType;
	}

	public void setBy(ByType byType) {
		this.byType = byType;
	}

}

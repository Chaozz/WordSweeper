package xml;

import junit.framework.TestCase;

public class TestMessage extends TestCase {

	@Override
	protected void setUp() {
		assertTrue (Message.configure("skeleton.xsd"));
	}
	
	public void testMessage() {
		try {
			new Message("BAD");
			fail ("Must throw invalid exception for non-xml");
		} catch (Exception e) {
			// success
		}
		
		// valid XML but not valid skeleton.XSD
		try {
			new Message("<response nothing='help'/>");
			fail ("Must throw invalid exception for non-xml");
		} catch (Exception e) {
			// success
		}
	}

	public void testSuccess() {
		String xmlString = Message.responseHeader("123") + "<connectResponse id='SD'/></response>";
		Message m = new Message(xmlString);
		assertTrue (m.success());	
		
		xmlString = Message.responseHeader("123", "Failure") + "<connectResponse id='SD'/></response>";
		m = new Message(xmlString);
		assertFalse (m.success());
	}

	public void testReason() {
		String xmlString = Message.responseHeader("123", "Failure") + "<connectResponse id='SD'/></response>";
		Message m = new Message(xmlString);
		assertEquals ("Failure", m.reason());
	}

	public void testId() {
		String xmlString = Message.responseHeader("123", "Failure") + "<connectResponse id='SD'/></response>";
		Message m = new Message(xmlString);
		assertEquals ("123", m.id());
	}

	public void testRequestHeader() {
		String xmlString = Message.requestHeader() + "<connectRequest/></request>";
		new Message(xmlString);
		// success just getting here...
	}

}

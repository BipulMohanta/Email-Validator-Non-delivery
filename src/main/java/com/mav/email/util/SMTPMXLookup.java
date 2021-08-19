package com.mav.email.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mav.email.constants.MailConstants;
import com.mav.email.exception.GenericSMTPException;

/**
 * 
 * @author bipul.mohanta
 *
 *         Check for mail validity:-
 * 
 *         211 System status, or system help reply. 214 Help message. 220 Domain
 *         service ready. Ready to start TLS. 221 Domain service closing
 *         transmission channel. 250 OK, queuing for node node started.
 *         Requested mail action okay,completed. 251 OK, no messages waiting for
 *         node node. User not local, will forward to forwardpath. 252 OK,
 *         pending messages for node node started. Cannot VRFY user (e.g., info
 *         is not local), but will take message for this user and attempt
 *         delivery. 253 OK, messages pending messages for node node started.
 *         354 Start mail input; end with .. 355 Octet-offset is the transaction
 *         offset. 421 Domain service not available, closing transmission
 *         channel. 432 A password transition is needed. 450 Requested mail
 *         action not taken: mailbox unavailable. (ex. mailbox busy) 451
 *         Requested action aborted: local error in processing. Unable to
 *         process ATRN request now 452 Requested action not taken: insufficient
 *         system storage. 453 You have no mail. 454 TLS not available due to
 *         temporary reason. Encryption required for requested authentication
 *         mechanism. 458 Unable to queue messages for node node. 459 Node node
 *         not allowed: reason. 500 Command not recognized: command. Syntax
 *         error. 501 Syntax error, no parameters allowed. 502 Command not
 *         implemented. 503 Bad sequence of commands. 504 Command parameter not
 *         implemented. 521 Machine does not accept mail. 530 Must issue a
 *         STARTTLS command first. Encryption required for requested
 *         authentication mechanism. 534 Authentication mechanism is too weak.
 *         538 Encryption required for requested authentication mechanism. 550
 *         Requested action not taken: mailbox unavailable. 551 User not local;
 *         please try forwardpath. 552 Requested mail action aborted: exceeded
 *         storage allocation. 553 Requested action not taken:mailbox name not
 *         allowed. 554 Transaction failed.
 * 
 */
public class SMTPMXLookup {

	private final static Logger logger = LogManager.getLogger();

	/**
	 * @author bipul.mohanta
	 * @param emailList
	 * @return List<String> : List of all Invalid email address
	 */
	public static Map<String, String> invalidEmailAddress(List<String> emailList) {

		Map<String, String> returnMap = new HashMap<>();

		emailList = emailList.stream().distinct().collect(Collectors.toList());
		
		Map<String, List<String>> emailToDomainNameMap = new HashMap<String, List<String>>();
		Map<String, List<String>> validDomainnameMXMap = new HashMap<String, List<String>>();
		List<String> invalidDomainNameList = new ArrayList<String>();
		
		initializeAllMailAsValid(returnMap,emailList);

		for (String emailAddress : emailList) {
			if (MailUtil.syntaxValidateMail(emailAddress)) {
				String domainName = emailAddress.split("@")[1];
				if (emailToDomainNameMap.containsKey(domainName)) {
					emailToDomainNameMap.get(domainName).add(emailAddress);
					if (invalidDomainNameList.contains(domainName)) {
						returnMap.put(emailAddress, "Invalid Domain Name");
					}
				} else {
					emailToDomainNameMap.put(domainName, new ArrayList<String>());
					emailToDomainNameMap.get(domainName).add(emailAddress);
					try {
						List<String> MXresult = getSortedMXRecord(domainName);
						if (CollectionUtils.isNotEmpty(MXresult)) {
							validDomainnameMXMap.put(domainName, new ArrayList<String>());
							validDomainnameMXMap.get(domainName).addAll(MXresult);
						} else {
							invalidDomainNameList.add(domainName);
							returnMap.put(emailAddress, "Invalid Domain Name");
						}
					} catch (NamingException e) {
						invalidDomainNameList.add(domainName);
						returnMap.put(emailAddress, "Invalid Domain Name");
						logger.error(e.getMessage() + " email address:" + emailAddress);
					} catch (Exception e) {
						invalidDomainNameList.add(domainName);
						returnMap.put(emailAddress, "Invalid Domain Name");
						logger.error("Exception ocured while getting MX record for email address " + emailAddress);
					}
				}
			} else {
				returnMap.put(emailAddress, "Invalid email Syntax");
			}
		}

		for (String emailHostname : validDomainnameMXMap.keySet()) {

			Map<String, Boolean> mxValidationResultMap = validateEmailAddressMXRecord(
					emailToDomainNameMap.get(emailHostname), validDomainnameMXMap.get(emailHostname));
			for (String key : mxValidationResultMap.keySet()) {
				if (!mxValidationResultMap.get(key)) {
					returnMap.put(key, "Email Address not present in the specified Domain Name "
							+ "or mail cannot be sent to the specified domain name");
				}
			}

		}

		return returnMap;

	}

	private static void initializeAllMailAsValid(Map<String, String> returnMap, List<String> emailList) {
		emailList.stream().forEach(address->returnMap.put(address, "valid"));
		
	}

	/**
	 * @author bipul.mohanta
	 * @param emailList
	 * @return Map<String, Object> :- Map of known invalid addresses and list of
	 *         FUTURES for email addresses whose validity will be fetched in the
	 *         later stage of the code.
	 */
	public static Map<String, Object> invalidEmailAddressInFuture(List<String> emailList) {

		List<String> invalidEmailAddress = new ArrayList<String>();
		emailList = emailList.stream().distinct().collect(Collectors.toList());

		// emailToDomainNameMap:- This will contain the mapping of each email to it's
		// respective domain name.
		// validDomainnameMXMap:- This will contain list of all the MX record
		// corresponding to its domain name.
		// invalidDomainNameList:- Contains the list of invalid domain name.

		Map<String, List<String>> emailToDomainNameMap = new HashMap<String, List<String>>();
		Map<String, List<String>> validDomainnameMXMap = new HashMap<String, List<String>>();
		List<String> invalidDomainNameList = new ArrayList<String>();

		for (String emailAddress : emailList) {
			String domainName = emailAddress.split("@")[1];

			if (emailToDomainNameMap.containsKey(domainName)) {
				emailToDomainNameMap.get(domainName).add(emailAddress);
				if (invalidDomainNameList.contains(domainName)) {
					invalidEmailAddress.add(emailAddress);
				}
			} else {
				emailToDomainNameMap.put(domainName, new ArrayList<String>());
				emailToDomainNameMap.get(domainName).add(emailAddress);
				try {
					List<String> MXresult = getSortedMXRecord(domainName);
					if (CollectionUtils.isNotEmpty(MXresult)) {
						validDomainnameMXMap.put(domainName, new ArrayList<String>());
						validDomainnameMXMap.get(domainName).addAll(MXresult);
					} else {
						invalidDomainNameList.add(domainName);
						invalidEmailAddress.add(emailAddress);
					}
				} catch (NamingException e) {
					invalidDomainNameList.add(domainName);
					invalidEmailAddress.add(emailAddress);
					logger.error(e.getMessage() + " email address:" + emailAddress);
				} catch (Exception e) {
					invalidDomainNameList.add(domainName);
					invalidEmailAddress.add(emailAddress);
					logger.error("Exception ocured while getting MX record for email address " + emailAddress);
				}
			}
		}

		List<Future<Map<String, Boolean>>> futureEmailMaps = new ArrayList<Future<Map<String, Boolean>>>();
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for (String emailHostname : validDomainnameMXMap.keySet()) {
			Callable<Map<String, Boolean>> task = new Callable<Map<String, Boolean>>() {

				@Override
				public Map<String, Boolean> call() throws Exception {
					return validateEmailAddressMXRecord(emailToDomainNameMap.get(emailHostname),
							validDomainnameMXMap.get(emailHostname));
				}
			};
			futureEmailMaps.add(executorService.submit(task));
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("INVALID_EMAIL_ADDRESSES", futureEmailMaps);
		returnMap.put("INVALID_EMAIL_ADDRESSES_FUTURE", invalidEmailAddress);

		return returnMap;

	}

	/**
	 * @author bipul.mohanta
	 * @param domainName
	 * @return List of MX record for the supplied hostName
	 * @throws NamingException
	 * 
	 *                         MX records:- A DNS 'mail exchange' (MX) record
	 *                         directs email to a mail server. The MX record
	 *                         indicates how email messages should be routed in
	 *                         accordance with the Simple Mail Transfer Protocol
	 *                         (SMTP, the standard protocol for all email). Like
	 *                         CNAME records, an MX record must always point to
	 *                         another domain.
	 */
	public static List<String> getSortedMXRecord(String domainName) throws NamingException {

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
		DirContext ictx = new InitialDirContext(env);
		Attributes attrs = ictx.getAttributes(domainName, new String[] { "MX" });
		Attribute attr = attrs.get("MX");

		if ((attr == null) || (attr.size() == 0)) {
			attrs = ictx.getAttributes(domainName, new String[] { "A" });
			attr = attrs.get("A");
			if (attr == null)
				throw new NamingException("No MX record or machine found for hostname '" + domainName + "'");
		}

		List<String> res = new ArrayList<String>();
		NamingEnumeration<?> en = attr.getAll();

		while (en.hasMore()) {
			String mailhost;
			String x = (String) en.next();
			String f[] = x.split(" ");
			if (f.length == 1) {
				mailhost = f[0];
			} else if (f[1].endsWith(".")) {
				mailhost = f[1].substring(0, (f[1].length() - 1));
			} else {
				mailhost = f[1];
			}
			res.add(mailhost);
		}
		return res;
	}

	public static List<String> getMX(String domainName) throws NamingException {

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
		DirContext ictx = new InitialDirContext(env);
		Attributes attrs = ictx.getAttributes(domainName, new String[] { "MX" });
		Attribute attr = attrs.get("MX");

		if ((attr == null) || (attr.size() == 0)) {
			attrs = ictx.getAttributes(domainName, new String[] { "A" });
			attr = attrs.get("A");
			if (attr == null)
				throw new NamingException("No MX record or machine found for hostname '" + domainName + "'");
		}

		List<String> res = new ArrayList<String>();
		NamingEnumeration<?> en = attr.getAll();

		while (en.hasMore()) {

			res.add((String) en.next());
		}
		return res;
	}

	/**
	 * 
	 * @param emailAddresses
	 * @param MXList
	 * @return Map<String,Boolean> -> String:emailAddress Boolean: isValid Validate
	 *         each email addresses from the list of emailAddresses with each MX
	 *         record as given in MXList. If the email Addresses is valid in any one
	 *         of the MX record then it is assumed to be valid as a whole.
	 * 
	 *         Initially marking all the Email Address as invalid. Then after each
	 *         RCPT command updating returnMap with result(Valid or not). Once a
	 *         address is marked as valid it is not checked again. Once all the
	 *         address is marked as valid before iterating through all the MX record
	 *         then the rest of the MX record are not checked.
	 * 
	 */
	public static Map<String, Boolean> validateEmailAddressMXRecord(List<String> emailAddresses, List<String> MXList) {
		HashMap<String, Boolean> returnMap = new HashMap<String, Boolean>();
		markAllEMailAsInvalid(returnMap, emailAddresses);
		int invalidCounter = emailAddresses.size();
		if (CollectionUtils.isNotEmpty(MXList)) {
			for (int mx = 0; mx < MXList.size() && invalidCounter > 0; mx++) {
				Socket skt = null;
				BufferedReader rdr = null;
				BufferedWriter wtr = null;

				try {
					int res;

					skt = new Socket((String) MXList.get(mx), 25);
					rdr = new BufferedReader(new InputStreamReader(skt.getInputStream()));
					wtr = new BufferedWriter(new OutputStreamWriter(skt.getOutputStream()));
					res = hear(rdr);
					if (res != 220) {
						throw new GenericSMTPException("Invalid header");
					}
					say(wtr, "EHLO ultria.com");
					res = hear(rdr);
					if (res >= 500) {
						throw new GenericSMTPException("Not ESMTP server");
					}
					say(wtr, "MAIL FROM: <dummy.dummy@ultria.com>");
					res = hear(rdr);
					if (res >= 500) {
						throw new GenericSMTPException("Sender rejected by recipient Server");
					}

					for (String emailAddress : emailAddresses) {
						if (returnMap.get(emailAddress) == false) {
							say(wtr, "RCPT TO: <" + emailAddress + ">");
							res = hear(rdr);
							if (res < 500) {
								returnMap.put(emailAddress, true);
								invalidCounter--;
							}

						}
					}

					say(wtr, "RSET");
					hear(rdr);
					say(wtr, "QUIT");
					hear(rdr);

					rdr.close();
					wtr.close();
					skt.close();

				} catch (GenericSMTPException ex) {

					logger.error(MXList.get(mx) + " " + ex.getMessage());
				} catch (IOException e) {

					logger.error("Unable to connect to MX server " + MXList.get(mx), e);
				} catch (Exception e) {

					logger.error(" Exception occured while accessing/reading/writing to :" + MXList.get(mx), e);
				} finally {

					try {
						if (skt != null) {
							skt.close();
						}
						if (rdr != null) {
							rdr.close();
						}
						if (wtr != null) {
							wtr.close();
						}
					} catch (IOException e) {
						logger.error(e);
					}

				}
			}
		}
		return returnMap;
	}

	private static void markAllEMailAsInvalid(HashMap<String, Boolean> returnMap, List<String> emailAddress) {
		for (String email : emailAddress) {
			returnMap.put(email, false);
		}

	}

	private static int hear(BufferedReader in) throws IOException {
		String line = null;
		int res = 0;

		while ((line = in.readLine()) != null) {
			String pfx = line.substring(0, 3);
			try {
				res = Integer.parseInt(pfx);
			} catch (Exception ex) {
				res = -1;
			}
			if (line.charAt(3) != '-')
				break;
		}

		return res;
	}

	private static void say(BufferedWriter wr, String text) throws IOException {
		wr.write(text + "\r\n");
		wr.flush();
		return;
	}
}

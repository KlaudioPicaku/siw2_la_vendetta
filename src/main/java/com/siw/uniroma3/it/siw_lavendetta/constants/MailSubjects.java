package com.siw.uniroma3.it.siw_lavendetta.constants;

import java.util.HashMap;
import java.util.Map;

public class MailSubjects {
    public static final String STANDARD_ACTIVATE_ACCOUNT_SUBJECT = "[PICTURE PERFECT] ACCOUNT ACTIVATION";

    public static final Map<String, String> PATH_TO_MAIL_HTML_TEMPLATE;

    static {
        PATH_TO_MAIL_HTML_TEMPLATE = new HashMap<>();
        PATH_TO_MAIL_HTML_TEMPLATE.put(STANDARD_ACTIVATE_ACCOUNT_SUBJECT, "/templates/mails/activation_email.html");
    }
}
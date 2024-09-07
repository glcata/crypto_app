// * @author Catalin Glavan
package crypto.helper;

import crypto.captcha.captcha_form;

import javax.swing.*;
import java.util.ResourceBundle;

public class validate {

    private boolean val = false;
    private String tooltip;

    public validate(JTextField field, String value, captcha_form captcha, ResourceBundle bundle) {

        switch (field.getName()) {
            case "name_id": {
                boolean check = (!field.getText().isEmpty() && !value.trim().equals("") && value.length() >= 5 && value.length() <= 15);
                checkValAndBundle(bundle, check, "key_name_check", "key_name_check_confirm");
            }
            break;
            case "email_id": {
                boolean check = (!field.getText().isEmpty() && !value.trim().equals("") && isValidEmailAddress(value));
                checkValAndBundle(bundle, check, "key_email_check", "key_email_check_confirm");
            }
            break;
            case "re_email_id": {
                boolean check = (!field.getText().isEmpty() && !value.trim().equals("") && isValidEmailAddress(value));
                checkValAndBundle(bundle, check, "key_re_email_check", "key_re_email_check_confirm");
            }
            break;
            case "captcha_id": {
                boolean check = (!field.getText().isEmpty() && !value.trim().equals("") && field.getText().equals(captcha.getCaptcha()));
                checkValAndBundle(bundle, check, "key_captcha_check", "key_captcha_check_confirm");
            }
            break;
            case "common_name_id": {
                boolean check = (!field.getText().isEmpty() && !value.trim().equals("") && value.length() >= 5);
                checkValAndBundle(bundle, check, "key_common_name_check", "key_common_name_confirm");
            }
            break;
            case "country_id": {
                boolean check = (!field.getText().isEmpty() && !value.trim().equals("") && value.length() == 2);
                checkValAndBundle(bundle, check, "key_country_check", "key_country_confirm");
            }
            break;
            case "state_id": {
                boolean check = (!field.getText().isEmpty() && !value.trim().equals("") && value.length() >= 3);
                checkValAndBundle(bundle, check, "key_state_check", "key_state_confirm");
            }
            break;
            case "location_id": {
                boolean check = (!field.getText().isEmpty() && !value.trim().equals("") && value.length() >= 3);
                checkValAndBundle(bundle, check, "key_location_check", "key_location_confirm");
            }
            break;
            case "org_id": {
                boolean check = (!field.getText().isEmpty() && !value.trim().equals("") && value.length() >= 2);
                checkValAndBundle(bundle, check, "key_org_check", "key_org_confirm");
            }
            break;
            case "org_unit_id": {
                boolean check = (!field.getText().isEmpty() && !value.trim().equals("") && value.length() >= 3);
                checkValAndBundle(bundle, check, "key_org_unit_check", "key_org_unit_confirm");
            }
            break;
        }
    }

    private void checkValAndBundle(ResourceBundle bundle, boolean check, String bundleString, String bundleStringConfirm) {

        if (check) {
            setVal(true);
            if (bundle != null) {
                setTooltip(bundle.getString(bundleStringConfirm));
            }
        } else {
            setVal(false);
            if (bundle != null) {
                setTooltip(bundle.getString(bundleString));
            }
        }
    }

    private static boolean isValidEmailAddress(String email) {

        try {
            String[] regex = {"ymail.ro", "yahoo.com", "yahoo.ro", "gmail.com", "hotmail.com", "outlook.com", "s.utm.ro", "prof.utm.ro"};
            String[] splitString = email.split("@");
            for (String loop : regex) {
                if (splitString[1].equals(loop)) {
                    return true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    public boolean getVal() {
        return val;
    }

    private void setVal(boolean val) {
        this.val = val;
    }

    public String getTooltip() {
        return tooltip;
    }

    private void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }
}
